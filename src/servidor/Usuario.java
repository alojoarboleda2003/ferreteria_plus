package servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class Usuario {
    private String id;
    private String rol;
    private Socket socket;
    private ServerSocket serverSocket;

    public Usuario(String id, String rol, Socket socket) {
        this.id = id;
        this.rol = rol;
        this.socket = socket;
    }

    public String getId() {
        return id;
    }

    public String getRol() {
        return rol;
    }

    public Socket getSocket() {
        return socket;
    }
}
