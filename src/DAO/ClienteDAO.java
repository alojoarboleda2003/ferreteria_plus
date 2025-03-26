package DAO;

import conexionBD.ConexionBD;
import modelos.Cliente;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO
{
    private ConexionBD conexionBD = new ConexionBD();

    public void agregar(Cliente cliente)
    {
        Connection con = conexionBD.getConnection();

        String query = "INSERT INTO cliente (nombre, telefono, direccion, correo) VALUES (?,?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,cliente.getNombre());
            pst.setDouble(2,cliente.getTelefono());
            pst.setString(3,cliente.getDireccion());
            pst.setString(4,cliente.getCorreo());

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

        String query = "DELETE FROM cliente WHERE id_cliente=?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id_cliente);

            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null,"Cliente eliminado ");
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

    public void actualizar(Cliente cliente)
    {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE cliente set nombre = ?, telefono = ?, direccion = ?, correo = ? WHERE id_cliente = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,cliente.getNombre());
            pst.setDouble(2,cliente.getTelefono());
            pst.setString(3,cliente.getDireccion());
            pst.setString(4,cliente.getCorreo());
            pst.setInt(5,cliente.getId_cliente());

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
