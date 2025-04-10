package formulariosGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class InicioFerreteria {
    private JPanel main;
    private JComboBox comboBox1;
    private JButton venderButton;
    private JButton salirButton;
    private JPanel contenedor2;
    private JButton ordenDeCompraButton;
    private JButton reportesButton;
    private JButton chatButton;
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
        ordenDeCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormOrden formOrden = new FormOrden();
                formOrden.setVisible(true);

            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reportes reportes = new Reportes();
                reportes.setVisible(true);
            }
        });
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/ese.png"));
// Escalamos la imagen al tamaño que queramos (por ejemplo, 40x40 píxeles)
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

// Volvemos a crear un ImageIcon con la imagen escalada
        ImageIcon scaledIcon = new ImageIcon(image);

// Establecemos el icono escalado en el botón
        chatButton.setIcon(scaledIcon);

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormChat formChat = new FormChat("cliente1", "cliente"); // Usa un ID y rol reales si los tienes
                formChat.conectarServidor("localhost", 12345); // Usa la IP/host y puerto correctos
                formChat.setVisible(true);
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