package formulariosGUI;

import cliente.ClienteChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class FormChat extends JFrame {
    private JTextArea areaMensajes;
    private JButton botonEnviar;
    private JTextField campoMensaje;
    private ClienteChat cliente;

    public FormChat(String id, String rol) {
        super("Chat - Cliente: " + id); // Llama al constructor de JFrame
        cliente = new ClienteChat(id, rol); // Inicializa el cliente
        inicializarComponentes(); // Configura los componentes gráficos
    }

    private void inicializarComponentes() {
        // Configurar ventana
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        add(new JScrollPane(areaMensajes), BorderLayout.CENTER);

        // Campo de texto para mensajes
        campoMensaje = new JTextField();
        botonEnviar = new JButton("Enviar");
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

        // Acción del botón "Enviar"
        botonEnviar.addActionListener(e -> enviarMensaje());

        // Mostrar ventana
        setVisible(true);
    }

    public void conectarServidor(String host, int puerto) {
        try {
            cliente.conectarAlServidor(host, puerto);
            areaMensajes.append("Conectado al servidor.\n");
        } catch (IOException e) {
            areaMensajes.append("Error al conectar: " + e.getMessage() + "\n");
        }
    }

    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            cliente.enviarMensaje("cliente2", mensaje); // Cambia el destinatario según sea necesario
            areaMensajes.append("Tú: " + mensaje + "\n");
            campoMensaje.setText("");
        }
    }


    public static void main(String[] args) {
        FormChat chat = new FormChat("cliente1", "cliente");
        chat.conectarServidor("localhost", 12345);
    }
}



