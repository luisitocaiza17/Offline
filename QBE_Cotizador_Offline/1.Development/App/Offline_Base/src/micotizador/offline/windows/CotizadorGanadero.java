package micotizador.offline.windows;

import com.toedter.calendar.JDateChooser;
import micotizador.offline.Main;
import micotizador.offline.filestructure.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Veronica Zhagui on 04/08/2015.
 */
public class CotizadorGanadero extends JDialog {
    private JPanel PnlPrincipal;
    private JPanel pnlUbicacion;
    private JLabel lblProvincia;
    private JComboBox cmb_Provincia;
    private JLabel lblCanton;
    private JComboBox cmb_Canton;
    private JLabel lblParroquia;
    private JComboBox cmb_Parroquia;
    private JLabel lblSitio;
    private JTextField textField1;
    private JLabel lblRecinto;
    private JTextField textField2;
    private JPanel PnlDatosCLiente;
    private JLabel lblTipoIdentificacion;
    private JLabel lblNumeroIdentifiacion;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JComboBox cmb_TipoIdentificacion;
    private JTextField txtNumeroIdentificacion;
    private JTextField txtNombres;
    private JLabel lblNombres;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JLabel lblCelular;
    private JTextField txtCelular;
    private JPanel pnlDivision;
    private JPanel pnlListaAnimales;
    private JTable tableListaAnimales;
    private JButton btnAgregarAnimal;
    private JPanel PnlDatosPrima;
    private JLabel lblPrimaNeta;
    private JLabel lblSuperBancos;
    private JLabel lblSeguroCampesino;
    private JFormattedTextField txtPrimaNeta;
    private JFormattedTextField txtSuperBancos;
    private JFormattedTextField txtSeguroCampesino;
    private JLabel lblDerechoEmision;
    private JFormattedTextField txtDerechoEmision;
    private JLabel lblIva;
    private JFormattedTextField txtIva;
    private JLabel lblTotalPrima;
    private JFormattedTextField txtTotalPrima;
    private JLabel lblMontoCredito;
    private JFormattedTextField txtMontoCredito;
    private JLabel lblTotalCredito;
    private JFormattedTextField txtTotalCredito;
    private JButton grabarButton;
    private JButton btnPendiente;
    private JPanel pnlDatosGanaderia;
    private JLabel lblCantidadAnimalesVacunos;
    private JTextField txtCantidadAnimales;
    private JLabel lblAccesosAgua;
    private JTextField textField6;
    private JLabel lblMetodoAlimentacion;
    private JCheckBox chkPastoreo;
    private JCheckBox chkCorte;
    private JCheckBox chkSogueo;
    private JCheckBox chkOtros;
    private JLabel lblIngresoEconomico;
    private JRadioButton rbn_IngresoSi;
    private JRadioButton rbn_IngresoNo;
    private JCheckBox chkVacunaFiebre;
    private JCheckBox chkVacunaBrucelosis;
    private JCheckBox chkVacunaTriple;
    private JCheckBox chkLeptospirosis;
    private JCheckBox chkVacunaIBR;
    private JLabel lblVacunaOtras;
    private JTextField textField11;
    private JCheckBox chkParasitosInternos;
    private JCheckBox chkParasitosExternos;
    private JLabel lblControlDosis1;
    private JLabel lblControlDosis2;
    private JTextField textField12;
    private JTextField textField13;
    private JLabel lblFrecuencia1;
    private JLabel lblFrecuencia2;
    private JTextField textField14;
    private JTextField textField15;
    private JPanel pnlCaracteristicasFinca;
    private JPanel pnlAltitud;
    private JRadioButton rbn_Altitud1;
    private JRadioButton rbn_Altitud2;
    private JRadioButton rbn_Altitud3;
    private JRadioButton rbn_Altitud4;
    private JLabel lblTopPlana;
    private JTextField txtTopPlana;
    private JLabel lblHas1;
    private JLabel lblTopoPendiente1;
    private JTextField txtTopPendiente1;
    private JLabel lblHas2;
    private JLabel lblTopPendiente2;
    private JTextField txtTopPendiente2;
    private JLabel lblHas3;
    private JLabel lblTipoPasto1;
    private JTextField textField3;
    private JLabel lblHecPasto;
    private JTextField caTextField;
    private JTextField txtEnfermedadMatistis;
    private JTextField txtEnfermedadPanadizo;
    private JTextField txtEnfermedadFiebre;
    private JLabel lblApellidos;
    private JTextField txtApellidos;
    private JLabel lblAsistenciaVeterinaria;
    private JRadioButton rbn_AsistenciaSi;
    private JRadioButton rbn_AsistenciaNo;
    private JTextField textField10;
    private JLabel lblFrecuencia;
    private JLabel lblTipoProduccion;
    private JRadioButton rbn_TipoProducCarne;
    private JRadioButton rbn_TipoProducLeche;
    private JRadioButton rbn_TipoProducOtroTipo;
    private JLabel lblRegion;
    private JRadioButton rbn_RegionCosta;
    private JRadioButton rbn_RegionSierra;
    private JRadioButton rbn_RegionOriente;
    private JDateChooser dtFechaSolicitud;
    private JLabel lblUsuario;
    private JTextField txtEnfermedadNeumonia;
    private JTextField txtEnfermedadCual;
    private JTextField txtEnfermedadOtras;
    private JTextField txtEnfermedadLesion;
    private JTextField txtExperienciaCrianza;
    private List<Provincia> provi = new ArrayList<Provincia>();
    private List<Canton> cantones = new ArrayList<Canton>();
    private List<Parroquia> parroquias = new ArrayList<Parroquia>();
    private List<TipoIdentificacion> tipoIdentificacions = new ArrayList<TipoIdentificacion>();
    private Image icon;
    private static TransporteCotizacionGanadero CurrentTransporteCotizacionGanadero;

