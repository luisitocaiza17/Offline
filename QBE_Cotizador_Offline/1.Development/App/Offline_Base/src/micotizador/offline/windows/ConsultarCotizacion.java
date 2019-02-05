package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica Zhagui on 29/07/2015.
 */
public class ConsultarCotizacion extends JDialog {
    private JPanel pnlPrincipal;
    private JTable tableCotizaciones;
    private static TransporteCotizaciones CurrentTransporteCotizaciones;
    private static TransporteCotizacionGanadero CurrentTransporteCotizacionesGanadero;
    private List<TipoCultivo> tipoCultivos = Main.getCurrentTransporteData().getTiposCultivos();
    private ConsultarCotizacion ventanaConsultar;
    private String ProductoCotizacion;
    private DefaultTableModel model = new DefaultTableModel();
    //private Object []object = new Object[8];
    
    public ConsultarCotizacion(String Produc) {
        super();
        //Cambiar el icono
        //Image icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        //this.setIconImage(icon);
        setContentPane(pnlPrincipal);
        pack();
        ProductoCotizacion = Produc;
        ImageIcon icono = new ImageIcon(getClass().getResource("/micotizador/offline/image/Eliminar.png"));

        if (ProductoCotizacion.equals("CotizacionAgricola_"))
            GenerarTableAgricola();
        else
            GenerarTableGanadero();

        ventanaConsultar = this;
        tableCotizaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //if (tableCotizaciones.getRowCount() > 1 && tableCotizaciones.getSelectedRow() != 0) {
                    JTable table = (JTable) e.getSource();
                    String objetoID = table.getValueAt(table.getSelectedRow(), 0).toString();
                    if (!objetoID.isEmpty()) {
                        if (ProductoCotizacion.equals("CotizacionAgricola_")) {
                            CotizadorAgricola co = new CotizadorAgricola(objetoID, ventanaConsultar);
                            //co.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
                            //co.setSize(990, 600);

                            co.pack();
                            //co.setLocation(210, 80);
                            co.setResizable(true);
                            co.setLocationRelativeTo(null);
                            co.setModal(true);
                            co.setVisible(true);
                        } else {
                            CotizadorGanadero cg = new CotizadorGanadero();
                            //cg.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
                            cg.setSize(1100, 700);
                            cg.setLocationRelativeTo(null);
                            cg.setResizable(true);
                            cg.setModal(true);
                            cg.setVisible(true);
                        }
                        //JOptionPane.showMessageDialog(null,""+ objetoID);
                    }
           //     }
            }
        });
    }

    private void CargarCotizaciones() {
        CurrentTransporteCotizaciones =null;
        String usuario = Main.getCurrentUser().getName();
        String CI = Main.getCurrentUser().getCIUser() == null ? "" : Main.getCurrentUser().getCIUser();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //Date fechaCreacion = new Date();
        Gson g = new Gson();
        String Json = "";
        //String ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\cotizaciones\\CotizacionAgricola_"+usuario+CI+".config";
        String Encrypted = "";
        try {
            //final File StructureFile = new File(ruta);
            String StartFolder = Main.get_StartFolder();
            final File StructureFile = new File(StartFolder + "cotizaciones" + File.separator + ProductoCotizacion + usuario + CI + ".config");
            Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
            if (StructureFile.exists()) {
                String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                String StructureDecypted = AES_Helper.decrypt(StructureContents);
                if (ProductoCotizacion.equals("CotizacionAgricola_"))
                    CurrentTransporteCotizaciones = g.fromJson(StructureDecypted, TransporteCotizaciones.class);
                else
                    CurrentTransporteCotizacionesGanadero = g.fromJson(StructureDecypted, TransporteCotizacionGanadero.class);
            }
        } catch (Exception ignore) {
            //Exception e = ignore;
            String Message = ignore.getMessage();
        }
    }

    private void GenerarTableAgricola() {

        Font font = new Font("Times New Roman", Font.BOLD, 16);

        JButton btnEditar = new JButton();
        btnEditar.setFont(font);
        btnEditar.setText("Eliminar");
        JTableHeader header = tableCotizaciones.getTableHeader();
        header.setFont(font);

        tableCotizaciones.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        model.addColumn("ID");
        model.addColumn("IDENTIFICACION");
        model.addColumn("NOMBRES");
        model.addColumn("CULTIVO");
        model.addColumn("HECT.");
        model.addColumn("PRIMA");
        model.addColumn("V.ASEG");
        model.addColumn("ESTADOS");
        model.addColumn("Eliminar");
        tableCotizaciones.setModel(model);

        CargarCotizaciones();
        Object[] object = new Object[9];
        if (CurrentTransporteCotizaciones != null) {
            for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()) {
                object[0] = cotizacion.getObjetoCotizacionId();
                object[1] = cotizacion.getNumeroIdentificacion();
                object[2] = cotizacion.getNombres().replace("|A", "Á").replace("|E","É").replace("|I","Í").replace("|O","Ó").replace("|U", "Ú")
                        .replace("|a", "á").replace("|e", "é").replace("|i","í").replace("|o","ó").replace("|u","ú")
                        .replace("-", "Ñ").replace("_", "ñ") + " " + cotizacion.getApellidos().replace("|A", "Á").replace("|E","É").replace("|I","Í").replace("|O","Ó").replace("|U", "Ú")
                        .replace("|a", "á").replace("|e", "é").replace("|i","í").replace("|o","ó").replace("|u","ú")
                        .replace("-", "Ñ").replace("_", "ñ");
                object[4] = cotizacion.getHectareasAsegurables();
                object[5] = cotizacion.getTotalPrima();
                object[6] = cotizacion.getAnalisisMontoAsegurado();

                int estado=cotizacion.getEstado();
                switch (estado){
                    case 0:
                        object[7] = "Borrador";
                        break;
                    case 1:
                        //boolean observacion=existeObservacionesRegla(cotizacion.getReglaId().toString());
                        boolean observacion=cotizacion.getTieneObservacion();
                        if(!observacion && cotizacion.getTotalPrima()!=0 ){
                            object[7] = "Aprobado";
                        }else{
                            object[7] = "Pendiente de Aprobar";
                        }
                        break;
                }

                object[8] = btnEditar.getText();
                TipoCultivo cultivo = new TipoCultivo();
                for (TipoCultivo tipo : tipoCultivos) {
                    if (tipo.getTipoCultivoId().equals(cotizacion.getTipoCultivoId())) {
                        cultivo = tipo;
                        continue;
                    }
                }
                object[3] = cultivo.getNombre();
                model.addRow(object);
            }
        }
        // Establecemos el renderer y editor que usaremos para el boton
        tableCotizaciones.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        //tableCotizaciones.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));
        tableCotizaciones.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), tableCotizaciones, this, ProductoCotizacion));

        tableCotizaciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tableCotizaciones.getColumnModel().getColumn(0).setMinWidth(0);
        tableCotizaciones.getColumnModel().getColumn(0).setPreferredWidth(0);


        tableCotizaciones.getColumnModel().getColumn(1).setMaxWidth(150);
        tableCotizaciones.getColumnModel().getColumn(1).setMinWidth(150);
        tableCotizaciones.getColumnModel().getColumn(1).setPreferredWidth(150);

        tableCotizaciones.getColumnModel().getColumn(2).setMaxWidth(150);
        tableCotizaciones.getColumnModel().getColumn(2).setMinWidth(150);
        tableCotizaciones.getColumnModel().getColumn(2).setPreferredWidth(150);

        tableCotizaciones.getColumnModel().getColumn(3).setMaxWidth(200);
        tableCotizaciones.getColumnModel().getColumn(3).setMinWidth(200);
        tableCotizaciones.getColumnModel().getColumn(3).setPreferredWidth(200);

        tableCotizaciones.getColumnModel().getColumn(4).setMaxWidth(80);
        tableCotizaciones.getColumnModel().getColumn(4).setMinWidth(80);
        tableCotizaciones.getColumnModel().getColumn(4).setPreferredWidth(80);

        tableCotizaciones.getColumnModel().getColumn(5).setMaxWidth(80);
        tableCotizaciones.getColumnModel().getColumn(5).setMinWidth(80);
        tableCotizaciones.getColumnModel().getColumn(5).setPreferredWidth(80);

        tableCotizaciones.getColumnModel().getColumn(6).setMaxWidth(80);
        tableCotizaciones.getColumnModel().getColumn(6).setMinWidth(80);
        tableCotizaciones.getColumnModel().getColumn(6).setPreferredWidth(80);

        tableCotizaciones.getColumnModel().getColumn(7).setMaxWidth(250);
        tableCotizaciones.getColumnModel().getColumn(7).setMinWidth(250);
        tableCotizaciones.getColumnModel().getColumn(7).setPreferredWidth(250);

        tableCotizaciones.getColumnModel().getColumn(8).setMaxWidth(150);
        tableCotizaciones.getColumnModel().getColumn(8).setMinWidth(150);
        tableCotizaciones.getColumnModel().getColumn(8).setPreferredWidth(150);

        tableCotizaciones.setAutoResizeMode(tableCotizaciones.AUTO_RESIZE_LAST_COLUMN);
        tableCotizaciones.setRowHeight(40);


    }

    public boolean existeObservacionesRegla(String idRegla){
        boolean observacion=true;
        List<Regla>reglass = Main.getCurrentTransporteData().getReglas();
        recorrido:for (Regla regla : reglass) {

            String reglaBusqueda=""+regla.getReglaId();
            if(reglaBusqueda.equals(idRegla)){
                System.out.println("si se encontrol la regla");
                if(regla.getObservaciones().trim().equals("")||regla.getObservaciones()==null) {
                    observacion = false;
                    break recorrido;
                }
            }
        }
        return observacion;
    }

    private void GenerarTableGanadero() {
        Font font = new Font("Arial", Font.BOLD, 16);

        JButton btnEditar = new JButton();
        btnEditar.setFont(font);
        btnEditar.setText("Eliminar");
        JTableHeader header = tableCotizaciones.getTableHeader();
        header.setFont(font);

        tableCotizaciones.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        model.addColumn("ID");
        model.addColumn("IDENTIFICACION");
        model.addColumn("NOMBRES");
        model.addColumn("CULTIVO");
        model.addColumn("HECT");
        model.addColumn("PRIMA");
        model.addColumn("V.ASEG");
        model.addColumn("ESTADOS");
        model.addColumn("Eliminar");
        tableCotizaciones.setModel(model);


        CargarCotizaciones();
        Object[] object = new Object[7];
        model.addRow(object);
        if (CurrentTransporteCotizacionesGanadero != null) {
            for (ObjetoGanadero cotizacion : CurrentTransporteCotizacionesGanadero.getCotizacionGanadero()) {
                object[0] = cotizacion.getObjetoGanaderoID();
                object[1] = cotizacion.getNumeroIdentificacion();
                object[2] = cotizacion.getNombres().replace("|A", "Á").replace("|E","É").replace("|I","Í").replace("|O","Ó").replace("|U", "Ú")
                        .replace("|a", "á").replace("|e", "é").replace("|i","í").replace("|o","ó").replace("|u","ú")
                        .replace("-", "Ñ").replace("_", "ñ") + " " + cotizacion.getApellidos().replace("|A", "Á").replace("|E","É").replace("|I","Í").replace("|O","Ó").replace("|U", "Ú")
                        .replace("|a", "á").replace("|e", "é").replace("|i","í").replace("|o","ó").replace("|u","ú")
                        .replace("-", "Ñ").replace("_", "ñ");
                object[3] = cotizacion.getUbicacion().replace("|A", "Á").replace("|E","É").replace("|I","Í").replace("|O","Ó").replace("|U", "Ú")
                        .replace("|a", "á").replace("|e", "é").replace("|i","í").replace("|o","ó").replace("|u","ú")
                        .replace("-", "Ñ").replace("_", "ñ");
                object[4] = cotizacion.getRegion();
                object[5] = cotizacion.getTipo_Produccion();
                model.addRow(object);
            }
        }
        // Establecemos el renderer y editor que usaremos para el boton
        tableCotizaciones.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        //tableCotizaciones.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));
        tableCotizaciones.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), tableCotizaciones, this, ProductoCotizacion));

        tableCotizaciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tableCotizaciones.getColumnModel().getColumn(0).setMinWidth(0);
        tableCotizaciones.getColumnModel().getColumn(0).setPreferredWidth(0);

        tableCotizaciones.getColumnModel().getColumn(1).setMaxWidth(150);
        tableCotizaciones.getColumnModel().getColumn(1).setMinWidth(150);
        tableCotizaciones.getColumnModel().getColumn(1).setPreferredWidth(150);

        tableCotizaciones.getColumnModel().getColumn(2).setMaxWidth(150);
        tableCotizaciones.getColumnModel().getColumn(2).setMinWidth(150);
        tableCotizaciones.getColumnModel().getColumn(2).setPreferredWidth(150);

        tableCotizaciones.getColumnModel().getColumn(3).setMaxWidth(200);
        tableCotizaciones.getColumnModel().getColumn(3).setMinWidth(200);
        tableCotizaciones.getColumnModel().getColumn(3).setPreferredWidth(200);

        tableCotizaciones.getColumnModel().getColumn(4).setMaxWidth(80);
        tableCotizaciones.getColumnModel().getColumn(4).setMinWidth(80);
        tableCotizaciones.getColumnModel().getColumn(4).setPreferredWidth(80);

        tableCotizaciones.getColumnModel().getColumn(5).setMaxWidth(80);
        tableCotizaciones.getColumnModel().getColumn(5).setMinWidth(80);
        tableCotizaciones.getColumnModel().getColumn(5).setPreferredWidth(80);

        tableCotizaciones.getColumnModel().getColumn(6).setMaxWidth(80);
        tableCotizaciones.getColumnModel().getColumn(6).setMinWidth(80);
        tableCotizaciones.getColumnModel().getColumn(6).setPreferredWidth(80);

        tableCotizaciones.getColumnModel().getColumn(7).setMaxWidth(250);
        tableCotizaciones.getColumnModel().getColumn(7).setMinWidth(250);
        tableCotizaciones.getColumnModel().getColumn(7).setPreferredWidth(250);

        tableCotizaciones.getColumnModel().getColumn(8).setMaxWidth(150);
        tableCotizaciones.getColumnModel().getColumn(8).setMinWidth(150);
        tableCotizaciones.getColumnModel().getColumn(8).setPreferredWidth(150);


        tableCotizaciones.setAutoResizeMode(tableCotizaciones.AUTO_RESIZE_LAST_COLUMN);
        tableCotizaciones.setRowHeight(40);
    }

    public static TransporteCotizaciones getCurrentTransporteCotizaciones() {
        return CurrentTransporteCotizaciones;
    }

    public static void setCurrentTransporteCotizaciones(TransporteCotizaciones currentTransporteCotizaciones) {
        CurrentTransporteCotizaciones = currentTransporteCotizaciones;
    }

    public static TransporteCotizacionGanadero getCurrentTransporteCotizacionesGanadero() {
        return CurrentTransporteCotizacionesGanadero;
    }

    public static void setCurrentTransporteCotizacionesGanadero(TransporteCotizacionGanadero currentTransporteCotizacionesGanadero) {
        CurrentTransporteCotizacionesGanadero = currentTransporteCotizacionesGanadero;
    }
}

