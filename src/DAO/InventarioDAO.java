package DAO;

import conexionBD.ConexionBD;
import modelos.Inventario;

import java.sql.Connection;

public class InventarioDAO
{
    private static ConexionBD conexionBD = new ConexionBD();

    public static void  buscarp(Inventario inventario){
        Connection con = conexionBD.getConnection();


        String query = "SELECT id_inventario,nombres,precio,cant_disponible FROM inventario";
    };


}
