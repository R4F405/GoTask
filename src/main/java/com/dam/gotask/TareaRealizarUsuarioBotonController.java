/**
 * Controlador para los botones que representan las tareas asignadas a un usuario.
 * Permite configurar y manejar la interacción con los botones de las tareas.
 */
package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TareaRealizarUsuarioBotonController {

    @javafx.fxml.FXML
    private Button TareasUsuarioButton;

    int idTarea;
    String NombreTarea;
    Tarea t = new Tarea();
    private TareasRealizarUsuarioController parentController= new TareasRealizarUsuarioController();

    public void setParentController(TareasRealizarUsuarioController parentController) {
        this.parentController = parentController;
    }


    /**
     * Maneja el evento de clic en el botón de las tareas del usuario.
     *
     * @param actionEvent Evento de acción que activa la función.
     */
    @javafx.fxml.FXML
    public void onTareasUsuarioButtonclick(ActionEvent actionEvent) {
        // Implementación del manejo del evento de clic en el botón de la tarea del usuario.

        t.setIdTarea(this.idTarea);
        enviarDatosTarea(actionEvent);

        if (parentController != null) {
            parentController.cambiarContenidoAnchorPaneTRUC();
        }

    }

    /**
     * Inserta la información de la tarea en el botón correspondiente.
     *
     * @param idTarea El identificador único de la tarea.
     * @param nombre  El nombre de la tarea.
     */
    public void Insertar(int idTarea, String nombre){
        // Asigna el valor de idTarea al atributo idTarea de la clase.
        this.idTarea=idTarea;
        // Asigna el valor de nombre al atributo NombreTarea de la clase.
        this.NombreTarea=nombre;
        // Configura las propiedades visuales del botón para mostrar el nombre de la tarea.
        TareasUsuarioButton.setText(NombreTarea);
        TareasUsuarioButton.setPrefWidth(1070);
        TareasUsuarioButton.setPrefHeight(88);
    }



    private void enviarDatosTarea(ActionEvent event) {

        int id = t.getIdTarea();

        this.t = new Tarea(id);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();



        // Paso 1: Obtener instancia de UsuarioHolder
        TareaHolder holder = TareaHolder.getInstance();
        // Paso 2: Establecer usuario
        holder.setTarea(this.t);



    }
}
