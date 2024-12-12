package com.dam.gotask;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la vista de inicio de sesión.
 */
public class IniciarSesionController implements Initializable {

    @FXML
    private Button botonIniciarSesion;
    @FXML
    private TextField anyadirEmailField;
    @FXML
    private ImageView logoImagen;
    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private CheckBox verContraseñaCheck;
    @FXML
    private TextField mostrarContraseñaField;
    @FXML
    private PasswordField AnydirContraseñaField;
    @FXML
    private Button olvidasteContraseñaBoton;
    @FXML
    private Button crearCuentaBoton;
    @FXML
    private ImageView verContraseñaImagen;
    @FXML
    private Label errorLogin;

    // Objeto para almacenar los datos del usuario
    Usuarios u = new Usuarios();

    /**
     * Método que se ejecuta al iniciar la vista.
     *
     * @param url            La ubicación del archivo FXML.
     * @param resourceBundle El ResourceBundle que se puede utilizar para localizar objetos de la interfaz de usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura el texto de error y lo oculta al iniciar la vista.
        errorLogin.setText("Contraseña o usuario incorrecto");
        errorLogin.setVisible(false);
    }

    /**
     * Método para alternar la visibilidad de la contraseña.
     *
     * @param event El evento que desencadena la acción.
     */
    @FXML
    protected void togglePasswordVisible(ActionEvent event) {
        // Verifica si la casilla de verificación está seleccionada
        if (verContraseñaCheck.isSelected()) {
            // Si está seleccionada, muestra el campo de texto y oculta el campo de contraseña
            mostrarContraseñaField.setText(AnydirContraseñaField.getText());
            mostrarContraseñaField.setVisible(true);
            AnydirContraseñaField.setVisible(false);
        } else {
            // Si no está seleccionada, muestra el campo de contraseña y oculta el campo de texto
            AnydirContraseñaField.setText(mostrarContraseñaField.getText());
            AnydirContraseñaField.setVisible(true);
            mostrarContraseñaField.setVisible(false);
        }
    }

    /**
     * Método para manejar el evento de hacer clic en el botón de inicio de sesión.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void botonIniciarSesion(ActionEvent actionEvent) {
        // Obtiene el correo electrónico y la contraseña ingresados por el usuario
        String email = anyadirEmailField.getText();
        String contraseña = AnydirContraseñaField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Objeto para interactuar con la base de datos de usuarios
        UsuariosModel um = new UsuariosModel();

        // Obtiene el usuario correspondiente al correo electrónico y la contraseña ingresados
        this.u = um.getUsuarioLogin(email, contraseña);
        // Verifica si se encontró un usuario correspondiente
        if (this.u == null) {
            // Si no se encontró un usuario, muestra un mensaje de error
            errorLogin.setVisible(true);
            // Configura un temporizador para ocultar el mensaje de error después de 5 segundos
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> errorLogin.setVisible(false));
            pause.play();
        } else {
            // Si se encontró un usuario, oculta el mensaje de error
            errorLogin.setVisible(false);
            // Envía los datos del usuario y carga la vista del menú principal
            enviarDatosUsuarios(actionEvent);
            try {
                // Cargar MenuPrincipal.fxml
                AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/MenuPrincipal.fxml"));
                this.panelTrasero.getChildren().setAll(pane);
            } catch (IOException e) {
                Logger.getLogger(IniciarSesionController.class.getName()).log(Level.SEVERE, "Error cargando archivo FXML", e);
            }
        }
    }

    /**
     * Método para manejar el evento de hacer clic en el botón de olvidar contraseña.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void OlvidasteContraseñaBoton(ActionEvent actionEvent) {
        // Cargar la vista para recuperar la contraseña
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/RecuperarContraseña-view.fxml"));
            this.panelTrasero.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para manejar el evento de hacer clic en el botón de crear cuenta.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void crearCuentaBoton(ActionEvent actionEvent) {
        // Cargar la vista para crear una nueva cuenta de usuario
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/CrearCuenta.fxml"));
            this.panelTrasero.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(IniciarSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para enviar los datos del usuario y cerrar la ventana actual.
     *
     * @param event El evento que desencadena la acción.
     */
    private void enviarDatosUsuarios(ActionEvent event) {
        // Obtiene los datos del usuario
        int id = u.getId();
        String nombre = u.getNombre();
        String email = u.getCorreo();
        String password = u.getPassword();
        // Crea un nuevo objeto de usuario con los datos obtenidos
        this.u = new Usuarios(id, nombre, email, password);

        try {

            // Paso 1: Obtener instancia de UsuarioHolder
            UsuarioHolder holder = UsuarioHolder.getInstance();
            // Paso 2: Establecer usuario
            holder.setUsuario(this.u);

        } catch (Exception e) {
            System.err.println(String.format("Error creando ventana: %s", e.getMessage()));
        }

    }
}
