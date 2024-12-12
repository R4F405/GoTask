package com.dam.gotask;

/**
 * Clase que actúa como un contenedor singleton para un objeto Grupo.
 */
public class GrupoHolder {

    private Grupo grupo;

    private static final GrupoHolder INSTANCE = new GrupoHolder();

    /**
     * Método estático para obtener la instancia única de GrupoHolder.
     *
     * @return La instancia única de GrupoHolder.
     */
    public static GrupoHolder getInstance() {
        return INSTANCE;
    }

    /**
     * Establece el grupo en el GrupoHolder.
     *
     * @param g El grupo a establecer.
     */
    public void setGrupo(Grupo g) {
        this.grupo = g;
    }

    /**
     * Obtiene el grupo almacenado en el GrupoHolder.
     *
     * @return El grupo almacenado.
     */
    public Grupo getGrupo() {
        return this.grupo;
    }
}
