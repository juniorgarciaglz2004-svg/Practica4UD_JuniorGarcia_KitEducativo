package kitEducativo.datos;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Kit_Educativo {
    private ObjectId id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private ObjectId empresasKit;
    private ObjectId productoKit;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private double precio;
    private int valoracion;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ObjectId getEmpresasKit() {
        return empresasKit;
    }

    public void setEmpresasKit(ObjectId empresasKit) {
        this.empresasKit = empresasKit;
    }

    public ObjectId getProductoKit() {
        return productoKit;
    }

    public void setProductoKit(ObjectId productoKit) {
        this.productoKit = productoKit;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDate fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Kit_Educativo(ObjectId id, String nombre, String descripcion, int cantidad, ObjectId empresasKit, ObjectId productoKit, LocalDate fechaCreacion, LocalDate fechaActualizacion, double precio, int valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.empresasKit = empresasKit;
        this.productoKit = productoKit;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.precio = precio;
        this.valoracion = valoracion;
    }

    public Kit_Educativo() {
    }

    @Override
    public String toString() {
        return "Kit_Educativo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", empresasKit=" + empresasKit +
                ", productoKit=" + productoKit +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", precio=" + precio +
                ", valoracion=" + valoracion +
                '}';
    }
}