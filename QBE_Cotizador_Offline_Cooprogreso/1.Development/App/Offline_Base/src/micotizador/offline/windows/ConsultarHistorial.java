package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.CotizacionAgricola;
import micotizador.offline.filestructure.ListadoProcesado;
import micotizador.offline.filestructure.TipoCultivo;
import micotizador.offline.filestructure.TransporteCotizaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Veronica Zhagui on 04/08/2015.
 */
public class ConsultarHistorial extends JDialog {
    private JTable TableHistorial;
    private JPanel PnlPrincipal;
    private static TransporteCotizaciones CurrentTransporteCotizaciones;
    private List<TipoCultivo> tipoCultivos = Main.getCurrentTransporteData().getTiposCultivos();
    private ConsultarCotizacion ventanaConsultar;
    private String ObtetoCotizacionID = "";

    public ConsultarHistorial(String ArchivoNombre) {
        super();
        ObtetoCotizacionID = ArchivoNombre;
        setContentPane(PnlPrincipal);
        pack();
        DefaultTableModel model = new DefaultTableModel();

        JLabel Id = new JLabel();
        JLabel identificacion = new JLabel();
        JLabel nombres = new JLabel();
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


        TableHistorial.setModel(model);
        //Redimensionar el tamaño de la columnas
        TableHistorial.getColumnModel().getColumn(1).setPreferredWidth(140);
        TableHistorial.getColumnModel().getColumn(2).setPreferredWidth(150);
        TableHistorial.getColumnModel().getColumn(3).setPreferredWidth(110);
        TableHistorial.getColumnModel().getColumn(4).setPreferredWidth(110);
        TableHistorial.getColumnModel().getColumn(5).setPreferredWidth(135);
        TableHistorial.getColumnModel().getColumn(6).setPreferredWidth(85);
        TableHistorial.getColumnModel().getColumn(7).setPreferredWidth(120);
        TableHistorial.getColumnModel().getColumn(8).setPreferredWidth(115);
        TableHistorial.getColumnModel().getColumn(9).setPreferredWidth(120);
        TableHistorial.getColumnModel().getColumn(10).setPreferredWidth(300);
        //TableHistorial.getColumnModel().getColumn(11).setPreferredWidth(100);

        CargarCotizaciones();
        Object[] object = new Object[11];
        object[0] = "";
        object[1] = "";
        object[2] = "";
        object[3] = "";
        object[4] = "";
        object[5] = "";
        object[6] = "";
        object[7] = "";
        object[8] = "";
        object[9] = "";
        object[10] = "";
        model.addRow(object);
        if (CurrentTransporteCotizaciones !=null && CurrentTransporteCotizaciones.getCotizacionAgricola()!=null){
            for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()) {
                String Fecha = new SimpleDateFormat("dd/MM/yyyy").format(cotizacion.getFechaCredito());
                object[0] = cotizacion.getObjetoCotizacionId();
                object[1]= cotizacion.getNumeroIdentificacion();
                object[2]= cotizacion.getNombres()+" "+cotizacion.getApellidos();
                object[4]= Fecha;
                object[5]=cotizacion.getHectareasAsegurables();
                object[6]=cotizacion.getTotalPrima();
                object[7]= cotizacion.getAnalisisMontoAsegurado();
                TipoCultivo cultivo = new TipoCultivo();
                for (TipoCultivo tipo : tipoCultivos) {
                    if (tipo.getTipoCultivoId().equals(cotizacion.getTipoCultivoId())) {
                        cultivo = tipo;
                        continue;
                    }
                }
                ListadoProcesado objetoProcesado = new ListadoProcesado();
                if (CurrentTransporteCotizaciones.getlistadoCotizaciones()!=null) {
                    for (ListadoProcesado procesado : CurrentTransporteCotizaciones.getlistadoCotizaciones()) {
                        if (procesado.getObjetoCotizacionID().equals(cotizacion.getObjetoCotizacionId())) {
                            objetoProcesado = procesado;
                            continue;
                        }
                    }
                }
                object[3]= cultivo.getNombre();
                object[8] = objetoProcesado.getCotizacionID();
                object[9] = objetoProcesado.getFacturaID();
                object[10] = objetoProcesado.getComentario();
                model.addRow(object);
            }
        }
        // Establecemos el renderer y editor que usaremos para el boton
        //TableHistorial.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        //tableCotizaciones.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));
        //&TableHistorial.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(),TableHistorial,this));


        TableHistorial.getColumnModel().getColumn(0).setMaxWidth(0);
        TableHistorial.getColumnModel().getColumn(0).setMinWidth(0);
        TableHistorial.getColumnModel().getColumn(0).setPreferredWidth(0);
        //ventanaConsultar = this;
    }

    private void CargarCotizaciones() {
        String usuario = Main.getCurrentUser().getName();
        String CI = Main.getCurrentUser().getCIUser() == null ? "" : Main.getCurrentUser().getCIUser();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //Date fechaCreacion = new Date();
        Gson g = new Gson();
        String Json = "";
        //String ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\HistorialCotizaciones\\CotizacionAgricola_"+usuario+CI+".config";
        //String ruta = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\micotizador\\offline\\HistorialCotizaciones\\CotizacionAgricola_"+usuario+CI+".config").getAbsolutePath();

        String Encrypted = "";
        try {
            //final File StructureFile = new File(ruta);
            String StartFolder = Main.get_StartFolder();
            final File StructureFile = new File(StartFolder + "HistorialCotizaciones" + File.separator + ObtetoCotizacionID);
            Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
            if (StructureFile.exists()) {
                String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                String StructureDecypted = AES_Helper.decrypt(StructureContents);
                CurrentTransporteCotizaciones = g.fromJson(StructureDecypted, TransporteCotizaciones.class);
            }
        }
        catch (Exception ignore){
            CurrentTransporteCotizaciones = null;
        }
    }

    public static TransporteCotizaciones getCurrentTransporteCotizaciones() {
        return CurrentTransporteCotizaciones;
    }

    public static void setCurrentTransporteCotizaciones(TransporteCotizaciones currentTransporteCotizaciones) {
        CurrentTransporteCotizaciones = currentTransporteCotizaciones;
    }

}
