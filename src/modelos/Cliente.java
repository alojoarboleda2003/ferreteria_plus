package modelos;

public class Cliente
{
    private int id_cliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;

    // Constructor
    public Cliente(int id, String nombre, String telefono, String direccion, String correo)
    {
        this.id_cliente = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public int getId()
    {
        return id_cliente;
    }

    public void setId(int id)
    {
        this.id_cliente = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
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
