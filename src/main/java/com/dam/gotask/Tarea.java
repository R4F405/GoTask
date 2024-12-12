package com.dam.gotask;


/**
 * Clase que representa una tarea.
 */
public class Tarea {
    /** ID de la tarea. */
    private int idTarea;
    /** Título de la tarea. */
    private String titulo;
    /** Nota o descripción de la tarea. */
    private String nota;
    /** Recompensa asociada a la tarea. */
    private int recompensa;


    /**
     * Constructor de la clase Tarea con todos los atributos.
     *
     * @param idTarea ID de la tarea.
     * @param titulo Título de la tarea.
     * @param nota Nota o descripción de la tarea.
     * @param recompensa Recompensa asociada a la tarea.
     */
    public Tarea(int idTarea, String titulo, String nota, int recompensa) {
        this.idTarea = idTarea;
        this.titulo = titulo;
        this.nota = nota;
        this.recompensa = recompensa;
    }

    /**
     * Constructor de la clase Tarea con título y nota.
     *
     * @param titulo Título de la tarea.
     * @param nota Nota o descripción de la tarea.
     */
    public Tarea(String titulo, String nota) {
        this.titulo = titulo;
        this.nota = nota;
    }

    /**
     * Constructor de la clase Tarea con título, nota y recompensa.
     *
     * @param titulo Título de la tarea.
     * @param nota Nota o descripción de la tarea.
     * @param recompensa Recompensa asociada a la tarea.
     */
    public Tarea(String titulo, String nota, int recompensa) {
        this.titulo = titulo;
        this.nota = nota;
        this.recompensa = recompensa;
    }

    /** Constructor vacío de la clase Tarea. */
    public Tarea() {
    }

    /**
     * Constructor de la clase Tarea con ID de tarea.
     *
     * @param idTarea ID de la tarea.
     */
    public Tarea(int idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * Obtiene el ID de la tarea.
     *
     * @return ID de la tarea.
     */
    public int getIdTarea() {
        return idTarea;
    }


    /**
     * Establece el ID de la tarea.
     *
     * @param idTarea ID de la tarea.
     */
    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * Obtiene el título de la tarea.
     *
     * @return Título de la tarea.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la tarea.
     *
     * @param titulo Título de la tarea.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la nota o descripción de la tarea.
     *
     * @return Nota o descripción de la tarea.
     */
    public String getNota() {
        return nota;
    }

    /**
     * Establece la nota o descripción de la tarea.
     *
     * @param nota Nota o descripción de la tarea.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }


    /**
     * Obtiene la recompensa asociada a la tarea.
     *
     * @return Recompensa asociada a la tarea.
     */
    public int getRecompensa() {
        return recompensa;
    }


    /**
     * Establece la recompensa asociada a la tarea.
     *
     * @param recompensa Recompensa asociada a la tarea.
     */
    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }
}
