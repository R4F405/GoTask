package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la página de grupos, que muestra los grupos a los que pertenece el usuario actual.
 */
public class GrupoPaginaController implements Initializable {

    // Elementos de la interfaz definidos en el archivo FXML
    @javafx.fxml.FXML
    private AnchorPane PaginaGrupo;
    @javafx.fxml.FXML
    private GridPane grupoGridPane;

    // Instancias de modelos y controladores necesarios
    GrupoModel gm = new GrupoModel();
    Usuarios u = new Usuarios();

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido completamente procesado.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas para el objeto raíz o {@code null} si no está disponible.
     * @param resourceBundle El recurso utilizado para localizar los objetos raíz, o {@code null} si no está disponible.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0; // Variable para controlar la posición de la columna en el GridPane.
        int row = 1; // Variable para controlar la posición de la fila en el GridPane.

        // Carga de datos necesarios
        recuperarDatos();
        gm.getVerGrupos(this.u.getId()); // Obtiene los grupos del usuario actual

        // Iteración sobre cada grupo y carga de su vista asociada
        for (Grupo g : gm.listaGrupo) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vistas-paraTodos/gruposUsuarios.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                GruposUsuariosController prueba = fxmlLoader.getController();
                prueba.Insertar(g.getIdGrupo(), g.getNombreGrupo());

                // Controla la disposición de los elementos en el GridPane
                if (column == 3) {
                    column = 0;
                    row++;
                }
                column++;

                // Añade el AnchorPane al GridPane en la posición especificada
                grupoGridPane.add(anchorPane, column, row);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Maneja el evento de clic en el botón "Añadir Grupo".
     *
     * @param actionEvent Evento de acción generado por el clic en el botón.
     */
    @javafx.fxml.FXML
    public void onAnyadirGrupoButtonClick(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/AnyadirGrupo.fxml"));
            this.PaginaGrupo.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(GrupoPaginaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Recupera los datos del usuario actual del {@code UsuarioHolder}.
     */
    public void recuperarDatos() {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();
        if (u == null) {
            System.err.println("El usuario es nulo.");
        }
    }
}
