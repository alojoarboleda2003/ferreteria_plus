package formulariosGUI;

import DAO.InventarioDAO;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class FormInventario extends JFrame{
    private JPanel Finventario;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JComboBox categoria1;
    private JTextField buscar;
    int filas=0;

    InventarioDAO inventarioDAO = new InventarioDAO();
    public FormInventario() {
        setContentPane(Finventario);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1006, 400);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);
        // Centra la ventana en la pantalla
        textField1.setEnabled(false);
        obtener_datos();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombres = textField2.getText();
                String categoria = (String) categoria1.getSelectedItem();
                double precio = Double.parseDouble(textField4.getText());
                int cant_disponible = Integer.parseInt(textField5.getText());
                String proveedor_asoc = textField6.getText();


                Inventario inventario1 = new Inventario(0,nombres, categoria, precio, cant_disponible, proveedor_asoc);
                inventarioDAO.agregar(inventario1);
                obtener_datos();
                clear();



            }
        });


        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombres = textField2.getText();
                String categoria = (String) categoria1.getSelectedItem();
                int precio = Integer.parseInt(textField4.getText());
                int cant_disponible = Integer.parseInt(textField5.getText());
                String proveedor_asoc = textField6.getText();

                int id_inventario = Integer.parseInt(textField1.getText());


                Inventario inventario = new Inventario(id_inventario, nombres, categoria, precio, cant_disponible, proveedor_asoc);
                inventarioDAO.actualizar(inventario);
                obtener_datos();
                clear();



            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_inventario = Integer.parseInt(textField1.getText());
                inventarioDAO.eliminar(id_inventario);
                obtener_datos();
                clear();


            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectFila = table1.getSelectedRow();

                if (selectFila >= 0) {
                    textField1.setText((String) table1.getValueAt(selectFila, 0));
                    textField2.setText((String) table1.getValueAt(selectFila, 1));
                    categoria1.setSelectedItem( table1.getValueAt(selectFila, 2));
                    textField4.setText((String) table1.getValueAt(selectFila, 3));
                    textField5.setText((String) table1.getValueAt(selectFila, 4));
                    textField6.setText((String) table1.getValueAt(selectFila, 5));


                    filas = selectFila;
                }
            }
        });
        categoria1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_inventario = Integer.parseInt(buscar.getText());



                Cliente cliente = new Cliente(id_inventario);
                cliente.setCedula(id_inventario);
                buscar_i();

            }
        });
    }

        public void clear() {
            textField1.setText("");
            textField2.setText("");
            categoria1.setSelectedItem("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");

        }

        ConexionBD conexionBD = new ConexionBD();


        public void obtener_datos() {
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID Inventario");
            model.addColumn("Nombres");
            model.addColumn("Categoria");
            model.addColumn("Precio");
            model.addColumn("Cantidad Disponible");
            model.addColumn("Proveedor Asociado");


            table1.setModel(model);
            String[] dato = new String[6];
            Connection con = conexionBD.getConnection();

            try {
                Statement start = con.createStatement();
                String query = "SELECT * FROM inventario";
                ResultSet rs = start.executeQuery(query);

                while (rs.next()) {
                    dato[0] = rs.getString(1);
                    dato[1] = rs.getString(2);
                    dato[2] = rs.getString(3);
                    dato[3] = rs.getString(4);
                    dato[4] = rs.getString(5);
                    dato[5] = rs.getString(6);

                    model.addRow(dato);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    public void buscar_i() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Inventario");
        model.addColumn("Nombres");
        model.addColumn("Categoria");
        model.addColumn("Precio");
        model.addColumn("Cantidad Disponible");
        model.addColumn("Proveedor Asociado");


        table1.setModel(model);
        String[] dato = new String[6];
        Connection con = conexionBD.getConnection();

        try {
            Statement start = con.createStatement();

            // Si no hay texto en el campo de búsqueda, cargamos todos los productos
            String query = "SELECT * FROM inventario";

            String nombreProductoSeleccionado = buscar.getText();
            if (!nombreProductoSeleccionado.isEmpty()) {
                // Si el campo de búsqueda no está vacío, filtrar por nombre
                query = "SELECT * FROM inventario WHERE id_inventario = ?";
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
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);



                model.addRow(dato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("FormInventario");
        frame.setContentPane(new FormInventario().Finventario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1006, 400);
        frame.setResizable(false);
    }

    public void setSelected(boolean b) {
    }
}
