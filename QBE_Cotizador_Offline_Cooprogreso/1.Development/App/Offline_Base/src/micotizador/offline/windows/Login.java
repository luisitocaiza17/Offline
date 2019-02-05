package micotizador.offline.windows;

import com.google.gson.Gson;
import micotizador.offline.AES_Helper;
import micotizador.offline.Main;
import micotizador.offline.filestructure.Security;
import micotizador.offline.filestructure.Structure;
import micotizador.offline.filestructure.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;


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
        btnCrearUsuario.setVisible(false);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Actualizamos el archivo JSON Con todos los usuarios y una ves realizado procedemos a verificar la identidad
                String StartFolder = get_AppFolder();
                String AppFolder = Login.get_AppFolder();
                // Ruta relativa de publicación
                final File SecurityFile = new File(AppFolder + "security.config");

                try {
                    //leemos la direccion del servlet

                    /*TODO:COMENTADO ESTO FUNCIONA EN DESARROLLO*/
                    /*final File StructureFile = new File(StartFolder + "ServerFile.config");
                    Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
                    String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                    /*TODO: HASTA AQUI EN DESARROLLO*/


                    /*TODO ESTO FUNCION EN PRODUCCION RUTA RELATIVA DE JAR*/
                    String[] elems = System.getProperty("java.class.path").split(File.pathSeparator);
                    File mijar = null;
                    for (String s1 : elems) {
                        if (!s1.endsWith("Offline_Base.jar")) continue;
                        mijar = new File(s1);
                        break;
                    }
                    System.out.println("Ruta absoluta a mi jar es :  " + mijar.getAbsolutePath());
                    /*TODO: FIN DE LECTURA DE JAR*/

                    /*TODO: LECTURA DE ARCHIVO CONFIG DEL SERVLET PRODUCCION*/
                    String ruta = mijar.getAbsolutePath().replace("Offline_Base.jar", "");
                    final File StructureFile = new File(ruta + "ServerFile.config");
                    Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
                    String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                    /*TODO: FIN DE ELCTURA DE SERVLET*/

                    //tomamos el archivo que retornamos con un buffer temporal
                    URL url = new URL(StructureContents.trim() + "/AgriLogeoOffline");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                    String res = "";
                    String s = null;
                    while ((s = reader.readLine()) != null)
                        res += res += s + "\r\n";

                    // decripto el mensaje
                    String decrypted = AES_Helper.decrypt(res);

                    // deserializo
                    Gson g = new Gson();
                    Structure st = g.fromJson(decrypted, Structure.class);
                    Boolean ShouldUpdate = true;

                    /*TODO: Comentado esto funciona en desarrollo*/
                    //final File CurrentStructureFile = new File(AppFolder + "security.config");


                    /*TODO:ESTO DEBE ESTAR DESCOMENTADO PARA SALIR A PRODUCCION*/
                    ruta = mijar.getAbsolutePath().replace("Offline_Base.jar", "");
                    final File CurrentStructureFile = new File(ruta + "security.config");
                   /*TODO: FINAL PRODUCCION*/

                    Path CurrentStructurePath = FileSystems.getDefault().getPath(CurrentStructureFile.getPath());

                    // Leo el archivo actual y comparo si las versiones son iguales o el descargado es más actual, en ese caso se procede al reemplazo del contenido
                    //se verifica si es un directorio y el contenido son validos
                    if (CurrentStructureFile.exists() && !CurrentStructureFile.isDirectory()) {

                        String CurrentContents = new String(Files.readAllBytes(CurrentStructurePath), StandardCharsets.UTF_8);

                        if (!CurrentContents.equals("")) {
                            String CurrentDecrypted = AES_Helper.decrypt(CurrentContents);
                            Structure cst = g.fromJson(CurrentDecrypted, Structure.class);
                            if (cst.equals(null)) {
                                ShouldUpdate = true;
                            } else {
                                if (st.getVersion() > cst.getVersion()) {
                                    ShouldUpdate = true;
                                }
                            }
                        } else
                            ShouldUpdate = true;
                    } else {
                        ShouldUpdate = true;
                    }

                    //Si debe actualizarse entonces realizarlo
                    if (ShouldUpdate) {
                        // se procede con la actualización del archivo a las nuevas definiciones
                        if (!CurrentStructureFile.exists() || CurrentStructureFile.isDirectory()) {
                            Files.write(CurrentStructurePath, res.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
                        } else {

                            Files.write(CurrentStructurePath, res.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
                        }
                    }
                    System.out.println("Proceso Culminado - Actualización Correcta");

                }catch (Exception e2){
                    JOptionPane.showMessageDialog(null,"ERROR: "+e2.getMessage());
                    JOptionPane.showMessageDialog(null, "Upps!! Tuvimos un problema o no se tiene acceso a Internet, solo puede continuar con los usarios actuales");

                    e2.printStackTrace();
                }

                //verificamos la informacion que nos envian luego de la actualizacion.

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
                us.setSize(500,300);
                us.setLocation(450,200);
                us.setResizable(false);
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

    public static String get_AppFolder() {
        String StartFolder = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        if (StartFolder.endsWith("Updater.jar"))
            StartFolder = StartFolder.substring(0, StartFolder.length() - "Updater.jar".length()) + ".." + File.separator + "app" + File.separator;
        else
            StartFolder = StartFolder + ".." + File.separator + "Offline_Base" + File.separator + "micotizador" + File.separator + "offline" + File.separator;
        return StartFolder;
    }

    public static Security getCurrentSecurity() {
        return CurrentSecurity;
    }
}
