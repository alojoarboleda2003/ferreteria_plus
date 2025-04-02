package modelos;

public class DetetalleOrden {
    private int id_detalle_orden;
    private int id_orden;
    private int id_inventario;
    private int id_cliente;
    private int id_empleado;
    private String nombre_cliente;
    private String nombre_empleado;
    private int cantidad;
    private double subtotal;
    private  String estado;

    public DetetalleOrden(int id_detalle_orden, int id_orden, int id_inventario,int id_cliente,int id_empleado,String nombre_cliente,String nombre_empleado,  int cantidad, double subtotal, String estado) {
        this.id_detalle_orden = id_detalle_orden;
        this.id_orden = id_orden;
        this.id_inventario = id_inventario;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
        this.nombre_cliente = nombre_cliente;
        this.nombre_empleado = nombre_empleado;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.estado = estado;
    }

    public DetetalleOrden(int idCliente) {
    }

    public DetetalleOrden(String nombreCliente) {
    }

    public DetetalleOrden() {

    }


    public DetetalleOrden(String nombreCliente, String nombreEmpleado, int cantidad, double subtotal, String estado) {
    }

    public DetetalleOrden(int id_orden, int idCliente, int id_empleado) {
    }

    public DetetalleOrden(int id_detalle_orden, int id_inventario, int idCliente, int id_empleado, String nombreCliente, String nombreEmpleado, int cantidad, double subtotal, String estado) {
    }


    public int getId_detalle_orden() {
        return id_detalle_orden;
    }

    public void setId_detalle_orden(int id_detalle_orden) {
        this.id_detalle_orden = id_detalle_orden;
    }

    public  int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public  int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public  int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    public  int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre_empleado() {
        return getNombre_empleado();
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public  int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public  int getSubtotal() {
        return (int) subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
