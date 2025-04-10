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


        /**
         * se le da la funcionalidad al boton combobox donde sse se escoge cliente o empleado
         * lo lleve a ese formulario o traiga mejor dicho
         */
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
        /**
         * el boton me cierra el programa el boton de salir
         */
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);

            }
        });
        /**
         * me trae el formulario de vender en mi main
         */
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormVenta formVenta = new FormVenta();
                formVenta.setVisible(true);

            }
        });
        /**
         * me  trae el formulario de oder_compra
         */
        ordenDeCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormOrden formOrden = new FormOrden();
                formOrden.setVisible(true);

            }
        });
        /**
         * me trae el formulario de los reportes
         */
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reportes reportes = new Reportes();
                reportes.setVisible(true);
            }
        });
        /**
         * le damos el tamaño al boton de chat como imagen
         */
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/ese.png"));
// Escalamos la imagen al tamaño que queramos (por ejemplo, 40x40 píxeles)
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

// Volvemos a crear un ImageIcon con la imagen escalada
        ImageIcon scaledIcon = new ImageIcon(image);

// Establecemos el icono escalado en el botón
        chatButton.setIcon(scaledIcon);
        /**
         * abrimos el formulario de chat
         */
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