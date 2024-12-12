package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la segunda versión del menú principal.
 */
public class MenuPrincipal2Controller implements Initializable {
    @javafx.fxml.FXML
    private Pane menuPrincipalPane2;
    @javafx.fxml.FXML
    private Pane MenuPane;
    @javafx.fxml.FXML
    private Button SalirBtn;
    @javafx.fxml.FXML
    private ChoiceBox filtros;
    @javafx.fxml.FXML
    private Button filtrarBtn;
    @javafx.fxml.FXML
    private Button verUsuarios;
    @javafx.fxml.FXML
    private AnchorPane MenuPrincipal2AnchorPane;

    // Opciones para el ChoiceBox de filtros
    private static final String val1 = "Tareas por realizar del grupo";
    private static final String val2 = "Tareas Realizadas del grupo";
    private static final String val3 = "Ver mis tareas sin realizar";
    private static final String val4 = "Ver mis tareas realizadas";

    GrupoUsuario gu = new GrupoUsuario();

    /**
     * Método para manejar el evento de hacer clic en el botón Salir.
     * @param actionEvent El evento que desencadena la acción.
     */
    @javafx.fxml.FXML
    public void Salir(ActionEvent actionEvent) {
        try {
            // Cargar la vista del menú principal original
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/MenuPrincipal.fxml"));
            this.MenuPrincipal2AnchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que se ejecuta al iniciar la vista.
     * @param url La ubicación del archivo FXML.
     * @param resourceBundle El ResourceBundle que se puede utilizar para localizar objetos de la interfaz de usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Agregar las opciones al ChoiceBox de filtros
        filtros.getItems().addAll(val1, val2, val3, val4);

//    AQUI VA EL IF----------------------------------------------------------------

        try {
            // Cargar la vista predeterminada al iniciar la ventana
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/TareasRealizarUsuario-view.fxml"));
            this.MenuPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipal2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para manejar el evento de hacer clic en el botón filtrar.
     * @param actionEvent El evento que desencadena la acción.
     */
    @javafx.fxml.FXML
    public void filtarar(ActionEvent actionEvent) {
        recuperarDatosGU();
        String valor = filtros.getValue().toString();
        System.out.println(valor);

        // Realizar acciones según la opción seleccionada en el ChoiceBox
        switch (valor) {
            case val1:
                if (this.gu.isEs_Admin()) {

                    // Cargar la vista de tareas por realizar del grupo
                    cargarVista("Vistas-admin/TareasRealizarGrupo-view.fxml");

                } else {

                    cargarVista("Vistas-paraTodos/TareasRealizarGrupo-view.fxml");


                }


                break;
            case val2:
                // Cargar la vista de tareas realizadas del grupo
                cargarVista("Vistas-paraTodos/TareasRealizadasGrupo-view.fxml");
                break;
            case val3:
                // Cargar la vista de tareas por realizar del usuario
                cargarVista("Vistas-paraTodos/TareasRealizarUsuario-view.fxml");
                break;
            case val4:
                // Cargar la vista de tareas realizadas del usuario
                cargarVista("Vistas-paraTodos/TareasRealizadasUsuario-view.fxml");
                break;
            default:
                Logger.getLogger(MenuPrincipal2Controller.class.getName()).log(Level.WARNING, "Filtro desconocido: " + valor);
                break;
        }
    }

    /**
     * Método para cargar una vista en el panel principal.
     * @param vista La ubicación de la vista FXML.
     */
    private void cargarVista(String vista) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(vista));
            this.MenuPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Deprecated
    public void OnVerUsuariosButtonClick(ActionEvent actionEvent) {

        recuperarDatosGU();

        if (this.gu.isEs_Admin()==true) {

            try {
                // Cargar la vista predeterminada al iniciar la ventana
                AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-admin/Usuario-vistaAdmin.fxml"));
                this.MenuPane.getChildren().setAll(pane);
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else {

            try {
                // Cargar la vista predeterminada al iniciar la ventana
                AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/Usuario-vista.fxml"));
                this.MenuPane.getChildren().setAll(pane);
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void recuperarDatosGU() {
        GrupoUsuarioHolder holder = GrupoUsuarioHolder.getInstance();
        gu = holder.getGrupoUsuario();
        if (gu == null) {
            System.err.println("El usuario es nulo.");
        }
    }



}
