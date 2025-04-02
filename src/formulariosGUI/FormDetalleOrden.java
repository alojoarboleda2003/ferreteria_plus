package formulariosGUI;

import conexionBD.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormDetalleOrden  extends JFrame{
    private JPanel Fdetalle_orden;
    private JTextField nombreE;
    private JTextField textField1;
    private JTable table1;
    private JScrollPane productosordenados;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JButton finalizarVentaButton;


    public FormDetalleOrden() {
        setContentPane(Fdetalle_orden);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1006, 400);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);

        nombreE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
    ConexionBD conexionBD = new ConexionBD();


    public static void main(String[] args) {
        JFrame frame = new JFrame("FormDetalleOrden");
        frame.setContentPane(new FormDetalleOrden().Fdetalle_orden);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 350);
        frame.setResizable(false);
    }
}
