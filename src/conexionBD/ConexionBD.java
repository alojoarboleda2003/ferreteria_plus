package conexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ConexionBD {

    public static Connection getConnection()
    {
        Connection con = null;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferreteria_plus","root", "");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }


}


