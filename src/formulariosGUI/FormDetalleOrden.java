package formulariosGUI;

import javax.swing.*;

public class FormDetalleOrden {
    private JPanel Fdetalle_orden;
    private JTextField nombreE;
    private JTextField textField1;
    private JTable table1;
    private JScrollPane productosordenados;
    private JComboBox comboBox1;
    private JTextField textField2;

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
