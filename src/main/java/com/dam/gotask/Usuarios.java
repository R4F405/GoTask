/**
 * Clase que representa a un usuario.
 */
package com.dam.gotask;

import javafx.scene.image.Image;

public class Usuarios {

    private int id;
    private String nombre;
    private String correo;
    private String password;
    private Image imagen;
    private boolean es_admin;

    /**
     * Constructor completo para un usuario.
     *
     * @param id       El ID del usuario.
     * @param nombre   El nombre del usuario.
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @param imagen   La imagen del usuario.
     * @param es_admin Indica si el usuario es administrador.
     */
    public Usuarios(int id, String nombre, String correo, String password, Image imagen, boolean es_admin) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.imagen = imagen;
        this.es_admin = es_admin;
    }

    public Usuarios(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor para un usuario sin imagen y es_admin.
     *
     * @param id       El ID del usuario.
     * @param nombre   El nombre del usuario.
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public Usuarios(int id, String nombre, String correo, String password) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }

    /**
     * Constructor para un usuario con solo correo y contraseña.
     *
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public Usuarios(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    /**
     * Constructor vacío para un usuario.
     */
    public Usuarios() {

    }

    public Usuarios(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public boolean isEs_admin() {
        return es_admin;
    }

    public void setEs_admin(boolean es_admin) {
        this.es_admin = es_admin;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
