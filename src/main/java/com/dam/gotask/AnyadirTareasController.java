package com.dam.gotask;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Controlador para la vista de añadir tareas.
 * Implementa la interfaz {@link Initializable} para inicializar la vista.
 */
public class AnyadirTareasController extends DBUtil implements Initializable {

    /**
     * Botón para añadir la tarea.
     */
    @javafx.fxml.FXML
    private Button anyadirBtn;

    /**
     * Checkbox para indicar si se desea añadir una recompensa a la tarea.
     */
    @javafx.fxml.FXML
    private CheckBox anyadirRecompensaCh;

    /**
     * Área de texto para ingresar notas del usuario sobre la tarea.
     */
    @javafx.fxml.FXML
    private TextArea notaUser;

    /**
     * Campo de texto para ingresar el título de la tarea.
     */
    @javafx.fxml.FXML
    private TextField TituloTi;

    /**
     * Panel de anclaje para la vista de añadir tareas.
     */
    @javafx.fxml.FXML
    private AnchorPane PanelAnyadirTareas;

    /**
     * Campo de texto para ingresar la recompensa de la tarea.
     */
    @javafx.fxml.FXML
    private TextField recompensa;

    /**
     * Maneja el evento de clic en el botón "Añadir Tarea".
     * Obtiene los valores de los campos de texto y, según si se ha seleccionado la opción de recompensa, añade la tarea con o sin recompensa.
     * Luego muestra una alerta indicando el estado de la operación.
     *
     * @param actionEvent el evento que se produce cuando se hace clic en el botón.
     */

    Usuarios u = new Usuarios();
    Grupo g = new Grupo();
    @javafx.fxml.FXML
    private ChoiceBox usuariosChoiceBox;
    @javafx.fxml.FXML
    private Button botonVolverLogin;

    /**
     * Método para manejar el evento de añadir una tarea.
     * Se encarga de obtener los datos de la tarea desde la interfaz de usuario,
     * validarlos, agregar la tarea a la base de datos y asignarla al usuario seleccionado.
     *
     * @param actionEvent Evento que desencadena la acción de añadir tarea.
     */

