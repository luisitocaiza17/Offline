package micotizador.offline;

import com.google.gson.Gson;
import micotizador.offline.filestructure.Configuration;
import micotizador.offline.filestructure.Security;
import micotizador.offline.filestructure.TransporteData;
import micotizador.offline.filestructure.User;
import micotizador.offline.windows.Producto;

import javax.swing.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Veronica Zhagui on 29/06/2015.
 */
public class Main {

    private static Security CurrentSecurity;
    private static User CurrentUser;
    private static TransporteData CurrentTransporteData;
    private static Configuration CurrentConfiguration;

    public static void main(String[] args) throws Exception {
        // leo los archivos de configuraciónn y cargo los objetos de sesión
        try {

            Gson g = new Gson();
            String StartFolder = get_StartFolder();

            final File SecurityFile = new File(StartFolder + "security.config");
            Path SecurityPath = FileSystems.getDefault().getPath(SecurityFile.getPath());
            String contents = new String(Files.readAllBytes(SecurityPath), StandardCharsets.UTF_8);
            String SecurityDecypted = AES_Helper.decrypt(contents);
            CurrentSecurity = g.fromJson(SecurityDecypted, Security.class);


            final File StructureFile = new File(StartFolder + "structure.config");
            Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
            String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
            String StructureDecypted = AES_Helper.decrypt(StructureContents);
            CurrentTransporteData = g.fromJson(StructureDecypted, TransporteData.class);
            //CurrentTransporteData = g.fromJson(StructureContents,TransporteData.class);

            //TODO: cargar el archivo de configuration punto de venta
            final File ConfigurationFile = new File(StartFolder + "Configuration.config");
            Path ConfigurationPath = FileSystems.getDefault().getPath(ConfigurationFile.getPath());
            String ConfigurationContents = new String(Files.readAllBytes(ConfigurationPath), StandardCharsets.UTF_8);
            //String encrypted = AES_Helper.encrypt(AES_Helper.padString(ConfigurationContents));
            String ConfigurationDecypted = AES_Helper.decrypt(ConfigurationContents);
            CurrentConfiguration = g.fromJson(ConfigurationDecypted, Configuration.class);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Antes de instanciar la ventana principal se cambia el estilo de las pantallas
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            //File ruta = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\micotizador\\offline\\Themes\\Veronica.theme");
            //UIManager.setLookAndFeel(new UpperEssentialLookAndFeel(ruta.getAbsolutePath()));
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Principal form = new Principal();
        Producto producto = new Producto();

    }

    public static String get_StartFolder(){
        String StartFolder = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        if (StartFolder.endsWith("Offline_Base.jar"))
            StartFolder = StartFolder.substring(0, StartFolder.length() - "Offline_Base.jar".length());
        else
            StartFolder = StartFolder + File.separator + "micotizador" + File.separator + "offline" +  File.separator;
        return StartFolder;
    }

    // NO PUEDO CAMBIAR LA SEGURIDA Y ESTRUCTURA DESDE AQUI

    public static void setCurrentSecurity(Security currentSecurity) {
        CurrentSecurity = currentSecurity;
    }

    public static Security getCurrentSecurity() {
        return CurrentSecurity;
    }

    public static User getCurrentUser() {
        return CurrentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUser = currentUser;
    }

    public static TransporteData getCurrentTransporteData() {
        return CurrentTransporteData;
    }

    public static void setCurrentTransporteData(TransporteData currentTransporteData) {
        CurrentTransporteData = currentTransporteData;
    }
    //TODO: Configuration
    public static Configuration getCurrentConfiguration() {
        return CurrentConfiguration;
    }

    public static void setCurrentConfiguration(Configuration CurrentConfiguration) {
        CurrentConfiguration = CurrentConfiguration;
    }
}
