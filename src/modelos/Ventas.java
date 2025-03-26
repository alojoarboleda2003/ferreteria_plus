package modelos;

public class Ventas
{
    public int id_venta;
    public int cant_ventas;
    public int id_cliente;
    public int id_empleado;

    public Ventas(int id_venta, int cant_ventas, int id_cliente, int id_empleado)
    {
        this.id_venta = id_venta;
        this.cant_ventas = cant_ventas;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
    }

    public int getId()
    {
        return id_venta;
    }

    public void setId(int id)
    {
        this.id_venta = id;
    }

    public int getCantVentas()
    {
        return cant_ventas;
    }

    public void setCantVentas(int cant)
    {
        this.cant_ventas = cant;
    }

    public int getIdCliente()
    {
        return id_cliente;
    }

    public void setIdCliente(int id)
    {
        this.id_cliente = id;
    }

    public int getIdEmpleado()
    {
        return id_empleado;
    }

    public void setIdEmpleado(int id)
    {
        this.id_empleado = id;
    }


}
