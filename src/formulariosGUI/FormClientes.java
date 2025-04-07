package formulariosGUI;

import DAO.ClienteDAO;
import conexionBD.ConexionBD;
import modelos.Cliente;

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

public class FormClientes extends JFrame{

    private JPanel Fclientes;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton ELIMINARButton;
    private JButton GUARDARButton;
    private JButton ACTUALIZARButton;
    private JTextField textField6;
    int filas = 0;


    ClienteDAO clienteDAO = new ClienteDAO();

    public FormClientes() {
        setContentPane(Fclientes);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1006, 400);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);

        obtener_datos();
        textField1.setEnabled(false);


        GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cedula = Integer.parseInt(textField6.getText());
                String nombre = textField2.getText();
                double telefono = Double.parseDouble(textField3.getText());
                String direccion = textField4.getText();
                String correo = textField5.getText();

                Cliente cliente = new Cliente(0, cedula,nombre, telefono, direccion,correo);
                clienteDAO.agregar(cliente);
                obtener_datos();
                clear();

            }
        });
        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cedula = Integer.parseInt(textField6.getText());
                String nombre = textField2.getText();
                double telefono = Double.parseDouble(textField3.getText());
                String direccion = textField4.getText();
                String correo = textField5.getText();
                int id = Integer.parseInt(textField1.getText());

                Cliente cliente = new Cliente(id, cedula,nombre, telefono, direccion,correo);
                clienteDAO.actualizar(cliente);
                obtener_datos();
                clear();

            }
        });
        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                clienteDAO.eliminar(id);
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
                    textField6.setText((String) table1.getValueAt(selectFila, 1));
                    textField2.setText((String) table1.getValueAt(selectFila, 2));
                    textField3.setText((String) table1.getValueAt(selectFila, 3));
                    textField4.setText((String) table1.getValueAt(selectFila, 4));
                    textField5.setText((String) table1.getValueAt(selectFila, 5));

                    filas = selectFila;
                }
            }
        });


    }






    public void clear() {
            textField1.setText("");
            textField6.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
        }

        ConexionBD conexionBD = new ConexionBD();


        public void obtener_datos() {
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID Cliente");
            model.addColumn("Cedula");
            model.addColumn("Nombre");
            model.addColumn("Telefono");
            model.addColumn("Direccion");
            model.addColumn("Correo");

            table1.setModel(model);
            String[] dato = new String[6];
            Connection con = conexionBD.getConnection();

            try {
                Statement start = con.createStatement();
                String query = "SELECT * FROM cliente";
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



    public static void main(String[] args) {
        JFrame frame = new JFrame("FormClientes");
        frame.setContentPane(new FormClientes().Fclientes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1006, 400);
        frame.setResizable(false);
    }


}
