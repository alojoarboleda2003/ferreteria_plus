package formulariosGUI;

import DAO.EmpleadoDAO;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class FormEmpleados extends JFrame{
    private JPanel Fempleados;

    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField buscar;
    private JTextField textField4;
    private JButton AGREGARButton;
    private JButton MODIFICARButton;
    private JButton ELIMINARButton;
    private JComboBox comboBox1;
    int filas=0;
    EmpleadoDAO empleadoDAO = new EmpleadoDAO();


    public FormEmpleados() {
        /**
         * le damos el tamaño al formulario cuando se abre en el menu priincipal
         */

        setContentPane(Fempleados);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1050, 500);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla


        obtener_datos();
        textField1.setEnabled(false);
        /**
         * le damos la funcionalidad al boton de agregar empleado a la bd
         * con el metodo de agregar de la clase DAO
         */
        AGREGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String cargo = (String) comboBox1.getSelectedItem();
                double salario = Double.parseDouble(textField4.getText());


                Empleado empleado = new Empleado(0, nombre, cargo, salario);
                empleadoDAO.agregar(empleado);
                obtener_datos();
                clear();


            }
        });
        /**
         * LE DAMOS LA FUNCIONALIDAD AL BOTON DE MODIFICAR los datos del empleado
         * con la clase DAO
         */
        MODIFICARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String cargo = (String) comboBox1.getSelectedItem();
                double salario = Double.parseDouble(textField4.getText());
                int id_empleado = Integer.parseInt(textField1.getText());

                Empleado empleado = new Empleado(id_empleado, nombre, cargo, salario);
                empleadoDAO.actualizar(empleado);
                obtener_datos();
                clear();


            }
        });
        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_empleado = Integer.parseInt(textField1.getText());
                empleadoDAO.eliminar(id_empleado);
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
                    comboBox1.setSelectedItem(table1.getValueAt(selectFila, 2));
                    textField4.setText((String) table1.getValueAt(selectFila, 3));

                    filas = selectFila;
                }
            }
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_empleado = Integer.parseInt(buscar.getText());



                Cliente cliente = new Cliente(id_empleado);
                cliente.setCedula(id_empleado);
                buscar_e();

            }
        });
    }

    public void clear() {
        textField1.setText("");
        textField2.setText("");
        comboBox1.setSelectedItem("");
        textField4.setText("");
    }

    ConexionBD conexionBD = new ConexionBD();

    /**
     * metodo para obtener los datos de la bd y los muestre en la tabla
     */
    public void obtener_datos() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID EMPLEADO");
        model.addColumn("Nombre");
        model.addColumn("CARGO");
        model.addColumn("SALARIO");

        table1.setModel(model);
        String[] dato = new String[4];
        Connection con = conexionBD.getConnection();

        try {
            Statement start = con.createStatement();
            String query = "SELECT * FROM empleado";
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

    /**
     * metodo para buscar a un empleado
     */

    public void buscar_e() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID EMPLEADO");
        model.addColumn("Nombre");
        model.addColumn("CARGO");
        model.addColumn("SALARIO");


        table1.setModel(model);
        String[] dato = new String[4];
        Connection con = conexionBD.getConnection();

        try {
            Statement start = con.createStatement();

            // Si no hay texto en el campo de búsqueda, cargamos todos los productos
            String query = "SELECT * FROM empleado";

            String nombreProductoSeleccionado = buscar.getText();
            if (!nombreProductoSeleccionado.isEmpty()) {
                // Si el campo de búsqueda no está vacío, filtrar por nombre
                query = "SELECT * FROM empleado WHERE id_empleado = ?";
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormEmpleados");
        frame.setContentPane(new FormEmpleados().Fempleados);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1006, 400);
        frame.setResizable(false);
    }

    public void setSelected(boolean b) {
    }
}

