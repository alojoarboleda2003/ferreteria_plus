package DAO;

import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.Proveedor;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProveedorDAO
{
    private ConexionBD conexionBD = new ConexionBD();

    /**
     * creamos el metodo de agragr a un proveedor
     * @param proveedor recibe la clase proveedor
     */
    public void agregar(Proveedor proveedor)
    {
        Connection con = conexionBD.getConnection();

        String query = "INSERT INTO proveedor (nombre, contacto, producto_sum) VALUES (?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,proveedor.getNombre());
            pst.setString(2,proveedor.getContacto());
            pst.setString(3,proveedor.getProducto_sum());


            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"proveedor Agregado");
            }else
            {
                JOptionPane.showMessageDialog(null,"Error al ingresar al Proveedor ");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * metod para eliminar a un proveedor
     * @param id_proveedor
     */
    public void eliminar(int id_proveedor)
    {
        Connection con = conexionBD.getConnection();

        String query = "DELETE FROM proveedor WHERE id_proveedor=?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id_proveedor);

            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"Proveedor eliminado ");
            }else
            {
                JOptionPane.showMessageDialog(null,"error al eliminar Proveedor");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * metodo para actualizar a un proveedor
     * @param proveedor
     */

    public void actualizar(Proveedor proveedor)
    {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE proveedor set nombre = ?, contacto = ?, producto_sum = ? WHERE id_proveedor = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,proveedor.getNombre());
            pst.setString(2,proveedor.getContacto());
            pst.setString(3,proveedor.getProducto_sum());
            pst.setInt(4,proveedor.getId_proveedor());


            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"Proveedor actualizado");
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
