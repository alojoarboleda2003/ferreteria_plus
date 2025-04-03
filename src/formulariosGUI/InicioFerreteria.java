package formulariosGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioFerreteria {
    private JPanel main;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel ferreteriaPlusLabel;
    private JButton venderButton;
    private JButton salirButton;

    public InicioFerreteria() {
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String seleccion = (String) comboBox1.getSelectedItem();


                if (seleccion != null && seleccion.equals("Clientes")) {
                    FormClientes formClientes = new FormClientes();
                    formClientes.setVisible(true);

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("InicioFerreteria");
        frame.setContentPane(new InicioFerreteria().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1320, 900);
        frame.setResizable(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
