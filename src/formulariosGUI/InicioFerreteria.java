package formulariosGUI;

import javax.swing.*;

public class InicioFerreteria {
    private JPanel main;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel ferreteriaPlusLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        frame.setContentPane(new InicioFerreteria().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 700);
        frame.setResizable(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
