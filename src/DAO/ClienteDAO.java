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

    public static void buscarc(Cliente cliente) {
        Connection con = ConexionBD.getConnection();


        String query = "SELECT nombre FROM inventario WHERE cedula = ?";
    }

    /**
     * creamos la funcion para agregar un cliente a la base de datos
     * @param cliente recibe como parametro todos los datos del cliente q estan en la bd
     */

    public void agregar(Cliente cliente)
    {
        Connection con = conexionBD.getConnection();

        String query = "INSERT INTO cliente (cedula,nombre, telefono, direccion, correo) VALUES (?,?,?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,cliente.getCedula());
            pst.setString(2,cliente.getNombre());
            pst.setDouble(3,cliente.getTelefono());
            pst.setString(4,cliente.getDireccion());
            pst.setString(5,cliente.getCorreo());

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

    /**
     * creamops la funcion de eliminar un cliente en la bd
     * @param id_cliente aqui recibe como parametro el id del cliente a borrar
     */
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

    /**
     * creamos el metodo de actualizar un cliente en la bd
     * @param cliente recibe todos los datos del cliente a actualizar
     */

    public void actualizar(Cliente cliente)
    {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE cliente set cedula = ?, nombre = ?, telefono = ?, direccion = ?, correo = ? WHERE id_cliente = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,cliente.getCedula());
            pst.setString(2,cliente.getNombre());
            pst.setDouble(3,cliente.getTelefono());
            pst.setString(4,cliente.getDireccion());
            pst.setString(5,cliente.getCorreo());
            pst.setInt(6,cliente.getId_cliente());

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
