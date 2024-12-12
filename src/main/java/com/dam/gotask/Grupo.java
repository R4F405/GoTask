package com.dam.gotask;

/**
 * Clase que representa un grupo en la aplicación.
 */
public class Grupo {

    private int idGrupo;
    private String nombreGrupo;

    /**
     * Constructor para crear un nuevo grupo con un ID y un nombre.
     *
     * @param idGrupo     El ID del grupo.
     * @param nombreGrupo El nombre del grupo.
     */
    public Grupo(int idGrupo, String nombreGrupo) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
    }

    /**
     * Constructor vacío para la creación de un grupo.
     */
    public Grupo() {
    }

    /**
     * Constructor para crear un nuevo grupo con un nombre.
     *
     * @param nombreGrupo El nombre del grupo.
     */
    public Grupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    /**
     * Constructor para crear un nuevo grupo con un ID.
     *
     * @param idGrupo El ID del grupo.
     */
    public Grupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Obtiene el ID del grupo.
     *
     * @return El ID del grupo.
     */
    public int getIdGrupo() {
        return idGrupo;
    }

    /**
     * Establece el ID del grupo.
     *
     * @param idGrupo El ID del grupo.
     */
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Obtiene el nombre del grupo.
     *
     * @return El nombre del grupo.
     */
    public String getNombreGrupo() {
        return nombreGrupo;
    }

    /**
     * Establece el nombre del grupo.
     *
     * @param nombreGrupo El nombre del grupo.
     */
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
}
