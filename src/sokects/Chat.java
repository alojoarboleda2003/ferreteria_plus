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
import java.net.ServerSocket;
import java.net.Socket;

public class Chat {
    private JTextField textField1;
    private JButton button1;
    private JTextArea textArea1;
    private JPanel main;

    private PrintWriter out;
    private BufferedReader in;
    private Socket clienteSocket;

    public Chat() {
        new Thread(this::servidor).start();
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

    public void servidor(){
        try
        {
            ServerSocket serverSocket = new ServerSocket(6666);

            textArea1.append("servidor conectado\nEsperando conexion....");
            clienteSocket = serverSocket.accept();
            textArea1.append("cliente conectado\n");

            in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            out = new PrintWriter(clienteSocket.getOutputStream(),true);

            new Thread(() -> {
                try
                {
                    String reciveMessage;
                    while ((reciveMessage = in.readLine()) != null)
                    {
                        if (reciveMessage.equalsIgnoreCase("CLIENTE: EXIT"))
                        {
                            textArea1.append("cliente Desconectado\n");
                            cerraConexion();
                            return;
                        }
                        String finalMessage = reciveMessage;
                        SwingUtilities.invokeLater(() -> textArea1.append(finalMessage));
                    }

                }
                catch (IOException ex)
                {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,ex.getMessage()));
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
        String sendMessage = textField1.getText().trim();
        if (sendMessage.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"por favor ingrese un texto");
            return;
        }

        if (out != null)
        {
            out.println("servidor:"+sendMessage);
            textArea1.append("servidor:"+sendMessage);
            textArea1.setText("");

            if (sendMessage.equalsIgnoreCase("salir"))

            {
                out.println("servidor: salir");
                cerraConexion();
            }
        }

        textField1.requestFocus();
    }

    public void cerraConexion()
    {
        try
        {

            if (out != null) out.close();
            if (in != null ) in.close();
            if (clienteSocket != null) clienteSocket.close();
            textArea1.append("conexion cerrada");

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("serverChat");
        frame.setContentPane(new Chat().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();

    }
}
