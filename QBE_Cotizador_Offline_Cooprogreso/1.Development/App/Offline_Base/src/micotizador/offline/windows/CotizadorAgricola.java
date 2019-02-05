package micotizador.offline.windows;

import com.google.gson.Gson;
import com.toedter.calendar.JDateChooser;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Veronica Zhagui on 29/06/2015.
 */
public class CotizadorAgricola extends JDialog {
    //private JTextArea textArea1;
    private JButton grabarButton;
    private JPanel pnlPrincipal;
    private JComboBox cmb_Entidad;
    private JComboBox cmb_Agencia;
    //private JTextField txtFechaSolicitud;
    private JTextField txtNumeroIdentificacion;
    private JPanel PnlDatosCLiente;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtTelefono;
    private JTextField txtCelular;
    private JTextField txtEmail;
    private JPanel PnlDatosCredito;
    private JPanel PnlDatosSiembra;
    private JComboBox cmb_Provincia;
    private JComboBox cmb_Canton;
    private JTextField txtDireccionReferencia;
    private JComboBox cmb_TipoCultivo;
    private JComboBox cmb_Variedad;
    private JRadioButton rb_1Si;
    private JRadioButton rb_1No;
    private JRadioButton rb_2Si;
    private JRadioButton rb_2No;
    private JPanel PnlDatosAnalisisRiesgo;
    private JTextField txtAnalisisSiembra;
    private JTextField txtAnalisisObservaciones;
    private JRadioButton rb_3Si;
    private JRadioButton rb_3No;
    private JPanel PnlDatosPrima;
    private JPanel PnlPreguntas;
    private JLabel lblAgenca;
    private JLabel lblFechaSolicitud;
    private JLabel lblCreditoMontoCredito;
    private JLabel lblTipoIdentificacion;
    private JComboBox cmb_TipoIdentificacion;
    private JLabel lblNumeroIdentifiacion;
    private JLabel lblNombres;
    private JLabel lblApellidos;
    private JLabel lblTelefono;
    private JLabel lblCelular;
    private JLabel lblEmail;
    private JLabel lblProvincia;
    private JLabel lblCanton;
    private JLabel lblDireccionReferencia;
    private JLabel lblTipoCultivo;
    private JLabel lblVariedad;
    private JLabel lblPregunta1;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;
    private JLabel lblTotalHecLote;
    private JLabel lblTotalHecAsegurables;
    private JLabel lblFechaPrevistaSiembra;
    private JLabel lblAnalisisSiembra;
    private JLabel lblAnalisisMontoAsegurado;
    private JLabel lblAnalisisObservaciones;
    private JLabel lblPrimaNeta;
    private JLabel lblSuperBancos;
    private JLabel lblDerechoEmision;
    private JLabel lblIva;
    private JLabel lblRecargoSegCamp;
    private JLabel lblTotalPrima;
    private JLabel lblAnalisisMontoRecom;
    private JLabel lblMontoCredito;
    private JLabel lblTotalCredito;
    private JLabel lblSeguroCampesino;
    private JLabel lbl_vigencia_hasta;
    //private JFormattedTextField txtFechaPrevistaSiembra;
    private JFormattedTextField txtTotalHecLote;
    private JFormattedTextField txtTotalHecAsegurables;
    private JFormattedTextField txtCreditoMontoCredito;
    private JFormattedTextField txtAnalisisMontoAsegurado;
    private JFormattedTextField txtAnalisisMontoRecom;
    private JFormattedTextField txtPrimaNeta;
    private JFormattedTextField txtSuperBancos;
    private JFormattedTextField txtDerechoEmision;
    private JFormattedTextField txtImpuestoAprox;
    private JFormattedTextField txtSeguroCampesino;
    private JFormattedTextField txtIva;
    private JFormattedTextField txtRecargoSegCamp;
    private JFormattedTextField txtTotalPrima;
    private JFormattedTextField txtMontoCredito;
    private JFormattedTextField txtTotalCredito;
    private JPanel PnlDate;
    private JPanel PnlTotalHectareas;
    private JPanel PnlUbicacion;
    private JDateChooser dtFechaSolicitud;
    private JDateChooser dtFechaSiembra;

    private JLabel lblUsuario;
    //private JTextField txtVariedad;
    private JLabel lblParroquia;
    private JComboBox cmb_Parroquia;
    private JLabel lblFormaCotizacion;
    private JComboBox cmb_FormaCotizacion;
    private JLabel lblEdadCultivo;
    private JFormattedTextField txtEdadCultivo;
    private JButton btnPendiente;
    private JTextField txt_Latitud;
    private JTextField txt_longitud;
    //private JComboBox cmbVariedad;
    private List<CotizacionAgricola>CotizacionSolicitadas = new ArrayList<CotizacionAgricola>();
    private String ObtetoCotizacionID="";
    private Date FechaCreacion = null;
    private List<Agencia>agencias = new ArrayList<Agencia>();
    private List<TipoIdentificacion> tipoIdentificacions = new ArrayList<TipoIdentificacion>();
    List<TipoCultivo>tiposCultivo = new ArrayList<TipoCultivo>();
    List<Provincia> provi = new ArrayList<Provincia>();
    List<Canton>cantones = new ArrayList<Canton>();
    List<Parroquia>parroquias = new ArrayList<Parroquia>();
    List<FormaCotizacion>tiposSeguro = new ArrayList<FormaCotizacion>();
    List<Variedad>variedades = new ArrayList<Variedad>();
    //private Image icon;
    //Creo unas variables para las ventanas abiertas
    private ConsultarCotizacion ventanaConsultar;
    private CotizadorAgricola ventanaCotizador;
    private BigInteger ReglaId;
    private Boolean TieneObservacion = false;
    private BigDecimal TasaCotizacion= new BigDecimal("0");

    private static TransporteCotizaciones CurrentTransporteCotizaciones;

    /*public String getDataInterna() {
        return dataInterna;
    }*/

    //private String dataInterna = "no seteado";

    public CotizadorAgricola(String objetoId, ConsultarCotizacion cc){
        // pone el nombre de la ventana

        super();

        cmb_Agencia.setVisible(false);
        lblAgenca.setVisible(false);
        //icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        //this.setIconImage(icon);
        ///Variables
        ObtetoCotizacionID = objetoId;
        ventanaConsultar = cc;
        //String a;

        //String usuario = User;
        Date fecha = new Date();
        Locale local = new Locale("es","EC");
        //Se utiliza un ComboBoxModel para cargar los valores de los combos
        DefaultComboBoxModel valueTipoIdentificacion = new DefaultComboBoxModel();
        DefaultComboBoxModel valueAgencia = new DefaultComboBoxModel();
        DefaultComboBoxModel valueProvincia = new DefaultComboBoxModel();
        DefaultComboBoxModel valueTipoCultivo = new DefaultComboBoxModel();
        DefaultComboBoxModel valueFormaCotizacion = new DefaultComboBoxModel();
        DefaultComboBoxModel valueVariedad = new DefaultComboBoxModel();

        ///Carga el combo agencia
        /*cmb_Agencia.setModel(valueAgencia);
        if (Main.getCurrentTransporteData()!=null)
            agencias = Main.getCurrentTransporteData().getAgencias();
        if (agencias!=null) {
            for (Agencia agencia : agencias) {
                //cmb_Agencia.insertItemAt(agencia.getNombre(), new Integer(agencia.getCodigo().intValue()));
                String PuntoVentaId = Main.getCurrentConfiguration().getPuntoVentaId();
                if (agencia.getPuntoVentaId().equals(new BigInteger(PuntoVentaId)))
                    valueAgencia.addElement(agencia);
            }
        }*/
        //Cargar combo tipo identificacion

        cmb_TipoIdentificacion.setModel(valueTipoIdentificacion);
        tipoIdentificacions = Main.getCurrentTransporteData().getTiposIdentificacion();
        for (TipoIdentificacion tipo : tipoIdentificacions){
            //cmb_TipoIdentificacion.insertItemAt(tipo.Nombre,new Integer(tipo.Codigo.intValue()));
            valueTipoIdentificacion.addElement(tipo);
        }

        // combo Tipo de cultivo
        tiposCultivo=Main.getCurrentTransporteData().getTiposCultivos();
        cmb_TipoCultivo.setModel(valueTipoCultivo);
        for(TipoCultivo tipoCultivo:tiposCultivo){
            valueTipoCultivo.addElement(tipoCultivo);
        }
        //combo provincia
        cmb_Provincia.setModel(valueProvincia);
         provi = Main.getCurrentTransporteData().getProvincias();
        for (Provincia prov : provi){
            valueProvincia.addElement(prov);
        }
        CargarTipoSeguro();
        cmb_FormaCotizacion.setModel(valueFormaCotizacion);
        for (FormaCotizacion tipoSeguro:tiposSeguro)
                valueFormaCotizacion.addElement(tipoSeguro);

        cmb_Variedad.setModel(valueVariedad);
        variedades = Main.getCurrentTransporteData().getVariedades();
        for (Variedad variedad : variedades)
                valueVariedad.addElement(variedad);

        //crear nuevo registro en los combos
        //cmb_Agencia.insertItemAt("Seleccionar", 0);
        cmb_Provincia.insertItemAt("Seleccionar",0);
        cmb_Canton.insertItemAt("Seleccionar",0);
        cmb_Parroquia.insertItemAt("Seleccionar",0);
        cmb_TipoCultivo.insertItemAt("Seleccionar",0);
        cmb_FormaCotizacion.insertItemAt("Seleccionar",0);
        cmb_Variedad.insertItemAt("Seleccionar",0);
        //combo canton
        //CargarCanton(String.valueOf(cmb_Provincia.getSelectedIndex()));


        //Obtener el usuario logeado
        String usuario = Main.getCurrentUser().getName()+" " +(Main.getCurrentUser().getLastName()==null?"": Main.getCurrentUser().getLastName());
        lblUsuario.setText(lblUsuario.getText()+" "+usuario+"   ");

        //Inicializar campos
        setCamposValores();//Los valores inician con 0.00
        dtFechaSolicitud.setDate(fecha);
        dtFechaSiembra.setDate(fecha);
        HabilitarFechas();//Habilitar la fechas unicamente dentro del rango

        rb_1No.setSelected(true);
        rb_2No.setSelected(true);
        rb_3No.setSelected(true);
        //cmb_Agencia.setSelectedIndex(0);
        cmb_TipoIdentificacion.setSelectedIndex(0);
        cmb_Provincia.setSelectedIndex(0);
        cmb_Canton.setSelectedIndex(0);
        cmb_Parroquia.setSelectedIndex(0);
        cmb_TipoCultivo.setSelectedIndex(0);
        cmb_FormaCotizacion.setSelectedIndex(0);
        cmb_Variedad.setSelectedIndex(0);
        ((JTextField)dtFechaSiembra.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField)dtFechaSolicitud.getDateEditor().getUiComponent()).setEditable(false);