    public void anyadirTarea(ActionEvent actionEvent) {
        // Obtener datos de la tarea desde la interfaz de usuario
        String nota;
        String titulo;
        String recomp;

        nota = notaUser.getText();
        titulo = TituloTi.getText();
        recomp = recompensa.getText();

        // Verificar si el título está vacío
        if (titulo == null || titulo.trim().isEmpty()) {
            // Mostrar alerta si el título está vacío
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nombre de Tarea Vacía");
            alert.showAndWait();
            return;
        }

        // Obtener el usuario seleccionado del ChoiceBox
        Usuarios usuarioSeleccionado = (Usuarios) usuariosChoiceBox.getValue();
        if (usuarioSeleccionado == null) {
            // Mostrar alerta si no se ha seleccionado un usuario
            showAlert(Alert.AlertType.ERROR, "Usuario no seleccionado", "Debe seleccionar un usuario para asignar la tarea.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Verificar si se ha seleccionado añadir recompensa
        if (anyadirRecompensaCh.isSelected()) {
            // Verificar si la recompensa ingresada es un número válido
            if (isValidNumber(recomp)) {
                // Agregar la tarea con recompensa
                TareaModel TM = new TareaModel();
                TM.anyadirTarea1(titulo, nota, recomp);
            } else {
                // Mostrar alerta si la recompensa no es un número válido
                showAlert(Alert.AlertType.ERROR, "Valor Inválido", "Por favor, ingrese un número válido para la recompensa.");
                return;
            }
        } else {
            // Agregar la tarea sin recompensa
            TareaModel TM = new TareaModel();
            TM.anyadirTarea2(titulo, nota);
        }

        // Recuperar datos de usuario y grupo
        recuperarDatosUsuario();
        recuperarDatosGrupo();
        TareaModel T = new TareaModel();
        // Obtener el ID de la tarea recién agregada
        int idTarea = T.getVerIdTarea(titulo);
        try {
            // Asignar la tarea al usuario seleccionado en el grupo actual
            T.asignarTarea(g.getIdGrupo(), usuarioSeleccionado.getId(), idTarea,false);
        } catch (SQLException e) {
            // Manejar excepción de SQL
            throw new RuntimeException(e);
        }

        // Mostrar mensaje de éxito
        alert.setTitle("Info");
        alert.setHeaderText("Estado");
        alert.setContentText("Tarea guardada con éxito");
        alert.showAndWait();

        // Transición para cargar otra vista después de un breve retraso
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            try {
                // Cargar otra vista después del retraso
                AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-admin/TareasRealizarGrupo-view.fxml"));
                this.PanelAnyadirTareas.getChildren().setAll(pane);
            } catch (IOException ex) {
                // Manejar excepción de IO
                Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pause.play();
    }

    /**
     * Método para obtener una lista de usuarios que pertenecen al grupo actual.
     * Realiza una consulta a la base de datos para obtener la información.
     *
     * @return Lista de usuarios del grupo actual.
     */
    public List<Usuarios> obtenerUsuariosDelGrupo() {
        recuperarDatosGrupo();
        List<Usuarios> usuarios = new ArrayList<>();
        // Consulta SQL para obtener usuarios del grupo actual
        String sql = "SELECT u.id_usuario, u.nombre FROM usuarios u INNER JOIN grupousuario gu ON u.id_usuario = gu.id_usuario WHERE gu.idgrupo = ?";
        try (Connection connection = getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, g.getIdGrupo());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Obtener datos de usuario de la consulta
                    int idUsuario = resultSet.getInt("id_usuario");
                    String nombre = resultSet.getString("nombre");
                    // Crear objeto Usuario y añadirlo a la lista
                    Usuarios usuario = new Usuarios(idUsuario, nombre);
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            // Manejar excepción de SQL
            e.printStackTrace();
            // Manejo de excepciones según tu caso
        }
        // Retornar la lista de usuarios
        return usuarios;
    }
    /**
     * Inicializa el controlador de la vista.
     * Establece la visibilidad del campo de texto de la recompensa como falsa.
     *
     * @param url el URL de la localización utilizada para resolver rutas relativas para el objeto raíz, o null si no se ha proporcionado.
     * @param resourceBundle los recursos utilizados para localizar el objeto raíz, o null si no se ha proporcionado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Ocultar el campo de texto de la recompensa inicialmente
        recompensa.setVisible(false);

        Pattern validNumberPattern = Pattern.compile("-?\\d*(\\.\\d*)?");
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (validNumberPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });

        recompensa.setTextFormatter(textFormatter);
        cargarUsuariosEnChoiceBox();
    }

    /**
     * Maneja el evento de cambio en el estado del checkbox "Añadir Recompensa".
     * Muestra u oculta el campo de texto de la recompensa según el estado del checkbox.
     *
     * @param actionEvent el evento que se produce cuando se cambia el estado del checkbox.
     */
    @javafx.fxml.FXML
    public void anyadirRecompesa(ActionEvent actionEvent) {
        // Comprobar el estado del checkbox y ajustar la visibilidad del campo de recompensa
        if (anyadirRecompensaCh.isSelected()) {
            recompensa.setVisible(true);
        } else {
            recompensa.setVisible(false);
        }
    }
    public void recuperarDatosUsuario(){
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();

        if (u == null) {
            System.err.println("El usuario es nulo.");
        }
    }
    public void recuperarDatosGrupo(){
        GrupoHolder holder = GrupoHolder.getInstance();
        g = holder.getGrupo();

        if (g != null) {
            // Agregar aquí cualquier lógica adicional requerida al recuperar los datos del grupo.
        } else {
            System.err.println("El grupo es nulo.");
        }
    }

    private void cargarUsuariosEnChoiceBox() {
        if (g != null) {
            List<Usuarios> usuarios = obtenerUsuariosDelGrupo();
            if (usuarios != null) {
                usuariosChoiceBox.setItems(FXCollections.observableArrayList(usuarios));
            }
        } else {
            System.err.println("El grupo es nulo.");
}
}
    private boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void botonVolverInicio(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-admin/TareasRealizarGrupo-view.fxml"));
            this.PanelAnyadirTareas.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
