package formulariosGUI;

import DAO.ClienteDAO;
import DAO.InventarioDAO;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.Inventario;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;

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
    private JTextField subtotal;
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
    int filas = 0;
    double totalm = 0;
    double totalr = 0;
    double IVA = 0.19;




    InventarioDAO inventarioDAO = new InventarioDAO();
    private String buscar_cliente;

    public FormVenta() {
        obtener_datos_producto();

        setContentPane(Fventas);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1006, 600);  // Establece el tamaño de la ventana
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

                String nombrest = buscador.getText();

                    Inventario inventario = new Inventario(nombrest);
                    inventario.setNombres(nombrest);
                    InventarioDAO.buscarp(inventario);
                    obtener_datos_producto();





            }
        });
        clickParaSeleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombresr = agregarProductoButton.getText();
                Inventario inventario = new Inventario(nombresr);
                inventario.setNombres(nombresr);
                agregar_datos_p();
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
                        if (totalm - totalr >= 0) {
                            totalm -= totalr;  // Restar el total con IVA
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


                double precio = Double.parseDouble(cant_venta.getText());

                // Crear el inventario con el precio
                Inventario inventario = new Inventario(precio);
                inventario.setNombres(String.valueOf(precio));


                psubtotal();

            }




        });
        subtotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                psubtotal();



            }
        });

        buscarcliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombresc = buscarcliente.getText();

                Cliente cliente = new Cliente(nombresc);
                cliente.setNombre(nombresc);
                buscar_cliente();

            }
        });
        Ccedula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void clear() {
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField1.setText("");
        cant_venta.setText("");
        subtotal.setText("");

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
            // Usamos PreparedStatement para prevenir problemas con valores dinámicos
            String query = "SELECT id_inventario, nombres, precio, cant_disponible FROM inventario WHERE nombres = ?";
            PreparedStatement pstmt = con.prepareStatement(query);


            String nombreProductoSeleccionado = buscador.getText();
            pstmt.setString(1, nombreProductoSeleccionado);

            // Ejecutamos la consulta
            ResultSet rs = pstmt.executeQuery();



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
            String subtotatl = subtotal.getText();

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = cantidad;
                dato[3] = subtotatl;

                model.addRow(dato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void buscar_cliente(){


        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            con = conexionBD.getConnection();

            // Obtener la cédula ingresada
            String dnom = buscarcliente.getText();

            // Consulta SQL
            String query = "SELECT nombre FROM cliente WHERE cedula = ?";

            // Preparar la sentencia SQL
            stmt = con.prepareStatement(query);
            stmt.setString(1, dnom);


            rs = stmt.executeQuery();

            // Verificar si se encuentra el cliente
            if (rs.next()) {
                String nombresc = rs.getString("nombre");



                // Asignar el nombre al JTextField Ccedula
                Ccedula.setText(nombresc);
            } else {

                System.out.println("Cliente no encontrado.");
                Ccedula.setText("Cliente no encontrado");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    public  void psubtotal(){

        double precio = Double.parseDouble(textField4.getText());
        Inventario inventario = new Inventario(precio);
        int cantidad = Integer.parseInt(cant_venta.getText());

        totalr = precio * cantidad;
        double ivatotal = totalr * IVA;
        double totalconiva = totalr+ivatotal;





        subtotal.setText(String.valueOf(totalr));
        totalm += totalconiva;
        textField8.setText(String.valueOf(totalm));


    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("FormVenta");
        frame.setContentPane(new FormVenta().Fventas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(900, 650);
        frame.setResizable(false);
    }
}
