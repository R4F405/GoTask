package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para manejar la vista de tareas por realizar de un grupo para un usuario específico.
 */
public class TareasRealizarGrupoUsuarioController implements Initializable {

    @javafx.fxml.FXML
    private ScrollPane TareasPorRealizarGrupoScrollPane;
    @javafx.fxml.FXML
    private GridPane TareasPorRealizarGrupoGridPane;

    Usuarios u = new Usuarios();
    GrupoModel gm = new GrupoModel();
    GruposUsuariosController guc;
    Tarea t = new Tarea();
    Grupo g = new Grupo();
    @javafx.fxml.FXML
    private AnchorPane Menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuPrincipalController menuPrincipalController = new MenuPrincipalController();
        guc = new GruposUsuariosController(menuPrincipalController);
        recuperarDatosGrupo();
        gm.getVerTareasPorRealizarGrupo(g.getIdGrupo());

        int column = 0;
        int row = 1;

        for (Tarea t : gm.listaGTU) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vistas-paraTodos/TareasRealizarGrupoBoton.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TareasRealizarGrupoBotonController botonController = fxmlLoader.getController();
                botonController.Insertar(t.getIdTarea(), t.getTitulo());
                botonController.setParentController(this);

                row++;
                TareasPorRealizarGrupoGridPane.add(anchorPane, column, row);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Recupera los datos del grupo actual.
     */
    public void recuperarDatosGrupo() {
        GrupoHolder holder = GrupoHolder.getInstance();
        g = holder.getGrupo();
        if (g != null) {
            // Any logic needed here
        } else {
            System.err.println("El grupo es nulo.");
        }
    }


    /**
     * Cambia el contenido del AnchorPane para mostrar las tareas del grupo.
     */
    public void cambiarContenidoAnchorPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas-paraTodos/verTareaGrupo-view.fxml"));
            AnchorPane nuevaVista = fxmlLoader.load();
            Menu.getChildren().setAll(nuevaVista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
