/**
 * Controlador para la gestión de usuarios.
 */
package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la visualización y gestión de usuarios.
 */
public class UsuarioController extends DBUtil implements Initializable{


    @javafx.fxml.FXML
    private AnchorPane VerUsuarioAnchorPane;
    @javafx.fxml.FXML
    private GridPane VerUsuariosGridPane;
    @javafx.fxml.FXML
    private Button EliminarUsuariosButton;

    Grupo g = new Grupo();
    GrupoModel gm = new GrupoModel();
    @javafx.fxml.FXML
    private Button AñadirUsuariosButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recuperarDatosGrupo();

        gm.getVerUsuariosGrupo(g.getIdGrupo());

        // Inicializa la variable 'column' para controlar la posición de la columna en el GridPane.
        int column = 0;
        // Inicializa la variable 'row' para controlar la posición de la fila en el GridPane.
        int row = 1;

        for (Usuarios u : gm.listaUG){

            try {
                // Crea un nuevo FXMLLoader para cargar el archivo FXML 'gruposUsuarios.fxml'.
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vistas-admin/VerUsuarios.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                // Obtiene el controlador asociado con el archivo FXML cargado.
                VerUsuariosController prueba = fxmlLoader.getController();
                // Llama al método 'getInsertar' del controlador para inicializarlo con los datos del grupo actual.
                prueba.insertar(u.getId(),u.getNombre());

                // Controla la disposición de los elementos en el GridPane.

                // Incrementa la columna para el próximo elemento.
                row ++;
                // Añade el AnchorPane al GridPane en la posición especificada por 'column' y 'row'.
                VerUsuariosGridPane.add(anchorPane, column, row); //(child,column,row)

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
     * Muestra una alerta en la interfaz gráfica.
     *
     * @param tipo Tipo de alerta.
     * @param titulo Título de la alerta.
     * @param mensaje Mensaje de la alerta.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}
