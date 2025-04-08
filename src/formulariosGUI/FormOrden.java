package formulariosGUI;

import conexionBD.ConexionBD;

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
    int filas = 0;
    int filas1 = 0;
    int idOrden = 0;


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
                    // Obtenemos el ID de la orden de la columna 0 (asegúrate de que esta columna tenga el ID de la orden)
                     idOrden = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());

                    // Llamamos al método para agregar los detalles de la orden en table2
                    agregar_datos_p(idOrden);
                }  // Depuración
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
                    table2.getValueAt(selectFila, 5);




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

            }
        });
        total.addActionListener(new ActionListener() {
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
            String query = "SELECT o.id_orden, c.nombre AS nombre_cliente, e.nombre AS nombre_empleado " +
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
        model.addColumn("Cliente");
        model.addColumn("Encargado");
        model.addColumn("Nombre Producto");
        model.addColumn("Cantidad");
        model.addColumn("Subtotal");

        table2.setModel(model);

        String[] dato = new String[6]; // Datos por cada fila (ID, Cliente, Encargado, Cantidad, Subtotal)
        Connection con = conexionBD.getConnection();

        try {
            // Realizamos la consulta para obtener los detalles de la orden
            String query = "SELECT d.id_detalle_orden, " +
                    "c.nombre AS nombre_cliente, " +
                    "e.nombre AS nombre_empleado, " +
                    "r.nombres AS nombre_inventario, " +
                    "d.cantidad, d.subtotal " +
                    "FROM detalle_orden_compra d " +
                    "JOIN cliente c ON c.id_cliente = d.id_cliente " +
                    "JOIN empleado e ON e.id_empleado = d.id_empleado " +
                    "JOIN inventario r ON r.id_inventario = d.id_inventario " +
                    "WHERE d.id_detalle_orden = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, this.idOrden);  // Usamos el ID de la orden pasada como parámetro
            ResultSet rs = stmt.executeQuery();

            // Iteramos sobre los resultados y los añadimos a la tabla
            while (rs.next()) {
                dato[0] = rs.getString(1); // ID del detalle de la orden
                dato[1] = rs.getString(2); // Nombre del cliente
                dato[2] = rs.getString(3); // Nombre del encargado
                dato[3] = rs.getString(4); // Nombre del producto
                dato[4] = rs.getString(5); // Cantidad
                dato[5] = rs.getString(6); // Subtotal

                model.addRow(dato);  // Añadimos la fila a la tabla
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

