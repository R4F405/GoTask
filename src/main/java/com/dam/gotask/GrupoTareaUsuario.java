package com.dam.gotask;

import java.util.Date;

/**
 * Clase que representa la relación entre un grupo, una tarea y un usuario.
 */
public class GrupoTareaUsuario {

    private int idGrupo; // ID del grupo
    private int idTarea; // ID de la tarea
    private int idUsuario; // ID del usuario
    private Date fecha; // Fecha relacionada con la tarea (opcional)

    /**
     * Constructor que inicializa el grupo, la tarea y el usuario.
     *
     * @param idGrupo   ID del grupo.
     * @param idTarea   ID de la tarea.
     * @param idUsuario ID del usuario.
     */
    public GrupoTareaUsuario(int idGrupo, int idTarea, int idUsuario) {
        this.idGrupo = idGrupo;
        this.idTarea = idTarea;
        this.idUsuario = idUsuario;
    }

    /**
     * Constructor que inicializa la tarea y el usuario.
     *
     * @param idTarea   ID de la tarea.
     * @param idUsuario ID del usuario.
     */
    public GrupoTareaUsuario(int idTarea, int idUsuario) {
        this.idTarea = idTarea;
        this.idUsuario = idUsuario;
    }

    /**
     * Constructor vacío.
     */
    public GrupoTareaUsuario() {
    }

    /**
     * Constructor que inicializa el grupo, la tarea, el usuario y la fecha.
     *
     * @param idGrupo   ID del grupo.
     * @param idTarea   ID de la tarea.
     * @param idUsuario ID del usuario.
     * @param fecha     Fecha relacionada con la tarea.
     */
    public GrupoTareaUsuario(int idGrupo, int idTarea, int idUsuario, Date fecha) {
        this.idGrupo = idGrupo;
        this.idTarea = idTarea;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
    }

    // Métodos getter y setter para cada atributo

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
     * Obtiene el ID de la tarea.
     *
     * @return El ID de la tarea.
     */
    public int getIdTarea() {
        return idTarea;
    }

    /**
     * Establece el ID de la tarea.
     *
     * @param idTarea El ID de la tarea.
     */
    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param idUsuario El ID del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene la fecha relacionada con la tarea.
     *
     * @return La fecha relacionada con la tarea.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha relacionada con la tarea.
     *
     * @param fecha La fecha relacionada con la tarea.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
