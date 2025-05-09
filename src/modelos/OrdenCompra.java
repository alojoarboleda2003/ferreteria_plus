package modelos;

public class OrdenCompra {
    /**
     * inicializamos los campos de nuestra base de datos de acuerdo a la tabla q se esta manejando
     * se hace el constructor por el cual va a trabajar y
     * se hace el setahnget q es para enviar y traer datos
     */
    private int id_orden;
    private int id_cliente;
    private int id_empleado;

    public OrdenCompra(int id_orden, int id_cliente, int id_empleado) {
        this.id_orden = id_orden;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }
}
