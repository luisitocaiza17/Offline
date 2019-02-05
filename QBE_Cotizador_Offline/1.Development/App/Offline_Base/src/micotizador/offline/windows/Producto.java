package micotizador.offline.windows;

import micotizador.offline.Main;
import micotizador.offline.Principal;
import micotizador.offline.PrincipalGanadero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Veronica Zhagui on 06/08/2015.
 */
public class Producto extends JFrame{
    private JButton btnProcuctoAgricola;
    private JButton btnProductoGanadero;
    private JPanel pnlPrincipal;
    private Image icon;

    public Producto() {
        super("Producto");
        //Cambiar el icono
        icon = new ImageIcon(getClass().getResource("/micotizador/offline/image/logo.png")).getImage();
        this.setIconImage(icon);
        setContentPane(pnlPrincipal);
        setSize(400,260);
        setLocationRelativeTo (null);
        setVisible(true);
        setResizable(false);
        //TODO: Ocultar boton ganadero
        btnProductoGanadero.setVisible(false);
        final Producto producto = this;

        // Ventana de Ingreso
        Login lg = new Login();
        //lg.setIconImage(icon);
        lg.setSize(400,300);
        lg.setLocation(510,240);
        lg.setLocationRelativeTo (null);
        lg.setTitle("Ingreso al Sistema");
        lg.setResizable(true);
        lg.setModal(true);
        lg.setVisible(true);

        // Si logueo, sigue, sino sale del sistema
        if (Main.getCurrentUser() == null) {
            System.exit(0);
            return;
        }

        lg.dispose();
        btnProductoGanadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalGanadero ganadero = new PrincipalGanadero();
                ganadero.setIconImage(icon);
                ganadero.setSize(500, 600);
                ganadero.setTitle("Cotizador");
                ganadero.setLocationRelativeTo(null);
                //ganadero.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // presenta visualmente la pantalla
                ganadero.setVisible(true);
                producto.dispose();
            }
        });
        btnProcuctoAgricola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Principal agricola = new Principal();
                agricola.setIconImage(icon);
                //agricola.setSize(500, 700);
                agricola.pack();
                agricola.setTitle("Cotizador");
                agricola.setLocationRelativeTo(null);
                agricola.setVisible(true);
                producto.dispose();
            }
        });
    }
}
