package modelos;

public class Proveedor
{
    /**
     * inicializamos los campos de nuestra base de datos de acuerdo a la tabla q se esta manejando
     * se hace el constructor por el cual va a trabajar y
     * se hace el setahnget q es para enviar y traer datos
     */
    private int id_proveedor;
    private String nombre;
    private String contacto;
    private String producto_sum;

    public Proveedor(int id_proveedor, String nombre, String contacto, String producto_sum) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.contacto = contacto;
        this.producto_sum = producto_sum;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getProducto_sum() {
        return producto_sum;
    }

    public void setProducto_sum(String producto_sum) {
        this.producto_sum = producto_sum;
    }


}
