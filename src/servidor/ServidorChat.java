package servidor;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorChat {
    private ServerSocket serverSocket; // Maneja las conexiones
    private Map<String, Usuario> conexiones = new HashMap<>(); // Usuarios conectados

    // Constructor
    public ServidorChat(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto); // Configura el servidor en el puerto dado
    }

    // Método para iniciar el servidor
    public void iniciar() {
        System.out.println("Esperando conexiones...");
        while (true) {
            try {
                Socket socket = serverSocket.accept(); // Espera conexiones
                System.out.println("Cliente conectado.");

                // Inicia un hilo para manejar al cliente
                new HiloServidor(socket, this).start();
            } catch (IOException e) {
                System.err.println("Error al aceptar la conexión: " + e.getMessage());
            }
        }
    }

    // Método para registrar un usuario en el servidor
    public synchronized void registrarUsuario(String id, String rol, Socket socket) {
        conexiones.put(id, new Usuario(id, rol, socket)); // Almacena al cliente
        System.out.println("Usuario registrado: " + id + " como " + rol);
    }

    // Método para enviar mensajes entre clientes
    public synchronized void enviarMensaje(String remitente, String destinatario, String mensaje) {
        Usuario usuarioDestino = conexiones.get(destinatario);
        if (usuarioDestino != null) {
            try {
                PrintWriter out = new PrintWriter(usuarioDestino.getSocket().getOutputStream(), true);
                out.println(remitente + ": " + mensaje);
            } catch (IOException e) {
                System.err.println("Error al enviar mensaje: " + e.getMessage());
            }
        } else {
            System.out.println("Usuario no encontrado: " + destinatario);
        }
    }
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando el servidor...");
            ServidorChat servidor = new ServidorChat(12345); // Configura el puerto
            servidor.iniciar(); // Inicia el servidor
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}