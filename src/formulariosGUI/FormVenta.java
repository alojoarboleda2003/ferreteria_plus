package formulariosGUI;

import DAO.DetalleOrdenDAO;
import DAO.InventarioDAO;
import com.toedter.calendar.JDateChooser;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.DetetalleOrden;
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
        setSize(1006, 700);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);


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
        clickParaSeleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregar_datos_p2();


            }
        });
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
        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

                String nombre_cliente = Ccedula.getText();
                String nombre_empleado = nombreE.getText();
                int cantidad = Integer.parseInt(cant_venta.getText());
                double subtotal = Double.parseDouble(textField8.getText());
                String estado = (String) estado1.getSelectedItem();
                int id_cliente = Integer.parseInt(idcliente.getText());
                int id_empleado = Integer.parseInt(idempleado.getText());
                int id_inventario = Integer.parseInt(textField2.getText());
                int id_orden = Integer.parseInt(idcliente.getText());


                DetetalleOrden detetalleOrden1 = new DetetalleOrden(0,id_cliente,id_empleado);
                DetetalleOrden detetalleOrden = new DetetalleOrden(0,id_orden,id_inventario,id_cliente,id_empleado,nombre_cliente,nombre_empleado,cantidad,subtotal,estado);
                detetalleOrden.setNombre_cliente(nombre_cliente);
                detetalleOrden.setNombre_cliente(nombre_empleado);
                detetalleOrden.setCantidad(cantidad);
                detetalleOrden.setSubtotal(subtotal);
                detetalleOrden.setEstado(estado);
                detetalleOrden1.setId_cliente(id_cliente);
                detetalleOrden1.setId_empleado(id_empleado);

                detalleOrdenDAO.agregar_orden(0,id_cliente,id_empleado);
                detalleOrdenDAO.agregar_cliente(0,id_orden,id_inventario,id_cliente,id_empleado,nombre_cliente,nombre_empleado,cantidad,subtotal,estado);

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

        subtotalf.setText("");
        DefaultTableModel model = (DefaultTableModel) datosproducto.getModel();
        model.setRowCount(0);

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

    public void actualizarInventario(int idProducto, int cantidadVendida) {
        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;
        try {

            String query = "UPDATE inventario SET cant_disponible = cant_disponible - ? WHERE id_inventario = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, cantidadVendida);
            stmt.setInt(2, idProducto);

            // Ejecutamos la actualización
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Inventario actualizado exitosamente.");
            } else {
                System.out.println("Error al actualizar el inventario.");
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
        frame.setSize(900, 660);
        frame.setResizable(false);
    }

    public void setSelected(boolean b) {
    }
}
