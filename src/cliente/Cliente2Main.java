package cliente;
import java.io.IOException;
import java.util.Scanner;

public class Cliente2Main {
    public static void main(String[] args) {
        try {
            ClienteChat cliente = new ClienteChat("cliente2", "cliente");
            cliente.conectarAlServidor("localhost", 12345);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Escribe tu mensaje para cliente1 (escribe 'salir' para terminar):");

            String mensaje;
            while (!(mensaje = scanner.nextLine()).equalsIgnoreCase("salir")) {
                cliente.enviarMensaje("cliente1", mensaje);
            }

            System.out.println("Has terminado la conexión.");
        } catch (IOException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }
}