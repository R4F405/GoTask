package com.dam.gotask;

/**
 * Clase que actúa como contenedor para un objeto GrupoUsuario, permitiendo
 * establecer y obtener esta instancia de manera sincronizada.
 */
public class GrupoUsuarioHolder {

    /** Objeto GrupoUsuario almacenado en este contenedor. */
    private GrupoUsuario grupoUsuario;

    /**
     * Declaración de una instancia estática de la misma clase para implementar
     * el patrón Singleton.
     */
    private static final GrupoUsuarioHolder INSTANCE = new GrupoUsuarioHolder();

    /**
     * Método estático para obtener la instancia única de GrupoUsuarioHolder.
     *
     * @return La instancia única de GrupoUsuarioHolder.
     */
    public static GrupoUsuarioHolder getInstance() {
        return INSTANCE;
    }

    /**
     * Método sincronizado para establecer el objeto GrupoUsuario en el contenedor.
     *
     * @param gu El objeto GrupoUsuario que se desea almacenar en el contenedor.
     */
    public void setGrupoUsuario(GrupoUsuario gu) {
        this.grupoUsuario = gu;
    }


    /**
     * Método sincronizado para obtener el objeto GrupoUsuario del contenedor.
     *
     * @return El objeto GrupoUsuario almacenado en el contenedor.
     */
    public GrupoUsuario getGrupoUsuario() {
        return this.grupoUsuario;
    }
}
