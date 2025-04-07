package formulariosGUI;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class InicioFerreteria {
    private JPanel main;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton venderButton;
    private JButton salirButton;
    private JPanel contenedor2;
    private JDesktopPane container;

    public InicioFerreteria() {





        comboBox1.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

                String seleccion = (String) comboBox1.getSelectedItem();


                if (seleccion != null && seleccion.equals("Clientes")) {
                    FormClientes formClientes = new FormClientes();
                    formClientes.setVisible(true);  // Hacerlo visible


                } else if (seleccion != null && seleccion.equals("Proveedores")) {
                    FormProveedor formProveedor = new FormProveedor();
                    formProveedor.setVisible(true);

                } else if (seleccion != null && seleccion.equals("Inventario")) {
                    FormInventario formInventario = new FormInventario();
                    formInventario.setVisible(true);

                } else if (seleccion != null && seleccion.equals("Empleados")) {
                    FormEmpleados formEmpleados = new FormEmpleados();
                    formEmpleados.setVisible(true);
                }


            }


        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormVenta formVenta = new FormVenta();
                formVenta.setVisible(true);
            }
        });
    }

    private void setTitle(String ventanaPrincipal) {
    }

    private void setDefaultCloseOperation(int exitOnClose) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("InicioFerreteria");
        frame.setContentPane(new InicioFerreteria().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1920, 1006);
        frame.setResizable(false);
    }


    private void createUIComponents() {
        contenedor2 = new formulariosGUI.FondoPanel();
    }
}