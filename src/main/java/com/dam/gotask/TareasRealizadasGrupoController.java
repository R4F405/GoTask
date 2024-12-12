/**
 * Controlador para la vista de tareas realizadas por un grupo.
 * Permite cargar y mostrar las tareas realizadas por el grupo en un GridPane.
 */
package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TareasRealizadasGrupoController implements Initializable {

    @javafx.fxml.FXML
    private ScrollPane TareasRealizadasGrupoScrollPane;
    @javafx.fxml.FXML
    private GridPane TareasRealizadasGrupoGridPane;

    GrupoUsuario gu = new GrupoUsuario();
    GrupoUsuarioModel gum = new GrupoUsuarioModel();
    Usuarios u = new Usuarios();
    GrupoModel gm = new GrupoModel();
    GruposUsuariosController guc;
    Tarea t = new Tarea();
    Grupo g = new Grupo();
    @javafx.fxml.FXML
    private AnchorPane TareasRealizadasGrupoAnchorPane;

    /**
     * Inicializa la vista de tareas realizadas por un grupo.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas de la raíz del objeto.
     * @param resourceBundle El recurso de configuración utilizado para localizar los objetos FXML.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Crea una instancia de MenuPrincipalController si es necesario
        MenuPrincipalController menuPrincipalController = new MenuPrincipalController();
        // Crea una instancia de GruposUsuariosController pasando la instancia de MenuPrincipalController
        guc = new GruposUsuariosController(menuPrincipalController);

        recuperarDatosGrupo();

        gm.getVerTareasRealizadasGrupo(g.getIdGrupo());
        // Inicializa la variable 'column' para controlar la posición de la columna en el GridPane.
        int column = 0;
        // Inicializa la variable 'row' para controlar la posición de la fila en el GridPane.
        int row = 1;

        for (Tarea t : gm.listaGTU){

            try {
                // Crea un nuevo FXMLLoader para cargar el archivo FXML 'TareasRealizadasGrupoBoton.fxml'.
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vistas-paraTodos/TareasRealizadasGrupoBoton.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                // Obtiene el controlador asociado con el archivo FXML cargado.
                TareasRealizadasGrupoBotonController prueba = fxmlLoader.getController();
                // Llama al método 'Insertar' del controlador para inicializarlo con los datos de la tarea actual.
                prueba.Insertar(t.getIdTarea(),t.getTitulo());
                prueba.setParentController(this);
                // Controla la disposición de los elementos en el GridPane.

                // Incrementa la fila para el próximo elemento.
                row++;
                // Añade el AnchorPane al GridPane en la posición especificada por 'column' y 'row'.
                TareasRealizadasGrupoGridPane.add(anchorPane, column, row); //(child,column,row)

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Recupera los datos del grupo seleccionado.
     */
    public void recuperarDatosGrupo(){
        GrupoHolder holder = GrupoHolder.getInstance();
        g = holder.getGrupo();

        if (g != null) {
            // Agregar aquí cualquier lógica adicional requerida al recuperar los datos del grupo.
        } else {
            System.err.println("El grupo es nulo.");
        }
    }
    public void cambiarContenidoAnchorPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas-paraTodos/verTareaGrupoRealizadas-view.fxml"));
            AnchorPane nuevaVista = fxmlLoader.load();
            TareasRealizadasGrupoAnchorPane.getChildren().setAll(nuevaVista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
