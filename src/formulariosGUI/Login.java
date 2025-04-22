package formulariosGUI;

import javax.swing.*;

public class Login extends JFrame{
    private JPanel main;
    private JPanel bg;
    private JTextField ingreseSuUsuarioTextField;
    private JPasswordField passwordField1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("login");
        frame.setContentPane(new Login().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 500);
        frame.setResizable(false);
    }
}
