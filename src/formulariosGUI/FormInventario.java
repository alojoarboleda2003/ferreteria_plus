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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FormInventario extends JFrame{
    private JPanel Finventario;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    int filas=0;

    InventarioDAO inventarioDAO = new InventarioDAO();
    public FormInventario() {
        setContentPane(Finventario);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1006, 400);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);
        // Centra la ventana en la pantalla

        obtener_datos();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombres = textField2.getText();
                String categoria = textField3.getText();
                double precio = Double.parseDouble(textField4.getText());
                int cant_disponible = Integer.parseInt(textField5.getText());
                String proveedor_asoc = textField6.getText();
                int stock = Integer.parseInt(textField7.getText());

                Inventario inventario1 = new Inventario(0,nombres, categoria, precio, cant_disponible, proveedor_asoc, stock);
                inventarioDAO.agregar(inventario1);
                obtener_datos();
                clear();


            }
        });


        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombres = textField2.getText();
                String categoria = textField3.getText();
                int precio = Integer.parseInt(textField4.getText());
                int cant_disponible = Integer.parseInt(textField5.getText());
                String proveedor_asoc = textField6.getText();
                int stock = Integer.parseInt(textField7.getText());
                int id_inventario = Integer.parseInt(textField1.getText());


                Inventario inventario = new Inventario(id_inventario, nombres, categoria, precio, cant_disponible, proveedor_asoc, stock);
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
                    textField3.setText((String) table1.getValueAt(selectFila, 2));
                    textField4.setText((String) table1.getValueAt(selectFila, 3));
                    textField5.setText((String) table1.getValueAt(selectFila, 4));
                    textField6.setText((String) table1.getValueAt(selectFila, 5));
                    textField7.setText((String) table1.getValueAt(selectFila, 6));

                    filas = selectFila;
                }
            }
        });
    }

        public void clear() {
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");
            textField7.setText("");
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
            model.addColumn("Stock");

            table1.setModel(model);
            String[] dato = new String[7];
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
                    dato[6] = rs.getString(7);
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
}
