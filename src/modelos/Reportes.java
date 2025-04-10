package modelos;

public class Reportes
{
    /**
     * inicializamos los campos de nuestra base de datos de acuerdo a la tabla q se esta manejando
     * se hace el constructor por el cual va a trabajar y
     * se hace el setahnget q es para enviar y traer datos
     */
    public int id_reporte;
    public int id_venta;
    public int id_inventario;
    public int id_cliente;

    public Reportes(int id_reporte, int id_venta, int id_inventario, int id_cliente)
    {
        this.id_reporte = id_reporte;
        this.id_venta = id_venta;
        this.id_inventario = id_inventario;
        this.id_cliente = id_cliente;
    }

    public int getId()
    {
        return id_reporte;
    }

    public void setId(int id)
    {
        this.id_reporte = id;
    }

    public int getIdVenta()
    {
        return id_venta;
    }

    public void setIdVenta(int id)
    {
        this.id_venta = id;
    }

    public int getIdInventario()
    {
        return id_inventario;
    }

    public void setIdInventario(int id)
    {
        this.id_inventario = id;
    }

    public int getIdCliente()
    {
        return id_cliente;
    }

    public void setIdCliente(int id)
    {
        this.id_cliente = id;
    }


}
