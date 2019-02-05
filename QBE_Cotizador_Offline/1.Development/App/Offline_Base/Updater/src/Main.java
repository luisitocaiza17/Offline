import com.sun.org.apache.xpath.internal.operations.Bool;
import micotizador.offline.AES_Helper;
import micotizador.offline.filestructure.Security;
import micotizador.offline.filestructure.Structure;
import com.google.gson.Gson;
import micotizador.offline.filestructure.TransporteData;
import micotizador.offline.filestructure.User;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 29/06/2015.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String StartFolder = get_AppFolder();
        // Ruta relativa de publicación
        //final File ServerFile = new File(StartFolder + "ServerFile.config");
        // Si el archivo no existe, lo creo con los datos básicos de acceso
        //if (!ServerFile.exists() || ServerFile.isDirectory() || ServerFile.length() == 0) {

            // grabo en el archivo
            // String data = "200.93.229.179:8080";
            //Path p = FileSystems.getDefault().getPath(ServerFile.getPath());
            // Si existe el archivo que lo sobreescriba caso contrario da errror
            //if (!ServerFile.exists())
                //Files.write(p,data.getBytes(StandardCharsets.UTF_8),StandardOpenOption.CREATE_NEW);
        //}

        String AppFolder = Main.get_AppFolder();

        // Ruta relativa de publicación
        final File SecurityFile = new File(AppFolder + "security.config");

        // Si el archivo no existe, lo creo con los datos básicos de acceso
        if (!SecurityFile.exists() || SecurityFile.isDirectory() || SecurityFile.length() == 0) {

            Security s = new Security();
            User firstUser = new User();
            firstUser.setUserID(1);
            firstUser.setName("Administrador");
            firstUser.setUserName("admin");
            firstUser.setPassword("1234");

            List<User> users = new ArrayList<User>();
            users.add(firstUser);
            s.setUsers(users);

            // serializo a Json
            Gson g = new Gson();
            String SecurityContent = g.toJson(s);

            // encripto la información
            String encrypted = AES_Helper.encrypt(AES_Helper.padString(SecurityContent));

            // grabo en el archivo
            Path p = FileSystems.getDefault().getPath(SecurityFile.getPath());
            // Si existe el archivo que lo sobreescriba caso contrario da errror
            if (SecurityFile.exists())
                Files.write(p, encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            else
                Files.write(p, encrypted.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
        }

        // leo el servicio para ver si hay una nueva versión de la definición
        //URL url = new URL("http://localhost:8080/UpdateServer_war_exploded/UpdateService");
        //URL url = new URL("http://192.168.0.111:8080/CotizadorWeb/AgriExportarDatos");
        final File StructureFile = new File(StartFolder + "ServerFile.config");
        Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
        String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);

        //URL url = new URL("http://"+StructureContents.trim()+"/CotizadorWeb/AgriExportarDatos");
        //ESPECIFICAMO 1 PARA CREDIFE Y 2 PARA COOPROGRESO

        //COPROGRESO
        //URL url = new URL(StructureContents.trim()+"/AgriExportarDatos?cotizador=2");

        //CREDIFE
        URL url = new URL(StructureContents.trim()+"/AgriExportarDatos?cotizador=1");
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
        //Structure st = g.fromJson(res, Structure.class);
        //TransporteData st = g.fromJson(decrypted, TransporteData.class);

        // Deberá actualizar? false cambie por true
        Boolean ShouldUpdate = true;


        // Leo el archivo actual y comparo si las versiones son iguales o el descargado es más actual, en ese caso se procede al reemplazo del contenido
        final File CurrentStructureFile = new File(AppFolder + "structure.config");
        Path CurrentStructurePath = FileSystems.getDefault().getPath(CurrentStructureFile.getPath());

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

        if (ShouldUpdate) {
            // se procede con la actualización del archivo a las nuevas definiciones
            if (!CurrentStructureFile.exists() || CurrentStructureFile.isDirectory()) {
                Files.write(CurrentStructurePath, res.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
            } else {

                Files.write(CurrentStructurePath, res.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
            }
        }
        System.out.println("Proceso Culminado - Actualización Correcta");
        // leo el servicio para ver si hay una nueva versión de los class del sistema
        // TODO

        //FileUtils.copyURLToFile("","");

        //FileUtils.copyURLToFile("","");
    }

    public static String get_AppFolder() {
        String StartFolder = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        if (StartFolder.endsWith("Updater.jar"))
            StartFolder = StartFolder.substring(0, StartFolder.length() - "Updater.jar".length()) + ".." + File.separator + "app" + File.separator;
        else
            StartFolder = StartFolder + ".." + File.separator + "Offline_Base" + File.separator + "micotizador" + File.separator + "offline" + File.separator;
        return StartFolder;
    }
}
