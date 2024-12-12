package com.dam.gotask;

/**
 * Clase que actúa como contenedor para un objeto Tarea, permitiendo
 * establecer y obtener esta instancia de manera sincronizada.
 */
public class TareaHolder {

    /** Objeto Tarea almacenado en este contenedor. */
    private Tarea tarea;

    /**
     * Declaración de una instancia estática de la misma clase para implementar
     * el patrón Singleton.
     */
    private static final TareaHolder INSTANCE = new TareaHolder();

    /**
     * Método estático para obtener la instancia única de TareaHolder.
     *
     * @return La instancia única de TareaHolder.
     */
    public static TareaHolder getInstance() {

        return INSTANCE;

    }

    /**
     * Método sincronizado para establecer el objeto Tarea en el contenedor.
     *
     * @param t El objeto Tarea que se desea almacenar en el contenedor.
     */
    public void setTarea(Tarea t) {

        this.tarea = t;

    }

    /**
     * Método sincronizado para obtener el objeto Tarea del contenedor.
     *
     * @return El objeto Tarea almacenado en el contenedor.
     */
    public Tarea getTarea() {

        return this.tarea;

    }

}
