package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.Security;
import micotizador.offline.filestructure.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Created by Veronica Zhagui on 06/07/2015.
 */
public class Login extends JDialog {
    private JTextField txt_Username;
    private JPasswordField txt_Password;
    private JButton ingresarButton;
    private JPanel panel1;
    private JLabel lblIcon;
    private JLabel lblCrearUsuario;
    private JButton btnCrearUsuario;
    private static Security CurrentSecurity;

    public Login() {

        // inicializa el diálogo
        super();
        //Image icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        //this.setIconImage(icon);
        // establece el panel (del diseño) que va a presentarse dentro del contenido del JFrame
        setContentPane(panel1);
        //btnCrearUsuario.setVisible(false);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Boolean Logged = false;
                try {
                    //Si se crea un nuevo usuario se tiene que volver a cargar
                    //el archivo con los usuarios
                    Actulizar();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                Security a =  Main.getCurrentSecurity();
                if (Main.getCurrentSecurity()!=null) {
                    for (User u : Main.getCurrentSecurity().getUsers()) {
                        if (u.getUserName().toLowerCase().equals(txt_Username.getText().toLowerCase()) &&
                                u.getPassword().equals(txt_Password.getText())) {
                            Main.setCurrentUser(u);
                            Logged = true;
                            setVisible(false);
                            break;
                        }
                    }
                }

                if (!Logged) {
                    JOptionPane.showMessageDialog(null, "Credenciales erróneas");
                }


            }
        });


        btnCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario us = new Usuario();
                //us.setSize(500,300);
                us.pack();
                //us.setLocation(450,200);
                us.setLocationRelativeTo(null);
                us.setResizable(true);
                us.setModal(true);
                us.setVisible(true);
            }
        });

    }
    public void Actulizar()throws Exception{
        Gson g = new Gson();
        String StartFolder = Main.get_StartFolder();
        final File SecurityFile = new File(StartFolder + "security.config");
        Path SecurityPath = FileSystems.getDefault().getPath(SecurityFile.getPath());
        String contents = new String(Files.readAllBytes(SecurityPath), StandardCharsets.UTF_8);
        String SecurityDecypted = AES_Helper.decrypt(contents);
        CurrentSecurity = g.fromJson(SecurityDecypted, Security.class);
        Main.setCurrentSecurity(CurrentSecurity);
        Security s = Main.getCurrentSecurity();
    }
    public static Security getCurrentSecurity() {
        return CurrentSecurity;
    }
}
