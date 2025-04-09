package DAO;

import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.DetetalleOrden;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleOrdenDAO {
    private static ConexionBD conexionBD = new ConexionBD();

    private int id_detalle_orden;
    private int id_orden;
    private int id_inventario;
    private int id_cliente;
    private int id_empleado;
    private int cantidad;
    private double subtotal;
    private String estado;

    public static void actualizar(int id_detalle_orden, String estado) {
        Connection con = ConexionBD.getConnection();
        String query = "UPDATE detalle_orden_compra SET estado = ? WHERE id_detalle_orden = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);

            // Establece el valor para 'estado' en el primer parámetro
            stmt.setString(1, estado);

            // Establece el valor para 'id_detalle_orden' en el segundo parámetro
            stmt.setInt(2, id_detalle_orden);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,"Estado actualizado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null,"No se encontró la orden con id_detalle_orden = " + id_detalle_orden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregar_cliente(int id_detalle_orden,int id_orden,int id_inventario, int id_cliente, int id_empleado,String nombre_cliente,String nombre_empleado, int cantidad, double subtotal,String estado) {


        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;

        try {
            // Consulta para insertar el nombre del cliente
            String query = "INSERT INTO detalle_orden_compra (id_orden, id_inventario, id_cliente,id_empleado, cantidad, subtotal, estado) VALUES ( ,? ,?, ?,?, ?, ?, ?)";
            stmt = con.prepareStatement(query);


            DetetalleOrden detalleOrden = new DetetalleOrden();

             // Usar la instancia aquí

            stmt.setInt(1, id_orden);
            stmt.setInt(2, id_inventario);
            stmt.setInt(3, id_cliente);
            stmt.setInt(4, id_empleado);
            stmt.setInt(5, cantidad);
            stmt.setDouble(6, subtotal);
            stmt.setString(7, estado);


            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Detalle de orden insertado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregar_orden( int id_orden, int id_cliente, int id_empleado) {


        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;

        try {
            // Consulta para insertar el nombre del cliente
            String query = "INSERT INTO orden_compra ( id_cliente,id_empleado) VALUES ( ?, ?)";
            stmt = con.prepareStatement(query);


            DetetalleOrden detalleOrden = new DetetalleOrden();

            // Usar la instancia aquí


            stmt.setInt(1, id_cliente);
            stmt.setInt(2, id_empleado);



            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "orden insertado correctamente.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void actualizar(int id_orden,int id_inventario,int id_cliente,int id_empleado,int cantidad, double subtotal, String estado)
    {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE detalle_orden_compra set id_orden = ?, id_inventario = ?, id_cliente = ?, id_empleado = ?, cantidad = ?, subtotal= ? ,estado = ? WHERE id_detalle_orden = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1,id_orden);
            pst.setInt(2,id_inventario);
            pst.setInt(3,id_cliente);
            pst.setInt(4,id_empleado);
            pst.setInt(5,cantidad);
            pst.setDouble(6,subtotal);
            pst.setString(6,estado);


            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"estado actualizado");
            }else
            {
                JOptionPane.showMessageDialog(null,"error al modificar estado");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }




}
