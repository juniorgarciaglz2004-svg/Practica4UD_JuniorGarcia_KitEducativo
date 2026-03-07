package kitEducativo.datos;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Empresa {
    private ObjectId id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String ubicacion;
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Empresa(ObjectId id, String nombre, String descripcion, LocalDate fechaCreacion, String ubicacion, int valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.ubicacion = ubicacion;
        this.valoracion = valoracion;
    }

    public Empresa() {
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", ubicacion='" + ubicacion + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
