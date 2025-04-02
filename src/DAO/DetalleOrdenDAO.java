package DAO;

import conexionBD.ConexionBD;
import modelos.DetetalleOrden;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleOrdenDAO {
    private ConexionBD conexionBD = new ConexionBD();

    private int id_detalle_orden;
    private int id_orden;
    private int id_inventario;
    private int id_cliente;
    private int id_empleado;
    private int cantidad;
    private double subtotal;
    private String estado;

    public void agregar_cliente(int id_detalle_orden,int id_orden,int id_inventario, int id_cliente, int id_empleado,String nombre_cliente,String nombre_empleado, int cantidad, double subtotal,String estado) {


        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;

        try {
            // Consulta para insertar el nombre del cliente
            String query = "INSERT INTO detalle_orden_compra (id_detalle_orden,id_orden, id_inventario, id_cliente,id_empleado, nombre_cliente,nombre_empleado, cantidad, subtotal, estado) VALUES (?,?,? ,? ,?, ?,?, ?, ?, ?)";
            stmt = con.prepareStatement(query);


            DetetalleOrden detalleOrden = new DetetalleOrden();

             // Usar la instancia aquí
            stmt.setInt(1, id_detalle_orden);
            stmt.setInt(2, id_orden);
            stmt.setInt(3, id_inventario);
            stmt.setInt(4, id_cliente);
            stmt.setInt(5, id_empleado);
            stmt.setString(6, nombre_cliente);
            stmt.setString(7, nombre_empleado);
            stmt.setInt(8, cantidad);
            stmt.setDouble(9, subtotal);
            stmt.setString(10, estado);


            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Detalle de orden insertado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregar_orden(int id_orden, int id_cliente, int id_empleado) {


        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;

        try {
            // Consulta para insertar el nombre del cliente
            String query = "INSERT INTO orden_compra (id_orden, id_cliente,id_empleado) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(query);


            DetetalleOrden detalleOrden = new DetetalleOrden();

            // Usar la instancia aquí

            stmt.setInt(1, id_orden);
            stmt.setInt(2, id_cliente);
            stmt.setInt(3, id_empleado);



            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "orden insertado correctamente.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
