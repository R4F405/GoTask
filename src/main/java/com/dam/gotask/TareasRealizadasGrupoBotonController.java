/**
 * Controlador para los botones que representan las tareas realizadas por un grupo.
 * Permite configurar y manejar la interacción con los botones de las tareas realizadas.
 */
package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TareasRealizadasGrupoBotonController {

    @javafx.fxml.FXML
    private Button TRealizadasGButton;

    int idTarea;
    String NombreTarea;
    private TareasRealizadasGrupoController parentController= new TareasRealizadasGrupoController();
    Tarea t = new Tarea();



    public void setParentController(TareasRealizadasGrupoController parentController) {
        this.parentController = parentController;
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
        TRealizadasGButton.setText(NombreTarea);
        TRealizadasGButton.setPrefWidth(1070);
        TRealizadasGButton.setPrefHeight(88);
    }

    /**
     * Maneja el evento de clic en el botón de las tareas realizadas por el grupo.
     *
     * @param actionEvent Evento de acción que activa la función.
     */
    @javafx.fxml.FXML
    public void onTRealizadasGButtonClick(ActionEvent actionEvent) {
        // Implementación del manejo del evento de clic en el botón de las tareas realizadas por el grupo.
        t.setIdTarea(this.idTarea);
        enviarDatosTarea(actionEvent);

            if (parentController != null) {
                parentController.cambiarContenidoAnchorPane();
            }

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




