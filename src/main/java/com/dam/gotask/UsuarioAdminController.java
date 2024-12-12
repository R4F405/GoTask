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
 * Controlador para la administración de usuarios.
 */
public class UsuarioAdminController extends DBUtil implements Initializable{


    @javafx.fxml.FXML
    private AnchorPane VerUsuarioAnchorPane;
    @javafx.fxml.FXML
    private GridPane VerUsuariosGridPane;

    Grupo g = new Grupo();
    GrupoModel gm = new GrupoModel();
    @javafx.fxml.FXML
    private Button botonVolverLogin;

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
     * Maneja el evento de clic en el botón "Eliminar usuarios".
     *
     * @param event Evento de acción generado.
     */
    @Deprecated
    public void onEliminarUsuariosButtonClick(ActionEvent event) {
        // Obtener el ID del grupo actual
        int idGrupo = g.getIdGrupo();
        this.g = new Grupo(idGrupo);

        // Mostrar una alerta para que el usuario ingrese el nombre del usuario a eliminar
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Eliminar Usuario");
        dialog.setHeaderText(null);
        dialog.setContentText("Escribe el nombre del usuario que quieres eliminar:");
        Optional<String> result = dialog.showAndWait();

        // Si el usuario ingresó un nombre y confirmó la acción
        result.ifPresent(username -> {
            try (Connection connection = getConexion()) {
                // Buscar el ID del usuario en base a su nombre
                String sqlSelectUserId = "SELECT id_usuario FROM usuarios WHERE nombre = ?";
                try (PreparedStatement statementSelectUserId = connection.prepareStatement(sqlSelectUserId)) {
                    statementSelectUserId.setString(1, username);
                    ResultSet resultSet = statementSelectUserId.executeQuery();

                    if (resultSet.next()) {
                        int idUsuario = resultSet.getInt("id_usuario");

                        // Eliminar al usuario del grupo
                        String sqlDeleteUserFromGroup = "DELETE FROM GrupoUsuario WHERE idgrupo = ? AND id_usuario = ?";
                        try (PreparedStatement statementDeleteUserFromGroup = connection.prepareStatement(sqlDeleteUserFromGroup)) {
                            statementDeleteUserFromGroup.setInt(1, idGrupo);
                            statementDeleteUserFromGroup.setInt(2, idUsuario);
                            int rowsAffected = statementDeleteUserFromGroup.executeUpdate();
                            if (rowsAffected > 0) {
                                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente del grupo.");
                            } else {
                                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró al usuario en el grupo.");
                            }
                        }
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró al usuario.");
                    }
                }
            } catch (SQLException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al eliminar al usuario del grupo.");
                e.printStackTrace();
            }
        });
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

    /**
     * Maneja el evento de clic en el botón "Añadir usuarios".
     *
     * @param actionEvent Evento de acción generado.
     */
    @Deprecated
    public void onAñadirUsuariosButtonClick(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-admin/AnyadirUsuario.fxml"));
            this.VerUsuarioAnchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @javafx.fxml.FXML
    public void botonVolverInicio(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/TareasRealizarUsuario-view.fxml"));
            this.VerUsuarioAnchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