    public CotizadorGanadero() {
        super();
        //Cambiar el icono
        //icon = this.getIconImage();
        //this.setIconImage(icon);
        setContentPane(PnlPrincipal);
        pack();
        Date fecha = new Date();
        Locale local = new Locale("es","EC");
        String textoSitio =  "<html><body>Sitio ( Ubicación<br> Ganado)</body></html>";
        //Obtener el usuario logeado
        String usuario = Main.getCurrentUser().getName()+" " +(Main.getCurrentUser().getLastName()==null?"": Main.getCurrentUser().getLastName());
        lblUsuario.setText(lblUsuario.getText()+" "+usuario+"   ");
        //String textoRecinto= "<html><body>Recinto<br>(Ubicación<br>Ganado)</body></html>";
        lblSitio.setText(textoSitio);
        //lblRecinto.setText(textoRecinto);
        DefaultComboBoxModel valueProvincia = new DefaultComboBoxModel();
        DefaultTableModel model = new DefaultTableModel();
        DefaultComboBoxModel valueTipoIdentificacion = new DefaultComboBoxModel();

        JLabel Id = new JLabel();
        JLabel Tipo = new JLabel();
        JLabel Arete_Nombre = new JLabel();
        JLabel Chip = new JLabel();
        JLabel Raza = new JLabel();
        JLabel Origen = new JLabel();
        JLabel Procedencia = new JLabel();
        JLabel EdadMeses = new JLabel();
        JLabel ValorAsegurar = new JLabel();
        JButton btnEliminar = new JButton();


        //Cargar combo tipo identificacion
        cmb_TipoIdentificacion.setModel(valueTipoIdentificacion);
        tipoIdentificacions = Main.getCurrentTransporteData().getTiposIdentificacion();
        for (TipoIdentificacion tipo : tipoIdentificacions){
            //cmb_TipoIdentificacion.insertItemAt(tipo.Nombre,new Integer(tipo.Codigo.intValue()));
            valueTipoIdentificacion.addElement(tipo);
        }

        //combo provincia
        cmb_Provincia.setModel(valueProvincia);
        provi = Main.getCurrentTransporteData().getProvincias();
        for (Provincia prov : provi){
            valueProvincia.addElement(prov);
        }

        //crear nuevo registro en los combos
        //cmb_Agencia.insertItemAt("Seleccionar", 0);
        cmb_Provincia.insertItemAt("Seleccionar",0);
        cmb_Canton.insertItemAt("Seleccionar",0);
        cmb_Parroquia.insertItemAt("Seleccionar",0);

        //Inicializar los combos
        cmb_Provincia.setSelectedIndex(0);
        cmb_Canton.setSelectedIndex(0);
        cmb_Parroquia.setSelectedIndex(0);
        cmb_TipoIdentificacion.setSelectedIndex(0);

        //Inicializar los radio button
        rbn_TipoProducCarne.setSelected(true);
        rbn_RegionCosta.setSelected(true);
        rbn_AsistenciaNo.setSelected(true);
        rbn_IngresoSi.setSelected(true);
        rbn_Altitud1.setSelected(true);

        dtFechaSolicitud.setDate(fecha);
        // deshabilitar la fecha de solicitud de cotizacion
        ((JTextField)dtFechaSolicitud.getDateEditor().getUiComponent()).setEditable(false);
        dtFechaSolicitud.setEnabled(false);

        dtFechaSolicitud.setLocale(local);
        ImageIcon icono = new ImageIcon(getClass().getResource("/micotizador/offline/image/Eliminar.png"));
        //Cargar Tabla
        Id.setName("ID");
        Tipo.setName("Tipo");
        Arete_Nombre.setName("#Arete/Nombre");
        Chip.setName("Nro. Chip");
        Raza.setName("Raza");
        Origen.setName("Origen");
        Procedencia.setName("Procedencia");
        EdadMeses.setName("Edad(Meses)");
        ValorAsegurar.setName("Valor Asegurado");
        btnEliminar.setText("Eliminar");
        btnEliminar.setIcon(icono);


        model.addColumn(Id.getName());
        model.addColumn(Tipo.getName());
        model.addColumn(Arete_Nombre.getName());
        model.addColumn(Chip.getName());
        model.addColumn(Raza.getName());
        model.addColumn(Origen.getName());
        model.addColumn(Procedencia.getName());
        model.addColumn(EdadMeses.getName());
        model.addColumn(ValorAsegurar.getName());
        model.addColumn(btnEliminar.getText());
        tableListaAnimales.setModel(model);

        Object []object = new Object[10];
        object[0]= Id.getName();
        object[1]= Tipo.getName();
        object[2]= Arete_Nombre.getName();
        object[3]= Chip.getName();
        object[4]= Raza.getName();
        object[5]=Origen.getName();
        object[6]=Procedencia.getName();
        object[7]=EdadMeses.getName();
        object[8]=ValorAsegurar.getName();
        object[9]=btnEliminar.getText();
        model.addRow(object);
        if (CurrentTransporteCotizacionGanadero!=null) {
            for (ObjetoGanaderoDetalle animal : CurrentTransporteCotizacionGanadero.getCotizacionGanaderoDetalle()) {
                object[1] = animal.getTipoAnimal();
                object[2] = animal.getAreteNombre();
                object[3] = animal.getNroChip();
                object[4] = animal.getRaza();
                object[5] = animal.getOrigen();
                object[6] = animal.getProcedencia();
                object[7] = animal.getEdad();
                object[8] = animal.getValorAsegurar();
                model.addRow(object);
            }
        }
        // Establecemos el renderer y editor que usaremos para el boton
        tableListaAnimales.getColumn("Eliminar").setCellRenderer(new ButtonRendererAnimal());
        //tableCotizaciones.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));
        tableListaAnimales.getColumn("Eliminar").setCellEditor(new ButtonEditorAnimal(new JCheckBox(),tableListaAnimales,this));
        //Ocultamos la columna con el ID
        tableListaAnimales.getColumnModel().getColumn(0).setMaxWidth(0);
        tableListaAnimales.getColumnModel().getColumn(0).setMinWidth(0);
        tableListaAnimales.getColumnModel().getColumn(0).setPreferredWidth(0);

        cmb_Provincia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmb_Provincia.getSelectedIndex()==0)
                    CargarCanton(String.valueOf(cmb_Provincia.getSelectedIndex()));
                else {
                    Provincia prov = (Provincia)cmb_Provincia.getSelectedItem();
                    CargarCanton(prov.getProvinciaId());
                }
            }
        });
        cmb_Canton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmb_Canton.getSelectedIndex()==0){
                    CargarParroquia(String.valueOf(cmb_Canton.getSelectedIndex()));
                }
                else {
                Canton canton = (Canton)cmb_Canton.getSelectedItem();
                CargarParroquia(canton.getCantonId());
                }
            }
        });
        btnAgregarAnimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Animal animal = new Animal();
                animal.setIconImage(icon);
                //animal.setResizable(false);
                animal.setLocationRelativeTo(null);
                animal.setSize(550,280);
                animal.setModal(true);
                animal.setVisible(true);
            }
        });
        rbn_AsistenciaSi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_AsistenciaSi.isSelected())
                    rbn_AsistenciaNo.setSelected(false);
                else
                    rbn_AsistenciaNo.setSelected(true);
            }
        });
        rbn_AsistenciaNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_AsistenciaNo.isSelected())
                    rbn_AsistenciaSi.setSelected(false);
                else
                    rbn_AsistenciaSi.setSelected(true);
            }
        });
        rbn_TipoProducCarne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_TipoProducCarne.isSelected()){
                    rbn_TipoProducLeche.setSelected(false);
                    rbn_TipoProducOtroTipo.setSelected(false);
                }
                else {
                    rbn_TipoProducLeche.setSelected(true);
                    rbn_TipoProducCarne.setSelected(false);
                    rbn_TipoProducOtroTipo.setSelected(false);
                }
            }
        });
        rbn_TipoProducLeche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_TipoProducLeche.isSelected()){
                    rbn_TipoProducCarne.setSelected(false);
                    rbn_TipoProducOtroTipo.setSelected(false);
                }
                else {
                    rbn_TipoProducCarne.setSelected(true);
                    rbn_TipoProducLeche.setSelected(false);
                    rbn_TipoProducOtroTipo.setSelected(false);
                }
            }
        });

        rbn_TipoProducOtroTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_TipoProducOtroTipo.isSelected()){
                    rbn_TipoProducCarne.setSelected(false);
                    rbn_TipoProducLeche.setSelected(false);
                }
                else {
                    rbn_TipoProducCarne.setSelected(true);
                    rbn_TipoProducLeche.setSelected(false);
                    rbn_TipoProducOtroTipo.setSelected(false);
                }
            }
        });
        rbn_RegionCosta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_RegionCosta.isSelected()){
                    rbn_RegionSierra.setSelected(false);
                    rbn_RegionOriente.setSelected(false);
                }
                else {
                    rbn_RegionSierra.setSelected(true);
                    rbn_RegionOriente.setSelected(false);
                }
            }
        });
        rbn_RegionSierra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_RegionSierra.isSelected()){
                    rbn_RegionOriente.setSelected(false);
                    rbn_RegionCosta.setSelected(false);
                }
                else {
                    rbn_RegionOriente.setSelected(true);
                    rbn_RegionCosta.setSelected(false);
                }
            }
        });
        rbn_RegionOriente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_RegionOriente.isSelected()){
                    rbn_RegionSierra.setSelected(false);
                    rbn_RegionCosta.setSelected(false);
                }
                else {
                    rbn_RegionCosta.setSelected(true);
                    rbn_RegionSierra.setSelected(false);
                }
            }
        });
        rbn_IngresoSi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_IngresoSi.isSelected())
                    rbn_IngresoNo.setSelected(false);
                else
                    rbn_IngresoNo.setSelected(true);
            }
        });
        rbn_IngresoNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_IngresoNo.isSelected())
                    rbn_IngresoSi.setSelected(false);
                else
                    rbn_IngresoSi.setSelected(true);
            }
        });
        rbn_Altitud1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_Altitud1.isSelected()){
                    rbn_Altitud2.setSelected(false);
                    rbn_Altitud3.setSelected(false);
                    rbn_Altitud4.setSelected(false);
                }
                else {
                    rbn_Altitud2.setSelected(true);
                    rbn_Altitud3.setSelected(false);
                    rbn_Altitud4.setSelected(false);
                }
            }
        });
        rbn_Altitud2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_Altitud2.isSelected()){
                    rbn_Altitud1.setSelected(false);
                    rbn_Altitud3.setSelected(false);
                    rbn_Altitud4.setSelected(false);
                }
                else{
                    rbn_Altitud1.setSelected(true);
                    rbn_Altitud3.setSelected(false);
                    rbn_Altitud4.setSelected(false);
                }
            }
        });
        rbn_Altitud3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_Altitud3.isSelected()){
                    rbn_Altitud2.setSelected(false);
                    rbn_Altitud1.setSelected(false);
                    rbn_Altitud4.setSelected(false);
                }
                else {
                    rbn_Altitud1.setSelected(true);
                    rbn_Altitud2.setSelected(false);
                    rbn_Altitud4.setSelected(false);
                }
            }
        });
        rbn_Altitud4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbn_Altitud4.isSelected()){
                    rbn_Altitud1.setSelected(false);
                    rbn_Altitud2.setSelected(false);
                    rbn_Altitud3.setSelected(false);
                }
                else {
                    rbn_Altitud1.setSelected(true);
                    rbn_Altitud2.setSelected(false);
                    rbn_Altitud3.setSelected(false);
                }
            }
        });

        txtNumeroIdentificacion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);

                if (!a.matches("([0-9]|)"))
                    e.consume();
                TipoIdentificacion tipoId = (TipoIdentificacion)cmb_TipoIdentificacion.getSelectedItem();
                if (txtNumeroIdentificacion.getText().length()==10 && (tipoId.getId().toString().equals("1")
                        ||tipoId.getId().toString().equals("2")))
                    e.consume();
                if (txtNumeroIdentificacion.getText().length()>=13)
                    e.consume();
            }
        });
        txtTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();

                if (txtTelefono.getText().length()>=10)
                    e.consume();
            }
        });
        txtCelular.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();

                if (txtCelular.getText().length()>=10)
                    e.consume();
            }
        });
        txtCantidadAnimales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
            }
        });
        txtEnfermedadMatistis.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
                if (txtEnfermedadMatistis.getText().length()>=1)
                    e.consume();
            }
        });
        txtEnfermedadPanadizo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
                if (txtEnfermedadPanadizo.getText().length()>=1)
                    e.consume();
            }
        });
        txtEnfermedadFiebre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
                if (txtEnfermedadFiebre.getText().length()>=1)
                    e.consume();
            }
        });
        txtEnfermedadLesion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
                if (txtEnfermedadLesion.getText().length()>=1)
                    e.consume();
            }
        });
        txtEnfermedadNeumonia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
                if (txtEnfermedadNeumonia.getText().length()>=1)
                    e.consume();
            }
        });
        txtEnfermedadOtras.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
                if (txtEnfermedadOtras.getText().length()>=1)
                    e.consume();
            }
        });

        txtTopPlana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
            }
        });
        txtTopPendiente1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
            }
        });
        txtTopPendiente2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
            }
        });
        txtExperienciaCrianza.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();
            }
        });

        grabarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void CargarCanton(String provinciaId) {
        DefaultComboBoxModel valueCanton = new DefaultComboBoxModel();
        cmb_Canton.setModel(valueCanton);
        if (provinciaId.equals("0")) {
            valueCanton.removeAllElements();
            cmb_Canton.insertItemAt("Seleccionar", 0);
            cmb_Canton.setSelectedIndex(0);
            return;
        }
        cantones = Main.getCurrentTransporteData().getCantones();
        for (Canton canton : cantones) {
            if (canton.getProvinciaId().equals(provinciaId))
                valueCanton.addElement(canton);
        }
        cmb_Canton.insertItemAt("Seleccionar", 0);
        cmb_Canton.setSelectedIndex(0);
    }



    public void CargarParroquia(String CantonId) {
        DefaultComboBoxModel valueParrroquia = new DefaultComboBoxModel();
        cmb_Parroquia.setModel(valueParrroquia);
        if (CantonId.equals("0")) {
            valueParrroquia.removeAllElements();
            cmb_Parroquia.insertItemAt("Seleccionar", 0);
            cmb_Parroquia.setSelectedIndex(0);
            return;
        }
        parroquias = Main.getCurrentTransporteData().getParroquias();
        for (Parroquia parroquia : parroquias) {
            if (parroquia.getCantonId().equals(CantonId))
                valueParrroquia.addElement(parroquia);
        }
        cmb_Parroquia.insertItemAt("Seleccionar", 0);
        cmb_Parroquia.setSelectedIndex(0);
    }
    public void CargarAnimales (){
        List<ObjetoGanaderoDetalle> animales = new ArrayList<ObjetoGanaderoDetalle>();
        ObjetoGanaderoDetalle animal = new ObjetoGanaderoDetalle();
        animal.setTipoAnimal("TOROS");
        animal.setAreteNombre("344");
        animal.setNroChip("233");
        animal.setRaza("HOLSTEINER");
        animal.setOrigen("IMPORTADA");
        animal.setProcedencia("URUGUAY");
        animal.setEdad(24);
        animal.setValorAsegurar(new Double("1500"));
        animales.add(animal);
        CurrentTransporteCotizacionGanadero.setCotizacionGanaderoDetalle(animales);
    }
    public static TransporteCotizacionGanadero getCurrentTransporteCotizacionGanadero() {
        return CurrentTransporteCotizacionGanadero;
    }

    public static void setCurrentTransporteCotizacionGanadero(TransporteCotizacionGanadero currentTransporteCotizacionGanadero) {
        CurrentTransporteCotizacionGanadero = currentTransporteCotizacionGanadero;
    }
}

