package modelos;

import org.w3c.dom.Text;

public class Inventario
{
    public int id_inventario;
    public String nombre;
    public String categoria;
    public int precio;
    public int cantidad_disponible;
    public String proveedor_asoc;
    public int stock;

    public Inventario(int id, String nombre, String categoria, int precio, int cantidad_disponible, String proveedor_asoc, int stock)
    {
        this.id_inventario = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad_disponible = cantidad_disponible;
        this.proveedor_asoc = proveedor_asoc;
        this.stock = stock;
    }

    public int getId()
    {
        return id_inventario;
    }

    public void setId(int id)
    {
        this.id_inventario = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public int getPrecio()
    {
        return precio;
    }

    public void setPrecio(int precio)
    {
        this.precio = precio;
    }

    public int getCantidadDisponible()
    {
        return cantidad_disponible;
    }

    public void setCantidadDisponible(int cantidad_disponible)
    {
        this.cantidad_disponible = cantidad_disponible;
    }

    public String getProveedorAsociado()
    {
        return proveedor_asoc;
    }

    public void setProveedorAsociado(String proveedor_asoc)
    {
        this.proveedor_asoc = proveedor_asoc;
    }

    public int getStock()
    {
        return stock;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }


}
