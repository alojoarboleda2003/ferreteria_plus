package servidor;

import java.net.*;
import java.io.*;

public class HiloServidor extends Thread {
    private Socket socket;
    private ServidorChat servidor;
    private BufferedReader in;
    private PrintWriter out;
    private String id;

    public HiloServidor(Socket socket, ServidorChat servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Registrar al usuario al conectarse
            String registro = in.readLine(); // Por ejemplo: "REGISTRAR cliente1 cliente"
            String[] partesRegistro = registro.split(" "); // Divide la instrucción
            if (partesRegistro.length == 3 && partesRegistro[0].equalsIgnoreCase("REGISTRAR")) {
                id = partesRegistro[1]; // ID del cliente
                String rol = partesRegistro[2]; // Rol del cliente
                servidor.registrarUsuario(id, rol, socket);
                out.println("Registro exitoso como: " + id);
            }

            // Escuchar y procesar mensajes
            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensaje);

                // Procesar el mensaje para obtener destinatario y contenido
                String[] partesMensaje = mensaje.split(" ", 3); // Por ejemplo: "ENVIAR cliente2 Hola"
                if (partesMensaje.length == 3 && partesMensaje[0].equalsIgnoreCase("ENVIAR")) {
                    String destinatario = partesMensaje[1];
                    String contenido = partesMensaje[2];
                    servidor.enviarMensaje(id, destinatario, contenido); // Llama al servidor para reenviar
                } else {
                    out.println("Formato de mensaje inválido.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el hilo: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Conexión cerrada con: " + id);
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }
}