//clases creadas para permitir eventos en los botones de la tabla
class ButtonRendererAnimal extends JButton implements TableCellRenderer {

    public ButtonRendererAnimal() {
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

class ButtonEditorAnimal extends DefaultCellEditor {
    protected JButton button;

    private String label;

    private boolean isPushed;
    private JTable tableAnimal;
    private List<CotizacionAgricola>CotizacionSolicitadas = new ArrayList<CotizacionAgricola>();
    private static TransporteCotizaciones CurrentTransporteCotizaciones;
    private  CotizadorGanadero pantConsulta;
    private  String ProductoCotizacion;

    public ButtonEditorAnimal(JCheckBox checkBox,JTable tablaAnimal, CotizadorGanadero principal) {
        super(checkBox);
        tableAnimal = tablaAnimal;
        pantConsulta = principal;
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
            if (row!=0){
                int response = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar?");
                if (response!=JOptionPane.OK_OPTION){
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
    public void ConsultarFila(int row){
        //String objetoID= objetoID = table.getValueAt(table.getSelectedRow(),0).toString();
        String objetoID = tableAnimal.getValueAt(row,0).toString();
        if (!objetoID.isEmpty()){
            ObtenerCotizaciones(objetoID);
            pantConsulta.dispose();

            ///Se elimian la fila y se deberia recargar la pagina
        }
    }
    public  void ObtenerCotizaciones(String ObtetoCotizacionID){

    }
}