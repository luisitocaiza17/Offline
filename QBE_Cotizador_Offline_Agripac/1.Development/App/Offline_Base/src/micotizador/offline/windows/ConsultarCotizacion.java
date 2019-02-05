package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
                if (tableCotizaciones.getRowCount() > 1 && tableCotizaciones.getSelectedRow() != 0) {
                    JTable table = (JTable) e.getSource();
                    String objetoID = table.getValueAt(table.getSelectedRow(), 0).toString();
                    if (!objetoID.isEmpty()) {
                        if (ProductoCotizacion.equals("CotizacionAgricola_")) {
                            CotizadorAgricola co = new CotizadorAgricola(objetoID, ventanaConsultar);
                            //co.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
                            co.setSize(990, 600);
                            co.setLocation(210, 80);
                            co.setResizable(false);
                            co.setModal(true);
                            co.setVisible(true);
                        } else {
                            CotizadorGanadero cg = new CotizadorGanadero();
                            //cg.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
                            cg.setSize(1100, 700);
                            cg.setLocationRelativeTo(null);
                            cg.setResizable(false);
                            cg.setModal(true);
                            cg.setVisible(true);
                        }
                        //JOptionPane.showMessageDialog(null,""+ objetoID);
                    }
                }
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
        JLabel Id = new JLabel();
        JLabel identificacion = new JLabel();
        JLabel nombres = new JLabel();
        JLabel tipoCultivo = new JLabel();
        JLabel HecAsegurables = new JLabel();
        JLabel TotalPrima = new JLabel();
        JLabel ValorAsegurado = new JLabel();
        /*Proceso para mostrar los estados*/
        JLabel Estado = new JLabel();

        JButton btnEditar = new JButton();

        Id.setName("ID");
        identificacion.setName("Nro. Identificacion");
        nombres.setName("Nombres");
        tipoCultivo.setName("Tipo de Cultivo");
        HecAsegurables.setName("Hec. Asegurables");
        HecAsegurables.setHorizontalAlignment(SwingConstants.RIGHT);
        TotalPrima.setName("Total Prima");
        TotalPrima.setHorizontalAlignment(SwingConstants.RIGHT);
        ValorAsegurado.setName("Valor Asegurado");
        Estado.setName("Estados");
        btnEditar.setText("Eliminar");


        model.addColumn("ID");
        model.addColumn("Nro. Identificación");
        model.addColumn("Nombres");
        model.addColumn("Tipo de Cultivo");
        model.addColumn("Hec. Asegurables");
        model.addColumn("Total Prima");
        model.addColumn("Valor Asegurado");
        model.addColumn("Estados");
        model.addColumn("Eliminar");
        tableCotizaciones.setModel(model);

        CargarCotizaciones();
        Object[] object = new Object[9];
         object[0] = "";
        object[1] = "";
        object[2] = "";
        object[3] = "";
        object[4] = "";
        object[5] = "";
        object[6] ="";
        object[7] = "";
        object[8] ="";
        model.addRow(object);
        if (CurrentTransporteCotizaciones != null) {
            for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()) {
                object[0] = cotizacion.getObjetoCotizacionId();
                object[1] = cotizacion.getNumeroIdentificacion();
                object[2] = cotizacion.getNombres() + " " + cotizacion.getApellidos();
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
        JLabel Id = new JLabel();
        JLabel identificacion = new JLabel();
        JLabel nombres = new JLabel();
        JLabel Ubicacion = new JLabel();
        JLabel Region = new JLabel();
        JLabel TipoProduccion = new JLabel();
        JButton btnEditar = new JButton();

        Id.setName("ID");
        identificacion.setName("Nro. Identificacion");
        nombres.setName("Nombres");
        Ubicacion.setName("Ubicación");
        Region.setName("Region");
        TipoProduccion.setName("Tipo Producción");
        btnEditar.setText("Eliminar");

        model.addColumn(Id.getName());
        model.addColumn(identificacion.getName());
        model.addColumn(nombres.getName());
        model.addColumn(Ubicacion.getName());
        model.addColumn(Region.getName());
        model.addColumn(TipoProduccion.getName());
        model.addColumn("Eliminar");
        tableCotizaciones.setModel(model);

        CargarCotizaciones();
        Object[] object = new Object[7];
        object[0] = Id.getName();
        object[1] = identificacion.getName();
        object[2] = nombres.getName();
        object[3] = Ubicacion.getName();
        object[4] = Region.getName();
        object[5] = TipoProduccion.getName();
        object[6] = btnEditar.getText();
        model.addRow(object);
        if (CurrentTransporteCotizacionesGanadero != null) {
            for (ObjetoGanadero cotizacion : CurrentTransporteCotizacionesGanadero.getCotizacionGanadero()) {
                object[0] = cotizacion.getObjetoGanaderoID();
                object[1] = cotizacion.getNumeroIdentificacion();
                object[2] = cotizacion.getNombres() + " " + cotizacion.getApellidos();
                object[3] = cotizacion.getUbicacion();
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
            cc.setSize(700, 500);
            cc.setLocation(350, 130);
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
