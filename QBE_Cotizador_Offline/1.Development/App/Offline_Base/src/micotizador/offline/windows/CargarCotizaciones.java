package micotizador.offline.windows;


import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.Principal;
import micotizador.offline.filestructure.*;
import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.allcolor.yahp.converter.CYaHPConverter;
//import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

/**
 * Created by Veronica Zhagui (Gorrdita) on 24/07/2015.
 */
public class CargarCotizaciones extends JDialog {
    private JPanel pnlPrincipal;
    private JButton btn_EnviarWeb;
    private JButton btn_EnviarArchivo;
    private static TransporteCotizaciones CurrentTransporteCotizaciones;
    private List<CotizacionAgricola> cotizacionesCompletas = new ArrayList<CotizacionAgricola>();
    private List<CotizacionAgricola> cotizacionIncompletas = new ArrayList<CotizacionAgricola>();
    private List <ListadoProcesado> procesadosCompletos = new ArrayList<ListadoProcesado>();
    private String usuario = "";
    private String ProductoCotizacion;
    private TransporteCotizaciones tcc;
    //private String ruta = "";

    private String CI = "";

    public CargarCotizaciones(String ObjetoProducto) {
        super();
        btn_EnviarWeb.setVisible(false);
        //Image icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        //this.setIconImage(icon);
        usuario = Main.getCurrentUser().getName();
        CI = Main.getCurrentUser().getCIUser() == null ? "" : Main.getCurrentUser().getCIUser();
        ProductoCotizacion = ObjetoProducto;
        //ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\cotizaciones\\CotizacionAgricola_"+usuario+CI+".config";
        setContentPane(pnlPrincipal);
        pack();
        btn_EnviarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Boolean realizado = CargarArchivo();
                    if (!realizado) return;
                    //JOptionPane.showMessageDialog(null, "Se ha guardado correctamente");
                    //System.exit(0);
                    Login login = new Login();
                    login.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Se ha producido un error: " + ex.getMessage());
                }
            }
        });

        btn_EnviarWeb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CargarCotizacionesGuardadas();
                if (cotizacionIncompletas.size() > 0) {
                    int response = JOptionPane.showConfirmDialog(null, "Advertencia: Existen (" + cotizacionIncompletas.size() + ") cotizaciones incompletas que serán borradas. Esta seguro de enviar?");
                    if (response != JOptionPane.OK_OPTION) return;
                }
                try {
                    JOptionPane.showMessageDialog(null, "Esto puede tardar un momento\n\n......Espere mientras el proceso continua\n\n", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    HttpResponse response = CargarWeb();
                    if (response != null) {
                        Integer respuesta = response.getStatusLine().getStatusCode();
                        String resp = response.getStatusLine().getReasonPhrase();
                        //Integer respuesta = 200;
                        if (respuesta == 200) {
                            int CotizacionesId = 0;
                            if (procesadosCompletos!=null){
                                for (ListadoProcesado proces : procesadosCompletos){
                                    if (!proces.getCotizacionID().equals("0"))
                                    {
                                        CotizacionesId++;
                                        break;
                                    }
                                }
                            }
                            if (CotizacionesId !=0)
                            {
                                JOptionPane.showMessageDialog(null, "Cotizaciones Cargadas correctamente...\n\nEsperar mientras se generan los reportes\n\n", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                                //TODO_
                                ///Despues de cargadas las cotizaciones se genera los repostes
                                //List<ResultadoHtml> lstHtml = RecibirPlantilla();
                                List<ResultadoHtml> lstHtml =RecibirPlantillaCondicionesParticularesBlob();
                                for (ResultadoHtml htmlRes : lstHtml) {
                                    byte[] resultado = GenerarPDF(htmlRes.getHtml_());
                                    GrabarReporte(resultado, htmlRes.getCotizacionId_());
                                }
                                //Una vez guardados los reportes en una ubicacion dentro del programa se abre un filechooser para seleccionar
                                // la ruta donde se guardaran los reportes
                                if (lstHtml.size() > 0) {
                                    //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
                                    JFileChooser fc = new JFileChooser();

                                    String StartFolder = Main.get_StartFolder();

                                    String rutaCarpeta = new File( StartFolder + "Reportes" + File.separator).getAbsolutePath();
                                    final File rutaDir = new File(rutaCarpeta);
                                    File[] ficheros = rutaDir.listFiles();
                                    fc.setSelectedFiles(ficheros);
                                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF", "pdf");
                                    fc.setFileFilter(filter);
                                    fc.setDialogTitle("Seleccionar una ruta difetente para grabar los reportes");
                                    Integer seleccion = fc.showSaveDialog(pnlPrincipal);
                                    String RutaGuardada = "";
                                    if (seleccion.equals(JFileChooser.APPROVE_OPTION)) {
                                        File f = fc.getSelectedFile();
                                        String x = f.getParent();
                                        try {
                                            if (rutaCarpeta.equals(x)) {
                                                x = System.getProperty("user.home") + "\\reportes";
                                                File existe = new File(x);
                                                if (!existe.exists())
                                                    existe.mkdir();
                                            }
                                            RutaGuardada = x;
                                            for (File reportOld : ficheros) {
                                                File reporNew = new File(x + "\\" + reportOld.getName());
                                                if (!reporNew.exists()) {
                                                    //Se hace una copia del archivo en la nueva ruta
                                                    FileChannel in = (new FileInputStream(reportOld)).getChannel();
                                                    FileChannel out = (new FileOutputStream(reporNew)).getChannel();
                                                    in.transferTo(0, reportOld.length(), out);
                                                    in.close();
                                                    out.close();
                                                }
                                            }
                                        } catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null, "Se ha producido un error: " + ex.getMessage());
                                        }
                                        JOptionPane.showMessageDialog(null, "Reportes guardados correctamente en la ruta:" + RutaGuardada);
                                    }
                                    LimpiarVariables();
                                }

                           }
                           else {
                                JOptionPane.showMessageDialog(null, "Cotizaciones cargadas correctamente, el archivo cargado ya fue procesado anteriormente", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                                LimpiarVariables();
                           }

                        } else {
                            JOptionPane.showMessageDialog(null, "No se ha podido cargar las cotizaciones");
                            LimpiarVariables();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido cargar las cotizaciones");
                        LimpiarVariables();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Se ha producido un error: " + ex.getMessage());
                    LimpiarVariables();
                }
            }
        });


    }
    public void Cerrar(){
        this.setVisible(false);
    }
    private void CargarCotizacionesGuardadas() {
        Gson g = new Gson();
        //final File StructureFile = new File(ruta);
        String StartFolder = Main.get_StartFolder();
        final File StructureFile = new File(StartFolder + "cotizaciones" + File.separator + ProductoCotizacion + usuario + CI + ".config");
        Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
        try {
            String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
            //Cargar las cotizaciones en Current para enviar unicamente las que se encuentran en estado 1;
            //completa(1) y para modificacion (0)
            String StructureDecypted = AES_Helper.decrypt(StructureContents);
            CurrentTransporteCotizaciones = g.fromJson(StructureDecypted, TransporteCotizaciones.class);
            for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()) {
                if (cotizacion.getEstado() == 1)
                    cotizacionesCompletas.add(cotizacion);
                else
                    cotizacionIncompletas.add(cotizacion);
            }
        } catch (Exception ex) {

        }
    }

    private HttpResponse CargarWeb() {
        //String url = "http://192.168.0.111:8080/CotizadorWeb/AgriImportadorCotizaciones";
        String StartFolderServer = Main.get_StartFolder();
        final File StructureFileServer = new File(StartFolderServer + "ServerFile.config");
        Path StructurePathServer = FileSystems.getDefault().getPath(StructureFileServer.getPath());
        String StructureContentsServer = null;
        try {
            StructureContentsServer = new String(Files.readAllBytes(StructurePathServer), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = (StructureContentsServer.trim()+"/AgriImportadorCotizaciones");
        //String url = "http://200.93.229.179:8080/CotizadorWeb/AgriImportadorCotizaciones";
        //String url = "http://192.168.0.116:8080/CotizadorWeb/AgriImportadorCotizaciones";
        //import org.apache.http.impl.client.HttpClientBuilder;
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost dataCotizaciones = new HttpPost(url);
        HttpResponse response = null;
        //HttpRequest request = null;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat fHora = new SimpleDateFormat("HH:mm:ss");
        //DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date fechaCreacion = new Date();
        /*Calendar calAntes = Calendar.getInstance();
        calAntes.setTime(fechaCreacion);
        calAntes.add(Calendar.DAY_OF_MONTH, -1);*/
        //
        tcc = new TransporteCotizaciones();
        Gson g = new Gson();
        String Json = "";
        String Encrypted = "";
        //final File StructureFile = new File(ruta);
        String StartFolder = Main.get_StartFolder();
        final File StructureFile = new File(StartFolder + "cotizaciones" + File.separator + ProductoCotizacion + usuario + CI + ".config");
        try {
            Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
            if (StructureFile.exists()) {

                //Si existen cotizaciones completas por enviar
                if (cotizacionesCompletas.size() > 0) {
                    Gson gson = new Gson();
                    tcc.setCotizacionAgricola(cotizacionesCompletas);
                    Json = g.toJson(tcc);
                    // Encripto
                    Encrypted = AES_Helper.encrypt(AES_Helper.padString(Json));

                    ArrayList<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
                    urlParameters.add(new BasicNameValuePair("dataCotizaciones", Encrypted));
                    //dataCotizaciones.setEntity(new StringEntity(StructureContents));
                    dataCotizaciones.setEntity(new UrlEncodedFormEntity(urlParameters));
                    //dataCotizaciones.addHeader("content-type", "application/json");
                    response = client.execute(dataCotizaciones);
                    //HttpEntity entity = response.getEntity();
                    //String responseString = EntityUtils.toString(entity, "UTF-8");

                    ///Si se enviaron las cotizaciones con exito se graba el archivo en historial y se elimina de la ruta actual
                    if (response.getStatusLine().getStatusCode() == 200) {
                        Encrypted = "";
                        Json = "";
                        //recibir el listado de fredy
                        HttpEntity rp = response.getEntity();
                        //String StringJson = EntityUtils.toString(rp, "UTF-8");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                        String res = "";
                        String s = null;
                        while ((s = reader.readLine()) != null)
                            res += res += s + "\r\n";
                        ///se recibe un listado de respuesta una vez procesadas las cotizaciones
                        EstadoRespuesta estado = gson.fromJson(res, EstadoRespuesta.class);
                            if (estado.estado.equals(true)) {
                            procesadosCompletos = estado.getListadoCotizaciones();
                            tcc = gson.fromJson(res, TransporteCotizaciones.class);
                            tcc.setCotizacionAgricola(cotizacionesCompletas);
                                //tcc.setlistadoCotizaciones(estado.getListadoCotizaciones());
                            Json = g.toJson(tcc);
                            Encrypted = AES_Helper.encrypt(AES_Helper.padString(Json));
                            //CurrentTransporteProcesado = gson.fromJson(StringJson, TransporteListadoProcesado.class);
                            //TransporteListadoProcesado st = gson.fromJson(res, TransporteListadoProcesado.class);

                            //String rutaHistorial = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\HistorialCotizaciones\\";
                            String rutaHistorial = new File(StartFolder + "HistorialCotizaciones" + File.separator).getAbsolutePath();
                            //String rutaHistorial = rutaH.getAbsolutePath();
                            //rutaHistorial = rutaHistorial+"\\"+ StructureFile.getName() + df.format(fechaCreacion).replace("/", "");
                            String NameFile = StructureFile.getName();
                            //rutaHistorial = rutaHistorial + fc.getSelectedFile().getName();
                            rutaHistorial = rutaHistorial + "\\" + NameFile.replace(".config", "") + "_" + df.format(fechaCreacion).replace("/", "")+fHora.format(fechaCreacion).replace(":", "") + ".config";

                            final File HistoriaFile = new File(rutaHistorial);
                            Path HistorialPath = FileSystems.getDefault().getPath(HistoriaFile.getPath());
                            //Una vez enviado la cotizacioens el archivo se guarda en el historial y se elimina de las cotizaciones
                            Files.write(HistorialPath, Encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
                            Files.delete(StructurePath);
                        } else
                            return null;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No existen cotizaciones completas para enviar");
                    return null;
                }

            } else {
                String lastName = Main.getCurrentUser().getLastName() == null ? "" : Main.getCurrentUser().getLastName();
                JOptionPane.showMessageDialog(null, "No existen cotizaciones a enviar, para el  usuario " + usuario + " " + lastName);
                return null;
            }

        } catch (Exception ignore) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error: " + ignore.getMessage());
            return null;
        }
        return response;
    }

    private Boolean CargarArchivo() {
        String StartFolder = Main.get_StartFolder();

        //String rutad = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\cotizaciones\\";
        String rutad = new File(StartFolder + "cotizaciones" + File.separator).getAbsolutePath();
        //final File StructureFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\micotizador\\offline\\cotizaciones\\");
        final File ruta = new File(StartFolder + "cotizaciones" + File.separator + ProductoCotizacion + usuario + CI + ".config");
        //String archivo = ruta.getName();
        JFileChooser fc = new JFileChooser();
        //FileFilter filter = new FileNameExtensionFilter("CotizacionAgricola_"+usuario+CI,"config","CONFIG");
        //fc.addChoosableFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        if (ruta.exists()) {
            fc.setSelectedFile(ruta);//("CotizacionAgricola_"+usuario,"config"));
        } else {
            String lastName = Main.getCurrentUser().getLastName() == null ? "" : Main.getCurrentUser().getLastName();
            JOptionPane.showMessageDialog(null, "No existe archivo a enviar, para el usuario " + usuario + " " + lastName);
            return false;
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date fechaCreacion = new Date();
        //String ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\cotizaciones\\CotizacionAgricola_"+usuario+df.format(calAntes.getTime()).replace("/", "")+".config";
        //String rutaHistorial = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\HistorialCotizaciones\\";
        String rutaHistorial = new File(StartFolder + "HistorialCotizaciones" + File.separator).getAbsolutePath();
        //fc.setSelectedFile(StructureFile);
        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion = fc.showSaveDialog(pnlPrincipal);
        //Si el usuario, pincha en aceptar
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            BufferedWriter writer;
            //Seleccionamos el fichero
            if (!fc.getSelectedFile().getName().equals(ruta.getName())) {
                JOptionPane.showMessageDialog(null, "El archivo seleccionado no tiene permisos para ser enviado, elija el archivo adecuado");
                return false;
            }
            try {
                //Se obtiene la ruta del archivo seleccionado
                rutad = rutad + "\\" + fc.getSelectedFile().getName();
                final File StructureFile = new File(rutad);
                Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
                Date fecha = new Date();
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd_MM_yyyy");
                String rutaFinal=fc.getSelectedFile().getPath();
                rutaFinal=rutaFinal.replace(".config","_"+simpleDateFormat.format(fecha)+"_"+fecha.getHours()+""+fecha.getMinutes()+""+fecha.getSeconds()+".config");
                Path nuevoPath = FileSystems.getDefault().getPath(rutaFinal);
                if (StructurePath.equals(nuevoPath)) {
                    JOptionPane.showMessageDialog(null, "Debe eligir una nueva ruta para grabar el archivo");
                    return false;
                }
                //se cmabia la ruta y nombre para guardar en el historial
                String NameFile = fc.getSelectedFile().getName();
                //rutaHistorial = rutaHistorial + fc.getSelectedFile().getName();
                rutaHistorial = rutaHistorial + "\\" + NameFile.replace(".config", "") + "_" + df.format(fechaCreacion.getTime()).replace("/", "")+"_"+fecha.getHours()+""+fecha.getMinutes()+"" +fecha.getSeconds()+ ".config";
                final File HistoriaFile = new File(rutaHistorial);
                Path HistorialPath = FileSystems.getDefault().getPath(HistoriaFile.getPath());
                String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                if (fc.getSelectedFile().exists()) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "El archivo ya existe en esta ubicación, desea remplazarlo?");
                    if (respuesta == JOptionPane.YES_OPTION)
                        Files.write(nuevoPath, StructureContents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
                    else
                        return false;
                } else
                    Files.write(nuevoPath, StructureContents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
                //Una vez Grabado en la nueva ruta el archivo se guarda en el historial y se elimina de las cotizaciones
                Files.write(HistorialPath, StructureContents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
                Files.delete(StructurePath);
                JOptionPane.showMessageDialog(null, "El archivo de cotización fue procesó correctamente.");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error: " + e1.getMessage());
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private List<ResultadoHtml> RecibirPlantillaCotizacion() {
        tcc = new TransporteCotizaciones();

        String plantillaString = Main.getCurrentTransporteData().getParametrosGenerales().getTextoPlantilla();
        List<ResultadoHtml> listHtml = new ArrayList<ResultadoHtml>();
        tcc.setCotizacionAgricola(cotizacionesCompletas);
        if (tcc.getCotizacionAgricola() != null) {//&& tcc.getlistadoCotizaciones()!=null){
            for (CotizacionAgricola cotizacion : tcc.getCotizacionAgricola()) {
                if (!cotizacion.getTotalPrima().equals(0)){
                    ListadoProcesado procesadoCoti = new ListadoProcesado();
                    for ( ListadoProcesado lstProcesado : procesadosCompletos){
                        if (lstProcesado.getObjetoCotizacionID().equals(cotizacion.getObjetoCotizacionId()))
                        {
                            procesadoCoti = lstProcesado;
                        }
                    }
                    if (procesadoCoti!=null && !procesadoCoti.getCotizacionID().equals(0))
                    {
                        String rutaImage ="";

                        Integer VigenciaCoberura = Main.getCurrentTransporteData().getParametrosGenerales().getDiasValidacionCultivo();
                        SimpleDateFormat Fecha = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar calVigenciaHasta = Calendar.getInstance();
                        calVigenciaHasta.setTime(cotizacion.getFechaSiembra());
                        calVigenciaHasta.add(Calendar.DAY_OF_MONTH,Integer.parseInt(VigenciaCoberura.toString()==null?"0":VigenciaCoberura.toString()));

                        String FechaCreacion = new SimpleDateFormat("dd/MM/yyyy").format(cotizacion.getFechaCreacionCotizacion());

                        java.util.Hashtable<String, String> parametersHeader = new java.util.Hashtable<String, String>();
                        parametersHeader.put("FECHA_COTIZACION", FechaCreacion);
                        //parametersHeader.put("USUARIO_COTIZA", cotizacion.getUsuarioNombre() + " " + cotizacion.getUsuarioApellido());
                        parametersHeader.put("SRC_IMAGE", "file:///"+rutaImage);
                        parametersHeader.put("VIGENCIA_DESDE", Fecha.format(cotizacion.getFechaSiembra()));
                        parametersHeader.put("VIGENCIA_HASTA", Fecha.format(calVigenciaHasta.getTime()));
                        parametersHeader.put("NRO_POLIZA", "");
                        parametersHeader.put("NRO_ANEXO", "");


                        for (PuntoVenta punto : Main.getCurrentTransporteData().getPuntosVentaAgricola()) {
                            if (punto.getPuntoVentaID().toString().equals(cotizacion.getPuntoVentaId().toString())) {
                                //parametersHeader.put("CANAL_NOMBRE", punto.getNombre());
                                break;
                            }
                        }
                        //precio ajuste
                        String PrecioAjuste = "";
                        for (TipoCultivo cultivo : Main.getCurrentTransporteData().getTiposCultivos()) {
                            if (cultivo.getTipoCultivoId().toString().equals(cotizacion.getTipoCultivoId().toString())) {
                                parametersHeader.put("TIPO_CULTIVO", cultivo.getNombre());
                                //Precion de ajuste del cultivo
                                if (cultivo.getPrecioAjuste()!=null)
                                    PrecioAjuste = cultivo.getPrecioAjuste();
                                break;
                            }
                        }

                        for (Provincia provincia : Main.getCurrentTransporteData().getProvincias()) {
                            if (provincia.getProvinciaId().toString().equals(cotizacion.getProvinciaId().toString())) {
                                parametersHeader.put("PROVINCIA", provincia.getNombre());
                                break;
                            }
                        }

                        for (Canton canton : Main.getCurrentTransporteData().getCantones()) {
                            if (canton.getCantonId().toString().equals(cotizacion.getCantonId().toString())) {
                                parametersHeader.put("CANTON",canton.getNombre());
                                break;
                            }
                        }

                        for (Parroquia parroquia : Main.getCurrentTransporteData().getParroquias()) {
                            if (parroquia.getParroquiaId().toString().equals(cotizacion.getParroquiaId().toString())) {
                                parametersHeader.put("SITIO", (parroquia.getNombre() ==null?"":parroquia.getNombre().toUpperCase())+" "+
                                        (cotizacion.getDireccionSiembra()==null?"":cotizacion.getDireccionSiembra()));
                                break;
                            }
                        }

                        parametersHeader.put("CLIENTE_NOMBRE", cotizacion.getNombres() + " " + cotizacion.getApellidos());
                        parametersHeader.put("HECTAREAS_ASEGURABLES", (new BigDecimal(cotizacion.getHectareasAsegurables()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("VALOR_ASEGURADO", (new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("VALOR_ASEGURADO_TOTAL", (new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("MONTO_ASEGURADO",(new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2, RoundingMode.HALF_UP)).toString());

                        parametersHeader.put("PRIMA_TOTAL",(new BigDecimal(cotizacion.getTotalPrima()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("ASEGURADO", cotizacion.getNombres() + " " + cotizacion.getApellidos());

                        BigDecimal totalFactura = new BigDecimal(cotizacion.getTotalPrima()).setScale(2,RoundingMode.HALF_UP);
                        //parametersHeader.put("FORMAS_PAGO","visible");
                        parametersHeader.put("CUOTA1",totalFactura.toString());
                        BigDecimal cuota2 = totalFactura.divide(new BigDecimal("2"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota2.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA2",cuota2.toString());
                        BigDecimal cuota3 = totalFactura.divide(new BigDecimal("3"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota3.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA3",cuota3.toString());
                        BigDecimal cuota4 = totalFactura.divide(new BigDecimal("4"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota4.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA4",cuota4.toString());
                        BigDecimal cuota5 = totalFactura.divide(new BigDecimal("5"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota5.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA5",cuota5.toString());
                        BigDecimal cuota6 = totalFactura.divide(new BigDecimal("6"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota6.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA6",cuota6.toString());
                        BigDecimal cuota7 = totalFactura.divide(new BigDecimal("7"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota7.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA7",cuota7.toString());
                        BigDecimal cuota8 = totalFactura.divide(new BigDecimal("8"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota8.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA8",cuota8.toString());
                        BigDecimal cuota9 = totalFactura.divide(new BigDecimal("9"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota9.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA9",cuota9.toString());
                        BigDecimal cuota10 = totalFactura.divide(new BigDecimal("10"),MathContext.DECIMAL32).setScale(2,RoundingMode.HALF_UP);
                        if (cuota10.compareTo(new BigDecimal("0"))>0)
                            parametersHeader.put("CUOTA10",cuota10.toString());
                        ///Recibir parametros de coberturas
                        String coberturaTexto = "";
                        String deducibleTexto = "";
                        String metodoIndemnizacion = "";
                        for (TipoCultivoXTipoCalculo coberturas : Main.getCurrentTransporteData().getTiposCultivoXTiposCalculos()) {
                            if (coberturas.getTipoCultivoId().toString().equals(cotizacion.getTipoCultivoId().toString())) {
                                try {
                                    coberturaTexto = new String(coberturas.getCoberturaTexto(), "UTF-8");
                                    deducibleTexto = new String(coberturas.getDeducibleTexto(), "UTF-8");
                                    metodoIndemnizacion = new String(coberturas.getMetodoIndemnizacionTexto(), "UTF-8");
                                    break;
                                } catch (Exception ex) {
                                    continue;
                                }
                            }
                        }
                        //parametersHeader.put("COBERTURA_TEXTO", coberturaTexto);
                        //parametersHeader.put("DEDUCIBLE_TEXTO", deducibleTexto);
                        //parametersHeader.put("METODO_INDEMNIZACION_TEXTO", metodoIndemnizacion);
                        parametersHeader.put("VIGENCIA_DIAS", VigenciaCoberura.toString());
                        //Obtener parametros de la regla
                        BigDecimal CostoProduccion = new BigDecimal("0.00");
                        BigDecimal Tasa = new BigDecimal("0.00");
                        for (Regla regla : Main.getCurrentTransporteData().getReglas()) {
                            if (regla.getReglaId().equals(cotizacion.getReglaId())) {
                                CostoProduccion = new BigDecimal(regla.getCostoProduccion());
                                Tasa = new BigDecimal(regla.getTasa());
                                break;
                            }
                        }
                        ///TODO: Agregar el precio de ajuste
                        parametersHeader.put("PRECIO_AJUSTE",PrecioAjuste.toString());

                        //TODO: Si el valor del precio de ajuste es 0 ocultar la clausula
                        if (PrecioAjuste==null || PrecioAjuste.isEmpty())
                            parametersHeader.put("PRECIO_AJUSTE_CLASS","oculto");
                        else
                            parametersHeader.put("PRECIO_AJUSTE_CLASS","visible");

                        String Html = GenerarContenido(plantillaString, parametersHeader);

                        ResultadoHtml resHtml = new ResultadoHtml();
                        resHtml.setHtml_(Html);
                        //resHtml.setCotizacionId_(cotizacion.getObjetoCotizacionId());
                        resHtml.setCotizacionId_(procesadoCoti.getCotizacionID());
                        listHtml.add(resHtml);
                    }
                }
            }
        }
        return listHtml;
    }


    private List<ResultadoHtml> RecibirPlantillaCondicionesParticulares() {
        tcc = new TransporteCotizaciones();

        // plantillaString = Main.getCurrentTransporteData().getParametrosGenerales().getTextoPlantilla();
        List<ResultadoHtml> listHtml = new ArrayList<ResultadoHtml>();
        tcc.setCotizacionAgricola(cotizacionesCompletas);
        if (tcc.getCotizacionAgricola() != null) {//&& tcc.getlistadoCotizaciones()!=null){
            for (CotizacionAgricola cotizacion : tcc.getCotizacionAgricola()) {
                if (!cotizacion.getTotalPrima().equals(0)){
                    ListadoProcesado procesadoCoti = new ListadoProcesado();
                    for ( ListadoProcesado lstProcesado : procesadosCompletos){
                        if (lstProcesado.getObjetoCotizacionID().equals(cotizacion.getObjetoCotizacionId()))
                        {
                            procesadoCoti = lstProcesado;
                        }
                    }
                    if (procesadoCoti!=null && !procesadoCoti.getCotizacionID().equals(0))
                    {
                        String rutaImage ="";
                        String plantillaString = "";//Main.getCurrentTransporteData().getParametrosGenerales().getTextoPlantilla();
                        String PlantillaName="";
                        Integer VigenciaCoberura = Main.getCurrentTransporteData().getParametrosGenerales().getDiasValidacionCultivo();
                        SimpleDateFormat Fecha = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar calVigenciaHasta = Calendar.getInstance();
                        calVigenciaHasta.setTime(cotizacion.getFechaSiembra());
                        calVigenciaHasta.add(Calendar.DAY_OF_MONTH,Integer.parseInt(VigenciaCoberura.toString()==null?"0":VigenciaCoberura.toString()));

                        String FechaCreacion = new SimpleDateFormat("dd/MM/yyyy").format(cotizacion.getFechaCreacionCotizacion());

                        java.util.Hashtable<String, String> parametersHeader = new java.util.Hashtable<String, String>();
                        //precio ajuste
                        String PrecioAjuste = "";
                        for (TipoCultivo cultivo : Main.getCurrentTransporteData().getTiposCultivos()) {
                            if (cultivo.getTipoCultivoId().toString().equals(cotizacion.getTipoCultivoId().toString())) {
                                parametersHeader.put("TIPO_CULTIVO", cultivo.getNombre());
                                //TODO: Buscar el nombre de la plantilla en parametro por canal

                                for(ParametrosXCanal parametrosXCanal : Main.getCurrentTransporteData().getParametrosXCanal()){
                                    if(parametrosXCanal.getCanalId().toString().equalsIgnoreCase(Main.getCurrentConfiguration().getPuntoVentaId()) && parametrosXCanal.getTipoCultivoId().equals(cultivo.getTipoCultivoId())){
                                        PlantillaName = parametrosXCanal.getNombrePlantilla();
                                        break;
                                    }
                                }
                                //Precion de ajuste del cultivo
                                if (cultivo.getPrecioAjuste()!=null)
                                    PrecioAjuste = cultivo.getPrecioAjuste();
                                break;
                            }
                        }
                        //parametersHeader.put("FECHA_COTIZACION", FechaCreacion);
                        //parametersHeader.put("USUARIO_COTIZA", cotizacion.getUsuarioNombre() + " " + cotizacion.getUsuarioApellido());
                        parametersHeader.put("SRC_IMAGE", "file:///"+rutaImage);
                        parametersHeader.put("VIGENCIA_DESDE", Fecha.format(cotizacion.getFechaSiembra()));
                        parametersHeader.put("VIGENCIA_HASTA", Fecha.format(calVigenciaHasta.getTime()));
                        parametersHeader.put("NRO_POLIZA", procesadoCoti.getCotizacionID());
                        parametersHeader.put("NRO_ANEXO", procesadoCoti.getCotizacionID());


                        for (PuntoVenta punto : Main.getCurrentTransporteData().getPuntosVentaAgricola()) {
                            if (punto.getPuntoVentaID().toString().equals(cotizacion.getPuntoVentaId().toString())) {
                                //parametersHeader.put("CANAL_NOMBRE", punto.getNombre());
                                break;
                            }
                        }


                        for (Provincia provincia : Main.getCurrentTransporteData().getProvincias()) {
                            if (provincia.getProvinciaId().toString().equals(cotizacion.getProvinciaId().toString())) {
                                parametersHeader.put("PROVINCIA", provincia.getNombre());
                                break;
                            }
                        }

                        for (Canton canton : Main.getCurrentTransporteData().getCantones()) {
                            if (canton.getCantonId().toString().equals(cotizacion.getCantonId().toString())) {
                                parametersHeader.put("CANTON",canton.getNombre());
                                break;
                            }
                        }

                        for (Parroquia parroquia : Main.getCurrentTransporteData().getParroquias()) {
                            if (parroquia.getParroquiaId().toString().equals(cotizacion.getParroquiaId().toString())) {
                                parametersHeader.put("SITIO", (parroquia.getNombre() ==null?"":parroquia.getNombre().toUpperCase())+" "+
                                        (cotizacion.getDireccionSiembra()==null?"":cotizacion.getDireccionSiembra().toUpperCase()));
                                break;
                            }
                        }

                        parametersHeader.put("CLIENTE_NOMBRE", cotizacion.getNombres() + " " + cotizacion.getApellidos());
                        parametersHeader.put("HECTAREAS_ASEGURABLES", (new BigDecimal(cotizacion.getHectareasAsegurables()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("VALOR_ASEGURADO", (new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("VALOR_ASEGURADO_TOTAL", (new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("MONTO_ASEGURADO",(new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2, RoundingMode.HALF_UP)).toString());

                        parametersHeader.put("PRIMA_TOTAL",(new BigDecimal(cotizacion.getTotalPrima()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("ASEGURADO", cotizacion.getNombres() + " " + cotizacion.getApellidos());
                        ///Recibir parametros de coberturas
                        String coberturaTexto = "";
                        String deducibleTexto = "";
                        String metodoIndemnizacion = "";
                        for (TipoCultivoXTipoCalculo coberturas : Main.getCurrentTransporteData().getTiposCultivoXTiposCalculos()) {
                            if (coberturas.getTipoCultivoId().toString().equals(cotizacion.getTipoCultivoId().toString())) {
                                try {
                                    coberturaTexto = new String(coberturas.getCoberturaTexto(), "UTF-8");
                                    deducibleTexto = new String(coberturas.getDeducibleTexto(), "UTF-8");
                                    metodoIndemnizacion = new String(coberturas.getMetodoIndemnizacionTexto(), "UTF-8");
                                    break;
                                } catch (Exception ex) {
                                    continue;
                                }
                            }
                        }
                        //parametersHeader.put("COBERTURA_TEXTO", coberturaTexto);
                        //parametersHeader.put("DEDUCIBLE_TEXTO", deducibleTexto);
                        //parametersHeader.put("METODO_INDEMNIZACION_TEXTO", metodoIndemnizacion);
                        parametersHeader.put("VIGENCIA_DIAS", VigenciaCoberura.toString());
                        //Obtener parametros de la regla
                        BigDecimal CostoProduccion = new BigDecimal("0.00");
                        BigDecimal Tasa = new BigDecimal("0.00");
                        for (Regla regla : Main.getCurrentTransporteData().getReglas()) {
                            if (regla.getReglaId().equals(cotizacion.getReglaId())) {
                                CostoProduccion = new BigDecimal(regla.getCostoProduccion());
                                Tasa = new BigDecimal(regla.getTasa());
                                break;
                            }
                        }
                        ///TODO: Agregar el precio de ajuste
                        parametersHeader.put("PRECIO_AJUSTE",PrecioAjuste.toString());

                        ///TODO: Agregar el precio de ajuste
                        parametersHeader.put("PRECIO_AJUSTE",PrecioAjuste.toString());
                        //TODO: Si el valor del precio de ajuste es 0 ocultar la clausula
                        if (PrecioAjuste==null || PrecioAjuste.isEmpty())
                            parametersHeader.put("PRECIO_AJUSTE_CLASS","oculto");
                        else
                            parametersHeader.put("PRECIO_AJUSTE_CLASS","visible");


                        //TODO: se recorre el listado de plantillas
                        /*if (Main.getCurrentTransporteData().getPlantillas()!=null){
                            for (PlantillasReporte plantilla : Main.getCurrentTransporteData().getPlantillas()){
                                if (plantilla.getNombrePlantilla().equalsIgnoreCase(PlantillaName)){
                                    plantillaString = plantilla.getPlantilla();
                                    break;
                                }
                            }
                        }*/
                        String Html = GenerarContenido(plantillaString, parametersHeader);

                        ResultadoHtml resHtml = new ResultadoHtml();
                        resHtml.setHtml_(Html);
                        //resHtml.setCotizacionId_(cotizacion.getObjetoCotizacionId());
                        resHtml.setCotizacionId_(procesadoCoti.getCotizacionID());
                        listHtml.add(resHtml);
                    }
                }
            }
        }
        return listHtml;
    }

    private List<ResultadoHtml> RecibirPlantillaCondicionesParticularesBlob() throws Exception{
        tcc = new TransporteCotizaciones();

        // plantillaString = Main.getCurrentTransporteData().getParametrosGenerales().getTextoPlantilla();
        List<ResultadoHtml> listHtml = new ArrayList<ResultadoHtml>();
        tcc.setCotizacionAgricola(cotizacionesCompletas);
        if (tcc.getCotizacionAgricola() != null) {//&& tcc.getlistadoCotizaciones()!=null){
            cotizacionesProcesadas: for (CotizacionAgricola cotizacion : tcc.getCotizacionAgricola()) {
                //si no tiene observaciones y si es diferente de sero entonces se aprueban automaticamente y creamos el reporte
                if (!cotizacion.getTotalPrima().equals(0)&&!cotizacion.getTieneObservacion()){
                    ListadoProcesado procesadoCoti = new ListadoProcesado();
                    for ( ListadoProcesado lstProcesado : procesadosCompletos){
                        if (lstProcesado.getObjetoCotizacionID().equals(cotizacion.getObjetoCotizacionId()))
                        {
                            procesadoCoti = lstProcesado;
                        }
                    }
                    if (procesadoCoti!=null && !procesadoCoti.getCotizacionID().equals(0))
                    {
                        String rutaImage ="";
                        String plantillaString = "";//Main.getCurrentTransporteData().getParametrosGenerales().getTextoPlantilla();
                        String PlantillaName="";

                        Integer VigenciaCoberura = Main.getCurrentTransporteData().getParametrosGenerales().getDiasValidacionCultivo();
                        SimpleDateFormat Fecha = new SimpleDateFormat("dd/MM/yyyy");
                        String FechaCreacion = new SimpleDateFormat("dd/MM/yyyy").format(cotizacion.getFechaCreacionCotizacion());
                        //precio ajuste
                        String PrecioAjuste = "";
                        String html = "";

                        //mapeo de los elementos a mostrarse en el reporte
                        java.util.Hashtable<String, String> parametersHeader = new java.util.Hashtable<String, String>();
                        parametersHeader.put("AseguradoNombre", cotizacion.getNombres() + " " + cotizacion.getApellidos());

                        parametersHeader.put("Cliente",cotizacion.getNombres() + " " + cotizacion.getApellidos());
                        //Encontramos la provincia
                        for (Provincia provincia : Main.getCurrentTransporteData().getProvincias()) {
                            if(provincia.getProvinciaId().equals(cotizacion.getProvinciaId().toString())){
                                parametersHeader.put("Provincia", provincia.getNombre());
                            }
                        }
                        //Encontramos el canton
                        for (Canton canton : Main.getCurrentTransporteData().getCantones()) {
                            if(canton.getCantonId().equals(cotizacion.getCantonId().toString())){
                                parametersHeader.put("Canton", canton.getNombre());
                            }
                        }
                        //Encontramos la Parroquia
                        try {
                            for (Parroquia parroquia : Main.getCurrentTransporteData().getParroquias()) {
                                if(parroquia.getParroquiaId().equals(cotizacion.getParroquiaId().toString())){
                                    parametersHeader.put("Parroquia", parroquia.getNombre());
                                }
                            }
                        }catch (Exception e){
                            //nada
                        }
                        parametersHeader.put("SitioReferencia", cotizacion.getDireccionSiembra());
                        parametersHeader.put("CostoProduccion",""+(cotizacion.getAnalisisMontoAsegurado()/cotizacion.getHectareasAsegurables()));
                        parametersHeader.put("Hectareas", ""+cotizacion.getHectareasAsegurables());
                        parametersHeader.put("SumaAsegurada", (new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2,RoundingMode.HALF_UP)).toString());



                        for (TipoCultivo cultivo : Main.getCurrentTransporteData().getTiposCultivos()) {
                            if (cultivo.getTipoCultivoId().toString().equals(cotizacion.getTipoCultivoId().toString())) {
                                parametersHeader.put("Cultivo",cultivo.getNombre());
                                parametersHeader.put("PrecioAjuste",cultivo.getPrecioAjuste());
                                parametersHeader.put("CultivoDias", ""+cultivo.getVigenciaDias());
                                Calendar calVigenciaHasta = Calendar.getInstance();
                                calVigenciaHasta.setTime(cotizacion.getFechaSiembra());
                                calVigenciaHasta.add(Calendar.DAY_OF_MONTH, cultivo.getVigenciaDias());

                                parametersHeader.put("VigenciaDesde","DESDE "+ Fecha.format(cotizacion.getFechaSiembra())+" HASTA "+Fecha.format(calVigenciaHasta.getTime()));

                                //parametros en vigencias de los cultivos
                                if(cultivo.getTipo()==2){
                                    parametersHeader.put("VigenciaCultivo1",cultivo.getVigenciaDias()+ " d&iacute;as (Desde la siembra hasta la madurez fisiol&oacute;gica del cultivo).");
                                }else{
                                    parametersHeader.put("VigenciaCultivo1", "365 D&iacute;as." );

                                }

                                try{
                                    parametersHeader.put("UnidadMedida", cultivo.getUnidadMedida());
                                    if(cultivo.getPrecioAjuste2()!=null)
                                        parametersHeader.put("PrecioAjuste2",cultivo.getPrecioAjuste2());
                                    if(cultivo.getUnidadMedida2()!=null)
                                        parametersHeader.put("UnidadMedida2",cultivo.getUnidadMedida2());

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                parametersHeader.put("Prima",""+ cotizacion.getTotalPrima());


                                //TODO: Buscar el nombre de la plantilla en parametro por canal

                                for(ParametrosXCanal parametrosXCanal : Main.getCurrentTransporteData().getParametrosXCanal()){
                                    if(parametrosXCanal.getCanalId().toString().equalsIgnoreCase(Main.getCurrentConfiguration().getPuntoVentaId()) && parametrosXCanal.getTipoCultivoId().equals(cultivo.getTipoCultivoId())){
                                        //Proceso de lectura del Blob con las Plantillas
                                        parametersHeader.put("NumeroPoliza", parametrosXCanal.getContenedorNumero()+"-"+procesadoCoti.getCotizacionID().toString());

                                        byte[] buffer=parametrosXCanal.getPlantillaHtml();
                                        try {

                                            html = new String( buffer);

                                        } catch (Exception ex) {
                                            continue  cotizacionesProcesadas;
                                        }

                                        PlantillaName = parametrosXCanal.getNombrePlantilla();
                                        break;
                                    }
                                }
                                //Precion de ajuste del cultivo
                                if (cultivo.getPrecioAjuste()!=null)
                                    PrecioAjuste = cultivo.getPrecioAjuste();
                                break;
                            }
                        }

                        //Obtener parametros de la regla
                        BigDecimal CostoProduccion = new BigDecimal("0.00");
                        BigDecimal Tasa = new BigDecimal("0.00");
                        for (Regla regla : Main.getCurrentTransporteData().getReglas()) {
                            if (regla.getReglaId().equals(cotizacion.getReglaId())) {
                                CostoProduccion = new BigDecimal(regla.getCostoProduccion());
                                Tasa = new BigDecimal(regla.getTasa());
                                break;
                            }
                        }
                        parametersHeader.put("Tasa",""+Tasa);


                        if(html.trim().equals(""))
                            continue  cotizacionesProcesadas;

                        Date fechaActual = new Date();

                         /*Parametros de la plantilla*/
                        if(cotizacion.getPuntoVentaId().equals("4273")){//CASO DE CREDIFE, BANCO DEL PICHINCHA
                            parametersHeader.put("Endoso","BANCO PICHINCHA");
                        }else{
                            parametersHeader.put("Endoso","COOPERATIVA DE AHORRO Y CREDITO COOPROGRESO LTDA");
                        }
                        parametersHeader.put("FechaEmision", Fecha.format(fechaActual.getTime()));
                        //las imagenes del reporte
                        String rutaPlantilla = ConsultarCotizacion.class.getProtectionDomain().getCodeSource()
                                .getLocation().toURI().getPath();

                        rutaPlantilla=rutaPlantilla.replace("Offline_Base.jar", "");
                        String rutaRelativaReporte="image/firmaAgricola.png";
                        String rutaFinalFirma=rutaPlantilla+rutaRelativaReporte;
                        //JOptionPane.showMessageDialog(null, "rutaFinalFirma: " +rutaFinalFirma);
                        System.out.println(rutaFinalFirma);
                        parametersHeader.put("SRC_IMAGE", "file:///"+rutaFinalFirma);
                        String rutaImagen="image/logoQBE.png";;
                        String rutaFinalLogo=rutaPlantilla+rutaImagen;
                        parametersHeader.put("SRC_LOGO", "file:///"+rutaFinalLogo);
                        //JOptionPane.showMessageDialog(null, "SRC_LOGO: " +rutaFinalLogo);
                        System.out.println(rutaFinalLogo);
                        //TODO:ponemos los datos de la plantilla ya generada.
                        String Html = GenerarContenido(html, parametersHeader);
                        ResultadoHtml resHtml = new ResultadoHtml();
                        resHtml.setHtml_(Html);
                        //resHtml.setCotizacionId_(cotizacion.getObjetoCotizacionId());
                        resHtml.setCotizacionId_(procesadoCoti.getCotizacionID());
                        listHtml.add(resHtml);
                    }
                }
            }
        }
        return listHtml;
    }


    private List<ResultadoHtml> RecibirPlantilla() {
        tcc = new TransporteCotizaciones();


        List<ResultadoHtml> listHtml = new ArrayList<ResultadoHtml>();
        tcc.setCotizacionAgricola(cotizacionesCompletas);
        if (tcc.getCotizacionAgricola() != null) {//&& tcc.getlistadoCotizaciones()!=null){
            for (CotizacionAgricola cotizacion : tcc.getCotizacionAgricola()) {
                if (!cotizacion.getTotalPrima().equals(0)){
                    ListadoProcesado procesadoCoti = new ListadoProcesado();
                    for ( ListadoProcesado lstProcesado : procesadosCompletos){
                        if (lstProcesado.getObjetoCotizacionID().equals(cotizacion.getObjetoCotizacionId()))
                        {
                            procesadoCoti = lstProcesado;
                        }
                    }
                    if (procesadoCoti!=null && !procesadoCoti.getCotizacionID().equals(0))
                    {
                        String plantillaString = "";//Main.getCurrentTransporteData().getParametrosGenerales().getTextoPlantilla();
                        String PlantillaName="";
                        String FechaCreacion = new SimpleDateFormat("dd/MM/yyyy").format(cotizacion.getFechaCreacionCotizacion());
                        Integer VigenciaCoberura = Main.getCurrentTransporteData().getParametrosGenerales().getDiasValidacionCultivo();
                        java.util.Hashtable<String, String> parametersHeader = new java.util.Hashtable<String, String>();
                        parametersHeader.put("FECHA_COTIZACION", FechaCreacion);
                        parametersHeader.put("USUARIO_COTIZA", cotizacion.getUsuarioNombre() + " " + cotizacion.getUsuarioApellido());
                        //precio ajuste
                        String PrecioAjuste = "";
                        for (TipoCultivo cultivo : Main.getCurrentTransporteData().getTiposCultivos()) {
                            if (cultivo.getTipoCultivoId().toString().equals(cotizacion.getTipoCultivoId().toString())) {
                                parametersHeader.put("TIPO_CULTIVO", cultivo.getNombre());
                                //TODO: Buscar el nombre de la plantilla en parametro por canal
                                //PlantillaName = cultivo.getNombrePlantilla();
                                //Precion de ajuste del cultivo
                                if (cultivo.getPrecioAjuste()!=null)
                                    PrecioAjuste = cultivo.getPrecioAjuste();
                                break;
                            }
                        }




                        for (PuntoVenta punto : Main.getCurrentTransporteData().getPuntosVentaAgricola()) {
                            if (punto.getPuntoVentaID().toString().equals(cotizacion.getPuntoVentaId().toString())) {
                                parametersHeader.put("CANAL_NOMBRE", punto.getNombre());
                                break;
                            }
                        }

                        parametersHeader.put("CLIENTE_NOMBRE", cotizacion.getNombres() + " " + cotizacion.getApellidos());
                        parametersHeader.put("HECTAREAS_ASEGURABLES", (new BigDecimal(cotizacion.getHectareasAsegurables()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("VALOR_ASEGURADO_HECTAREA", (new BigDecimal(cotizacion.getAnalisisMontoRecom()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("VALOR_ASEGURADO_TOTAL", (new BigDecimal(cotizacion.getAnalisisMontoAsegurado()).setScale(2, RoundingMode.HALF_UP)).toString());
                        ///Recibir parametros de coberturas
                        String coberturaTexto = "";
                        String deducibleTexto = "";
                        String metodoIndemnizacion = "";
                        for (TipoCultivoXTipoCalculo coberturas : Main.getCurrentTransporteData().getTiposCultivoXTiposCalculos()) {
                            if (coberturas.getTipoCultivoId().toString().equals(cotizacion.getTipoCultivoId().toString())) {
                                try {
                                    coberturaTexto = new String(coberturas.getCoberturaTexto(), "UTF-8");
                                    deducibleTexto = new String(coberturas.getDeducibleTexto(), "UTF-8");
                                    metodoIndemnizacion = new String(coberturas.getMetodoIndemnizacionTexto(), "UTF-8");
                                    break;
                                } catch (Exception ex) {
                                    continue;
                                }
                            }
                        }
                        parametersHeader.put("COBERTURA_TEXTO", coberturaTexto);
                        parametersHeader.put("DEDUCIBLE_TEXTO", deducibleTexto);
                        parametersHeader.put("METODO_INDEMNIZACION_TEXTO", metodoIndemnizacion);
                        parametersHeader.put("VIGENCIA_DIAS", VigenciaCoberura.toString());
                        //Obtener parametros de la regla
                        BigDecimal CostoProduccion = new BigDecimal("0.00");
                        BigDecimal Tasa = new BigDecimal("0.00");
                        for (Regla regla : Main.getCurrentTransporteData().getReglas()) {
                            if (regla.getReglaId().equals(cotizacion.getReglaId())) {
                                CostoProduccion = new BigDecimal(regla.getCostoProduccion());
                                Tasa = new BigDecimal(regla.getTasa());
                                break;
                            }
                        }
                        parametersHeader.put("COSTO_PRODUCCION", CostoProduccion.setScale(2, RoundingMode.HALF_UP).toString());
                        parametersHeader.put("TASA", Tasa.setScale(2, RoundingMode.HALF_UP).toString());
                        parametersHeader.put("PRIMA_NETA", (new BigDecimal(cotizacion.getPrimaNeta()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("SUPERINTENDENCIA_BANCOS", (new BigDecimal(cotizacion.getSuperBancos()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("DERECHO_EMISION", (new BigDecimal(cotizacion.getDerechoEmision()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("SEGURO_CAMPESINO", (new BigDecimal(cotizacion.getSeguroCampesino()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("IVA", (new BigDecimal(cotizacion.getIva()).setScale(2, RoundingMode.HALF_UP)).toString());
                        parametersHeader.put("TOTAL_PAGAR", (new BigDecimal(cotizacion.getTotalCredito(), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP)).toString());

                        ///TODO: Agregar el precio de ajuste
                        parametersHeader.put("PRECIO_AJUSTE",PrecioAjuste.toString());
                        parametersHeader.put("FORMAS_PAGO","oculto");
                        //TODO: si el reporte es pre poliza
                        parametersHeader.put("TIPO_REPORTE","COTIZACIÓN");

                        //TODO: Si el valor del precio de ajuste es 0 ocultar la clausula
                        if (PrecioAjuste==null || PrecioAjuste.isEmpty())
                            parametersHeader.put("PRECIO_AJUSTE_CLASS","oculto");
                        else
                            parametersHeader.put("PRECIO_AJUSTE_CLASS","visible");

                        //TODO: se recorre el listado de plantillas
                        /*if (Main.getCurrentTransporteData().getPlantillas()!=null){
                            for (PlantillasReporte plantilla : Main.getCurrentTransporteData().getPlantillas()){
                                if (plantilla.getNombrePlantilla()==PlantillaName){
                                    plantillaString = plantilla.getPlantilla();
                                    break;
                                }
                            }
                        }*/
                        String Html = GenerarContenido(plantillaString, parametersHeader);

                        ResultadoHtml resHtml = new ResultadoHtml();
                        resHtml.setHtml_(Html);
                        //resHtml.setCotizacionId_(cotizacion.getObjetoCotizacionId());
                        resHtml.setCotizacionId_(procesadoCoti.getCotizacionID());
                        listHtml.add(resHtml);
                    }
                }
            }
        }
        return listHtml;
    }


    private String GenerarContenido(String html, java.util.Hashtable<String, String> ParamValues) {
        List<String> detectedParams = new ArrayList<String>();
        Pattern params = Pattern.compile("\\[[a-zA-Z0-9\\._]*\\]");
        Matcher mat = params.matcher(html);
        while (mat.find()) {
            detectedParams.add(mat.group());
        }
        for (String detectedParam : detectedParams) {
            //String valor = ParamValues.get(detectedParam.replace("[", "").replace("]", ""));
            String valor=(ParamValues.get(detectedParam.replace("[", "").replace("]", ""))==null?"":ParamValues.get(detectedParam.replace("[", "").replace("]", "")));
            html = html.replace(detectedParam, valor);
        }
        return html;
    }

    private byte[] GenerarPDF(String html) {
        java.io.ByteArrayOutputStream out = null;

        //FileOutputStream out = null;
        try {
            CYaHPConverter converter = new CYaHPConverter(false);

            List headerFooterList = new ArrayList();

            // cabecera y pie de página
            //headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter("", IHtmlToPdfTransformer.CHeaderFooter.HEADER));

            headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter("Página <pagenumber>/<pagecount><hr />", IHtmlToPdfTransformer.CHeaderFooter.FOOTER));


            Map properties = new HashMap();

            properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS, IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);

            // Soporte para fuentes
            properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, "c:\\Windows\\Fonts");

            //File fout = new File("D:\\Archivos\\Escritorio\\YaHP-Converter-master\\YaHP-Converter-master\\YaHPConverter\\out\\artifacts\\YaHPConverter\\cosa4.pdf");
            //out = new FileOutputStream(fout);
            out = new ByteArrayOutputStream();

            // si no se pone la etiqueta head, no valen los saltos de línea
            try {
                converter.convertToPdf(html,
                        IHtmlToPdfTransformer.A4P,
                        headerFooterList,
                        null,
                        out,
                        properties);
            }catch (Exception e){
                e.printStackTrace();
            }
            out.flush();
            out.close();

            // PDF renderizado en Byte Array
            byte[] result = out.toByteArray();
            return result;

            //System.out.println(result.length);

            //FileOutputStream fos = new FileOutputStream("D:\\Proyectos\\QBE_COTIZADOR\\PYMES\\Reporte\\cotizacion"+CotizacionId+".pdf");
            //FileOutputStream fos = new FileOutputStream("Cotizacion_"+CotizacionId+".pdf");
            //fos.write(result);
            //fos.close();


        } catch (Exception ex) {
            String data = ex.getMessage();
            try {

                out.flush();
                out.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private void GrabarReporte(byte[] resultado, String CotizacionId) {
        String StartFolder = Main.get_StartFolder();
        String rutaCarpeta = new File(StartFolder + "Reportes" + File.separator).getAbsolutePath();
        final File rutaDir = new File(rutaCarpeta);
        if (!rutaDir.exists())
            rutaDir.mkdir();
        Path nuevoPath = FileSystems.getDefault().getPath(rutaDir + "\\CotizacionAgricola_" + CotizacionId + ".pdf");
        //Se graban los reportes en una carpeta dentro del proyecto
        try {

            if (nuevoPath.toFile().exists())
                Files.write(nuevoPath, resultado, StandardOpenOption.WRITE);
            else
                Files.write(nuevoPath, resultado, StandardOpenOption.CREATE_NEW);
        } catch (Exception ex) {

        }
    }

    private void LimpiarVariables() {
        cotizacionIncompletas = new ArrayList<CotizacionAgricola>();
        cotizacionesCompletas = new ArrayList<CotizacionAgricola>();
    }

    public static TransporteCotizaciones getCurrentTransporteCotizaciones() {
        return CurrentTransporteCotizaciones;
    }

    public static void setCurrentTransporteCotizaciones(TransporteCotizaciones currentTransporteCotizaciones) {
        CurrentTransporteCotizaciones = currentTransporteCotizaciones;
    }

    public class EstadoRespuesta {
        private Boolean estado;
        private String mensaje;
        private List<ListadoProcesado> listadoCotizaciones;
        public Boolean getEstado() {
            return estado;
        }

        public void setEstado(Boolean estado) {
            this.estado = estado;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public List<ListadoProcesado> getListadoCotizaciones() {
            return listadoCotizaciones;
        }

        public void setListadoCotizaciones(List<ListadoProcesado> listadoCotizaciones) {
            this.listadoCotizaciones = listadoCotizaciones;
        }

    }

    public class ResultadoHtml {
        private String html_;

        public String getHtml_() {
            return html_;
        }

        public void setHtml_(String html_) {
            this.html_ = html_;
        }

        public String getCotizacionId_() {
            return CotizacionId_;
        }

        public void setCotizacionId_(String cotizacionId_) {
            CotizacionId_ = cotizacionId_;
        }

        private String CotizacionId_;
    }

}


