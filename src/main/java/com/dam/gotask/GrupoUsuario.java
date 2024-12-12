package com.dam.gotask;

/**
 * Clase que representa la relación entre un grupo y un usuario,
 * indicando si el usuario es administrador del grupo o no.
 */
public class GrupoUsuario {

    private Integer idGrupo; // ID del grupo
    private Integer idUsuario; // ID del usuario
    private boolean es_Admin; // Indica si el usuario es administrador del grupo o no

    /**
     * Constructor que inicializa el ID del grupo, el ID del usuario y
     * el estado de administrador del grupo.
     *
     * @param idGrupo   ID del grupo.
     * @param idUsuario ID del usuario.
     * @param es_Admin  Indica si el usuario es administrador del grupo.
     */
    public GrupoUsuario(Integer idGrupo, Integer idUsuario, boolean es_Admin) {
        this.es_Admin = es_Admin;
        this.idUsuario = idUsuario;
        this.idGrupo = idGrupo;
    }

    /**
     * Constructor vacío.
     */
    public GrupoUsuario() {
    }

    /**
     * Obtiene el ID del grupo.
     *
     * @return El ID del grupo.
     */
    public Integer getIdGrupo() {
        return idGrupo;
    }

    /**
     * Establece el ID del grupo.
     *
     * @param idGrupo El ID del grupo.
     */
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param idUsuario El ID del usuario.
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Verifica si el usuario es administrador del grupo.
     *
     * @return true si el usuario es administrador del grupo, false de lo contrario.
     */
    public boolean isEs_Admin() {
        return es_Admin;
    }

    /**
     * Establece si el usuario es administrador del grupo.
     *
     * @param es_Admin true si el usuario es administrador del grupo, false de lo contrario.
     */
    public void setEs_Admin(boolean es_Admin) {
        this.es_Admin = es_Admin;
    }
}
