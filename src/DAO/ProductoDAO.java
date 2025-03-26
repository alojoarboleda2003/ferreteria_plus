package DAO;

import conexionBD.ConexionBD;
import modelos.Producto;

import java.sql.Connection;

public class ProductoDAO {

    private ConexionBD conexionBD = new ConexionBD();

    public  void  buscarp(Producto producto){
        Connection con = conexionBD.getConnection();


        String query = "SELECT * FROM producto WHERE nombre=?";
    }

}
