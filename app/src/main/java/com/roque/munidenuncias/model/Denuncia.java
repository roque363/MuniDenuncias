package com.roque.munidenuncias.model;

/**
 * Created by ROQUE on 16/11/2017.
 */

public class Denuncia {

    private String titulo;
    private String users_id;
    private String descripcion;
    private String ubicacion;
    private String imagen;
    private String estado;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUsers_id() {
        return users_id;
    }

    public void setUsers_id(String users_id) {
        this.users_id = users_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "titulo='" + titulo + '\'' +
                ", users_id='" + users_id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
