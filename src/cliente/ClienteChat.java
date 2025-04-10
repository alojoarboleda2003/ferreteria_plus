package cliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class ClienteChat {
    private String id;
    private String rol; // Puede ser "cliente" o "empleado"
    private Socket socket;

    // Constructor para inicializar ID y rol
    public ClienteChat(String id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    // Método para conectar al servidor
    public void conectarAlServidor(String host, int puerto) throws IOException {
        System.out.println("Conectando al servidor " + host + " en el puerto " + puerto + "...");
        socket = new Socket(host, puerto);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Registrar al usuario en el servidor
        out.println("REGISTRAR " + id + " " + rol);
        System.out.println("Usuario registrado en el servidor como: " + rol);

        // Escuchar mensajes del servidor en un hilo separado
        new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                }
            } catch (IOException e) {
                System.err.println("Error al leer mensajes: " + e.getMessage());
            }
        }).start();
    }

    // Método para enviar un mensaje
    public void enviarMensaje(String destinatario, String mensaje) {
        if (mensaje.isEmpty()) {
            System.out.println("No se puede enviar un mensaje vacío.");
            return;
        }
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("ENVIAR " + destinatario + " " + mensaje);
            System.out.println("Mensaje enviado a " + destinatario + ": " + mensaje);
        } catch (IOException e) {
            System.err.println("Error al enviar mensaje: " + e.getMessage());
        }
    }

    // Método para integrar mensajes con una GUI (opcional, si usas una interfaz gráfica)
    public void configurarAreaDeTexto(javax.swing.JTextArea areaDeTexto) {
        new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    areaDeTexto.append(mensaje + "\n"); // Muestra el mensaje recibido en la GUI
                }
            } catch (IOException e) {
                System.err.println("Error al leer mensajes: " + e.getMessage());
            }
        }).start();
    }



}