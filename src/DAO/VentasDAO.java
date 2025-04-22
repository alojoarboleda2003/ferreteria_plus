package DAO;

import conexionBD.ConexionBD;
import modelos.DetetalleOrden;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VentasDAO

{
    public void agregar_venta( int id_inventario,int id_orden, int id_cliente, int id_empleado, double total, String fecha) {
        ConexionBD conexionBD = new ConexionBD();


        Connection con = conexionBD.getConnection();
        PreparedStatement stmt = null;

        try {
            // Consulta para insertar el nombre del cliente
            String query = "INSERT INTO ventas ( id_inventario,id_orden, id_cliente,id_empleado,total,fecha) VALUES ( ?,2,?,?,?,?)";
            stmt = con.prepareStatement(query);


            DetetalleOrden detalleOrden = new DetetalleOrden();

            // Usar la instancia aquí


            stmt.setInt(1, id_inventario);
            stmt.setInt(2, id_cliente);
            stmt.setInt(3, id_empleado);
            stmt.setDouble(4, total);
            stmt.setString(5, fecha);



            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Venta Realizada Exitosamente.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
