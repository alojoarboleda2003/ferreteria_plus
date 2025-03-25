package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    public Connection getConnection()
    {
        Connection con = null;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restauranteadso","root", "");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.getConnection();
    }
}