        // deshabilitar la fecha de solicitud de cotizacion
        dtFechaSolicitud.setEnabled(false);

        dtFechaSolicitud.setLocale(local);
        dtFechaSiembra.setLocale(local);


        cmb_FormaCotizacion.setVisible(false);
        lblFormaCotizacion.setVisible(false);
        txtEdadCultivo.setVisible(false);
        lblEdadCultivo.setVisible(false);
        lblVariedad.setVisible(false);
        cmb_Variedad.setVisible(false);
        // establece el panel (del diseño) que va a presentarse dentro del contenido del JFrame
        setContentPane(pnlPrincipal);
        // cambia el tamaño de la ventana en función del tamaño del contenido
        pack();
        if (!objetoId.isEmpty())
            CargarCotizacion();
        ventanaCotizador = this;
        grabarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String campos = ValidarCampos();
                if (campos.length()>0){
                    JOptionPane.showMessageDialog(null,"Debe Ingresar los campos obligatorios:\n* "+campos,"Aviso",1);
                    return;
                }
                if (txtCelular.getText().length()<10){
                    JOptionPane.showMessageDialog(null, "Advertencia: Debe Ingresar un número de celular válido");
                    return;
                }
                if (!txtEmail.getText().isEmpty()) {
                    if (!txtEmail.getText().matches(("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))) {
                        JOptionPane.showMessageDialog(null, "Advertencia: Debe Ingresar un email válido");
                        return;
                    }
                }

                /*if (!txtEmail.getText().matches(("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))){
                    JOptionPane.showMessageDialog(null,"Advertencia: Debe Ingresar un email válido");
                    return;
                }*/
                TipoIdentificacion tipoId = (TipoIdentificacion)cmb_TipoIdentificacion.getSelectedItem();
                if ((tipoId.getId().toString().equals("1")|| tipoId.getId().toString().equals("2"))
                        && txtNumeroIdentificacion.getText().length()<10)
                {
                    JOptionPane.showMessageDialog(null,"Error validación de documento. ");
                    return;
                }
                if ((tipoId.getId().toString().equals("3")|| tipoId.getId().toString().equals("4"))
                        && txtNumeroIdentificacion.getText().length()<13)
                {
                    JOptionPane.showMessageDialog(null,"Error validación de documento. ");
                    return;
                }
                String strResult = Validate_Identification.Validate_Identification(txtNumeroIdentificacion.getText());
                if (!strResult.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Error validación de documento. "+ strResult);
                    //txtCedula.Focus();
                    txtNumeroIdentificacion.requestFocus();
                    return;
                }
                String msRegla="";
                if (txtAnalisisSiembra.getText().isEmpty()){
                    //JOptionPane.showMessageDialog(null,"No se puede aceptar una solicitud 'No recomendada'");
                    //return;
                    msRegla=msRegla+"No se a encontrado una regla para el cálculo,la solicitud es \"No Recomendado\".\n ";
                }
                if (txtAnalisisSiembra.getBackground().equals(Color.magenta)) {
                    msRegla=msRegla+"Existen advertencias para la cotización \"No Recomendado\".\n ";
                }
                else if (TieneObservacion){
                    msRegla=msRegla+"La cotización tiene observaciones de regla.\n ";
                }
                int response=0;

                Regla regla = ObtenerRegla();
                if(regla!=null){
                    CalcularValores(regla);
                }
                else {
                    txtAnalisisSiembra.setBackground(Color.RED);
                    txtAnalisisSiembra.setText("No recomendado, no existe regla");
                }

                double valorMonetario=0;
                valorMonetario= Double.parseDouble(txtPrimaNeta.getText());
                if(valorMonetario!=0 && !TieneObservacion ) {
                    response = JOptionPane.showConfirmDialog(null, msRegla +"¿Está seguro de Grabar la Solicitud? será enviada como Aprobada.");
                }else{
                    response = JOptionPane.showConfirmDialog(null, msRegla +  "¿Está seguro de Grabar la Solicitud? será enviada como pendiente de Aprobación.");
                }

                if (response!=JOptionPane.OK_OPTION)return;
                try {
                    GrabarCotizacion(1);//Si  la cotizacion se graba completa
                    Boolean resp = GuardarCotizacion();
                    if (resp==true){
                        LimpiarCampos();
                        CotizacionSolicitadas.clear();
                        JOptionPane.showMessageDialog(null, "Datos registrados correctamente");
                    }
                    else {
                        CotizacionSolicitadas.clear();
                        return;
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Se ha producido un error: " + e1.getMessage());
                }
                if (ventanaConsultar!=null){
                    ventanaCotizador.dispose();
                    ventanaConsultar.dispose();
                }
            }
        });
        rb_2Si.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rb_2Si.isSelected())
                    rb_2No.setSelected(false);
                else
                    rb_2No.setSelected(true);
            }
        });
        rb_2No.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rb_2No.isSelected())
                    rb_2Si.setSelected(false);
                else
                    rb_2Si.setSelected(true);
            }
        });
        rb_1Si.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rb_1Si.isSelected())
                    rb_1No.setSelected(false);
                else
                    rb_1No.setSelected(true);
                //comprobamos regla

                if (cmb_TipoCultivo.getSelectedIndex()==0){
                    cmb_FormaCotizacion.setSelectedIndex(0);
                    txtEdadCultivo.setText("");
                    lblFormaCotizacion.setVisible(false);
                    cmb_FormaCotizacion.setVisible(false);
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                    lblVariedad.setVisible(false);
                    cmb_Variedad.setVisible(false);
                    CargarParroquia(String.valueOf(cmb_TipoCultivo.getSelectedIndex()));
                    setCamposValores();
                    return;
                }
                TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                DefaultComboBoxModel valueVariedad = new DefaultComboBoxModel();
                cmb_Variedad.setModel(valueVariedad);
                cmb_Variedad.insertItemAt("Seleccionar",0);
                for (Variedad varieda : variedades){
                    if (varieda.getTipoCultivoId().equals(cultivo.getTipoCultivoId()))
                        valueVariedad.addElement(varieda);
                }
                //cmb_Variedad.insertItemAt("Seleccionar",0);
                cmb_Variedad.setSelectedIndex(0);
                ///Si es Tipo de cultivo Perenne
                /*if (cultivo.getTipo()==1){
                    lblFormaCotizacion.setVisible(true);
                    cmb_FormaCotizacion.setVisible(true);
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                    if (cmb_FormaCotizacion.getSelectedIndex()==0){
                        setCamposValores();
                        if (valueVariedad.getSize()<=1) {
                            lblVariedad.setVisible(false);
                            cmb_Variedad.setVisible(false);
                        }
                        else {
                            lblVariedad.setVisible(true);
                            cmb_Variedad.setVisible(true);
                        }
                        JOptionPane.showMessageDialog(null, "Debe ingresar (Forma Cotización) para el tipo de cultivo seleccionado");
                        return;
                    }
                    FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                    if (tipoSeguro.getId()==1)//Si es seguro completo
                    {
                        lblEdadCultivo.setVisible(true);
                        txtEdadCultivo.setVisible(true);

                        if (txtEdadCultivo.getText().isEmpty()){
                            setCamposValores();
                            JOptionPane.showMessageDialog(null, "Debe ingresar (Edad del Cultivo) para la forma de cotización seleccionada");
                            return;
                        }
                    }else{
                        lblEdadCultivo.setVisible(false);
                        txtEdadCultivo.setVisible(false);
                    }

                }
                else {*/
                cmb_FormaCotizacion.setSelectedIndex(0);
                txtEdadCultivo.setText("");
                lblFormaCotizacion.setVisible(false);
                cmb_FormaCotizacion.setVisible(false);
                lblEdadCultivo.setVisible(false);
                txtEdadCultivo.setVisible(false);
                //}

                if (valueVariedad.getSize()<=1){
                    lblVariedad.setVisible(false);
                    cmb_Variedad.setVisible(false);
                }
                else {
                    lblVariedad.setVisible(true);
                    cmb_Variedad.setVisible(true);
                }
                if (txtTotalHecLote.getText().isEmpty() || txtTotalHecAsegurables.getText().isEmpty())
                    return;

                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){

                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }

            }
        });
        rb_1No.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rb_1No.isSelected())
                    rb_1Si.setSelected(false);
                else
                    rb_1Si.setSelected(true);

                if (cmb_TipoCultivo.getSelectedIndex()==0){
                    cmb_FormaCotizacion.setSelectedIndex(0);
                    txtEdadCultivo.setText("");
                    lblFormaCotizacion.setVisible(false);
                    cmb_FormaCotizacion.setVisible(false);
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                    lblVariedad.setVisible(false);
                    cmb_Variedad.setVisible(false);
                    CargarParroquia(String.valueOf(cmb_TipoCultivo.getSelectedIndex()));
                    setCamposValores();
                    return;
                }
                TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                DefaultComboBoxModel valueVariedad = new DefaultComboBoxModel();
                cmb_Variedad.setModel(valueVariedad);
                cmb_Variedad.insertItemAt("Seleccionar",0);
                for (Variedad varieda : variedades){
                    if (varieda.getTipoCultivoId().equals(cultivo.getTipoCultivoId()))
                        valueVariedad.addElement(varieda);
                }
                //cmb_Variedad.insertItemAt("Seleccionar",0);
                cmb_Variedad.setSelectedIndex(0);
                ///Si es Tipo de cultivo Perenne
                /*if (cultivo.getTipo()==1){
                    lblFormaCotizacion.setVisible(true);
                    cmb_FormaCotizacion.setVisible(true);
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                    if (cmb_FormaCotizacion.getSelectedIndex()==0){
                        setCamposValores();
                        if (valueVariedad.getSize()<=1) {
                            lblVariedad.setVisible(false);
                            cmb_Variedad.setVisible(false);
                        }
                        else {
                            lblVariedad.setVisible(true);
                            cmb_Variedad.setVisible(true);
                        }
                        JOptionPane.showMessageDialog(null, "Debe ingresar (Forma Cotización) para el tipo de cultivo seleccionado");
                        return;
                    }
                    FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                    if (tipoSeguro.getId()==1)//Si es seguro completo
                    {
                        lblEdadCultivo.setVisible(true);
                        txtEdadCultivo.setVisible(true);

                        if (txtEdadCultivo.getText().isEmpty()){
                            setCamposValores();
                            JOptionPane.showMessageDialog(null, "Debe ingresar (Edad del Cultivo) para la forma de cotización seleccionada");
                            return;
                        }
                    }else{
                        lblEdadCultivo.setVisible(false);
                        txtEdadCultivo.setVisible(false);
                    }

                }
                else {*/
                cmb_FormaCotizacion.setSelectedIndex(0);
                txtEdadCultivo.setText("");
                lblFormaCotizacion.setVisible(false);
                cmb_FormaCotizacion.setVisible(false);
                lblEdadCultivo.setVisible(false);
                txtEdadCultivo.setVisible(false);
                //}

                if (valueVariedad.getSize()<=1){
                    lblVariedad.setVisible(false);
                    cmb_Variedad.setVisible(false);
                }
                else {
                    lblVariedad.setVisible(true);
                    cmb_Variedad.setVisible(true);
                }
                if (txtTotalHecLote.getText().isEmpty() || txtTotalHecAsegurables.getText().isEmpty())
                    return;

                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){

                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }
            }
        });
        rb_3Si.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rb_3Si.isSelected())
                    rb_3No.setSelected(false);
                else
                    rb_3No.setSelected(true);
            }
        });

        rb_3No.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rb_3No.isSelected())
                    rb_3Si.setSelected(false);
                else
                    rb_3Si.setSelected(true);
            }
        });

        txtTotalHecLote.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                String a = String.valueOf(c);
                //Si el caracter ingresado es letra no permita ingresar
                /*if (Character.isLetter(c)){
                    getToolkit().beep();
                    e.consume();
                }*/
                //Validad que se ingresen solo números y punto de decimal
                if (!a.matches("([0-9]|\\.)+"))
                    e.consume();

                //Validar que no permita ingresar como primer caracter el punto
                if (c == '.' && txtTotalHecLote.getText().charAt(0) == '.') {
                    e.consume();
                }

                //Validar un solo punto en el campo
                if (c == '.' && txtTotalHecLote.getText().contains(".")) {
                    e.consume();
                }

                //Validar dos decimales
                if (txtTotalHecLote.getText().contains(".")) {
                    String cadena = txtTotalHecLote.getText().substring(txtTotalHecLote.getText().indexOf(".") + 1);
                    if (cadena.length() > 1)
                        e.consume();
                }

                //Validar Longitud
                if (txtTotalHecLote.getText().length() == 10) {
                    if (c != '.')
                        e.consume();
                }
                if (txtTotalHecLote.getText().length() > 12 && txtTotalHecLote.getText().contains(".")) {
                    e.consume();
                }
                //txtTotalHecAsegurables.setText(txtTotalHecLote.getText());
            }
        });
        txtTotalHecLote.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                /*if (e.getKeyCode()==8)
                    txtTotalHecLote.setText("");
                if (txtTotalHecLote.getText().contains(".")){
                String cadena = txtTotalHecLote.getText().substring(txtTotalHecLote.getText().indexOf(".")+1);
                   // JOptionPane.showMessageDialog(null,cadena);
                if (cadena.length()>=0)
                    txtTotalHecLote.setText(txtTotalHecLote.getText());
                else
                    txtTotalHecLote.setText(txtTotalHecLote.getText()+"00");
                }*/
                /*if (txtTotalHecLote.getText().isEmpty()){
                    //no se envian las hectareas peticion de cambio
                    txtTotalHecAsegurables.setText(txtTotalHecLote.getText());
                    setCamposValores();
                    return;
                }*/

                //no se envian las hectareas peticion de cambio
                //txtTotalHecAsegurables.setText(txtTotalHecLote.getText());
                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){
                    TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                    ///Si es Tipo de cultivo Perenne
                    if (cultivo.getTipo()==1){
                        if (cmb_FormaCotizacion.getSelectedIndex()==0){
                            setCamposValores();
                           return;
                        }
                        FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                        if (tipoSeguro.getId()==1)//Si es seguro completo
                        {
                            lblEdadCultivo.setVisible(true);
                            txtEdadCultivo.setVisible(true);

                            if (txtEdadCultivo.getText().isEmpty()){
                                setCamposValores();
                                JOptionPane.showMessageDialog(null, "Debe ingresar (Edad del Cultivo) para la forma de cotización seleccionada");
                                return;
                            }
                        }else{
                            lblEdadCultivo.setVisible(false);
                            txtEdadCultivo.setVisible(false);
                        }
                    }
                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }
            }
        });
        txtTotalHecAsegurables.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (txtTotalHecAsegurables.getText().isEmpty()){
                    setCamposValores();
                    return;
                }
                /*if (!txtTotalHecAsegurables.getText().contains(".") && !txtTotalHecAsegurables.getText().isEmpty()
                        && e.getKeyCode() != 8 && e.getKeyChar() != '.') {
                    txtTotalHecAsegurables.setText(txtTotalHecAsegurables.getText() + ".00");
                }

                txtTotalHecAsegurables.setText(txtTotalHecAsegurables.getText());*/

                BigDecimal asegurable = new BigDecimal(txtTotalHecAsegurables.getText());
                BigDecimal lote = new BigDecimal(txtTotalHecLote.getText());
                if (asegurable.doubleValue()>lote.doubleValue()){
                    JOptionPane.showMessageDialog(null,"No se puede asegurar una cantidad de hectáreas mayor a las del lote");
                    txtTotalHecAsegurables.setText("0");
                    setCamposValores();
                    return;
                }
                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){
                    TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                    ///Si es Tipo de cultivo Perenne
                    /*if (cultivo.getTipo()==1){
                        if (cmb_FormaCotizacion.getSelectedIndex()==0){
                            setCamposValores();
                            return;
                        }
                        FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                        if (tipoSeguro.getId()==1)//Si es seguro completo
                        {
                            lblEdadCultivo.setVisible(true);
                            txtEdadCultivo.setVisible(true);

                            if (txtEdadCultivo.getText().isEmpty()){
                                setCamposValores();
                                JOptionPane.showMessageDialog(null, "Debe ingresar (Edad del Cultivo) para la forma de cotización seleccionada");
                                return;
                            }
                        }else{
                            lblEdadCultivo.setVisible(false);
                            txtEdadCultivo.setVisible(false);
                        }
                    }/*
                    /*
                    else{
                        if (cmb_FormaCotizacion.getSelectedIndex()==0){
                            setCamposValores();
                            return;
                        }
                        else {
                            FormaCotizacion tipoSeguro = (FormaCotizacion) cmb_FormaCotizacion.getSelectedItem();
                            if (tipoSeguro.getId() == 1)//Si es seguro completo
                                if (txtEdadCultivo.getText().isEmpty()) {
                                    setCamposValores();
                                    return;
                                }
                        }
                    }*/
                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }
            }
        });
        txtTotalHecAsegurables.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|\\.)+"))
                    e.consume();

                if (c == '.' && txtTotalHecAsegurables.getText().charAt(0)=='.')
                {
                    e.consume();
                }

                if (c == '.' && txtTotalHecAsegurables.getText().contains(".")) {
                    e.consume();
                }

                if (txtTotalHecAsegurables.getText().contains(".")){
                    String cadena = txtTotalHecAsegurables.getText().substring(txtTotalHecAsegurables.getText().indexOf(".")+1);
                    if (cadena.length()>1)
                        e.consume();
                }
                if (txtTotalHecAsegurables.getText().length()==10){
                    if (c!='.')
                        e.consume();
                }if (txtTotalHecAsegurables.getText().length()>12 && txtTotalHecAsegurables.getText().contains(".")){
                    e.consume();
                }
            }
        });
        txtCreditoMontoCredito.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                BigDecimal credito;
                BigDecimal TotalPrima;
                /*if (!txtCreditoMontoCredito.getText().contains(".") && !txtCreditoMontoCredito.getText().isEmpty()
                        && e.getKeyCode() != 8 && e.getKeyChar() != '.') {
                    txtCreditoMontoCredito.setText(txtCreditoMontoCredito.getText() + ".00");
                }*/

                //txtCreditoMontoCredito.setText(txtCreditoMontoCredito.getText());

                if (txtCreditoMontoCredito.getText().isEmpty()) {
                    credito = new BigDecimal("0.00");
                    TotalPrima = new BigDecimal(txtTotalPrima.getText());
                    txtMontoCredito.setText(credito.setScale(2,RoundingMode.HALF_UP).toString());
                    txtTotalCredito.setText(credito.add(TotalPrima).setScale(2, RoundingMode.HALF_UP).toString());
                } else {
                    credito = new BigDecimal(txtCreditoMontoCredito.getText());
                    TotalPrima = new BigDecimal(txtTotalPrima.getText());
                    txtMontoCredito.setText(credito.setScale(2,RoundingMode.HALF_UP).toString());
                    txtTotalCredito.setText(credito.add(TotalPrima).setScale(2, RoundingMode.HALF_UP).toString());
                }
            }
        });
        txtCreditoMontoCredito.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);

                if (!a.matches("([0-9]|\\.)+"))
                    e.consume();
                if (c == '.' && txtCreditoMontoCredito.getText().charAt(0)=='.')
                {
                    e.consume();
                }
                if (c == '.' && txtCreditoMontoCredito.getText().contains(".")) {
                    e.consume();
                }

                if (txtCreditoMontoCredito.getText().contains(".")){
                    String cadena = txtCreditoMontoCredito.getText().substring(txtCreditoMontoCredito.getText().indexOf(".")+1);
                    if (cadena.length()>1)
                        e.consume();
                }
                if (txtCreditoMontoCredito.getText().length()==10){
                    if (c!='.')
                        e.consume();
                }if (txtCreditoMontoCredito.getText().length()>12 && txtCreditoMontoCredito.getText().contains(".")){
                    e.consume();
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
        txtEdadCultivo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)"))
                    e.consume();

                if (txtEdadCultivo.getText().length()>=4)
                    e.consume();
            }
        });


        cmb_TipoCultivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (cmb_TipoCultivo.getSelectedIndex()==0){
                    cmb_FormaCotizacion.setSelectedIndex(0);
                    txtEdadCultivo.setText("");
                    lblFormaCotizacion.setVisible(false);
                    cmb_FormaCotizacion.setVisible(false);
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                    lblVariedad.setVisible(false);
                    cmb_Variedad.setVisible(false);
                    CargarParroquia(String.valueOf(cmb_TipoCultivo.getSelectedIndex()));
                    setCamposValores();
                    return;
                }
                TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                DefaultComboBoxModel valueVariedad = new DefaultComboBoxModel();
                cmb_Variedad.setModel(valueVariedad);
                cmb_Variedad.insertItemAt("Seleccionar",0);
                for (Variedad varieda : variedades){
                    if (varieda.getTipoCultivoId().equals(cultivo.getTipoCultivoId()))
                        valueVariedad.addElement(varieda);
                }
                //cmb_Variedad.insertItemAt("Seleccionar",0);
                cmb_Variedad.setSelectedIndex(0);
                ///Si es Tipo de cultivo Perenne
                /*if (cultivo.getTipo()==1){
                    lblFormaCotizacion.setVisible(true);
                    cmb_FormaCotizacion.setVisible(true);
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                    if (cmb_FormaCotizacion.getSelectedIndex()==0){
                        setCamposValores();
                        if (valueVariedad.getSize()<=1) {
                            lblVariedad.setVisible(false);
                            cmb_Variedad.setVisible(false);
                        }
                        else {
                            lblVariedad.setVisible(true);
                            cmb_Variedad.setVisible(true);
                        }
                        JOptionPane.showMessageDialog(null, "Debe ingresar (Forma Cotización) para el tipo de cultivo seleccionado");
                        return;
                    }
                    FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                    if (tipoSeguro.getId()==1)//Si es seguro completo
                    {
                        lblEdadCultivo.setVisible(true);
                        txtEdadCultivo.setVisible(true);

                        if (txtEdadCultivo.getText().isEmpty()){
                            setCamposValores();
                            JOptionPane.showMessageDialog(null, "Debe ingresar (Edad del Cultivo) para la forma de cotización seleccionada");
                            return;
                        }
                    }else{
                        lblEdadCultivo.setVisible(false);
                        txtEdadCultivo.setVisible(false);
                    }

                }
                else {*/
                    cmb_FormaCotizacion.setSelectedIndex(0);
                    txtEdadCultivo.setText("");
                    lblFormaCotizacion.setVisible(false);
                    cmb_FormaCotizacion.setVisible(false);
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                //}

                if (valueVariedad.getSize()<=1){
                    lblVariedad.setVisible(false);
                    cmb_Variedad.setVisible(false);
                }
                else {
                    lblVariedad.setVisible(true);
                    cmb_Variedad.setVisible(true);
                }
                if (txtTotalHecLote.getText().isEmpty() || txtTotalHecAsegurables.getText().isEmpty())
                    return;

                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){

                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }
            }
        });

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
        cmb_TipoIdentificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNumeroIdentificacion.setText("");
            }
        });


        dtFechaSolicitud.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                dtFechaSiembra.setDate(dtFechaSolicitud.getDate());
            }
        });
        dtFechaSolicitud.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                dtFechaSiembra.setDate(dtFechaSolicitud.getDate());
                HabilitarFechas();
            }
        });


        cmb_Canton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmb_Canton.getSelectedIndex()==0){
                    CargarParroquia(String.valueOf(cmb_Canton.getSelectedIndex()));
                    setCamposValores();
                    return;
                }
                Canton canton = (Canton)cmb_Canton.getSelectedItem();
                CargarParroquia(canton.getCantonId());
                if (txtTotalHecLote.getText().isEmpty() || txtTotalHecAsegurables.getText().isEmpty())
                    return;

                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){
                    TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                    ///Si es Tipo de cultivo Perenne
                    /*if (cultivo.getTipo()==1){
                        if (cmb_FormaCotizacion.getSelectedIndex()==0){
                            setCamposValores();
                            JOptionPane.showMessageDialog(null, "Debe ingresar (Forma Cotización) para el tipo de cultivo seleccionado");
                            return;
                        }
                        FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                        if (tipoSeguro.getId()==1)//Si es seguro completo
                        {
                            lblEdadCultivo.setVisible(true);
                            txtEdadCultivo.setVisible(true);

                            if (txtEdadCultivo.getText().isEmpty()){
                                setCamposValores();
                                JOptionPane.showMessageDialog(null, "Debe ingresar (Edad del Cultivo) para la forma de cotización seleccionada");
                                return;
                            }
                        }else{
                            lblEdadCultivo.setVisible(false);
                            txtEdadCultivo.setVisible(false);
                        }
                    }*/
                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }
            }
        });
        dtFechaSiembra.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (txtTotalHecLote.getText().isEmpty() || txtTotalHecAsegurables.getText().isEmpty())
                    return;
                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){
                    TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                    ///Si es Tipo de cultivo Perenne
                    /*if (cultivo.getTipo()==1 && cmb_FormaCotizacion.getSelectedIndex()==0 && txtEdadCultivo.getText().isEmpty()){
                        setCamposValores();
                        JOptionPane.showMessageDialog(null, "Debe ingresar forma de cotización y la edad del cultivo para el tipo de cultivo seleccionado");
                        return;
                    }*/
                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.white);
                        txtAnalisisSiembra.setText("");
                    }
                }
            }
        });

        txtEdadCultivo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (txtEdadCultivo.getText().isEmpty()){
                    setCamposValores();
                    return;
                }
                if (txtTotalHecLote.getText().isEmpty() || txtTotalHecAsegurables.getText().isEmpty())
                    return;
                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){
                    TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
                    ///Si es Tipo de cultivo Perenne
                    /*if (cultivo.getTipo()==1 && cmb_FormaCotizacion.getSelectedIndex()==0){
                        setCamposValores();
                        JOptionPane.showMessageDialog(null, "Debe ingresar (Forma Cotización) para el tipo de cultivo seleccionado");
                        return;
                    }*/

                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }
            }
        });
        cmb_FormaCotizacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmb_FormaCotizacion.getSelectedIndex()==0){
                    txtEdadCultivo.setText("");
                    setCamposValores();
                    return;
                }

                FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                if (tipoSeguro.getId()==1)//Si es seguro completo
                {
                    lblEdadCultivo.setVisible(true);
                    txtEdadCultivo.setVisible(true);

                    if (txtEdadCultivo.getText().isEmpty()){
                        setCamposValores();
                        JOptionPane.showMessageDialog(null, "Debe ingresar (Edad del Cultivo) para la forma de cotización seleccionada");
                        return;
                    }
                }else{
                    lblEdadCultivo.setVisible(false);
                    txtEdadCultivo.setVisible(false);
                }
                if (txtTotalHecLote.getText().isEmpty() || txtTotalHecAsegurables.getText().isEmpty())
                    return;
                if (tipoSeguro.getId()==2)
                    txtEdadCultivo.setText("");

                if (cmb_Canton.getSelectedIndex()!=0 && cmb_TipoCultivo.getSelectedIndex()!=0){
                    Regla regla = ObtenerRegla();
                    if(regla!=null){
                        CalcularValores(regla);
                    }
                    else {
                        txtAnalisisSiembra.setBackground(Color.RED);
                        txtAnalisisSiembra.setText("No recomendado, no existe regla");
                    }
                }
            }
        });
        btnPendiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String campos = ValidarCampos();

                TipoIdentificacion tipoId = (TipoIdentificacion)cmb_TipoIdentificacion.getSelectedItem();
                if ((tipoId.getId().toString().equals("1")|| tipoId.getId().toString().equals("2"))
                        && txtNumeroIdentificacion.getText().length()<10)
                {
                    JOptionPane.showMessageDialog(null,"Error validación de documento. ");
                    return;
                }
                if ((tipoId.getId().toString().equals("3")|| tipoId.getId().toString().equals("4"))
                        && txtNumeroIdentificacion.getText().length()<13)
                {
                    JOptionPane.showMessageDialog(null,"Error validación de documento. ");
                    return;
                }
                String strResult = Validate_Identification.Validate_Identification(txtNumeroIdentificacion.getText());
                if (!strResult.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Error validación de documento. "+ strResult);
                    //txtCedula.Focus();
                    txtNumeroIdentificacion.requestFocus();
                    return;
                }
                if (txtCelular.getText().length()<10){
                    JOptionPane.showMessageDialog(null, "Advertencia: Debe Ingresar un número de celular válido");
                    return;
                }
                if (!txtEmail.getText().isEmpty()) {
                    if (!txtEmail.getText().matches(("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))) {
                        JOptionPane.showMessageDialog(null, "Advertencia: Debe Ingresar un email válido");
                        return;
                    }
                }
                if (campos.length()>0)
                    campos = "Existen campos sin llenar, ";
                else
                    campos="";
                int response = JOptionPane.showConfirmDialog(null,campos+"Esta seguro de grabar?");
                if (response!=JOptionPane.OK_OPTION)return;
                try {
                    GrabarCotizacion(0); // si la cotizacion se guarda incompleta
                    Boolean resp = GuardarCotizacion();
                    if (resp==true){
                        LimpiarCampos();
                        CotizacionSolicitadas.clear();
                        JOptionPane.showMessageDialog(null, "Datos registrados correctamente");
                    }
                    else {
                        CotizacionSolicitadas.clear();
                        return;
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null,"Se ha producido un error: "+ e1.getMessage());
                }
                if (ventanaConsultar!=null){
                    ventanaCotizador.dispose();
                    ventanaConsultar.dispose();
                }
            }

        });
        txt_Latitud.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([-+ 0-9]|\\.)+"))
                    e.consume();

                if (c == '.' && txt_Latitud.getText().charAt(0)=='.')
                {
                    e.consume();
                }

                if (c == '.' && txt_Latitud.getText().contains(".")) {
                    e.consume();
                }

                if (txt_Latitud.getText().contains(".")){
                    String cadena = txt_Latitud.getText().substring(txt_Latitud.getText().indexOf(".")+1);
                    if (cadena.length()>5)
                        e.consume();
                }
                if (txt_Latitud.getText().length()==3 && !txt_Latitud.getText().contains(".")){
                    if (c!='.')
                        e.consume();
                }
                if (txt_Latitud.getText().length()>12 && txt_Latitud.getText().contains(".")){
                    e.consume();
                }
            }
        });
        txt_longitud.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([-+ 0-9]|\\.)+"))
                    e.consume();

                if (c == '.' && txt_longitud.getText().charAt(0)=='.')
                {
                    e.consume();
                }

                if (c == '.' && txt_longitud.getText().contains(".")) {
                    e.consume();
                }

                if (txt_longitud.getText().contains(".")){
                    String cadena = txt_longitud.getText().substring(txt_longitud.getText().indexOf(".")+1);
                    if (cadena.length()>5)
                        e.consume();
                }
                if (txt_longitud.getText().length()==3 && !txt_longitud.getText().contains(".")){
                    if (c!='.')
                        e.consume();
                }
                if (txt_longitud.getText().length()>12 && txt_longitud.getText().contains(".")){
                    e.consume();
                }
            }
        });
    }

    public  void CargarCanton(String provinciaId){
        DefaultComboBoxModel valueCanton = new DefaultComboBoxModel();
        cmb_Canton.setModel(valueCanton);
        if (provinciaId.equals("0")){
            valueCanton.removeAllElements();
            cmb_Canton.insertItemAt("Seleccionar", 0);
            cmb_Canton.setSelectedIndex(0);
            return;
        }
        cantones = Main.getCurrentTransporteData().getCantones();
        for (Canton canton: cantones){
            if (canton.getProvinciaId().equals(provinciaId))
                valueCanton.addElement(canton);
        }
        cmb_Canton.insertItemAt("Seleccionar", 0);
        cmb_Canton.setSelectedIndex(0);
    }
    public  void CargarParroquia(String CantonId){
        DefaultComboBoxModel valueParrroquia = new DefaultComboBoxModel();
        cmb_Parroquia.setModel(valueParrroquia);
        if (CantonId.equals("0")){
            valueParrroquia.removeAllElements();
            cmb_Parroquia.insertItemAt("Seleccionar", 0);
            cmb_Parroquia.setSelectedIndex(0);
            return;
        }
        parroquias = Main.getCurrentTransporteData().getParroquias();
        for (Parroquia parroquia: parroquias){
            if (parroquia.getCantonId().equals(CantonId))
                valueParrroquia.addElement(parroquia);
        }
        cmb_Parroquia.insertItemAt("Seleccionar", 0);
        cmb_Parroquia.setSelectedIndex(0);
    }

    public void CargarTipoSeguro(){
        tiposSeguro = new ArrayList<FormaCotizacion>();
        FormaCotizacion f1 = new FormaCotizacion();
        f1.setId(1);
        f1.setNombre("Cultivo Completo");

        FormaCotizacion f2 = new FormaCotizacion();
        f2.setId(2);
        f2.setNombre("Mantenimiento Anual");
        tiposSeguro.add(f1);
        tiposSeguro.add(f2);
    }

    public void GrabarCotizacion(Integer estado) throws ParseException {

        CotizacionAgricola cotizacion = new CotizacionAgricola();

        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String UsuarioNombre = Main.getCurrentUser().getName();
        String AgenciaId=Main.getCurrentUser().getAgencia();
        String UsuarioApelido="";
        String UsuarioPuntoVenta ="";
        if (Main.getCurrentUser().getLastName()!=null)
            UsuarioApelido= Main.getCurrentUser().getLastName();
        if (Main.getCurrentUser().getPuntoVentaId()!=null)
            UsuarioPuntoVenta = Main.getCurrentUser().getPuntoVentaId();
        if (ObtetoCotizacionID.isEmpty())
            cotizacion.setObjetoCotizacionId(java.util.UUID.randomUUID().toString());
        else
            cotizacion.setObjetoCotizacionId(ObtetoCotizacionID);
        /*if (cmb_Agencia.getSelectedIndex()!=0) {
            Agencia agencia = (Agencia)cmb_Agencia.getSelectedItem();
            cotizacion.setAgencia(agencia.getAgenciaId());
        }*/
        //guardo la sucursal de donde fue creado el cliente
        cotizacion.setAgencia(new BigInteger(AgenciaId));

        if (cmb_Provincia.getSelectedIndex()!=0) {
            Provincia provincia = (Provincia)cmb_Provincia.getSelectedItem();
            cotizacion.setProvinciaId(new BigInteger(provincia.getProvinciaId()));
        }
        if (cmb_Canton.getSelectedIndex()!=0) {
            Canton canton = (Canton)cmb_Canton.getSelectedItem();
            cotizacion.setCantonId(new BigInteger(canton.getCantonId()));
        }
        if (cmb_Parroquia.getSelectedIndex()!=0) {
            Parroquia parroquia = (Parroquia)cmb_Parroquia.getSelectedItem();
            cotizacion.setParroquiaId(new BigInteger(parroquia.getParroquiaId()));
        }

        if (cmb_TipoCultivo.getSelectedIndex()!=0) {
        TipoCultivo tipoCultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
            cotizacion.setTipoCultivoId(tipoCultivo.getTipoCultivoId());
        }
        if (cmb_Variedad.getSelectedIndex()!=0){
            Variedad variedad = (Variedad)cmb_Variedad.getSelectedItem();
            cotizacion.setVariedad(variedad.getVariedadId());
        }

        TipoIdentificacion tipoIdentificacion = (TipoIdentificacion)cmb_TipoIdentificacion.getSelectedItem();
        cotizacion.setTipoIdentificacion(new BigInteger(tipoIdentificacion.getId()));

        FormaCotizacion tipoSeguro = null;
        if (cmb_FormaCotizacion.getSelectedIndex()!=0)
            tipoSeguro=(FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();

        Date fechaSiembra= dtFechaSiembra.getDate();
        Date fechaCredito= dtFechaSolicitud.getDate();
        //Date FechaCreacion = new Date();

        if (rb_1Si.isSelected())
            cotizacion.setDisponeRiego(true);
        else
            cotizacion.setDisponeRiego(false);
        if (rb_2Si.isSelected())
            cotizacion.setAgricultorTecnificado(true);
        else
            cotizacion.setAgricultorTecnificado(false);
        if (rb_3Si.isSelected())
            cotizacion.setDisponeAsistencia(true);
        else
            cotizacion.setDisponeAsistencia(false);
        cotizacion.setFechaSiembra(fechaSiembra);
        cotizacion.setFechaCredito(fechaCredito);
        if (ReglaId!=null)
            cotizacion.setReglaId(ReglaId);
        cotizacion.setDireccionSiembra(txtDireccionReferencia.getText());
        cotizacion.setNumeroIdentificacion(txtNumeroIdentificacion.getText());
        cotizacion.setNombres(txtNombres.getText());
        cotizacion.setApellidos(txtApellidos.getText());
        cotizacion.setTelefono(txtTelefono.getText());
        cotizacion.setCelular(txtCelular.getText());
        //Parametro mail facturacion electronica
        String MailFacturacion="";
        if (Main.getCurrentTransporteData().getParametrosGenerales()!=null)
            MailFacturacion = Main.getCurrentTransporteData().getParametrosGenerales().getMailFactuacionElectronica() ;

        cotizacion.setEmail(txtEmail.getText()=="" ? MailFacturacion : txtEmail.getText());
        String Observacion ="";
        if (txtAnalisisObservaciones.getText().isEmpty()) {
            Observacion=txtAnalisisSiembra.getText();
            if(Observacion.length()>150)
                Observacion=txtAnalisisSiembra.getText().substring(0,150);
            else
                Observacion=txtAnalisisSiembra.getText();
            cotizacion.setObservaciones(Observacion);
        }
        else if (!txtAnalisisObservaciones.getText().contains(txtAnalisisSiembra.getText())) {
            Observacion = txtAnalisisObservaciones.getText() + " " + txtAnalisisSiembra.getText();
            if (Observacion.length() > 150)
                Observacion = Observacion.substring(0, 150);
            cotizacion.setObservaciones(Observacion);
        }
        else {
            Observacion=txtAnalisisObservaciones.getText();
            if (Observacion.length() > 150)
                Observacion = Observacion.substring(0, 150);
            cotizacion.setObservaciones(Observacion);
        }
        //cotizacion.setVariedad(txtVariedad.getText());

        cotizacion.setHectareasLote(Double.parseDouble(txtTotalHecLote.getText().isEmpty()?"0.00":txtTotalHecLote.getText()));
        cotizacion.setHectareasAsegurables(Double.parseDouble(txtTotalHecAsegurables.getText().isEmpty()?"0.00":txtTotalHecAsegurables.getText()));
        cotizacion.setMontoCreditoSinSeguro(Double.parseDouble(txtCreditoMontoCredito.getText().isEmpty()?"0.00":txtCreditoMontoCredito.getText()));
        cotizacion.setAnalisisMontoAsegurado(Double.parseDouble(txtAnalisisMontoAsegurado.getText()));
        cotizacion.setAnalisisMontoRecom(Double.parseDouble(txtAnalisisMontoRecom.getText()));
        cotizacion.setPrimaNeta(Double.parseDouble(txtPrimaNeta.getText()));
        cotizacion.setSuperBancos(Double.parseDouble(txtSuperBancos.getText()));
        cotizacion.setDerechoEmision(Double.parseDouble(txtDerechoEmision.getText()));
        cotizacion.setSeguroCampesino(Double.parseDouble(txtSeguroCampesino.getText()));
        cotizacion.setIva(Double.parseDouble(txtIva.getText()));
        cotizacion.setTotalPrima(Double.parseDouble(txtTotalPrima.getText()));
        cotizacion.setMontoCredito(Double.parseDouble(txtMontoCredito.getText()));
        cotizacion.setTotalCredito(Double.parseDouble(txtTotalCredito.getText()));
        if (tipoSeguro!=null)
            cotizacion.setTipoSeguro(tipoSeguro.getId());
        else
            cotizacion.setTipoSeguro(0);
        cotizacion.setEdadCultivo(Integer.parseInt(txtEdadCultivo.getText().isEmpty() ? "0" : txtEdadCultivo.getText()));
        if (FechaCreacion==null)
            FechaCreacion = new Date();
        cotizacion.setFechaCreacionCotizacion(FechaCreacion);
        cotizacion.setUsuarioNombre(UsuarioNombre);
        cotizacion.setUsuarioApellido(UsuarioApelido);
        cotizacion.setPuntoVentaId(UsuarioPuntoVenta);
        //TODO: nuevos campos latitud y longitud
        cotizacion.setLatitud(txt_Latitud.getText().isEmpty()? Double.parseDouble("0") : Double.parseDouble(txt_Latitud.getText()));
        cotizacion.setLongitud(txt_longitud.getText().isEmpty() ? Double.parseDouble("0") : Double.parseDouble(txt_longitud.getText()));
        //TODO:Se controla si la cotizacion tiene observacion de regla
        cotizacion.setTieneObservacion(TieneObservacion);
        //TODO: se envia el valor de la tasa
        cotizacion.setTasa(TasaCotizacion.doubleValue());
        //Si la cotizacion se graba completa(1) y para modificacion (0)
        cotizacion.setEstado(estado);
        CotizacionSolicitadas.add(cotizacion);

    }

    private Boolean GuardarCotizacion(){

        TransporteCotizaciones s = new TransporteCotizaciones();
        //Se llena el nombre del usuario
        String usuario = Main.getCurrentUser().getName();
        String CI = Main.getCurrentUser().getCIUser()==null?"" : Main.getCurrentUser().getCIUser();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //Date fechaCreacion = new Date();
        Gson g = new Gson();
        String Json = "";
        //String ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\cotizaciones\\CotizacionAgricola_"+usuario+CI+".config";
        String Encrypted = "";
        try {
            //final File StructureFile = new File(ruta);
            String StartFolder = Main.get_StartFolder();
            final File StructureFile = new File(StartFolder + "cotizaciones" + File.separator + "CotizacionAgricola_"+usuario+CI+".config");
            Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
            if (StructureFile.exists()){
                String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                String StructureDecypted = AES_Helper.decrypt(StructureContents);
                CurrentTransporteCotizaciones = g.fromJson(StructureDecypted, TransporteCotizaciones.class);
                for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()){
                    //recordar validar
                    if (!cotizacion.getObjetoCotizacionId().toString().equals(ObtetoCotizacionID.toString()))
                        CotizacionSolicitadas.add(cotizacion);
                }
                s.setCotizacionAgricola(CotizacionSolicitadas);
                Json= g.toJson(s);
                // Encripto
                Encrypted=AES_Helper.encrypt(AES_Helper.padString(Json));
                Files.write(StructurePath, Encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            }
            else{
                s.setCotizacionAgricola(CotizacionSolicitadas);
                Json= g.toJson(s);
                // Encripto
                Encrypted=AES_Helper.encrypt(AES_Helper.padString(Json));
                Files.write(StructurePath, Encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
            }
            return  true;

        }catch (Exception ex){
           JOptionPane.showMessageDialog(null,"Se ha producido un error: "+ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public  void CalcularValores(Regla regla){
        ReglaId = regla.getReglaId();
        String recomendacion ="";
        String ObservacionRegla="";
        Locale local = new Locale("es","EC");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,local);
        TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
        BigDecimal CostoProduccion = BigDecimal.valueOf(regla.getCostoProduccion());
        BigDecimal TotalHec = new BigDecimal(txtTotalHecAsegurables.getText());
        BigDecimal banc = new BigDecimal("3.50");
        BigDecimal PorSeguroCam = new BigDecimal("0.50");
        BigDecimal divisor = new BigDecimal("100");
        BigDecimal MontoRecomendado =new BigDecimal("0.00");
        BigDecimal MontoAseguradoReco=new BigDecimal("0.00");
        BigDecimal CostoMantenimiento = BigDecimal.valueOf(regla.getCostoMantenimiento());

        //Tomamos el cultivo para ver los dias de vigencia.
        int diasVigencia=cultivo.getVigenciaDias();

        ///Si es Tipo de cultivo Perenne
        /*if (cultivo.getTipo()==1){
            FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
            if (tipoSeguro.getId()==1){//si es seguro completo
                BigDecimal CostoMantAños = CostoMantenimiento.multiply(new BigDecimal(txtEdadCultivo.getText())).multiply(TotalHec);
                MontoRecomendado = (CostoMantAños.add(CostoProduccion.multiply(TotalHec)));
                MontoAseguradoReco = (CostoMantAños.add(CostoProduccion.multiply(TotalHec)));
            }
            else if (tipoSeguro.getId()==2){//si es seguro de mantenimiento anual
                BigDecimal CostoMantAnual = CostoMantenimiento.multiply(new BigDecimal(1)).multiply(TotalHec);
                MontoRecomendado = CostoMantAnual;
                MontoAseguradoReco = CostoMantAnual;
                //MontoRecomendado = CostoMantenimiento.multiply(TotalHec);
                //MontoAseguradoReco = CostoMantenimiento.multiply(TotalHec);
            }
        }
        else {*/
            if(regla.getTasa() != 0){
                if(regla.getCostoProduccion() > 0){
                    //MontoRecomendado = TotalHec.multiply(CostoProduccion);
                    MontoRecomendado = CostoProduccion;
                    MontoAseguradoReco = TotalHec.multiply(CostoProduccion);
                }else if(regla.getCostoMantenimiento() > 0){
                    //MontoRecomendado = TotalHec.multiply(CostoMantenimiento);
                    MontoRecomendado = CostoMantenimiento;
                    MontoAseguradoReco = TotalHec.multiply(CostoMantenimiento);
                }
            }

        //}
        //TODO: se obtiene el valor de la tasa
        TasaCotizacion = BigDecimal.valueOf(regla.getTasa());
        BigDecimal prima = (MontoAseguradoReco.multiply(BigDecimal.valueOf(regla.getTasa())).divide(divisor));//(txtPrimaNeta.getText());
        BigDecimal superBanc = new BigDecimal("0.00");
        BigDecimal DerecEmision = new BigDecimal("0.00");
        BigDecimal SeguroCam = new BigDecimal("0.00");
        BigDecimal Iva = new BigDecimal("0.00");
        BigDecimal ImpuestoAprox = new BigDecimal("0.00");
        BigDecimal TotalPrima = new BigDecimal("0.00");
        BigDecimal MontoCredito;
        if (prima.compareTo(new BigDecimal("0"))>0) {

            superBanc = (banc.multiply(prima)).divide(divisor);
            DerecEmision = new BigDecimal("0.50");
            SeguroCam = (PorSeguroCam.multiply(prima)).divide(divisor);
           //Hacemos la distincion por provincia para aplicar ley de solidaridad iva 14% exceptamos MANABI Y ESMERALDAS
            Object e = cmb_Provincia.getSelectedItem();
            String ProvinciaExcetion= String.valueOf(e);

            if(ProvinciaExcetion.equals("MANABI")||ProvinciaExcetion.equals("ESMERALDAS")){
                Iva = (new BigDecimal("12").multiply(prima.add(superBanc).add(DerecEmision).add(SeguroCam))).divide(divisor);
            }else{
                Iva = (new BigDecimal("14").multiply(prima.add(superBanc).add(DerecEmision).add(SeguroCam))).divide(divisor);
            }
            ImpuestoAprox = ((Iva.add(SeguroCam)).add(DerecEmision)).add(superBanc);
            TotalPrima = prima.add(ImpuestoAprox);
        }
        if (txtCreditoMontoCredito.getText().isEmpty())
            MontoCredito = new BigDecimal("0.00");
        else
            MontoCredito = new BigDecimal(txtCreditoMontoCredito.getText());
        txtPrimaNeta.setText(prima.setScale(2, RoundingMode.HALF_UP).toString());
        txtSuperBancos.setText(superBanc.setScale(2,RoundingMode.HALF_UP).toString());
        txtDerechoEmision.setText(DerecEmision.toString());
        txtSeguroCampesino.setText(SeguroCam.setScale(2,RoundingMode.HALF_UP).toString());
        txtIva.setText(Iva.setScale(2,RoundingMode.HALF_UP).toString());
        //txtImpuestoAprox.setText(ImpuestoAprox.setScale(2,RoundingMode.HALF_UP).toString());
        txtTotalPrima.setText(TotalPrima.setScale(2,RoundingMode.HALF_UP).toString());
        txtTotalCredito.setText(TotalPrima.add(MontoCredito).setScale(2, RoundingMode.HALF_UP).toString());
        txtMontoCredito.setText(MontoCredito.setScale(2, RoundingMode.HALF_UP).toString());
        txtAnalisisMontoRecom.setText(MontoRecomendado.setScale(2, RoundingMode.HALF_UP).toString());
        txtAnalisisMontoAsegurado.setText(MontoAseguradoReco.setScale(2, RoundingMode.HALF_UP).toString());

        /*verificamos si esta seleccionado el campo de tiene riego*/
        Boolean tieneRiegoSeleccionado=false;
        if (rb_1Si.isSelected())
            tieneRiegoSeleccionado=true;
        else
            tieneRiegoSeleccionado=false;

        Boolean tieneRiegoObservacion=false;

        if (!regla.getObservaciones().trim().equals("")) {
            ObservacionRegla = ":" + regla.getObservaciones();
            //TODO: Validar si la regla tiene observacion va a pendiente de aprobar
            if(":DEBE CONTAR CON RIEGO".equals(ObservacionRegla)||":DEBE CONTAR CO RIEGO".equals(ObservacionRegla)||":SIEMPRE DEBE CONTAR CON RIEGO".equals(ObservacionRegla)){
                tieneRiegoObservacion=true;
                if(tieneRiegoSeleccionado && tieneRiegoObservacion) {
                    ObservacionRegla = "";
                    TieneObservacion = false;
                }else{
                    TieneObservacion = true;
                }
            }else{
                TieneObservacion=true;
            }
        }else{
            TieneObservacion=false;
        }
        if (regla.getTasa()==0&&regla.getTasa()==0.0){
            ///TODO: si no tiene ciclo se prenta mensaje de no existe periododo
            /*Date fechaDesde = regla.getAceptabilidadDesde();
            Date fechaHasta = regla.getAceptabilidadHasta();
            if (dtFechaSiembra.getDate().after(fechaDesde)&& dtFechaSiembra.getDate().before(fechaHasta)){
                recomendacion="Si recomendado, más recomendado entre "+df.format(fechaDesde)+" - "+df.format(fechaHasta)+ObservacionRegla;
                txtAnalisisSiembra.setBackground(Color.green);
            }
            else{
                recomendacion="No recomendado, por la fechas de siembra";
                txtAnalisisSiembra.setBackground(Color.magenta);
            }*/
            //recomendacion="No existe periodo definido para el calculo";
            SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaActual=dtFechaSiembra.getDate();
            String fechaActualComparar=formatea.format(fechaActual);

            //sumamos los dias a la fecha de siembra para obtener la vigencia de la poliza
            Calendar calVigenciaHasta = Calendar.getInstance();
            calVigenciaHasta.setTime(fechaActual);
            calVigenciaHasta.add(Calendar.DAY_OF_MONTH,diasVigencia);
            Date fechaHasta=calVigenciaHasta.getTime();
            lbl_vigencia_hasta.setText("Vigencia Hasta: "+formatea.format(fechaHasta));
            lbl_vigencia_hasta.setForeground(Color.RED);

            recomendacion="No existe regla.";
            txtAnalisisSiembra.setBackground(Color.RED);
        }
        else {
            List<Ciclo> ciclos = Main.getCurrentTransporteData().getCiclos();
                Ciclo cicloRegla = new Ciclo();
                for (Ciclo ciclo : ciclos) {
                    if (ciclo.getCicloId().toString().equals(regla.getCicloId().toString())) {
                        cicloRegla = ciclo;
                        break;
                    }
                }
                Date fechaInicio = cicloRegla.getFechaInicio();
                Date fechaFin = cicloRegla.getFechaFin();
                SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");
                String FInicioText = formatea.format(fechaInicio);
                String FFinText = formatea.format(fechaFin);
                Date fechaActual=dtFechaSiembra.getDate();
                String fechaActualComparar=formatea.format(fechaActual);

                //sumamos los dias a la fecha de siembra para obtener la vigencia de la poliza
                Calendar calVigenciaHasta = Calendar.getInstance();
                calVigenciaHasta.setTime(fechaActual);
                calVigenciaHasta.add(Calendar.DAY_OF_MONTH,diasVigencia);
                Date fechaHasta=calVigenciaHasta.getTime();
                lbl_vigencia_hasta.setText("Vigencia Hasta: "+formatea.format(fechaHasta));
                lbl_vigencia_hasta.setForeground(Color.BLUE);

                if ((dtFechaSiembra.getDate().after(fechaInicio) && dtFechaSiembra.getDate().before(fechaFin))
                        ||fechaActualComparar.equals(FInicioText)||fechaActualComparar.equals(FFinText)) {

                    if (TieneObservacion) {
                        txtAnalisisSiembra.setBackground(Color.ORANGE);
                        recomendacion = ObservacionRegla;
                    } else {
                        txtAnalisisSiembra.setBackground(Color.green);
                        recomendacion = "Si recomendado, mas recomendado entre " + df.format(fechaInicio) + " - " + df.format(fechaFin) + ObservacionRegla;
                    }
                } else {
                    recomendacion = "No recomendado, por la fechas de siembra,el ciclo de este cultivo esta entre " + FInicioText + " - " + FFinText;
                    txtAnalisisSiembra.setBackground(Color.ORANGE);
                    TieneObservacion = true;
                }

        }
        txtAnalisisSiembra.setText(recomendacion);
    }

    public Regla ObtenerRegla (){
        String PuntoVentaUsu = Main.getCurrentUser().getPuntoVentaId();
        List<Regla>reglass = Main.getCurrentTransporteData().getReglas();
        List<ParametrosXPuntoVenta> ParametroPuntoVent = Main.getCurrentTransporteData().getParametrosPuntoVenta();

        TipoCultivo tipoCultivoSelec = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
        Canton cantonSelec = (Canton)cmb_Canton.getSelectedItem();
        List<Regla>reglasCultivo = new ArrayList<Regla>();
        List<ParametrosXPuntoVenta> ParametrosUsuario = new ArrayList<ParametrosXPuntoVenta>();

        ///Verificar si existe parametros por punto de venta
        for (ParametrosXPuntoVenta parametro : ParametroPuntoVent){
            if (parametro.getPuntoVentaID().toString().equals(PuntoVentaUsu))
                ParametrosUsuario.add(parametro);
        }
        ///Si existen parametros por punto de venta recorremos el listado que coincide con el del usuario
        if (ParametrosUsuario.size()>0){
            for (Regla regla : reglass) {
                ///Se recorre a ver si existe un parametro que coincida con el canton y tipo de cultivo seleccionado.
                for (ParametrosXPuntoVenta puntoVent : ParametrosUsuario) {
                    if (regla.getTipoCalculoId().toString().equals(puntoVent.getTipoCalculoId().toString())&&
                            regla.getTipoCultivoId().toString().equals(tipoCultivoSelec.getTipoCultivoId().toString()) &&
                            regla.getCantonId().toString().equals(cantonSelec.getCantonId().toString()))
                        return regla;

                }
            }
        }
        else {
            ///SE recorre a ver si existe una regla que coincida con el tipo de cultivo y canton seleccionado
            for (Regla regla : reglass) {
                if (regla.getTipoCultivoId().toString().equals(tipoCultivoSelec.getTipoCultivoId().toString()) &&
                        regla.getCantonId().toString().equals(cantonSelec.getCantonId().toString()))
                    return regla;
            }
        }
        return null;
    }

    public void CargarCotizacion(){
        TransporteCotizaciones s = new TransporteCotizaciones();
        CotizacionAgricola cotizacionActualizable = new CotizacionAgricola();
        //Se llena el nombre del usuario
        String usuario = Main.getCurrentUser().getName();
        String CI = Main.getCurrentUser().getCIUser()==null?"" : Main.getCurrentUser().getCIUser();
        Gson g = new Gson();
        String Json = "";
        //String ruta = "D:\\Proyectos\\COTIZADOR_OFFLINE\\Offline_Base\\cotizaciones\\CotizacionAgricola_"+usuario+CI+".config";
        String Encrypted = "";
        try {
            //final File StructureFile = new File(ruta);
            String StartFolder = Main.get_StartFolder();
            final File StructureFile = new File(StartFolder + "cotizaciones" + File.separator + "CotizacionAgricola_"+usuario+CI+".config");
            Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
            if (StructureFile.exists()) {
                String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                String StructureDecypted = AES_Helper.decrypt(StructureContents);
                CurrentTransporteCotizaciones = g.fromJson(StructureDecypted, TransporteCotizaciones.class);
                for (CotizacionAgricola cotizacion : CurrentTransporteCotizaciones.getCotizacionAgricola()){
                    if (cotizacion.getObjetoCotizacionId().equals(ObtetoCotizacionID)){
                        cotizacionActualizable = cotizacion;
                        continue;
                    }
                }
                if (cotizacionActualizable!=null){
                    if (cotizacionActualizable.getReglaId()!=null)
                        ReglaId = cotizacionActualizable.getReglaId();
                    Agencia agencia = new Agencia();
                    if (cotizacionActualizable.getAgencia()!=null){
                    for (Agencia agen:agencias){
                        if (agen.getAgenciaId().toString().equals(cotizacionActualizable.getAgencia().toString())){
                            agencia = agen;
                            continue;
                        }
                    }
                    }
                    //cmb_Agencia.setSelectedItem(agencia);

                    if (cotizacionActualizable.getProvinciaId()!=null) {
                        Provincia provincia = new Provincia();
                        for (Provincia provin : provi) {
                            if (provin.getProvinciaId().toString().equals(cotizacionActualizable.getProvinciaId().toString())){
                                provincia = provin;
                                continue;
                            }
                        }
                        cmb_Provincia.setSelectedItem(provincia);
                        CargarCanton(provincia.getProvinciaId().toString());
                    }

                    if (cotizacionActualizable.getCantonId()!=null) {
                        Canton canton = new Canton();
                        for (Canton ciudad : cantones) {
                            if (ciudad.getCantonId().toString().equals(cotizacionActualizable.getCantonId().toString())){
                                canton = ciudad;
                                continue;
                            }
                        }
                        cmb_Canton.setSelectedItem(canton);
                        CargarParroquia(canton.getCantonId().toString());
                    }



                    Parroquia parroquia = new Parroquia();
                    if (cotizacionActualizable.getParroquiaId()!=null) {
                        for (Parroquia parroq : parroquias) {
                            if (parroq.getParroquiaId().toString().equals(cotizacionActualizable.getParroquiaId().toString())){
                                parroquia = parroq;
                                continue;
                            }
                        }
                    }
                    cmb_Parroquia.setSelectedItem(parroquia);

                    TipoCultivo tipoCultivo = new TipoCultivo();
                    if (cotizacionActualizable.getTipoCultivoId()!=null) {
                        for (TipoCultivo cultivo : tiposCultivo) {
                            if (cultivo.getTipoCultivoId().equals(cotizacionActualizable.getTipoCultivoId())){
                                tipoCultivo = cultivo;
                                continue;
                            }
                        }
                        ///Si es Tipo de cultivo Perenne
                        /*if (tipoCultivo.getTipo()==1){
                            lblFormaCotizacion.setVisible(true);
                            cmb_FormaCotizacion.setVisible(true);
                            lblEdadCultivo.setVisible(false);
                            txtEdadCultivo.setVisible(false);
                        }*/
                        //TODO_ Habilitar los controles de la variedad
                        DefaultComboBoxModel valueVariedad = new DefaultComboBoxModel();
                        for (Variedad varieda : variedades){
                            if (varieda.getTipoCultivoId().equals(cotizacionActualizable.getTipoCultivoId())) {
                                valueVariedad.addElement(varieda);
                                continue;
                            }
                        }
                        if (valueVariedad.getSize()<1){
                            lblVariedad.setVisible(false);
                            cmb_Variedad.setVisible(false);
                        }
                        else {
                            lblVariedad.setVisible(true);
                            cmb_Variedad.setVisible(true);
                        }
                    }
                    cmb_TipoCultivo.setSelectedItem(tipoCultivo);

                    //TODO_variedad
                     Variedad variedad = new Variedad();
                    if (cotizacionActualizable.getVariedad()!=null){
                        for (Variedad variedad_ : variedades){
                            if (variedad_.getVariedadId().equals(cotizacionActualizable.getVariedad())) {
                                variedad = variedad_;
                                continue;
                            }
                        }
                    }
                    cmb_Variedad.setSelectedItem(variedad);


                    TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
                    if (cotizacionActualizable.getTipoIdentificacion()!=null){
                        for (TipoIdentificacion identificacion : tipoIdentificacions){
                            if (identificacion.getId().toString().equals(cotizacionActualizable.getTipoIdentificacion().toString())){
                                tipoIdentificacion = identificacion;
                                continue;
                            }
                        }
                    }
                    cmb_TipoIdentificacion.setSelectedItem(tipoIdentificacion);

                    FormaCotizacion tipoSeguro = new FormaCotizacion();
                    if (cotizacionActualizable.getTipoSeguro()!=null&& cotizacionActualizable.getTipoSeguro()!=0){
                        for (FormaCotizacion seguro : tiposSeguro){
                            if (seguro.getId().toString().equals(cotizacionActualizable.getTipoSeguro().toString())){
                                tipoSeguro = seguro;
                                continue;
                            }
                        }
                        cmb_FormaCotizacion.setSelectedItem(tipoSeguro);
                        lblFormaCotizacion.setVisible(true);
                        cmb_FormaCotizacion.setVisible(true);
                        lblEdadCultivo.setVisible(false);
                        txtEdadCultivo.setVisible(false);
                    }
                    dtFechaSolicitud.setDate(cotizacionActualizable.getFechaCredito());
                    HabilitarFechas();
                    dtFechaSiembra.setDate(cotizacionActualizable.getFechaSiembra());
                    FechaCreacion = cotizacionActualizable.getFechaCreacionCotizacion();
                    //Date FechaCreacion = new Date();

                    if (cotizacionActualizable.getDisponeRiego().equals(true)){
                        rb_1Si.setSelected(true);
                        rb_1No.setSelected(false);
                    }
                    else{
                        rb_1No.setSelected(true);
                        rb_1Si.setSelected(false);
                    }
                    if (cotizacionActualizable.getAgricultorTecnificado().equals(true)) {
                        rb_2Si.setSelected(true);
                        rb_2No.setSelected(false);
                    }
                    else {
                        rb_2No.setSelected(true);
                        rb_2Si.setSelected(false);
                    }

                    if (cotizacionActualizable.getDisponeAsistencia().equals(true)) {
                        rb_3Si.setSelected(true);
                        rb_3No.setSelected(false);
                    }
                    else {
                        rb_3No.setSelected(true);
                        rb_3Si.setSelected(false);
                    }

                    txtDireccionReferencia.setText(cotizacionActualizable.getDireccionSiembra());
                    txtNumeroIdentificacion.setText(cotizacionActualizable.getNumeroIdentificacion());
                    txtNombres.setText(cotizacionActualizable.getNombres());
                    txtApellidos.setText(cotizacionActualizable.getApellidos());
                    txtTelefono.setText(cotizacionActualizable.getTelefono());
                    txtCelular.setText(cotizacionActualizable.getCelular());
                    txtEmail.setText(cotizacionActualizable.getEmail());
                    txtAnalisisObservaciones.setText(cotizacionActualizable.getObservaciones());
                    //txtVariedad.setText(cotizacionActualizable.getVariedad());

                    txtTotalHecLote.setText(cotizacionActualizable.getHectareasLote()==0 ? "": cotizacionActualizable.getHectareasLote().toString());
                    txtTotalHecAsegurables.setText(cotizacionActualizable.getHectareasAsegurables()==0 ? "": cotizacionActualizable.getHectareasAsegurables().toString());
                    txtCreditoMontoCredito.setText(cotizacionActualizable.getMontoCredito()== 0 ? "": cotizacionActualizable.getMontoCreditoSinSeguro().toString());
                    txtAnalisisMontoAsegurado.setText(cotizacionActualizable.getAnalisisMontoAsegurado().toString());
                    txtAnalisisMontoRecom.setText(cotizacionActualizable.getAnalisisMontoRecom().toString());
                    txtPrimaNeta.setText(cotizacionActualizable.getPrimaNeta().toString());
                    txtSuperBancos.setText(cotizacionActualizable.getSuperBancos().toString());
                    txtDerechoEmision.setText(cotizacionActualizable.getDerechoEmision().toString());
                    txtSeguroCampesino.setText(cotizacionActualizable.getSeguroCampesino().toString());
                    txtIva.setText(cotizacionActualizable.getIva().toString());
                    txtTotalPrima.setText(cotizacionActualizable.getTotalPrima().toString());
                    txtMontoCredito.setText(cotizacionActualizable.getMontoCredito().toString());
                    txtTotalCredito.setText(cotizacionActualizable.getTotalCredito().toString());
                    //txtEdadCultivo.getText().isEmpty() ? "0" : txtEdadCultivo.getText()));
                    txtEdadCultivo.setText(cotizacionActualizable.getEdadCultivo()==0 ? "": cotizacionActualizable.getEdadCultivo().toString());

                    if (cotizacionActualizable.getTotalCredito()!=0){
                        Regla regla = new Regla();
                        regla = ObtenerRegla();
                        //Regla regla = ObtenerRegla();
                        if(regla!=null){
                            CalcularValores(regla);
                        }
                        else {
                            txtAnalisisSiembra.setBackground(Color.RED);
                            txtAnalisisSiembra.setText("No recomendado, no existe regla");
                        }
                    }
                    //TODO: nuevos campos latitud y longitud
                    txt_Latitud.setText(cotizacionActualizable.getLatitud().toString());
                    txt_longitud.setText(cotizacionActualizable.getLongitud().toString());
                    //Se controla si la cotizacion tiene observacion de regla
                    TieneObservacion = cotizacionActualizable.getTieneObservacion();
                    //TODO: se envia el valor de la tasa
                    TasaCotizacion = BigDecimal.valueOf(cotizacionActualizable.getTasa());

                    //Segun el estado de la cotizacion se habilitan los botones de grabado
                    if (cotizacionActualizable.getEstado()!=0)
                        DeshabilitarComponentes();
                }
            }
        }
        catch (Exception ex ){
            //this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            JOptionPane.showMessageDialog(null,"Se ha producido un error: "+ex.getMessage());
        }

    }

    public  String  ValidarCampos(){
        String campos = "";
        String cadena="";
        Integer cont = 0;
        //if (cmb_Agencia.getSelectedIndex()==0)
        //campos = campos+lblAgenca.getText()+"\n*";
        if (txtCreditoMontoCredito.getText().isEmpty())
            campos = campos+lblCreditoMontoCredito.getText()+"\n*";
        if (txtNumeroIdentificacion.getText().isEmpty())
            campos = campos+lblNumeroIdentifiacion.getText()+"\n*";
        if (txtNombres.getText().isEmpty())
            campos = campos+lblNombres.getText()+"\n*";
        if (txtApellidos.getText().isEmpty())
            campos = campos+lblApellidos.getText()+"\n*";
        /*if (txtTelefono.getText().isEmpty())
            campos = campos+lblTelefono.getText()+"\n*";*/
        //Validar el celular en lugar del telefono
        if (txtCelular.getText().isEmpty())
            campos = campos+lblCelular.getText()+"\n*";
        //if (txtEmail.getText().isEmpty())
            //campos = campos+lblEmail.getText()+"\n*";
        if (cmb_Provincia.getSelectedIndex()==0)
            campos = campos+lblProvincia.getText()+"\n*";
        if (cmb_Canton.getSelectedIndex()==0)
            campos = campos+lblCanton.getText()+"\n*";
        if (txtDireccionReferencia.getText().isEmpty())
            campos = campos+lblDireccionReferencia.getText()+"\n*";
        if (cmb_TipoCultivo.getSelectedIndex()==0){
            campos = campos+lblTipoCultivo.getText()+"\n*";
        }
        else {
            TipoCultivo cultivo = (TipoCultivo)cmb_TipoCultivo.getSelectedItem();
            ///Si es Tipo de cultivo Perenne
            /*if (cultivo.getTipo()==1){
                if (cmb_FormaCotizacion.getSelectedIndex()==0)
                    campos = campos+lblFormaCotizacion.getText()+"\n*";
                else {
                    FormaCotizacion tipoSeguro = (FormaCotizacion)cmb_FormaCotizacion.getSelectedItem();
                    if (tipoSeguro.getId()==1)//Si es seguro completo
                    {
                        if (txtEdadCultivo.getText().isEmpty())
                        campos = campos+lblEdadCultivo.getText()+"\n*";
                    }
                }
            }*/
            for (Variedad varieda : variedades){
                if (varieda.getTipoCultivoId().equals(cultivo.getTipoCultivoId()))
                    cont++;
            }
            if (cont!=0  && cmb_Variedad.getSelectedIndex()==0)
            {
                campos = campos+lblVariedad.getText()+"\n*";
            }
        }
        /*if (cmb_Variedad.getText().isEmpty())
            campos = campos+lblVariedad.getText()+"\n*";
        if (cmb_Variedad.getSelectedIndex()==0){
            campos = campos+lblVariedad.getText()+"\n*";
        }*/
        if (txtTotalHecLote.getText().isEmpty())
            campos = campos+lblTotalHecLote.getText()+"\n*";
        else
            if (txtTotalHecLote.getText().trim().equals("0")||txtTotalHecLote.getText().trim().equals("0.0"))
                campos = campos+lblTotalHecLote.getText()+" no puede ser 0 \n*";

       if (txtTotalHecAsegurables.getText().isEmpty())
            campos = campos+lblTotalHecAsegurables.getText()+"\n*";
        else
            if (txtTotalHecAsegurables.getText().trim().equals("0")||txtTotalHecAsegurables.getText().trim().equals("0.0"))
                campos = campos+lblTotalHecAsegurables.getText()+" no puede ser 0 \n*";

        //if (txtAnalisisObservaciones.getText().isEmpty())
          //  campos = campos+lblAnalisisObservaciones.getText()+"*";
        if (!campos.isEmpty())
            cadena = campos.substring(0,campos.length()-1);
        return cadena;
    }

    public void setCamposValores(){
        txtPrimaNeta.setText("0.00");
        txtSuperBancos.setText("0.00");
        txtDerechoEmision.setText("0.00");
        //txtImpuestoAprox.setText("0.00");
        txtSeguroCampesino.setText("0.00");
        txtIva.setText("0.00");
        //txtRecargoSegCamp.setText("0.00");
        txtTotalPrima.setText("0.00");
        txtMontoCredito.setText("0.00");
        txtTotalCredito.setText("0.00");
        txtAnalisisMontoRecom.setText("0.00");
        txtAnalisisMontoAsegurado.setText("0.00");
        txtAnalisisSiembra.setText("");
        txtAnalisisSiembra.setBackground(Color.WHITE);
    }
    public  void HabilitarFechas(){
        Date fecha = dtFechaSolicitud.getDate();
        int DiasValidacionAntes = 15;
        int DiasValidacionDespues=30;
        //if (Main.getCurrentTransporteData().getParametrosGenerales()!=null)
        //    DiasValidacion = Main.getCurrentTransporteData().getParametrosGenerales().getDiasValidacionCultivo() ;
        Calendar calAntes = Calendar.getInstance();
        calAntes.setTime(fecha);
        calAntes.add(Calendar.DAY_OF_MONTH, -DiasValidacionAntes);

        Calendar calDespues = Calendar.getInstance();
        calDespues.setTime(fecha);
        calDespues.add(Calendar.DAY_OF_MONTH,DiasValidacionDespues);

        dtFechaSiembra.getJCalendar().setMinSelectableDate(calAntes.getTime());
        dtFechaSiembra.getJCalendar().setMaxSelectableDate(calDespues.getTime());
    }

    public  void DeshabilitarComponentes(){
        btnPendiente.setVisible(false);
        grabarButton.setVisible(false);
        cmb_Provincia.setEnabled(false);
        cmb_Canton.setEnabled(false);
        cmb_Parroquia.setEnabled(false);
        cmb_TipoCultivo.setEnabled(false);
        //cmb_Agencia.setEnabled(false);
        cmb_TipoIdentificacion.setEnabled(false);
        cmb_FormaCotizacion.setEnabled(false);
        cmb_Variedad.setEnabled(false);

        dtFechaSiembra.setEnabled(false);
        rb_1No.setEnabled(false);
        rb_2No.setEnabled(false);
        rb_3No.setEnabled(false);
        rb_1Si.setEnabled(false);
        rb_2Si.setEnabled(false);
        rb_3Si.setEnabled(false);
        txtDireccionReferencia.setEditable(false);
        txtNumeroIdentificacion.setEditable(false);
        txtNombres.setEditable(false);
        txtApellidos.setEditable(false);
        txtTelefono.setEditable(false);
        txtCelular.setEditable(false);
        txtEmail.setEditable(false);
        txtAnalisisObservaciones.setEditable(false);
        //txtVariedad.setEditable(false);
        txtCreditoMontoCredito.setEditable(false);
        txtTotalHecLote.setEditable(false);
        txtTotalHecAsegurables.setEditable(false);
        txtEdadCultivo.setEditable(false);
    }

    public  void  LimpiarCampos(){
        cmb_Provincia.setSelectedIndex(0);
        cmb_Canton.setSelectedIndex(0);
        cmb_Parroquia.setSelectedIndex(0);
        cmb_TipoCultivo.setSelectedIndex(0);
        //cmb_Agencia.setSelectedIndex(0);
        cmb_TipoIdentificacion.setSelectedIndex(0);
        cmb_Variedad.setSelectedIndex(0);

        dtFechaSiembra.setDate(new Date());
        dtFechaSolicitud.setDate(new Date());
        rb_1No.setSelected(true);
        rb_2No.setSelected(true);
        rb_3No.setSelected(true);
        rb_1Si.setSelected(false);
        rb_2Si.setSelected(false);
        rb_3Si.setSelected(false);
        txtDireccionReferencia.setText("");
        txtNumeroIdentificacion.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
        txtAnalisisObservaciones.setText("");
        //txtVariedad.setText("");
        txtCreditoMontoCredito.setText("");
        txtTotalHecLote.setText("");
        txtTotalHecAsegurables.setText("");
        txt_Latitud.setText("0");
        txt_longitud.setText("0");
        TieneObservacion=false;
        ObtetoCotizacionID="";
        FechaCreacion=null;
        ReglaId = null;
        TasaCotizacion = new BigDecimal("0");
        setCamposValores();
        /*
        txtCreditoMontoCredito.getText()));
        txtAnalisisMontoAsegurado.getText()));
        txtAnalisisMontoRecom.getText()));
        txtPrimaNeta.getText()));
        txtSuperBancos.getText()));
        txtDerechoEmision.getText()));
        txtSeguroCampesino.getText()));
        txtIva.getText()));
        txtTotalPrima.getText()));
        txtTotalCredito.getText()));
        txtTotalCredito.getText()));*/
    }

    public static TransporteCotizaciones getCurrentTransporteCotizaciones() {
        return CurrentTransporteCotizaciones;
    }

    public static void setCurrentTransporteCotizaciones(TransporteCotizaciones currentTransporteCotizaciones) {
        CurrentTransporteCotizaciones = currentTransporteCotizaciones;
    }



    public class FormaCotizacion {
        private Integer id;
        private String Nombre;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return Nombre;
        }

        public void setNombre(String nombre) {
            Nombre = nombre;
        }
        public String toString(){
            return Nombre;
        }
    }
}
