package formulariosGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame{
    private JPanel main;
    private JPanel bg;
    private JTextField ingreseSuUsuarioTextField;
    private JPasswordField passwordField1;
    private JButton ingresarButton;
    private JButton xButton;



    public Login() {


        ingreseSuUsuarioTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ingreseSuUsuarioTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (ingreseSuUsuarioTextField.getText().equals("Ingrese su Usuario")) {
                    ingreseSuUsuarioTextField.setText("");
                    ingreseSuUsuarioTextField.setForeground(Color.black);
                }
                if (String.valueOf(passwordField1.getPassword()).isEmpty()) {
                    passwordField1.setText("*********");
                    passwordField1.setForeground(Color.gray);
                }
            }
        });
        passwordField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (String.valueOf(passwordField1.getPassword()).equals("*********")) {
                    passwordField1.setText("");
                    passwordField1.setForeground(Color.gray);
                }
                if (ingreseSuUsuarioTextField.getText().isEmpty()) {
                    ingreseSuUsuarioTextField.setText("Ingrese su Usuario");
                    ingreseSuUsuarioTextField.setForeground(Color.black);
                }

            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion1 = (String) ingreseSuUsuarioTextField.getText();
                String seleccion2 = (String) passwordField1.getText();
                if (seleccion1 != null && seleccion1.equals("alojo") && seleccion2 != null && seleccion2.equals("2003")) {
                    InicioFerreteria inicioFerreteria = new InicioFerreteria();
                    inicioFerreteria.setVisible(true);  // Hacerlo visible)  {

                    // JOptionPane.showMessageDialog(null,"intento de login con los datos \n Usuario: " + ingreseSuUsuarioTextField.getText() + "\n Contraseña: " +String.valueOf(passwordField1.getPassword()));

                } else {
                    JOptionPane.showMessageDialog(null, "Error, Usuario o Contraseña Incorrectos \n ¡Vuelva a Intentarlo!");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("login");
        frame.setContentPane(new Login().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null); // 👉 Centrar la ventana
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
