package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by Veronica Zhagui on 12/08/2015.
 */
public class HistorialArchivos extends JDialog {
    private JTable TableArchivos;
    private JPanel pnlPrincipal;
    private JTextPane textPane1;
    private JButton buscarCI;
    private Image icon;
    private String ProductoCotizacion;


    public HistorialArchivos(String ObjetoProducto) {
        super();
        setContentPane(pnlPrincipal);
        pack();
        ProductoCotizacion = ObjetoProducto;
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

        Id.setName("ID");
        identificacion.setName("Cédula");
        nombres.setName("Usuario");
        FechaEnvio.setName("Fecha Envio");
        Consultar.setName("Consultar");

        model.addColumn(Id.getName());
        model.addColumn(identificacion.getName());
        model.addColumn(nombres.getName());
        model.addColumn(FechaEnvio.getName());
        model.addColumn(Consultar.getName());
        TableArchivos.setModel(model);


        //CargarArchivos();
        // Obtener un listado de los archivos del historial
        try {
            String StartFolder = Main.get_StartFolder();
            //final File StructureFile = new File(ruta);
            final File StructureFile = new File(StartFolder + "HistorialCotizaciones" + File.separator);
            ficheros = StructureFile.list();
        }
        catch (Exception ignore){
            //Exception e = ignore;
            String Message = ignore.getMessage();
        }
        //Cargar la tabla
        Object []object = new Object[5];
        object[0]= "";
        object[1]= "";
        object[2]= "";
        object[3]= "";
        object[4]= "";
        model.addRow(object);

        for (String archivo : ficheros){
            String archivoMostrar="";
            if (archivo.contains(ProductoCotizacion+usuario+CI+"_")){
                archivoMostrar=archivo;
                String fecha = "";
                String fecha_envio="";
                String[] ids=archivoMostrar.split("_");
                if(ids.length!=0){
                    //String[] ids2=ids[2].split(".");
                    fecha = ids[2].trim();
                }
                if (!fecha.equals("")) {
                    //fecha = fecha.substring(0,fecha.length()-7);
                    if (fecha.length()>8)
                    fecha_envio = fecha.substring(0, 2) + "/" + fecha.substring(2, 4) + "/" + fecha.substring(4, 8) + ":" + fecha.substring(8, 10) +
                            ":" + fecha.substring(10, 12) + ":" + fecha.substring(12, fecha.length());
                    else
                        fecha_envio = fecha.substring(0, 2) + "/" + fecha.substring(2, 4) + "/" + fecha.substring(4, 8);
                }
                object[0]= archivoMostrar;
                object[1]= CI;
                object[2]= usuarioNombres;
                object[3] = fecha_envio;
                model.addRow(object);
            }
        }
        TableArchivos.getColumnModel().getColumn(0).setMaxWidth(0);
        TableArchivos.getColumnModel().getColumn(0).setMinWidth(0);
        TableArchivos.getColumnModel().getColumn(0).setPreferredWidth(0);

        TableArchivos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (TableArchivos.getRowCount()>1 && TableArchivos.getSelectedRow()!=0) {
                    JTable table = (JTable) e.getSource();
                    String objetoID = table.getValueAt(table.getSelectedRow(), 0).toString();
                    if (!objetoID.isEmpty()) {
                        ConsultarHistorial ch = new ConsultarHistorial(objetoID);
                        ch.setIconImage(icon);
                        ch.setSize(990, 500);
                        ch.setLocationRelativeTo(null);
                        ch.setModal(true);
                        //ch.setResizable(false);
                        ch.setVisible(true);
                    }
                }
            }
        });


        buscarCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String producto = "CotizacionAgricola_";
                ConsultarCotizacionCI cc = new ConsultarCotizacionCI(producto);
                cc.setIconImage(icon);
                cc.setSize(990, 600);
                cc.setLocationRelativeTo(null);
                cc.setModal(true);
                cc.setVisible(true);
            }
        });
    }
    private   void CargarArchivos(){
        String usuario = Main.getCurrentUser().getName();
        String CI = Main.getCurrentUser().getCIUser()==null? "": Main.getCurrentUser().getCIUser();
        //DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //Date fechaCreacion = new Date();
        Gson g = new Gson();
        String Json = "";
        //String ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\HistorialCotizaciones\\CotizacionAgricola_"+usuario+CI+".config";
        //String ruta = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\micotizador\\offline\\HistorialCotizaciones\\CotizacionAgricola_"+usuario+CI+".config").getAbsolutePath();

        String Encrypted = "";
        try {
            //final File StructureFile = new File(ruta);
            String StartFolder = Main.get_StartFolder();
            final File StructureFile = new File(StartFolder + "HistorialCotizaciones" + File.separator);
            String[] ficheros = StructureFile.list();
        }
        catch (Exception ignore){
            //Exception e = ignore;
            String Message = ignore.getMessage();
        }
    }
}
