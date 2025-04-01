package DAO;

import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.Inventario;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventarioDAO
{
    private ConexionBD conexionBD = new ConexionBD();


    public void agregar(Inventario inventario)
    {
        Connection con = conexionBD.getConnection();

        String query = "INSERT INTO inventario (nombres, categoria ,precio, cant_disponible, proveedor_asoc, stock) VALUES (?,?,?,?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,inventario.getNombres());
            pst.setString(2,inventario.getCategoria());
            pst.setDouble(3,inventario.getPrecio());
            pst.setInt(4,inventario.getCant_disponible());
            pst.setString(5,inventario.getProveedor_asoc());
            pst.setInt(6,inventario.getStock());



            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"Cliente Agregado");
            }else
            {
                JOptionPane.showMessageDialog(null,"Error al ingresar al Ciente ");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void eliminar(int id_cliente)
    {
        Connection con = conexionBD.getConnection();

        String query = "DELETE FROM inventario WHERE id_inventario=?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id_cliente);

            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"Inventario eliminado ");
            }else
            {
                JOptionPane.showMessageDialog(null,"error al eliminar");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void actualizar(Inventario inventario)
    {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE inventario set nombres = ?, categoria = ?, precio = ?, cant_disponible = ?, proveedor_asoc = ?, stock = ? WHERE id_cliente = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,inventario.getNombres());
            pst.setString(2,inventario.getCategoria());
            pst.setDouble(3,inventario.getPrecio());
            pst.setInt(4,inventario.getCant_disponible());
            pst.setString(5,inventario.getProveedor_asoc());
            pst.setInt(6,inventario.getStock());



            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"Cliente actualizado");
            }else
            {
                JOptionPane.showMessageDialog(null,"error al modificar");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
