package DAO;

import conexionBD.ConexionBD;
import modelos.Empleado;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpleadoDAO
{

    private ConexionBD conexionBD = new ConexionBD();

    /**
     * creamos el metodod de agregar un empleado a la bd
     * @param empleado se instancia toda la clase empleado
     */
    public void agregar(Empleado empleado)
    {
        Connection con = conexionBD.getConnection();

        String query = "INSERT INTO empleado (nombre, cargo, salario) VALUES (?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, empleado.getNombre());
            pst.setString(2, empleado.getCargo());
            pst.setString(3, String.valueOf(empleado.getSalario()));


            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                System.out.println("Empleado Agregado");
            } else
            {
                System.out.println("Error al ingresar al Empleado");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * metodo para eliminar a un empleado
     * @param id_empleado recibe como parametro la clase del id
     */
    public void eliminar(int id_empleado)
    {
        Connection con = conexionBD.getConnection();

        String query = "DELETE FROM empleado WHERE id_empleado=?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_empleado);

            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "Empleado Eliminado");
            } else
            {
                JOptionPane.showMessageDialog(null, "Error al eliminar al Empleado");
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * metdo para actualizar el empleado
     * @param empleado recibe como parametro al empleado
     */

    public void actualizar(Empleado empleado)
    {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE empleado SET nombre=?, cargo=?, salario=? WHERE id_empleado=?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, empleado.getNombre());
            pst.setString(2, empleado.getCargo());
            pst.setDouble(3, empleado.getSalario());
            pst.setInt(4,empleado.getId_empleado());

            int resultado = pst.executeUpdate();

            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "Empleado Actualizado");
            } else
            {
                JOptionPane.showMessageDialog(null, "Error al actualizar al Empleado");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
