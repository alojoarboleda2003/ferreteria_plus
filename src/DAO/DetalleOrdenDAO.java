package DAO;

import conexionBD.ConexionBD;
import modelos.DetetalleOrden;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleOrdenDAO {
    private ConexionBD conexionBD = new ConexionBD();


    private int id_orden;
    private int id_inventario;
    private int id_cliente;
    private int id_empleado;
    private int cantidad;
    private double subtotal;
    private String estado;

    public void agregar_cliente(String nombre_cliente,String nombre_empleado, int cantidad, double subtotal,String estado) {


        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;

        try {
            // Consulta para insertar el nombre del cliente
            String query = "INSERT INTO detalle_orden_compra (id_orden, id_inventario, id_cliente,id_empleado, nombre_cliente,nombre_empleado, cantidad, subtotal, estado) VALUES (2, 2, 1,3, ?,?, ?, ?, ?)";
            stmt = con.prepareStatement(query);


            DetetalleOrden detalleOrden = new DetetalleOrden();

             // Usar la instancia aquí
            stmt.setString(1, nombre_cliente);
            stmt.setString(2, nombre_empleado);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, subtotal);
            stmt.setString(5, estado);


            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Detalle de orden insertado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
