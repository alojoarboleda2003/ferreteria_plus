package DAO;

import conexionBD.ConexionBD;
import modelos.DetetalleOrden;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleOrdenDAO {
    private ConexionBD conexionBD = new ConexionBD();


    private int id_orden;
    private int id_inventario;
    private int id_cliente;
    private int cantidad;
    private double subtotal;
    private String estado;

    public void agregar_cliente(String nombre_cliente, int cantidad, double subtotal) {


        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;

        try {
            // Consulta para insertar el nombre del cliente
            String query = "INSERT INTO detalle_orden_compra (id_orden, id_inventario, id_cliente, nombre_cliente, cantidad, subtotal, estado) VALUES (2, 2, 1, ?, ?, ?, ?)";
            stmt = con.prepareStatement(query);

            // Usar el objeto `detalleOrden` que pasaste como parámetro
            DetetalleOrden detalleOrden = new DetetalleOrden();

             // Usar la instancia aquí
            stmt.setString(1, nombre_cliente);  // Establecer el nombre del cliente
            stmt.setInt(2, cantidad);  // Usar la instancia aquí
            stmt.setDouble(3, subtotal);  // Usar la instancia aquí
            stmt.setString(4, String.valueOf(estado)); // Usar la instancia aquí

            // Ejecutar la inserción
            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Detalle de orden insertado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
