package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la vista de añadir grupo.
 * Implementa la interfaz {@link Initializable} para inicializar la vista.
 */
public class AnyadirGrupoController implements Initializable {

    /**
     * Campo de texto para ingresar el nombre del grupo.
     */
    @javafx.fxml.FXML
    private TextField NombreGrupoTextField;

    /**
     * Botón para crear el grupo.
     */
    @javafx.fxml.FXML
    private Button CrearGrupoButton;

    /**
     * Panel de anclaje donde se cargará la nueva vista.
     */
    @javafx.fxml.FXML
    private AnchorPane CrearGrupoPAne;

    private GrupoModel gm = new GrupoModel();
    private Grupo g = new Grupo();
    private Usuarios u = new Usuarios();
    private GrupoUsuario gu = new GrupoUsuario();
    private GrupoModel gum = new GrupoModel();
    private Boolean admin;

    /**
     * Inicializa el controlador de la vista.
     * Recupera los datos del usuario cuando se carga la vista.
     *
     * @param url el URL de la localización utilizada para resolver rutas relativas para el objeto raíz, o null si no se ha proporcionado.
     * @param resourceBundle los recursos utilizados para localizar el objeto raíz, o null si no se ha proporcionado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recuperarDatos();
    }

    /**
     * Maneja el evento de clic en el botón "Crear Grupo".
     * Obtiene el nombre del grupo del campo de texto, crea el grupo y añade el usuario como administrador.
     * Luego, carga la vista del grupo recién creado.
     *
     * @param actionEvent el evento que se produce cuando se hace clic en el botón.
     */
    @javafx.fxml.FXML
    public void onCrearGrupoButtonClick(ActionEvent actionEvent) {
        // Obtener el nombre del grupo del campo de texto
        String nombre = NombreGrupoTextField.getText();

        // Verificar si el nombre del grupo está vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nombre de Grupo Vacío");
            alert.showAndWait();
            return;

        }

        // Crear un nuevo grupo con el nombre proporcionado
        gm.getCrearGrupo(nombre);

        // Obtener el ID del grupo recién creado
        g = gm.getVerIdGrupo(nombre);

        // Asignar el usuario actual como administrador del nuevo grupo
        admin = true;
        gum.GetAnyadirUsuarioNuevoGrupo(g.getIdGrupo(), u.getId(), admin);

        // Cargar la vista del grupo recién creado
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/GrupoPagina.fxml"));
            this.CrearGrupoPAne.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AnyadirGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    /**
     * Recupera los datos del usuario y los asigna a la variable de instancia {@link Usuarios}.
     * Utiliza el patrón singleton para obtener la instancia actual del usuario.
     */
    public void recuperarDatos() {
        // Obtener la instancia del UsuarioHolder (patrón singleton)
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();

        // Verificar si el usuario es nulo y manejarlo apropiadamente
        if (u != null) {
            // Aquí puedes añadir más lógica para cuando el usuario no sea nulo.
        } else {
            System.err.println("El usuario es nulo.");
        }
    }
}
