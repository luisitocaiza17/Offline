package micotizador.offline.windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Veronica Zhagui on 11/08/2015.
 */
public class Animal extends JDialog {
    private JPanel pnlPrincipal;
    private JLabel lblDatosAnimal;
    private JLabel lblTipoAnimal;
    private JComboBox cmb_TipoAnimal;
    private JLabel lblNroArete;
    private JTextField txtNroArete;
    private JLabel lblNroChip;
    private JTextField txtNroChip;
    private JLabel lblRaza;
    private JComboBox cmb_Raza;
    private JLabel lblOrigen;
    private JComboBox cmb_Origen;
    private JLabel lblProcedencia;
    private JTextField txtProcedencia;
    private JLabel lblEdad;
    private JTextField txtEdad;
    private JLabel lblValorAsegurar;
    private JTextField txtValorAsegurar;
    private JButton btnAceptar;

    public Animal() {
        super();
        setContentPane(pnlPrincipal);
        pack();

        cmb_TipoAnimal.insertItemAt("Seleccionar",0);
        cmb_Raza.insertItemAt("Seleccionar",0);
        cmb_Origen.insertItemAt("Seleccionar",0);

        cmb_TipoAnimal.setSelectedIndex(0);
        cmb_Raza.setSelectedIndex(0);
        cmb_Origen.setSelectedIndex(0);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String campos = ValidarCampos();
                if (campos.length()>0){
                    JOptionPane.showMessageDialog(null,"Debe Ingresar los campos obligatorios:\n* "+campos,"Aviso",1);
                    return;
                }
            }
        });
    }
    public String ValidarCampos(){
        String campos = "";
        String cadena="";
        Integer cont = 0;
        if (cmb_TipoAnimal.getSelectedIndex()==0)
            campos = campos+lblTipoAnimal.getText()+"\n*";
        if (cmb_Raza.getSelectedIndex()==0)
            campos = campos+lblRaza.getText()+"\n*";
        if (txtNroArete.getText().isEmpty())
            campos = campos+lblNroArete.getText()+"\n*";
        if (txtNroChip.getText().isEmpty())
            campos = campos+lblNroChip.getText()+"\n*";
        if (cmb_Origen.getSelectedIndex()==0)
            campos = campos+lblOrigen.getText()+"\n*";
        if (txtProcedencia.getText().isEmpty())
            campos = campos+lblProcedencia.getText()+"\n*";
        if (txtEdad.getText().isEmpty())
            campos = campos+lblEdad.getText()+"\n*";
        if (txtValorAsegurar.getText().isEmpty())
            campos = campos+lblValorAsegurar.getText()+"\n*";
        if (!campos.isEmpty())
            cadena = campos.substring(0,campos.length()-1);
        return cadena;
    }
}
