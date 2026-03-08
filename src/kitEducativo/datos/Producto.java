package kitEducativo.datos;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Producto {
    private ObjectId id;
    private String nombre;
    private String descripcion;
    private String modelo;
    private String marca;
    private EstadoProducto estado;

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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public Producto(ObjectId id, String nombre, String descripcion, String modelo, String marca, EstadoProducto estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modelo = modelo;
        this.marca = marca;
        this.estado = estado;
    }


    public Producto() {
    }


    @Override
    public String toString() {
        return nombre + " (" + id + ")";
    }
}

