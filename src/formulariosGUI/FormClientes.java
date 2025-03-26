package formulariosGUI;

import javax.swing.*;

public class FormClientes {
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        frame.setContentPane(new FormClientes().Fclientes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 400);
        frame.setResizable(false);
    }
}
