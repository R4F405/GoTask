package com.dam.gotask;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para el cambio de contraseña.
 */
public class CambiarContraseñaFormato2Controller {

    @javafx.fxml.FXML
    private TextField anyadirEmailField;

    @javafx.fxml.FXML
    private Button botonVolverLogin;

    @javafx.fxml.FXML
    private PasswordField AnydirContraseñaField1;

    @javafx.fxml.FXML
    private Button botonCambiarContraseña;

    @javafx.fxml.FXML
    private PasswordField AnydirContraseñaField2;

    @javafx.fxml.FXML
    private CheckBox verContraseñaCheck1;

    @javafx.fxml.FXML
    private CheckBox verContraseñaCheck;

    @javafx.fxml.FXML
    private TextField contraseñaCambiadaField;

    @javafx.fxml.FXML
    private TextField mostrarContraseñaField2;

    @javafx.fxml.FXML
    private TextField mostrarContraseñaField1;

    @javafx.fxml.FXML
    private TextField CorreoNoExisteField;

    @javafx.fxml.FXML
    private TextField noRepetirContraseña;

    @javafx.fxml.FXML
    private TextField ContraseñaDiferenteField;

    @javafx.fxml.FXML
    private Button botonVolverLogin1;

    @javafx.fxml.FXML
    private AnchorPane panelTrasero;

    /**
     * Inicializa el controlador, configurando los campos de texto para mostrar la contraseña como invisibles.
     *
     * @param url              el URL de la localización utilizada para resolver rutas relativas para el objeto raíz, o null si no se ha proporcionado.
     * @param resourceBundle   los recursos utilizados para localizar el objeto raíz, o null si no se ha proporcionado.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarContraseñaField1.setVisible(false);
        mostrarContraseñaField2.setVisible(false);
    }

    /**
     * Maneja el evento de clic en el botón "Cambiar Contraseña".
     * Verifica si el correo existe, si las contraseñas coinciden y si no se repiten.
     * Luego, actualiza la contraseña en la base de datos.
     *
     * @param actionEvent el evento que se produce cuando se hace clic en el botón.
     */
    @javafx.fxml.FXML
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
     * Alterna la visibilidad de la contraseña en el segundo campo de contraseña.
     *
     * @param actionEvent el evento que se produce cuando se selecciona o deselecciona el CheckBox.
     */
    @javafx.fxml.FXML
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
     * Alterna la visibilidad de la contraseña en el primer campo de contraseña.
     *
     * @param actionEvent el evento que se produce cuando se selecciona o deselecciona el CheckBox.
     */
    @javafx.fxml.FXML
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
     * Maneja el evento de clic en el botón "Volver".
     * Carga la vista del perfil.
     *
     * @param actionEvent el evento que se produce cuando se hace clic en el botón.
     */
    @javafx.fxml.FXML
    public void volverAtras(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/Perfil.fxml"));
            this.panelTrasero.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipal2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
