package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TareasRealizadasUsuarioBotonController {
    @javafx.fxml.FXML
    private Button TareasRealizadasUsuarioButton;

    int idTarea;
    String NombreTarea;
    Tarea t = new Tarea();
    TareasRealizadasUsuarioController parentController = new TareasRealizadasUsuarioController();

    public void setParentController(TareasRealizadasUsuarioController parentController) {
        this.parentController = parentController;
    }

    @javafx.fxml.FXML
    public void onTareasRealizadasUsuarioButtonClick(ActionEvent actionEvent) {

        t.setIdTarea(this.idTarea);
        enviarDatosTarea(actionEvent);

        if (parentController != null) {
            parentController.cambiarContenidoAnchorPaneTRealizadasUC();
        }

    }

    public void Insertar(int idTarea, String nombre){
        // Asigna el valor de idGrupo al atributo id_Grupo de la clase.
        this.idTarea=idTarea;
        // Asigna el valor de nombre al atributo NombreGrupo de la clase.
        this.NombreTarea=nombre;
        // Configura las propiedades del botón 'GrupoBotton' con el nombre del grupo.
        TareasRealizadasUsuarioButton.setText(NombreTarea);
        TareasRealizadasUsuarioButton.setPrefWidth(1070);
        TareasRealizadasUsuarioButton.setPrefHeight(88);
    }
//a
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
