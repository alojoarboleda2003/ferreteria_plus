package sokects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteChat {
    private JTextArea textArea1;
    private JPanel main;
    private JTextField textField1;
    private JButton button1;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClienteChat() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();

            }
        });
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    enviarMensaje();
                }
            }
        });
    }

    public  void conectarServidor()
    {
        try {


            String host = JOptionPane.showInputDialog("Ingrese el host (ip)");

            if (host == null || host.isEmpty())
                host = "127.0.0.1";

            int puerto = 6666;
            socket = new Socket(host, puerto);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            new Thread(() -> {
                try {
                    String recivedMessage;
                    while ((recivedMessage = in.readLine()) != null) {
                        if (recivedMessage.equalsIgnoreCase("server EXIT")) {
                            cerraConexion();
                            return;
                        }
                        String finalMessage = recivedMessage;
                        SwingUtilities.invokeLater(() -> textArea1.append(finalMessage));
                    }
                } catch (IOException ex) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, ex.getMessage()));
                }
            }).start();
        }
        catch (IOException ex)
        {
            SwingUtilities.invokeLater(()-> JOptionPane.showMessageDialog(null,ex.getMessage()));
        }
    }

    public void enviarMensaje()
    {
        String sendMessage = textArea1.getText().trim();

        if (sendMessage.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Ingrese el texto");
            return;
        }

        if (out != null)
        {
            out.println("cliente:" +sendMessage);
            textArea1.append("cliente:" +sendMessage);
            textField1.setText("");

            if (sendMessage.equalsIgnoreCase("salir"))
            {
                out.println("cliente: Exit");
                cerraConexion();
            }
            textField1.requestFocus();
        }
    }

    public void cerraConexion()
    {
        try
        {

            if (out != null) out.close();
            if (in != null ) in.close();
            if (socket != null) socket.close();
            textArea1.append("conexion cerrada");

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("clienteChat");
        frame.setContentPane(new ClienteChat().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();

    }

}
