package modelos;

public class Proveedor
{
    public int id_proveedor;
    public String nombre;
    public String contacto;
    public String producto_sum;

    public Proveedor(int id, String nombre, String contacto, String producto_sum)
    {
        this.id_proveedor = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.producto_sum = producto_sum;
    }

    public int getId()
    {
        return id_proveedor;
    }

    public void setId(int id)
    {
        this.id_proveedor = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getContacto()
    {
        return contacto;
    }

    public void setContacto(String contacto)
    {
        this.contacto = contacto;
    }

    public String getProductoSum()
    {
        return producto_sum;
    }

    public void setProductoSum(String producto_sum)
    {
        this.producto_sum = producto_sum;
    }


}
