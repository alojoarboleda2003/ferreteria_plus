package modelos;

import org.w3c.dom.Text;

public class Inventario
{
    private int id_inventario;
    private String nombres;
    private  String categoria;
    private double precio;
    private int cant_disponible;
    private String proveedor_asoc;


    public Inventario(int id_inventario, String nombres, String categoria, double precio, int cant_disponible, String proveedor_asoc) {
        this.id_inventario = id_inventario;
        this.nombres = nombres;
        this.categoria = categoria;
        this.precio = precio;
        this.cant_disponible = cant_disponible;
        this.proveedor_asoc = proveedor_asoc;

    }


    public Inventario(double precio) {

    }



    public Inventario(String nombres, String categoria, double precio, int cantDisponible, String proveedorAsoc, int stock) {
    }

    public Inventario(String nombrest) {
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCant_disponible() {
        return cant_disponible;
    }

    public void setCant_disponible(int cant_disponible) {
        this.cant_disponible = cant_disponible;
    }

    public String getProveedor_asoc() {
        return proveedor_asoc;
    }

    public void setProveedor_asoc(String proveedor_asoc) {
        this.proveedor_asoc = proveedor_asoc;
    }




}
