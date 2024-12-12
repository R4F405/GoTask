package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * El controlador VerTareasController gestiona la vista para ver y añadir tareas.
 * Proporciona funcionalidades para cambiar a la vista de añadir tareas.
 */
public class VerTareasController {
    @javafx.fxml.FXML
    private Button anyadirTareasBtn;
    @javafx.fxml.FXML
    private AnchorPane MenuPane;

    /**
     * Cambia a la vista de añadir tareas cuando se hace clic en el botón "Añadir Tareas".
     *
     * @param actionEvent El evento de acción que desencadena esta función.
     */
    @javafx.fxml.FXML
    public void AnyadirTareas(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-admin/anyadirTareas.fxml"));
            this.MenuPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
