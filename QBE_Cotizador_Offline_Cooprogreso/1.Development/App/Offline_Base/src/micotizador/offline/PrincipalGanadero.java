package micotizador.offline;

import micotizador.offline.windows.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Veronica Zhagui on 07/08/2015.
 */
public class PrincipalGanadero extends JFrame{
    private JPanel panel1;
    private JButton btn_IniciaCotizaciones;
    private JButton btConsultarCotizacion;
    private JButton btn_EnviarCotizaciones;
    private JButton btn_CrearUsuario;
    private JButton btn_CerrarSesion;
    private JButton btn_Historico;
    private Image icon;

    public PrincipalGanadero() {
        super("PrincipalGanadero");
        icon = this.getIconImage();
        //this.setIconImage(icon);

        // establece el panel (del diseño) que va a presentarse dentro del contenido del JFrame // ligación con el diseño gráfico
        setContentPane(panel1);

        // cambia el tamaño de la ventana en función del tamaño del contenido
        //pack();
        //setSize(520,560);
        //setLocation(450,100);
        //setLocationRelativeTo(null);
        setResizable(false);
        // indica que cuando se cierre la ventana, se cierra el sistema
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // presenta visualmente la pantalla
        setVisible(true);

        final PrincipalGanadero x = this;
        btn_IniciaCotizaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                CotizadorGanadero cg = new CotizadorGanadero();
                cg.setIconImage(icon);
                //cg.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
                cg.setSize(1100, 700);
                cg.setLocationRelativeTo(null);
                cg.setResizable(false);
                cg.setModal(true);
                cg.setVisible(true);
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
        btConsultarCotizacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                //Se envia el tipo de producto seleccionado para la consulta
                String producto = "CotizacionGanadero_";
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
        btn_EnviarCotizaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                //Se envia el tipo de producto seleccionado para la consulta
                String producto = "CotizacionGanadero_";
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
        btn_Historico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                icon = x.getIconImage();
                //ConsultarHistorial ch = new ConsultarHistorial();
                //Se envia el tipo de producto seleccionado para la consulta
                String producto = "CotizacionGanadero_";
                HistorialArchivos ch = new HistorialArchivos(producto);
                ch.setIconImage(icon);
                ch.setSize(660,500);
                ch.setLocationRelativeTo(null);
                ch.setModal(true);
                ch.setVisible(true);
            }
        });
    }
}
