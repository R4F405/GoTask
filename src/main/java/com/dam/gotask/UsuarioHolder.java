/**
 * Clase singleton para almacenar el usuario actual.
 */
package com.dam.gotask;

public final class UsuarioHolder {

    private Usuarios usuario;
    private static final UsuarioHolder INSTANCE = new UsuarioHolder();

    /**
     * Método privado para crear una instancia única de UsuarioHolder.
     */
    private UsuarioHolder() {}

    /**
     * Devuelve la instancia única de UsuarioHolder.
     *
     * @return La instancia única de UsuarioHolder.
     */
    public static UsuarioHolder getInstance() {
        return INSTANCE;
    }

    /**
     * Establece el usuario actual.
     *
     * @param u El usuario actual.
     */
    public void setUsuario(Usuarios u) {
        this.usuario = u;
    }

    /**
     * Obtiene el usuario actual.
     *
     * @return El usuario actual.
     */
    public Usuarios getUsuario() {
        return this.usuario;
    }
}
