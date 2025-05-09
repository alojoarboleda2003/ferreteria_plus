package modelos;

public class Cliente
{
    /**
     * inicializamos los campos de nuestra base de datos de acuerdo a la tabla q se esta manejando
     * se hace el constructor por el cual va a trabajar y
     * se hace el setahnget q es para enviar y traer datos
     */
    private int id_cliente;
    private int cedula;
    private String nombre;
    private double telefono;
    private String direccion;
    private String correo;

    // Constructor
    public Cliente(int id_cliente,int cedula, String nombre, double telefono, String direccion, String correo)
    {
        this.id_cliente = id_cliente;
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public Cliente(String nombresc) {
    }

    public Cliente(String nombresc, int cedula) {
    }

    public Cliente(int cedula) {
    }

    public int getId_cliente()
    {
        return id_cliente;
    }

    public void setId(int id_cliente)
    {
        this.id_cliente = id_cliente;
    }
    public int getCedula()
    {
        return cedula;
    }

    public void setCedula(int cedula)
    {
        this.cedula = cedula;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public double getTelefono()
    {
        return telefono;
    }

    public void setTelefono(double telefono)
    {
        this.telefono = telefono;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }


}
