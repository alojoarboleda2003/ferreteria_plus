package formulariosGUI;

import DAO.ProveedorDAO;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.Proveedor;

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

public class FormProveedor extends JFrame {
    private JPanel Fproveedor;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton GUARDARButton;
    private JButton ELIMINARButton;
    private JButton ACTUALIZARButton;
    int filas = 0;

    ProveedorDAO proveedorDAO = new ProveedorDAO();

    public FormProveedor() {
        setContentPane(Fproveedor);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1006, 400);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);

        obtener_datos();
        textField1.setEnabled(false);

        GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String contacto = textField3.getText();
                String producto_sum = textField4.getText();


                Proveedor proveedor = new Proveedor(0,nombre,contacto,producto_sum);
                proveedorDAO.agregar(proveedor);
                obtener_datos();
                clear();

            }
        });
        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String contacto = textField3.getText();
                String producto_sum = textField4.getText();
                int id_proveedor = Integer.parseInt(textField1.getText());

                Proveedor proveedor = new Proveedor(id_proveedor, nombre, contacto, producto_sum);
                proveedorDAO.actualizar(proveedor);
                obtener_datos();
                clear();

            }
        });
        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_proveedor = Integer.parseInt(textField1.getText());
                proveedorDAO.eliminar(id_proveedor);
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

    }

    ConexionBD conexionBD = new ConexionBD();


    public void obtener_datos() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Proveedor");
        model.addColumn("Nombre");
        model.addColumn("Contacto");
        model.addColumn("Producto Suministrado");


        table1.setModel(model);
        String[] dato = new String[4];
        Connection con = conexionBD.getConnection();

        try {
            Statement start = con.createStatement();
            String query = "SELECT * FROM proveedor";
            ResultSet rs = start.executeQuery(query);

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormProveedor");
        frame.setContentPane(new FormProveedor().Fproveedor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(900, 300);
        frame.setResizable(false);
    }

    public void setSelected(boolean b) {
    }
}


