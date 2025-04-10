package modelos;

public class Orden_Compra
{
    /**
     * inicializamos los campos de nuestra base de datos de acuerdo a la tabla q se esta manejando
     * se hace el constructor por el cual va a trabajar y
     * se hace el setahnget q es para enviar y traer datos
     */
    private int id_orden;
    private int id_cliente;
    private int id_empleado;
    private String historial;
    private int total_orden;
    private String estado;

    // Constructor
    public Orden_Compra(int id_orden, int id_cliente, int id_empleado, String historial, int total_orden, String estado)
    {
        this.id_orden = id_orden;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
        this.historial = historial;
        this.total_orden = total_orden;
        this.estado = estado;
    }

    public int getId()
    {
        return id_orden;
    }

    public void setId(int id)
    {
        this.id_orden = id;
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

    public String getHistorial()
    {
        return historial;
    }

    public void setHistorial(String historial)
    {
        this.historial = historial;
    }

    public int getTotalOrden()
    {
        return total_orden;
    }

    public void setTotalOrden(int total)
    {
        this.total_orden = total;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }


}
