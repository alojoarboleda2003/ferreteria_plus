package formulariosGUI;

import DAO.DetalleOrdenDAO;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.DetetalleOrden;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class FormOrden extends JFrame {
    private JPanel Forden;
    private JTable table1;
    private JButton verButton;
    private JTextField encargado;
    private JTextField cliente;
    private JTable table2;
    private JComboBox estado2;
    private JTextField total;
    private JScrollPane Tproductos;
    private JButton seleccionarButton;
    private JButton generarFacturaButton;
    int filas = 0;
    int filas1 = 0;
    int idOrden = 0;


    DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();

    public FormOrden() {
        setContentPane(Forden);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1050, 500);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);


        obtener_datos();
        agregar_datos_p(idOrden);


        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                // Obtenemos la fila seleccionada en table1
                int selectedRow = table1.getSelectedRow();

                if (selectedRow >= 0) {
                    // Obtenemos el ID de la orden de la columna correspondiente
                    // Asegúrate de que la columna 0 tiene el ID de la orden en tu JTable
                    idOrden = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());

                    // Verifica que el idOrden se está obteniendo correctamente
                    System.out.println("ID Orden seleccionada: " + idOrden);

                    // Llamamos al método para agregar los detalles de la orden en table2
                    agregar_datos_p(idOrden);

                    // Llamamos al método para mostrar los datos del cliente y encargado
                    mostrar_cliente(idOrden);



                }




            }

        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectFila = table1.getSelectedRow();

                if (selectFila >= 0) {
                     table1.getValueAt(selectFila, 0);
                     table1.getValueAt(selectFila, 1);
                     table1.getValueAt(selectFila, 2);



                    filas = selectFila;
                }
            }
        });

        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectFila = table2.getSelectedRow();

                if (selectFila >= 0) {
                    table2.getValueAt(selectFila, 0);
                    table2.getValueAt(selectFila, 1);
                    table2.getValueAt(selectFila, 2);
                    table2.getValueAt(selectFila, 3);
                    table2.getValueAt(selectFila, 4);




                    filas1 = selectFila;

                }


            }
        });
        encargado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        estado2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Obtener la fila seleccionada
                int filaSeleccionada = table2.getSelectedRow();

                // Verificar si se seleccionó alguna fila
                if (filaSeleccionada != -1) {
                    // Obtener el valor de la columna "id_detalle_orden" (suponiendo que es la primera columna)
                    String idDetalleOrdenStr = (String) table2.getValueAt(filaSeleccionada, 0); // Obtiene el valor como String
                    int id_detalle_orden = Integer.parseInt(idDetalleOrdenStr);

                    // Mostrar el id_detalle_orden o hacer algo con él
                    System.out.println("ID Detalle Orden seleccionado: " + id_detalle_orden);

                    // Aquí puedes llamar a tu DAO o a cualquier función que necesite este id
                    String estado = (String) estado2.getSelectedItem(); // Estado seleccionado en el JComboBox

                    // Llamar al método de actualización con el id_detalle_orden y estado
                    detalleOrdenDAO.actualizar(id_detalle_orden, estado);
                } else {
                    // Si no hay fila seleccionada, puedes mostrar un mensaje
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una fila de la tabla.");
                }


            }
        });
        total.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos la fila seleccionada en table1
                int selectedRow = table2.getSelectedRow();

                if (selectedRow >= 0) {
                    // Obtenemos el ID de la orden de la columna correspondiente
                    // Asegúrate de que la columna 0 tiene el ID de la orden en tu JTable
                    idOrden = Integer.parseInt(table2.getValueAt(selectedRow, 0).toString());

                    mostrar_subtotal(idOrden);



                }

            }
        });
        generarFacturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



    ConexionBD conexionBD = new ConexionBD();

    public void obtener_datos() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Orden");
        model.addColumn("Cliente");
        model.addColumn("Encargado");



        table1.setModel(model);
        String[] dato = new String[3];
        Connection con = conexionBD.getConnection();

        try {
            Statement start = con.createStatement();
            String query = "SELECT o.id_orden, c.id_cliente AS id_cliente, e.id_empleado AS id_empleado " +
                    "FROM orden_compra o " +
                    "JOIN cliente c ON o.id_cliente = c.id_cliente " +
                    "JOIN empleado e ON o.id_empleado = e.id_empleado" ;



            ResultSet rs = start.executeQuery(query);

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);

                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void agregar_datos_p(int idOrden) {
        // Creamos el modelo de la tabla para que tenga las columnas correspondientes.
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Detalle O");
        model.addColumn("Nombre Producto");
        model.addColumn("Cantidad");
        model.addColumn("Subtotal");
        model.addColumn("Estado");


        table2.setModel(model);

        String[] dato = new String[5]; // Datos por cada fila (ID, Cliente, Encargado, Cantidad, Subtotal)
        Connection con = conexionBD.getConnection();

        try {
            // Realizamos la consulta para obtener los detalles de la orden
            String query = "SELECT d.id_detalle_orden, " +
                    "r.nombres AS nombre_inventario, " +
                    "d.cantidad, d.subtotal, d.estado " +
                    "FROM detalle_orden_compra d " +
                    "JOIN inventario r ON r.id_inventario = d.id_inventario " +
                    "WHERE d.id_detalle_orden = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idOrden);  // Usamos el ID de la orden pasada como parámetro
            ResultSet rs = stmt.executeQuery();



            // Iteramos sobre los resultados y los añadimos a la tabla
            while (rs.next()) {
                dato[0] = rs.getString(1); // ID del detalle de la orden
                dato[1] = rs.getString(2); // Nombre del producto (ahora el inventario)
                dato[2] = rs.getString(3); // Cantidad
                dato[3] = rs.getString(4); // Subtotal
                dato[4] = rs.getString(5); // estado



                model.addRow(dato);  // Añadimos la fila a la tabla
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void mostrar_cliente(int idOrden) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = conexionBD.getConnection();

            // Consulta SQL con los espacios correctamente colocados
            String query = "SELECT c.nombre AS nombre_cliente, e.nombre AS nombre_empleado " +
                    "FROM orden_compra o " +
                    "JOIN cliente c ON o.id_cliente = c.id_cliente " +
                    "JOIN empleado e ON o.id_empleado = e.id_empleado " +
                    "WHERE o.id_orden = ?";

            // Preparar la sentencia SQL
            stmt = con.prepareStatement(query);
            stmt.setInt(1, idOrden);  // Usamos idOrden aquí como parámetro numérico
            rs = stmt.executeQuery();

            // Verificar si se encuentra el cliente y empleado
            if (rs.next()) {
                String nombreCliente = rs.getString("nombre_cliente");
                String nombreEmpleado = rs.getString("nombre_empleado");

                // Establecer los valores en los JTextFields
                cliente.setText(nombreCliente);
                encargado.setText(nombreEmpleado);

            } else {
                System.out.println("Cliente o empleado no encontrados.");
                cliente.setText("No encontrado");
                encargado.setText("No encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_subtotal(int idOrden) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = conexionBD.getConnection();

            // Consulta SQL con los espacios correctamente colocados
            String query = "SELECT subtotal FROM detalle_orden_compra WHERE id_detalle_orden = ?";

            // Preparar la sentencia SQL
            stmt = con.prepareStatement(query);
            stmt.setInt(1, idOrden);  // Usamos idOrden aquí como parámetro numérico
            rs = stmt.executeQuery();

            // Verificar si se encuentra el cliente y empleado
            if (rs.next()) {
                double subtotales = rs.getDouble("subtotal");


                // Establecer los valores en los JTextFields
                total.setText(String.valueOf(subtotales));


            } else {
                System.out.println("subtotal no encontrados.");
                total.setText("No encontrado");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private String obtenerNombreEmpleado(int idEmpleado) {
        return "";
    }

    private String obtenerNombreCliente(int idCliente) {
        return "";
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("FormOrden");
        frame.setContentPane(new FormOrden().Forden);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(900, 400);
        frame.setResizable(false);
    }
}