//clases creadas para permitir eventos en los botones de la tabla
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;

    private String label;

    private boolean isPushed;
    private JTable tableCotizaciones;
    private List<CotizacionAgricola> CotizacionSolicitadas = new ArrayList<CotizacionAgricola>();

    private static TransporteCotizaciones CurrentTransporteCotizaciones;
    private ConsultarCotizacion pantConsulta;
    private String ProductoCotizacion;

    public ButtonEditor(JCheckBox checkBox, JTable tablaCotizacion, ConsultarCotizacion principal, String Producto) {
        super(checkBox);
        tableCotizaciones = tablaCotizacion;
        pantConsulta = principal;
        ProductoCotizacion = Producto;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }


    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        //if (row!=0) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        button.setName(String.valueOf(row));
        isPushed = true;

        //}
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            //
            //
            Integer row = Integer.valueOf(button.getName());
            if (row != 0) {
                int response = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?");
                if (response != JOptionPane.OK_OPTION) {
                    isPushed = false;
                    return new String(label);
                }
                ConsultarFila(row);
            }
            //String objetoId = tableCotizaciones.getValueAt(row,0).toString();
            //JOptionPane.showMessageDialog(button, label + button.getName()+"objetoid"+objetoId+ ": Ouch!");

            // System.out.println(label + ": Ouch!");

        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public void ConsultarFila(int row) {
        //String objetoID= objetoID = table.getValueAt(table.getSelectedRow(),0).toString();
        String objetoID = tableCotizaciones.getValueAt(row, 0).toString();
        if (!objetoID.isEmpty()) {
            ObtenerCotizaciones(objetoID);
            pantConsulta.dispose();

            ConsultarCotizacion cc = new ConsultarCotizacion(ProductoCotizacion);
            //cc.setSize(700, 500);
            cc.pack();
            cc.setLocationRelativeTo(null);
            //cc.setLocation(350, 130);
            cc.setModal(true);
            cc.setVisible(true);
            return;
        }
    }

    public void ObtenerCotizaciones(String ObtetoCotizacionID) {
        TransporteCotizaciones s = new TransporteCotizaciones();
        String usuario = Main.getCurrentUser().getName();
        String CI = Main.getCurrentUser().getCIUser() == null ? "" : Main.getCurrentUser().getCIUser();
        Gson g = new Gson();
        String Json = "";
        //String ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\cotizaciones\\CotizacionAgricola_"+usuario+CI+".config";
        String Encrypted = "";
        try

        {
            String StartFolder = Main.get_StartFolder();
            //final File StructureFile = new File(ruta);
            final File StructureFile = new File(StartFolder + "cotizaciones" + File.separator + ProductoCotizacion + usuario + CI + ".config");
            Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
            if (StructureFile.exists()) {
                String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                String StructureDecypted = AES_Helper.decrypt(StructureContents);
                CurrentTransporteCotizaciones = g.fromJson(StructureDecypted, TransporteCotizaciones.class);
                for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()) {
                    //recordar validar
                    if (!cotizacion.getObjetoCotizacionId().toString().equals(ObtetoCotizacionID.toString()))
                        CotizacionSolicitadas.add(cotizacion);
                }
                //if (CotizacionSolicitadas.size()!=0) {
                s.setCotizacionAgricola(CotizacionSolicitadas);
                Json = g.toJson(s);
                // Encripto
                Encrypted = AES_Helper.encrypt(AES_Helper.padString(Json));
                Files.delete(StructurePath);
                Files.write(StructurePath, Encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
                //}
                //else
                //  Files.write(StructurePath, Encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
