/**
 * Controlador para la vista de tareas realizadas por un usuario.
 * Permite cargar y mostrar las tareas realizadas por el usuario en un GridPane.
 */
package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controlador para manejar la vista de las tareas realizadas por un usuario.
 */
public class TareasRealizadasUsuarioController implements Initializable {

    @javafx.fxml.FXML
    private ScrollPane TareasRealizadasUsuarioScrollPane;
    @javafx.fxml.FXML
    private GridPane TareasRealizadasUsuarioGridPane;
    @javafx.fxml.FXML
    private AnchorPane Menu;

    /** Instancia del usuario actual. */
    Usuarios u = new Usuarios();
    /** Modelo para operaciones relacionadas con grupos. */
    GrupoModel gm = new GrupoModel();
    /** Controlador para la vista de grupos y usuarios. */
    GruposUsuariosController guc;
    /** Instancia de la tarea actual. */
    Tarea t = new Tarea();
    /** Instancia del grupo actual. */
    Grupo g = new Grupo();
    /** Lista de tareas realizadas por el usuario. */
    ArrayList<Tarea> lt = new ArrayList<Tarea>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Crea una instancia de MenuPrincipalController si es necesario
        MenuPrincipalController menuPrincipalController = new MenuPrincipalController();
        // Crea una instancia de GruposUsuariosController pasando la instancia de MenuPrincipalController
        guc = new GruposUsuariosController(menuPrincipalController);

        recuperarDatosUsuario();
        recuperarDatosGrupo();


        lt = gm.getVerTareasRealizadasUsuario(g.getIdGrupo(),u.getId());
        // Inicializa la variable 'column' para controlar la posición de la columna en el GridPane.
        int column = 0;
        // Inicializa la variable 'row' para controlar la posición de la fila en el GridPane.
        int row = 1;

        for (Tarea t : lt){

            try {
                // Crea un nuevo FXMLLoader para cargar el archivo FXML 'gruposUsuarios.fxml'.
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vistas-paraTodos/TareasRealizadasUsuarioBoton.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                // Obtiene el controlador asociado con el archivo FXML cargado.
                TareasRealizadasUsuarioBotonController prueba = fxmlLoader.getController();
                // Llama al método 'getInsertar' del controlador para inicializarlo con los datos del grupo actual.
                prueba.Insertar(t.getIdTarea(),t.getTitulo());
                prueba.setParentController(this);
                // Controla la disposición de los elementos en el GridPane.

                // Incrementa la columna para el próximo elemento.
                row ++;
                // Añade el AnchorPane al GridPane en la posición especificada por 'column' y 'row'.
                TareasRealizadasUsuarioGridPane.add(anchorPane, column, row); //(child,column,row)




            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    /**
     * Recupera los datos del usuario actual.
     */
    public void recuperarDatosUsuario(){
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();

        if (u != null) { // Agregado aquí para evitar NullPointerException


        } else {
            System.err.println("El usuario es nulo.");
        }
    }

    /**
     * Recupera los datos del grupo actual.
     */

    public void recuperarDatosGrupo(){
        GrupoHolder holder = GrupoHolder.getInstance();
        g = holder.getGrupo();

        if (g != null) { // Agregado aquí para evitar NullPointerException


        } else {
            System.err.println("El usuario es nulo.");
        }
    }

    /**
     * Cambia a la vista del menú principal.
     *
     * @param event Evento de acción.
     */
    public void cambioMenu(ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas-paraTodos/MenuPrincipal2-view.fxml"));
            Parent nuevaEscenaRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Configurar la nueva escena en el stage
            Scene nuevaEscena = new Scene(nuevaEscenaRoot);
            stage.setScene(nuevaEscena);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cambia el contenido del panel principal a la vista de la tarea realizada seleccionada.
     */
    public void cambiarContenidoAnchorPaneTRealizadasUC() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas-paraTodos/verTareaHecha-view.fxml"));
            AnchorPane nuevaVista = fxmlLoader.load();
            Menu.getChildren().setAll(nuevaVista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
