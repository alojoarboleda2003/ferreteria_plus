package formulariosGUI;

import DAO.InventarioDAO;
import conexionBD.ConexionBD;
import modelos.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Locale;

public class FormVenta {
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
    int filas = 0;
    double totalm = 0;
    double IVA = 0.19;



    InventarioDAO inventarioDAO = new InventarioDAO();

    public FormVenta() {
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

                String nombres = buscador.getText();
                if (nombres != null && nombres.equals("productos")){
                    Inventario inventario = new Inventario(nombres);
                    inventario.setNombres(nombres);
                    InventarioDAO.buscarp(inventario);
                    obtener_datos_producto();

                }



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

                String nombres = agregarProductoButton.getText();
                Inventario inventario = new Inventario(nombres);
                inventario.setNombres(nombres);
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
            Statement start = con.createStatement();
            String query = "SELECT * FROM inventario";
            ResultSet rs = start.executeQuery(query);

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(4);
                dato[3] = rs.getString(5);

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


    public  void psubtotal(){

        double precio = Double.parseDouble(textField4.getText());
        Inventario inventario = new Inventario(precio);
        int cantidad = Integer.parseInt(cant_venta.getText());

        double totalr = precio * cantidad;
        double ivatotal = totalr * IVA;
        double totalconivan = totalr + ivatotal;




        subtotal.setText(String.valueOf(totalr));
        totalm += totalconivan;
        textField8.setText(String.valueOf(totalm));


    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("FormVenta");
        frame.setContentPane(new FormVenta().Fventas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(900, 600);
        frame.setResizable(false);
    }
}
