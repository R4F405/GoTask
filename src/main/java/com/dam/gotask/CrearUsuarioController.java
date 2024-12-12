package com.dam.gotask;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Controlador para la creación de usuarios en la aplicación.
 */
public class CrearUsuarioController {

    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private CheckBox verContraseñaCheck1;
    @FXML
    private TextField añadirNombreField;
    @FXML
    private TextField anyadirEmailField;
    @FXML
    private TextField mostrarContraseñaField2;
    @FXML
    private TextField mostrarContraseñaField1;
    @FXML
    private PasswordField AnydirContraseñaField1;
    @FXML
    private TextField correoNombreExistentesField;
    @FXML
    private ImageView logoImagen;
    @FXML
    private TextField ContraseñaDiferenteField;
    @FXML
    private CheckBox verContraseñaCheck;
    @FXML
    private PasswordField AnydirContraseñaField2;
    @FXML
    private Button botonCrearUsuario;
    @FXML
    private TextField usuarioCreado;

    /**
     * Método invocado al hacer clic en el botón para mostrar o ocultar la contraseña.
     *
     * @param actionEvent Evento que desencadena la acción.
     */
    @FXML
    public void togglePasswordVisible(ActionEvent actionEvent) {
        if (verContraseñaCheck.isSelected()) {
            mostrarContraseñaField1.setText(AnydirContraseñaField1.getText());
            AnydirContraseñaField1.setVisible(false);
            mostrarContraseñaField1.setVisible(true);
        } else {
            AnydirContraseñaField1.setText(mostrarContraseñaField1.getText());
            AnydirContraseñaField1.setVisible(true);
            mostrarContraseñaField1.setVisible(false);
        }
    }

    /**
     * Método invocado al hacer clic en el botón para mostrar o ocultar la contraseña.
     *
     * @param actionEvent Evento que desencadena la acción.
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
     * Método invocado al hacer clic en el botón para crear un usuario nuevo.
     *
     * @param actionEvent Evento que desencadena la acción.
     */
    @FXML
    public void botonCrearUsuario(ActionEvent actionEvent) {
        String nombre = añadirNombreField.getText();
        String correo = anyadirEmailField.getText();
        String contraseña1 = AnydirContraseñaField1.getText();
        String contraseña2 = AnydirContraseñaField2.getText();
        String fotoPorDefecto = "Imagenes/ImagenesUsuario/EURO.png";

        if (!esCorreoValido(correo)) {
            correoNombreExistentesField.setText("Correo electrónico no válido");
            correoNombreExistentesField.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> correoNombreExistentesField.setVisible(false));
            pause.play();
            return;
        }

        UsuariosModel um = new UsuariosModel();
        ArrayList<Usuarios> lu = um.getUsuarios();

        boolean existeUsuario = false;

        for (Usuarios u : lu) {
            if (u.getNombre().equals(nombre) || u.getCorreo().equals(correo)) {
                existeUsuario = true;
                break;
            }
        }

        if (existeUsuario) {
            correoNombreExistentesField.setText("Nombre de usuario o correo ya existe");
            correoNombreExistentesField.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> correoNombreExistentesField.setVisible(false));
            pause.play();
        } else {
            if (contraseña1.equals(contraseña2)) {
                int exitoAñadir = um.setAñadirUsuarios(nombre, correo, contraseña1, fotoPorDefecto);
                if (exitoAñadir == 1) {
                    usuarioCreado.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        usuarioCreado.setVisible(false);
                        try {
                            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/IniciarSesion-view.fxml"));
                            this.panelTrasero.getChildren().setAll(pane);
                        } catch (IOException ex) {
                            Logger.getLogger(CrearUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    pause.play();
                }
            } else {
                ContraseñaDiferenteField.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(e -> ContraseñaDiferenteField.setVisible(false));
                pause.play();
            }
        }
    }

    /**
     * Verifica si una dirección de correo electrónico es válida.
     *
     * @param email Dirección de correo electrónico a verificar.
     * @return true si el correo es válido, false de lo contrario.
     */
    private boolean esCorreoValido(String email) {
        String regex = "^[\\w-\\.]+@[\\w-]+\\.com$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
}
