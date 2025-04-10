package formulariosGUI;

import DAO.DetalleOrdenDAO;
import DAO.InventarioDAO;
import com.toedter.calendar.JDateChooser;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.Empleado;
import modelos.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FormVenta  extends JFrame{
    private JTextField buscador;
    private JButton clickParaSeleccionarButton;
    private JTable datosproducto;
    private JPanel Clientesdisponible;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JSpinner spinner1;
    private JPanel preciocantidad;
    private JTextField cant_venta;
    private JButton agregarProductoButton;
    private JPanel Fventas;
    private JTextField subtotalf;
    private JButton eliminarButton;
    private JTable productosventa;

    private JTextField textField8;
    private JButton cobrarButton;
    private JScrollPane buscarproducto;
    private JScrollPane datoventa;
    private JTextField textField1;
    private JPanel producto_elegido;
    private JTextField buscarcliente;
    private JTextField Ccedula;
    private JPanel infoc;
    private JTextField calendario;
    private JTextField nombreE;
    private JTextField idempleado;
    private JComboBox estado1;
    private JTextField idcliente;
    private JDateChooser JDateChooser2;
    int filas = 0;
    double totalm = 0;
    double totalconiva = 0;
    double IVA = 0.19;




    InventarioDAO inventarioDAO = new InventarioDAO();
    DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();
    private String buscar_cliente;


    public FormVenta() {
        obtener_datos_producto();
        createUIComponents();

        setContentPane(Fventas);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1006, 800);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);

        /**
         * inicializamos las columnas de la tabla productosventa porq sino al mometos
         * de agregar un producto tambn se agregan las columnas, pero solose debe agregar las filas
         */
        //inicializamos las columnas de productos ventas aca porque sino se repiten cada vez que agreguemos un producto
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Producto");
        model.addColumn("Nombre");
        model.addColumn("Cantidad Venta");
        model.addColumn("SubTotal");
        productosventa.setModel(model);

        //fin

        buscador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }

        });
        /**
         * mando losd datos de la tabla a unos texfield donde se expica mas en el metodo
         */
        clickParaSeleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregar_datos_p2();


            }
        });
        /**
         * agregamos el producto seelcina a la tabla productos venta
         */
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombresr = agregarProductoButton.getText();
                Inventario inventario = new Inventario(nombresr);
                inventario.setNombres(nombresr);
                agregar_datos_p();
                psubtotal();
                clear();



            }
        });

        /**
         * se elimina un producto de la compra si ya no se desea
         */
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productosventa.getSelectedRow();

                if (selectedRow >= 0) {
                    // Confirmar la acción de eliminación
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar este producto?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Eliminar la fila de la tabla
                        DefaultTableModel model = (DefaultTableModel) productosventa.getModel();
                        model.removeRow(selectedRow); // Elimina la fila seleccionada


                        // Restar el total con IVA del producto eliminado
                        if (totalm - totalconiva >= 0) {
                            totalm -= totalconiva;  // Restar el total con IVA
                        } else {
                            totalm = 0;  // Evitar que totalm sea negativo
                        }

                        // Actualizar el total en textField8
                        textField8.setText(String.valueOf(totalm));

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún producto para eliminar.");
                }

            }




        });

        textField8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        /**
         * al momneto de hacer click en el boton se guadarn los datos de la venta en la bd
         */
        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                guardar_datos();

            }
        });

        datosproducto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectFila = datosproducto.getSelectedRow();

                if (selectFila >= 0) {
                    textField2.setText((String) datosproducto.getValueAt(selectFila, 0));
                    textField3.setText((String) datosproducto.getValueAt(selectFila, 1));
                    textField4.setText((String) datosproducto.getValueAt(selectFila, 2));
                    textField1.setText((String) datosproducto.getValueAt(selectFila, 3));


                    filas = selectFila;
                }
            }
        });
        cant_venta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {





                double precio = Double.parseDouble(textField4.getText());
                Inventario inventario = new Inventario(precio);
                int cantidad = Integer.parseInt(cant_venta.getText());

                double totalr = precio * cantidad;

                subtotalf.setText(String.valueOf(totalr));



            }




        });
        subtotalf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                psubtotal();



            }
        });

        buscarcliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }


        });
        Ccedula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });

        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });
        buscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                obtener_datos_producto();
            }
        });
        nombreE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        idempleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombree = idempleado.getText();

                Empleado empleado = new Empleado(nombree);
                empleado.setNombre(nombree);
                buscar_empleado();

            }
        });
        idcliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cedula = Integer.parseInt(idcliente.getText());
                String nombresc = idcliente.getText();


                Cliente cliente = new Cliente(nombresc,cedula);
                cliente.setNombre(nombresc);
                cliente.setCedula(cedula);
                buscar_cliente();

            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        JDateChooser2 = new JDateChooser();
        JDateChooser2.setDateFormatString("dd/MM/yyyy");  // Establecer formato de fecha

        // Si necesitas un valor predeterminado
        JDateChooser2.setDate(new java.util.Date());
        JDateChooser2.setPreferredSize(new Dimension(130, 25));
    }

    public void clear() {

        textField3.setText("");
        textField4.setText("");
        textField1.setText("");
        textField2.setText("");
        subtotalf.setText("");
        cant_venta.setText("");


    }



    ConexionBD conexionBD = new ConexionBD();


    public void obtener_datos_producto() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Producto");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Cantidad Disponible");


        datosproducto.setModel(model);
        String[] dato = new String[4];
        Connection con = conexionBD.getConnection();

        try {
            Statement start = con.createStatement();

            // Si no hay texto en el campo de búsqueda, cargamos todos los productos
            String query = "SELECT id_inventario, nombres, precio, cant_disponible FROM inventario";

            String nombreProductoSeleccionado = buscador.getText();
            if (!nombreProductoSeleccionado.isEmpty()) {
                // Si el campo de búsqueda no está vacío, filtrar por nombre
                query = "SELECT id_inventario, nombres, precio, cant_disponible FROM inventario WHERE nombres = ?";
            }
            PreparedStatement pstmt = con.prepareStatement(query);

            if (!nombreProductoSeleccionado.isEmpty()) {
                pstmt.setString(1,   nombreProductoSeleccionado);
            }
            



            ResultSet rs = pstmt.executeQuery();
            model.setRowCount(0);


            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);


                model.addRow(dato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void agregar_datos_p() {
        DefaultTableModel model = (DefaultTableModel) productosventa.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            int id_producto = Integer.parseInt((String) model.getValueAt(i, 0));
            String nombre = (String) model.getValueAt(i,1);
            int cantidad = Integer.parseInt((String) model.getValueAt(i, 2)); // Cantidad
            double subtotal = Double.parseDouble((String) model.getValueAt(i, 3)); // Subtotal



        }

        productosventa.setModel(model);
        String[] dato = new String[4];
        Connection con = conexionBD.getConnection();

        try {
            // Usamos PreparedStatement para prevenir problemas con valores dinámicos
            String query = "SELECT id_inventario, nombres FROM inventario WHERE id_inventario = ?";
            PreparedStatement pstmt = con.prepareStatement(query);


            String nombreProductoSeleccionado = textField2.getText();
            pstmt.setString(1, nombreProductoSeleccionado);

            // Ejecutamos la consulta
            ResultSet rs = pstmt.executeQuery();

            String cantidad = cant_venta.getText();
            String subtotatl = subtotalf.getText();

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = cantidad;
                dato[3] = subtotatl;

                model.addRow(dato);

                //llamamos al metodo de actualizar inventario
                int idProducto = Integer.parseInt(dato[0]);
                int cantidadVendida = Integer.parseInt(cantidad);
                actualizarInventario(idProducto, cantidadVendida);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void guardar_datos() {
        DefaultTableModel model = (DefaultTableModel) productosventa.getModel();

        // Establecemos la conexión a la base de datos
        Connection con = conexionBD.getConnection();

        // Declaramos el id_orden fuera del loop
        int id_orden = 0;

        try {
            // Primero insertamos la orden en la tabla de órdenes (no tenemos el id_orden aún, así que lo insertamos vacío)
            String query = "INSERT INTO orden_compra (id_cliente, id_empleado) VALUES ( ?,?)";
            PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Suponemos que ya tienes los valores de id_cliente y id_empleado desde los campos de texto
            int id_cliente = Integer.parseInt(idcliente.getText());  // Ejemplo
            int id_empleado = Integer.parseInt(idempleado.getText());  // Ejemplo


            pstmt.setInt(1, id_cliente);
            pstmt.setInt(2, id_empleado);


            // Ejecutamos la inserción para obtener el id_orden
            pstmt.executeUpdate();

            // Obtener el id_orden generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id_orden = rs.getInt(1);  // El id_orden generado automáticamente
            }

            // Ahora insertamos los productos en la tabla detalle_orden_compra
            for (int i = 0; i < model.getRowCount(); i++) {
                int id_inventario = Integer.parseInt((String) model.getValueAt(i, 0));  // ID Producto
                int cantidad = Integer.parseInt((String) model.getValueAt(i, 2));      // Cantidad
                double subtotal = Double.parseDouble((String) model.getValueAt(i, 3)); // Subtotal

                // Insertar en la tabla detalle_orden_compra con el id_orden generado
                String detalleQuery = "INSERT INTO detalle_orden_compra (id_orden, id_inventario, id_cliente, id_empleado, cantidad, subtotal, estado) VALUES (?, ?, ?, ?, ?, ?, 'pendiente')";
                PreparedStatement pstmtDetalle = con.prepareStatement(detalleQuery);
                pstmtDetalle.setInt(1, id_orden);  // Usamos el id_orden generado
                pstmtDetalle.setInt(2, id_inventario);  // ID Producto
                pstmtDetalle.setInt(3, id_cliente);  // ID Cliente
                pstmtDetalle.setInt(4, id_empleado);  // ID Empleado
                pstmtDetalle.setInt(5, cantidad);  // Cantidad
                pstmtDetalle.setDouble(6, subtotal);  // Subtotal

                // Ejecutamos la inserción para el detalle de la orden
                pstmtDetalle.executeUpdate();

                // Actualizamos el inventario después de agregar la venta
                actualizarInventario(id_inventario, cantidad); // Reducimos la cantidad en el inventario
            }

            // Si todo salió bien, puedes limpiar la tabla o realizar alguna otra acción
            model.setRowCount(0);  // Limpiamos la tabla de la interfaz (si lo deseas)

            // Mostrar un mensaje de éxito
            JOptionPane.showMessageDialog(null, "Venta registrada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void actualizarInventario(int idProducto, int cantidadVendida) {
        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;
        try {
            String query = "UPDATE inventario SET cant_disponible = cant_disponible - ? " +
                    "WHERE id_inventario = ? AND cant_disponible >= ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, cantidadVendida);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidadVendida);  // ¡Este te estaba faltando!

            // Ejecutamos la actualización
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null,"Inventario actualizado exitosamente.");

                // Verificamos si la cantidad actual es menor a 5
                String checkQuery = "SELECT cant_disponible FROM inventario WHERE id_inventario = ?";
                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                checkStmt.setInt(1, idProducto);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int cantRestante = rs.getInt("cant_disponible");
                    if (cantRestante < 5) {
                        JOptionPane.showMessageDialog(null,"⚠️ Alerta: el inventario del producto ID " + idProducto +
                                " está por agotarse. Cantidad disponible: " + cantRestante);
                    }
                }

                rs.close();
                checkStmt.close();
            } else {
                System.out.println("No hay suficiente inventario para realizar la venta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void agregar_datos_p2() {

        if (datosproducto.getRowCount() > 0) {

            int selectFila = 0;  


            textField2.setText((String) datosproducto.getValueAt(selectFila, 0));  // ID Producto
            textField3.setText((String) datosproducto.getValueAt(selectFila, 1));  // Nombre
            textField4.setText((String) datosproducto.getValueAt(selectFila, 2));  // Precio
            textField1.setText((String) datosproducto.getValueAt(selectFila, 3));  // Cantidad Disponible
        }

    }

    public void buscar_cliente() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = conexionBD.getConnection();

            String dnom = idcliente.getText();


            String query = "SELECT cedula, nombre FROM cliente WHERE id_cliente = ?";

            // Preparar la sentencia SQL
            stmt = con.prepareStatement(query);
            stmt.setString(1, dnom);
            rs = stmt.executeQuery();

            // Verificar si se encuentra el cliente
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int cedula = rs.getInt("cedula");


                buscarcliente.setText(String.valueOf(cedula));
                Ccedula.setText(nombre);
            } else {

                System.out.println("Cliente no encontrado.");
                buscarcliente.setText("Cédula no encontrado");
                Ccedula.setText("Cliente no encontrado");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void buscar_empleado(){


        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            con = conexionBD.getConnection();


            String dnom = idempleado.getText();


            String query = "SELECT nombre FROM empleado WHERE id_empleado = ?";

            // Preparar la sentencia SQL
            stmt = con.prepareStatement(query);
            stmt.setString(1, dnom);


            rs = stmt.executeQuery();

            // Verificar si se encuentra el cliente
            if (rs.next()) {
                String nombree = rs.getString("nombre");




                nombreE.setText(nombree);
            } else {

                System.out.println("Cliente no encontrado.");
                nombreE.setText("Cliente no encontrado");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    public  void psubtotal(){

        double precio = Double.parseDouble(textField4.getText());
        Inventario inventario = new Inventario(precio);
        int cantidad = Integer.parseInt(cant_venta.getText());

        double totalr = precio * cantidad;
        double ivatotal = totalr * IVA;
        totalconiva = totalr+ivatotal;


        subtotalf.setText(String.valueOf(totalr));
        totalm += totalconiva;
        textField8.setText(String.valueOf(totalm));


    }







    public static void main(String[] args) {
        JFrame frame = new JFrame("FormVenta");
        frame.setContentPane(new FormVenta().Fventas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1006, 660);
        frame.setResizable(false);
    }

    public void setSelected(boolean b) {
    }
}
