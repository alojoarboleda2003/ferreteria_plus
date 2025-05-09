package modelos;

public class Empleado
{
    /**
     * inicializamos los campos de nuestra base de datos de acuerdo a la tabla q se esta manejando
     * se hace el constructor por el cual va a trabajar y
     * se hace el setahnget q es para enviar y traer datos
     */
    private int id_empleado;
    private String nombre;
    private String cargo;
    private double salario;

    // Constructor
    public Empleado(int id_empleado, String nombre, String cargo, double salario)
    {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }

    public Empleado(String nombree) {
    }

    public int getId_empleado()
    {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado)
    {
        this.id_empleado = id_empleado;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getCargo()
    {
        return cargo;
    }

    public void setCargo(String cargo)
    {
        this.cargo = cargo;
    }

    public double getSalario()
    {
        return salario;
    }

    public void setSalario(double salario)
    {
        this.salario = salario;
    }

}
