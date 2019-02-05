package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.Agencia;
import micotizador.offline.filestructure.PuntoVenta;
import micotizador.offline.filestructure.Security;
import micotizador.offline.filestructure.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica Zhagui on 15/07/2015.
 */
public class Usuario extends JDialog {
    private JPanel pnlPrincipal;
    private JTextField txtCedula;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtPsw;
    private JButton btn_Grabar;
    private JLabel lblCedula;
    private JLabel lblNombres;
    private JLabel lblApellidos;
    private JLabel lblPsw;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JComboBox cmb_PuntoVenta;
    private JLabel lblPuntoVenta;
    private JCheckBox esAdmin;
    private JComboBox cbnAgencia;
    private JComboBox comboBox1;
    private JTextField txtAgencia;
    private JTextField textField1;
    private JCheckBox checkBox1;
    private List<User> usuarios = new ArrayList<User>();
    private List<PuntoVenta> puntosVenta = new ArrayList<PuntoVenta>();
    private List<Agencia>agencias = new ArrayList<Agencia>();
    private JComboBox cmb_Agencia;
    private JLabel JLabelAdmin;
    // Ruta relativa de publicación
    final File SecurityFile = new File(Main.get_StartFolder() + "security.config");

    public Usuario() {
        super();
        //Image icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        //this.setIconImage(icon);
        // establece el panel (del diseño) que va a presentarse dentro del contenido del JFrame
        setContentPane(pnlPrincipal);
        pack();
        cmb_PuntoVenta.setVisible(false);
        lblPuntoVenta.setVisible(false);
        esAdmin.setVisible(false);
        JLabelAdmin.setVisible(false);
        /*DefaultComboBoxModel valuePuntosVenta = new DefaultComboBoxModel();
        puntosVenta = Main.getCurrentTransporteData().getPuntosVentaAgricola();
        cmb_PuntoVenta.setModel(valuePuntosVenta);
        for (PuntoVenta punto : puntosVenta) {
            valuePuntosVenta.addElement(punto);
        }
        cmb_PuntoVenta.insertItemAt("Seleccionar", 0);
        cmb_PuntoVenta.setSelectedIndex(0);*/

        ///Carga el combo agencia
        DefaultComboBoxModel valueAgencia = new DefaultComboBoxModel();
        cmb_Agencia.setModel(valueAgencia);
        if (Main.getCurrentTransporteData()!=null)
            agencias = Main.getCurrentTransporteData().getAgencias();
        if (agencias!=null) {
            for (Agencia agencia : agencias) {
                //cmb_Agencia.insertItemAt(agencia.getNombre(), new Integer(agencia.getCodigo().intValue()));
                String PuntoVentaId = Main.getCurrentConfiguration().getPuntoVentaId();
                if (agencia.getPuntoVentaId().equals(new BigInteger(PuntoVentaId)))
                    valueAgencia.addElement(agencia);
            }
        }
        cmb_Agencia.insertItemAt("Seleccionar", 0);
        cmb_Agencia.setSelectedIndex(0);

        btn_Grabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String campos = ValidarCampos();
                    if (campos.length() > 0) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar los campos obligatorios:\n*" + campos);
                        return;
                    }
                    String strResult = Validate_Identification.Validate_Identification(txtCedula.getText());
                    if (!strResult.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error validación de documento. " + strResult);
                        //txtCedula.Focus();
                        txtCedula.requestFocus();
                        return;
                    }
                    if (ValidarCedulaExiste(txtCedula.getText())) {
                        JOptionPane.showMessageDialog(null, "El número de cédula del usuario ya se encuentra registrado");
                        return;
                    }
                    if (ValidarUserNameExiste()) {
                        JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado ya se encuentra registrado, ingrese uno diferente para continuar");
                        return;
                    }
                    Grabar();
                    LimpiarCampos();
                    JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Se ha producido un error: " + ex.getMessage());
                }
            }
        });

        txtCedula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                String a = String.valueOf(c);
                if (!a.matches("([0-9]|)+"))
                    e.consume();
                if (txtCedula.getText().length() >= 10)
                    e.consume();
            }
        });

        txtCedula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                txtUsuario.setText(txtCedula.getText());
                txtPsw.setText(txtCedula.getText());
            }
        });

    }



    public Boolean ValidarCedulaExiste(String cedulaUser) throws Exception {

        Gson g = new Gson();
        if (Main.getCurrentSecurity() != null)
            usuarios = Main.getCurrentSecurity().getUsers();
        for (User usuario : usuarios) {
            if (usuario.getCIUser() != null)
                if (usuario.getCIUser().equals(cedulaUser))
                    return true;
        }
        return false;
    }

    public Boolean ValidarUserNameExiste() {
        for (User usuario : usuarios) {
            if (usuario.getUserName().equals(txtUsuario.getText()))
                return true;
        }
        return false;
    }

    public void Grabar() throws Exception {
        try {
            boolean Admin = false;
            if (esAdmin.isSelected())
                Admin = true;
            else
                Admin = false;


            Security s = new Security();
            String PuntoVentaId = Main.getCurrentConfiguration().getPuntoVentaId();
            //Obtener el id del ultimo usuario para asignar al nuevo usuario el id
            Integer id = 1;
            if (usuarios.size() != 0)
                id = usuarios.get(usuarios.size() - 1).getUserID() + 1;
            PuntoVenta punto = (PuntoVenta) cmb_PuntoVenta.getSelectedItem();
            User us = new User();
            us.setUserID(id);
            us.setCIUser(txtCedula.getText());
            us.setName(txtNombres.getText());
            us.setLastName(txtApellidos.getText());
            us.setUserName(txtUsuario.getText());
            us.setPassword(txtPsw.getText());
            us.setPuntoVentaId(PuntoVentaId);

            if (cmb_Agencia.getSelectedIndex() != 0) {
                Agencia agencia = (Agencia) cmb_Agencia.getSelectedItem();
                us.setAgencia("" + agencia.getAgenciaId());
            }
            us.setAdmin(Admin);
            //us.setPuntoVentaId(punto.getPuntoVentaID());
            usuarios.add(us);
            s.setUsers(usuarios);
            // serializo a Json
            Gson g = new Gson();
            String SecurityContent = g.toJson(s);

            // encripto la información
            String encrypted = AES_Helper.encrypt(AES_Helper.padString(SecurityContent));

            // grabo en el archivo
            Path p = FileSystems.getDefault().getPath(SecurityFile.getPath());
            Files.write(p, encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            //cerramos la ventana
            JOptionPane.showMessageDialog(null, "Usuario creado de forma correcta: Por favor vuelva a abrir la app. para ingresar con sus credenciales");
            System.exit(0);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Se ha producido un error al crear el Usuarios " + e.getMessage());
            System.exit(0);
        }

    }

    public String ValidarCampos() {
        String campos = "";
        String cadena = "";
        if (cmb_Agencia.getSelectedIndex() == 0) {
            campos = campos + "Agencia " + "\n*";
        }
        if (txtCedula.getText().isEmpty())
            campos = campos + lblCedula.getText() + "\n*";
        if (txtNombres.getText().isEmpty())
            campos = campos + lblNombres.getText() + "\n*";
        if (txtApellidos.getText().isEmpty())
            campos = campos + lblApellidos.getText() + "\n*";
        if (txtUsuario.getText().isEmpty())
            campos = campos + lblUsuario.getText() + "\n*";
        if (txtPsw.getText().isEmpty())
            campos = campos + lblPsw.getText() + "\n*";
        if (!campos.isEmpty())
            cadena = campos.substring(0, campos.length() - 1);
        return cadena;
    }

    public void LimpiarCampos() {
        txtCedula.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtUsuario.setText("");
        txtPsw.setText("");
        //cmb_PuntoVenta.setSelectedIndex(0);
        usuarios.clear();
    }



}
