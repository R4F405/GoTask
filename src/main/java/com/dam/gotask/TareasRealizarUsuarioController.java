package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controlador para manejar la vista de tareas por realizar de un usuario.
 */
public class TareasRealizarUsuarioController implements Initializable {


    @javafx.fxml.FXML
    private ScrollPane TareasScrollPane;
    @javafx.fxml.FXML
    private GridPane TareasUsuarioGridPane;

    Usuarios u = new Usuarios();
    GrupoModel gm = new GrupoModel();
    GruposUsuariosController guc;
    Tarea t = new Tarea();
    Grupo g = new Grupo();
    ArrayList<Tarea> lt = new ArrayList<Tarea>();
    @javafx.fxml.FXML
    private Button TareasUsuarioButton;
    @javafx.fxml.FXML
    private AnchorPane TareasPorHacerUsuarioAnchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Crea una instancia de MenuPrincipalController si es necesario
        MenuPrincipalController menuPrincipalController = new MenuPrincipalController();
        // Crea una instancia de GruposUsuariosController pasando la instancia de MenuPrincipalController
        guc = new GruposUsuariosController(menuPrincipalController);

        recuperarDatosUsuario();
        recuperarDatosGrupo();


        gm.getVerUsuariosTareas(g.getIdGrupo(),u.getId());
        // Inicializa la variable 'column' para controlar la posición de la columna en el GridPane.
        int column = 0;
        // Inicializa la variable 'row' para controlar la posición de la fila en el GridPane.
        int row = 1;

        for (Tarea t : gm.listaGTU){

            try {
                // Crea un nuevo FXMLLoader para cargar el archivo FXML 'gruposUsuarios.fxml'.
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vistas-paraTodos/TareaRealizarUsuarioBoton.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                // Obtiene el controlador asociado con el archivo FXML cargado.
                TareaRealizarUsuarioBotonController prueba = fxmlLoader.getController();
                // Llama al método 'getInsertar' del controlador para inicializarlo con los datos del grupo actual.
                prueba.Insertar(t.getIdTarea(),t.getTitulo());
                prueba.setParentController(this);

                // Incrementa la columna para el próximo elemento.
                row ++;
                // Añade el AnchorPane al GridPane en la posición especificada por 'column' y 'row'.
                TareasUsuarioGridPane.add(anchorPane, column, row); //(child,column,row)
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
     * Cambia el contenido del AnchorPane de tareas por hacer del usuario.
     */
    public void cambiarContenidoAnchorPaneTRUC() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas-paraTodos/verTarea-view.fxml"));
            AnchorPane nuevaVista = fxmlLoader.load();
            TareasPorHacerUsuarioAnchorPane.getChildren().setAll(nuevaVista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que maneja el evento de enviar datos de la tarea.
     *
     * @param actionEvent Evento de acción generado.
     */
    @javafx.fxml.FXML
    public void enviarDatosTarea(ActionEvent actionEvent) {
    }
}
