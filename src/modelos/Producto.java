package modelos;

public class Producto {
    private int id_producto;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id_producto, String nombre, double precio, int stock) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    public Producto(String nombre) {
        this.nombre = nombre;
        this.id_producto = 0;  // valor predeterminado
        this.precio = 0.0;     // valor predeterminado
        this.stock = 0;        // valor predeterminado
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
