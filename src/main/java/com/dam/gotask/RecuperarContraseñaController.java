
package com.dam.gotask;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Controlador para la vista de recuperación de contraseña.
 * Permite al usuario restablecer su contraseña proporcionando su correo electrónico y una nueva contraseña.
 * Se incluyen funcionalidades para verificar la validez del correo electrónico, la coincidencia de contraseñas
 * y la repetición de contraseñas previamente utilizadas.
 * Además, se gestionan las acciones de visibilidad de contraseñas y el cambio de vista después de un restablecimiento
 * exitoso de la contraseña.
 */
public class RecuperarContraseñaController implements Initializable {

    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private CheckBox verContraseñaCheck1;
    @FXML
    private TextField anyadirEmailField;
    @FXML
    private PasswordField AnydirContraseñaField1;
    @FXML
    private ImageView logoImagen;
    @FXML
    private CheckBox verContraseñaCheck;
    @FXML
    private Button botonCambiarContraseña;
    @FXML
    private TextField contraseñaCambiadaField;
    @FXML
    private TextField ContraseñaDiferenteField;
    @FXML
    private TextField mostrarContraseñaField2;
    @FXML
    private TextField mostrarContraseñaField1;
    @FXML
    private PasswordField AnydirContraseñaField2;
    @FXML
    private TextField CorreoNoExisteField;
    @FXML
    private TextField noRepetirContraseña;
    @FXML
    private Button botonVolverLogin;

    /**
     * Inicializa la vista de recuperación de contraseña.
     * Configura la visibilidad de los campos de contraseña para ocultarlos inicialmente.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas de la raíz del objeto.
     * @param resourceBundle El recurso de configuración utilizado para localizar los objetos FXML.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarContraseñaField1.setVisible(false);
        mostrarContraseñaField2.setVisible(false);
    }

    /**
     * Cambia la visibilidad de la contraseña ingresada.
     *
     * @param actionEvent Evento de acción que activa la visibilidad de la contraseña.
     */
    @FXML
    public void togglePasswordVisible(ActionEvent actionEvent) {
        if (verContraseñaCheck.isSelected()) {
            mostrarContraseñaField1.setText(AnydirContraseñaField1.getText());
            mostrarContraseñaField1.setVisible(true);
            AnydirContraseñaField1.setVisible(false);
        } else {
            AnydirContraseñaField1.setText(mostrarContraseñaField1.getText());
            AnydirContraseñaField1.setVisible(true);
            mostrarContraseñaField1.setVisible(false);
        }
    }

    /**
     * Cambia la visibilidad de la segunda contraseña ingresada.
     *
     * @param actionEvent Evento de acción que activa la visibilidad de la segunda contraseña.
     */
    @FXML
    public void togglePasswordVisible2(ActionEvent actionEvent) {
        if (verContraseñaCheck1.isSelected()) {
            mostrarContraseñaField2.setText(AnydirContraseñaField2.getText());
            mostrarContraseñaField2.setVisible(true);
            AnydirContraseñaField2.setVisible(false);
        } else {
            AnydirContraseñaField2.setText(mostrarContraseñaField2.getText());
            AnydirContraseñaField2.setVisible(true);
            mostrarContraseñaField2.setVisible(false);
        }
    }

    /**
     * Realiza el proceso de cambio de contraseña.
     * Verifica la existencia del correo electrónico, la coincidencia de contraseñas y la repetición de contraseñas
     * previamente utilizadas antes de actualizar la contraseña en el sistema.
     *
     * @param actionEvent Evento de acción que activa el proceso de cambio de contraseña.
     */
    @FXML
    public void botonCambiarContraseña(ActionEvent actionEvent) {
        boolean correoExiste = false;
        boolean contraseñaRepetida = false;
        UsuariosModel um = new UsuariosModel();
        ArrayList<Usuarios> lu = um.getUsuarios();
        String email = anyadirEmailField.getText();
        String contraseña1 = AnydirContraseñaField1.getText();
        String contraseña2 = AnydirContraseñaField2.getText();

        contraseñaRepetida = um.verificarContraseñaRepetida(contraseña1);

        for (Usuarios usuarios : lu) {
            if (email.equals(usuarios.getCorreo())) {
                correoExiste = true;
                break;
            }
        }

        if (correoExiste) {
            if (contraseña1.equals(contraseña2)) {
                if (!contraseñaRepetida) {
                    try {
                        int exitoUpdate = um.updateCambiarContraseña(email, contraseña1);

                        if (exitoUpdate == 1) {
                            contraseñaCambiadaField.setVisible(true);
                            PauseTransition pause = new PauseTransition(Duration.seconds(2));
                            pause.setOnFinished(e -> {
                                contraseñaCambiadaField.setVisible(false);
                                try {
                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/IniciarSesion-view.fxml"));
                                    this.panelTrasero.getChildren().setAll(pane);
                                } catch (IOException ex) {
                                    Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            pause.play();
                        }
                    } catch (Exception e) {
                        ContraseñaDiferenteField.setText("La nueva contraseña no puede ser igual a alguna de las últimas tres contraseñas usadas.");
                        ContraseñaDiferenteField.setVisible(true);
                        PauseTransition pause = new PauseTransition(Duration.seconds(5));
                        pause.setOnFinished(ev -> ContraseñaDiferenteField.setVisible(false));
                        pause.play();
                    }
                } else {
                    noRepetirContraseña.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(5));
                    pause.setOnFinished(ev -> noRepetirContraseña.setVisible(false));
                    pause.play();
                }
            } else {
                ContraseñaDiferenteField.setText("Las contraseñas no coinciden.");
                ContraseñaDiferenteField.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(e -> ContraseñaDiferenteField.setVisible(false));
                pause.play();
            }
        } else {
            CorreoNoExisteField.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> CorreoNoExisteField.setVisible(false));
            pause.play();
        }
    }
    /**
     *
     * Tras pulsar el boton se vuelve a la vista iniciar sesion.
     *
     * @param actionEvent Evento de acción que activa el proceso de volver a inicio.
     */
    @FXML
    public void botonVolverInicio(ActionEvent actionEvent) {

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/IniciarSesion-view.fxml"));
            this.panelTrasero.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
