package micotizador.offline;

import micotizador.offline.windows.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Created by Veronica Zhagui on 29/06/2015.
 */
public class Principal extends JFrame {

    private JPanel panel1;
    private JButton btn_IniciaCotizaciones;
    private JButton btn_CerrarSesion;
    private JButton btn_EnviarCotizaciones;
    private JButton btn_CrearUsuario;
    private JButton btConsultarCotizacion;
    private JButton btn_Historico;
    private JButton btn_Historico_CI;
    private JButton btnActualizar;
    private JLabel lbl_Version;
    private JButton btnCotizadorGanadero;
    private JButton aceptarButton;
    private Image icon;

    public Principal() {

        // pone el nombre de la ventana
        super("Principal");
        //icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        //this.setIconImage(icon);
        //final Image icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        //this.setIconImage(icon);
        // establece el panel (del diseño) que va a presentarse dentro del contenido del JFrame // ligación con el diseño gráfico
        setContentPane(panel1);
        btn_CrearUsuario.setVisible(false);

        // cambia el tamaño de la ventana en función del tamaño del contenido
        //pack();
        //setSize(750,540);
        //setLocation(450,100);
        //setLocationRelativeTo(null);
        setResizable(false);
        // indica que cuando se cierre la ventana, se cierra el sistema
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        String StartFolder = Main.get_StartFolder();
        final File StructureFile = new File(StartFolder+File.separator+"version.config");
        Path StructurePath = FileSystems.getDefault().getPath(StructureFile.getPath());
        if (StructureFile.exists()) {
            try {
                String StructureContents = new String(Files.readAllBytes(StructurePath), StandardCharsets.UTF_8);
                lbl_Version.setText(StructureContents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // presenta visualmente la pantalla
        setVisible(true);
        final Principal x = this;
        //Main.getCurrentUser().getisAdmin();
        /*if(Main.getCurrentUser().getisAdmin()){
            btn_CrearUsuario.setVisible(true);
        }else{
            btn_CrearUsuario.setVisible(false);
        }*/

        btn_Historico_CI.setVisible(false);

        btn_IniciaCotizaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                CotizadorAgricola  ca = new CotizadorAgricola("",null);
                ca.setIconImage(icon);
                //ca.pack();
                //ca.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
                ca.setSize(990, 600);
                //ca.setLocation(210,80);
                ca.setLocationRelativeTo(null);
                ca.setResizable(false);
                ca.setModal(true);
                ca.setVisible(true);
               //String datainterna = ca.getDataInterna();
                //JOptionPane.showMessageDialog(null, datainterna);

            }
        });
        btn_EnviarCotizaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                //Se envia el tipo de producto seleccionado para la consulta

                String producto = "CotizacionAgricola_";
                CargarCotizaciones cargarCotizacion = new CargarCotizaciones(producto);
                cargarCotizacion.setIconImage(icon);
                cargarCotizacion.setSize(500,300);
                //cargarCotizacion.setLocation(450,200);
                cargarCotizacion.setLocationRelativeTo(null);
                cargarCotizacion.setResizable(false);
                cargarCotizacion.setModal(true);
                cargarCotizacion.setVisible(true);

            }
        });

        btn_CrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                Usuario us = new Usuario();
                us.setIconImage(icon);
                us.setSize(500,300);
                //us.setLocation(400,150);
                us.setLocationRelativeTo(null);
                us.setResizable(false);
                us.setModal(true);
                us.setVisible(true);
            }
        });

        btn_CerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setCurrentUser(null);
                // sale del sistema
                System.exit(0);
            }
        });


        btConsultarCotizacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                //Se envia el tipo de producto seleccionado para la consulta
                String producto = "CotizacionAgricola_";
                ConsultarCotizacion cc = new ConsultarCotizacion(producto);
                cc.setIconImage(icon);
                //ca.pack();
                //ca.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
                cc.setSize(700,500);
                //cc.setLocation(350,130);
                cc.setLocationRelativeTo(null);
                cc.setModal(true);
                cc.setVisible(true);
            }
        });

        btn_Historico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                //ConsultarHistorial ch = new ConsultarHistorial();
                //Se envia el tipo de producto seleccionado para la consulta
                String producto = "CotizacionAgricola_";
                HistorialArchivos ch = new HistorialArchivos(producto);
                ch.setIconImage(icon);
                ch.setSize(660,500);
                ch.setLocationRelativeTo(null);
                ch.setModal(true);
                ch.setVisible(true);
            }
        });

        btn_Historico_CI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                String producto = "CotizacionAgricola_";
                ConsultarCotizacionCI cc = new ConsultarCotizacionCI(producto);
                cc.setIconImage(icon);
                cc.setSize(990,600);
                cc.setLocationRelativeTo(null);
                cc.setModal(true);
                cc.setVisible(true);
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String StartFolder = Main.get_StartFolder();
                JFileChooser fc = new JFileChooser();
                fc.setMultiSelectionEnabled(false);
                int seleccion = fc.showSaveDialog(Principal.this.panel1);
                String rutad = "";
                if (seleccion == 0) {
                    try {
                        //mostramos ruta de donde se va a escoger la el archivo .zip
                        rutad = fc.getSelectedFile().getAbsolutePath();
                        File StructureFile = new File(rutad);
                        /*comentado para pruebas*/
                        String[] elems = System.getProperty("java.class.path").split(File.pathSeparator);
                        File mijar = null;
                        //Encontramos el jar del instalador
                        for (String s : elems) {
                            if (!s.endsWith("Offline_Base.jar")) continue;
                            mijar = new File(s);
                            break;
                        }
                        System.out.println("Ruta absoluta a mi jar es :  " + mijar.getAbsolutePath());
                        String ruta = mijar.getAbsolutePath().replace("Offline_Base.jar", "");
                        System.out.println(ruta);
                        //JOptionPane.showMessageDialog(null, ruta);
                        //verificamos si el archivo contiene el jar para poder escribirla
                        if (Principal.this.verificadorZIP(rutad, ruta)) {
                            Principal.this.DescomprimidoZIP(rutad, ruta);
                            System.out.println("Proceso de Actualizacion Correcto");
                            JOptionPane.showMessageDialog(null, "Proceso de Actualizacion Correcto");
                            //cerramos la ventana
                            Main.setCurrentUser(null);
                            // sale del sistema
                            System.exit(0);
                        } else {
                            System.out.println("Archivo Incorrecto no contiene el cotizador");
                            JOptionPane.showMessageDialog(null, "Archivo Incorrecto no contiene el cotizador");
                            //cerramos la ventana
                            Main.setCurrentUser(null);
                            // sale del sistema
                        }
                        //solo por pruebas
                        /*Seccion usada para pruebas*/
                        /*JOptionPane.showMessageDialog(null, "E:\\instaladores\\decomprimido\\app");
                        if (Principal.this.verificadorZIP(rutad, "E:\\instaladores\\decomprimido\\app")) {
                            Principal.this.DescomprimidoZIP(rutad, "E:\\instaladores\\decomprimido\\app");
                            System.out.println("Proceso de Actualizacion Correcto");
                            JOptionPane.showMessageDialog(null, "Proceso de Actualizacion Correcto");
                        } else {
                            System.out.println("Archivo Incorrecto no contiene el cotizador");
                            JOptionPane.showMessageDialog(null, "Archivo Incorrecto no contiene el cotizador");
                        }
                        String rutaPlantilla="E:\\instaladores\\decomprimido\\app\\version.config";
                        String NombreVers="";
                        try {
                            String[] NombreVersion = rutad.split("_");
                             NombreVers=NombreVersion[1];
                             NombreVers=NombreVers.replace(".zip","");
                             NombreVers="Versión : "+NombreVers;
                        }catch (Exception e11){
                            NombreVers="desconocido";
                        }
                       */

                        } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Se ha producido un error en la Actualizacion: " + e1.getMessage());
                    }
                }
            }
        });

    }
    final static int BUFFER = 2048;

    public boolean verificadorZIP(String zipUsado, String RutaActualCotizador){
        //ponemos la ruta a la que apuntamos el zip, y apuntamos la ruta del jar
        try {
            File dirDestino = new File(RutaActualCotizador);
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(zipUsado);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            FileOutputStream fos = null;
            ZipEntry entry;
            int count;
            int index = 0;
            byte data[] = new byte[BUFFER];
            String rutaarchivo = null;
            boolean existe=false;
            //Verdificamos si el .zip contiene el jar y solo si lo contiene permitimos que se realice todo el proceso
            while((entry = zis.getNextEntry()) != null) {
                System.out.println("Extracting: " +entry);
                rutaarchivo=""+entry;
                int resultado=rutaarchivo.indexOf("Offline_Base.jar");
                if(resultado != -1) {
                    System.out.println("Offline_Base.jar encontrado");
                    existe=true;
                    break;
                }
            }
            zis.close();
            if(existe){//Si existe le marcamos como un zip valido caso contrario no le permitimos procesar
                return true;
            }else{
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void DescomprimidoZIP(String zipUsado, String RutaActualCotizador){
        // entra la ruta del archivo elejido, y la ruta donde se va a reemplazar
        try {
            File dirDestino = new File(RutaActualCotizador);
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(zipUsado);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            FileOutputStream fos = null;
            ZipEntry entry;
            int count;
            int index = 0;
            byte data[] = new byte[BUFFER];
            String rutaarchivo = null;
            while((entry = zis.getNextEntry()) != null) {
                System.out.println("Extracting: " +entry);
                rutaarchivo=""+entry;
                int resultado=rutaarchivo.indexOf("Configuration.config");
                if(resultado != -1) {
                    System.out.println("salto Configuration.config");
                    continue;
                }
                resultado=rutaarchivo.indexOf("security.config");
                if(resultado != -1) {
                    System.out.println("salto security.config");
                    continue;
                }
                rutaarchivo = entry.getName();
                // tenemos que quitar el primer directorio
                index = rutaarchivo.indexOf("/");
                rutaarchivo = rutaarchivo.substring(index+1);
                if(rutaarchivo.trim().length() > 0)
                {
                // write the files to the disk
                    try {
                        dest = null;
                        File fileDest = new File(dirDestino.getAbsolutePath() + "/" + rutaarchivo);
                        if(entry.isDirectory())
                        {
                            fileDest.mkdirs();
                        }
                        else
                        {
                            if(fileDest.getParentFile().exists() == false)
                                fileDest.getParentFile().mkdirs();
                            fos = new FileOutputStream(fileDest);
                            dest = new BufferedOutputStream(fos, BUFFER);
                            while ((count = zis.read(data, 0, BUFFER)) != -1)
                            {
                                dest.write(data, 0, count);
                            }
                            dest.flush();
                        }
                    } finally {
                        try { if(dest != null) dest.close(); } catch(Exception e) {;}
                    }
                }
            }
            zis.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here


    }


}
