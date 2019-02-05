package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.*;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dmorales on 18-Dec-15.
 */
public class ConsultarCotizacionCI extends JDialog {
    private JPanel pnlPrincipal;
    private JTable TableArchivos;
    private JButton btn_Consultar;
    private JTextField txt_parametro;
    private Image icon;
    private String ProductoCotizacion;
    private static TransporteCotizaciones CurrentTransporteCotizaciones;
    private java.util.List<TipoCultivo> tipoCultivos = Main.getCurrentTransporteData().getTiposCultivos();

    private String[] parametrosRuta = {"cotizaciones","HistorialCotizaciones"};

    public ConsultarCotizacionCI(String ObjetoProducto) {
        super();
        setContentPane(pnlPrincipal);
        pack();
        ProductoCotizacion = ObjetoProducto;

        txt_parametro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                String a = String.valueOf(c);

                if (!a.matches("([0-9]|)"))
                    e.consume();

                if (txt_parametro.getText().length() > 13 )
                    e.consume();
            }
        });

        btn_Consultar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String usuario = Main.getCurrentUser().getName();
                String usuarioNombres = Main.getCurrentUser().getName()+" " + (Main.getCurrentUser().getLastName()==null?"":Main.getCurrentUser().getLastName());
                String CI = Main.getCurrentUser().getCIUser()==null? "": Main.getCurrentUser().getCIUser();
                DefaultTableModel model = new DefaultTableModel();
                String[] ficheros = null;
                JLabel Id = new JLabel();
                JLabel identificacion = new JLabel();
                JLabel nombres = new JLabel();
                JLabel FechaEnvio = new JLabel();
                JLabel Consultar = new JLabel();
                JLabel TipoCultivo = new JLabel();
                JLabel HecAsegurables = new JLabel();
                JLabel TotalPrima = new JLabel();
                JLabel ValorAsegurado = new JLabel();
                JLabel NroCotizacion = new JLabel();
                JLabel NroFactura = new JLabel();
                JLabel Comentario = new JLabel();
                JLabel FechaSolicitud = new JLabel();

                Id.setName("ID");
                identificacion.setName("Nro. Identificacion");
                nombres.setName("Nombres");
                TipoCultivo.setName("Tipo de Cultivo");
                HecAsegurables.setName("Hec. Asegurables");
                TotalPrima.setName("Total Prima");
                ValorAsegurado.setName("Valor Asegurado");
                NroCotizacion.setName("Nro. Cotización");
                NroFactura.setName("Nro. Factura");
                Comentario.setName("Observación");
                FechaSolicitud.setName("Fecha Solicitud");

                model.addColumn(Id.getName());
                model.addColumn(identificacion.getName());
                model.addColumn(nombres.getName());
                model.addColumn(TipoCultivo.getName());
                model.addColumn(FechaSolicitud.getName());
                model.addColumn(HecAsegurables.getName());
                model.addColumn(TotalPrima.getName());
                model.addColumn(ValorAsegurado.getName());
                model.addColumn(NroCotizacion.getName());
                model.addColumn(NroFactura.getName());
                model.addColumn(Comentario.getName());

                TableArchivos.setModel(model);
                //Redimensionar el tamaño de la columnas
                TableArchivos.getColumnModel().getColumn(1).setPreferredWidth(140);
                TableArchivos.getColumnModel().getColumn(2).setPreferredWidth(150);
                TableArchivos.getColumnModel().getColumn(3).setPreferredWidth(110);
                TableArchivos.getColumnModel().getColumn(4).setPreferredWidth(110);
                TableArchivos.getColumnModel().getColumn(5).setPreferredWidth(135);
                TableArchivos.getColumnModel().getColumn(6).setPreferredWidth(85);
                TableArchivos.getColumnModel().getColumn(7).setPreferredWidth(120);
                TableArchivos.getColumnModel().getColumn(8).setPreferredWidth(115);
                TableArchivos.getColumnModel().getColumn(9).setPreferredWidth(120);
                TableArchivos.getColumnModel().getColumn(10).setPreferredWidth(300);

                String[] updateList = null;

                for(String arg : parametrosRuta){
                    try {
                        String StartFolder = Main.get_StartFolder();
                        final File StructureFile = new File(StartFolder + arg + File.separator);
                        ficheros = StructureFile.list();

                    }
                    catch (Exception ignore){
                        String Message = ignore.getMessage();
                    }
                    updateList =  (String[]) ArrayUtils.addAll(updateList, ficheros);
                }

                //Cargar la tabla
                Object []object = new Object[11];
                object[0] = "";
                object[1] =  "";
                object[2] =  "";
                object[3] =  "";
                object[4] =  "";
                object[5] =  "";
                object[6] =  "";
                object[7] =  "";
                object[8] =  "";
                object[9] =  "";
                object[10] =  "";
                model.addRow(object);

                for (String archivo : updateList){
                    if(archivo.contains(ProductoCotizacion+usuario+CI)){
                        cargarResumenCotizaciones(archivo);
                        if (CurrentTransporteCotizaciones !=null && CurrentTransporteCotizaciones.getCotizacionAgricola()!=null){
                            for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()) {
                                if(cotizacion.getNumeroIdentificacion().contains(txt_parametro.getText())) {
                                    String Fecha = new SimpleDateFormat("dd/MM/yyyy").format(cotizacion.getFechaCredito());
                                    object[0] = cotizacion.getObjetoCotizacionId();
                                    object[1] = cotizacion.getNumeroIdentificacion();
                                    object[2] = cotizacion.getNombres() + " " + cotizacion.getApellidos();
                                    object[4] = Fecha;
                                    object[5] = cotizacion.getHectareasAsegurables();
                                    object[6] = cotizacion.getTotalPrima();
                                    object[7] = cotizacion.getAnalisisMontoAsegurado();
                                    TipoCultivo cultivo = new TipoCultivo();
                                    for (TipoCultivo tipo : tipoCultivos) {
                                        if (tipo.getTipoCultivoId().equals(cotizacion.getTipoCultivoId())) {
                                            cultivo = tipo;
                                            continue;
                                        }
                                    }
                                    ListadoProcesado objetoProcesado = new ListadoProcesado();
                                    if (CurrentTransporteCotizaciones.getlistadoCotizaciones() != null) {
                                        for (ListadoProcesado procesado : CurrentTransporteCotizaciones.getlistadoCotizaciones()) {
                                            if (procesado.getObjetoCotizacionID().equals(cotizacion.getObjetoCotizacionId())) {
                                                objetoProcesado = procesado;
                                                continue;
                                            }
                                        }
                                    }
                                    object[3] = cultivo.getNombre();
                                    object[8] = objetoProcesado.getCotizacionID();
                                    object[9] = objetoProcesado.getFacturaID();
                                    object[10] = objetoProcesado.getComentario();
                                    model.addRow(object);

                                }
                            }
                            CurrentTransporteCotizaciones = null;
                        }
                    }
                }
                TableArchivos.getColumnModel().getColumn(0).setMaxWidth(0);
                TableArchivos.getColumnModel().getColumn(0).setMinWidth(0);
                TableArchivos.getColumnModel().getColumn(0).setPreferredWidth(0);
            }
        });
    }

    private void cargarResumenCotizaciones(String arg){
        //String[] parametrosRuta = {"HistorialCotizaciones"};
        String usuario = Main.getCurrentUser().getName();
        String CI = Main.getCurrentUser().getCIUser() == null ? "" : Main.getCurrentUser().getCIUser();
        for(String arc: parametrosRuta) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            Gson g = new Gson();
            String Json = "";

            String Encrypted = "";

            try {
                //final File StructureFile = new File(ruta);
                String StartFolder = Main.get_StartFolder();
                final File StructureFile = new File(StartFolder + File.separator+ arc + File.separator + arg + File.separator);
                Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
                if (StructureFile.exists()) {
                    String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                    String StructureDecypted = AES_Helper.decrypt(StructureContents);
                    CurrentTransporteCotizaciones = g.fromJson(StructureDecypted, TransporteCotizaciones.class);
                }
            } catch (Exception ignore) {
                CurrentTransporteCotizaciones = null;
            }
        }

    }
}
