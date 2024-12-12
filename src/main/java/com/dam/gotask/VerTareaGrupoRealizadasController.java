package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la vista de visualización de una tarea realizada en un grupo.
 */
public class VerTareaGrupoRealizadasController implements Initializable {

    @javafx.fxml.FXML
    private Button botonVolverLogin;
    @javafx.fxml.FXML
    private Label nombreTarea;
    @javafx.fxml.FXML
    private Label textoDescripción;
    @javafx.fxml.FXML
    private Label realizador;
    @javafx.fxml.FXML
    private Label recompensa;
    @javafx.fxml.FXML
    private AnchorPane Menu;

    Usuarios u = new Usuarios();
    Tarea tarea = new Tarea();
    TareaModel tm = new TareaModel();

    /**
     * Maneja el evento del botón para volver al inicio.
     *
     * @param actionEvent Evento de acción que desencadena el retorno al inicio.
     */
    @javafx.fxml.FXML
    public void botonVolverInicio(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/TareasRealizadasGrupo-view.fxml"));
            this.Menu.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inicializa la vista y muestra los detalles de la tarea realizada en el grupo.
     *
     * @param url            La ubicación relativa del archivo FXML.
     * @param resourceBundle Los recursos utilizados para localizar el archivo FXML.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recuperarDatosTarea();
        recuperarDatosUsuario();



        Tarea t = tm.verTarea(tarea.getIdTarea());

        Usuarios u2 = tm.getNombreUsuarioTarea(tarea.getIdTarea());

        if (tarea != null) {
            nombreTarea.setText(t.getTitulo());
            textoDescripción.setText(t.getNota());
            //recompensa.setText(t.getRecompensa());
            realizador.setText(u2.getNombre());
        } else {
            nombreTarea.setText("Tarea no encontrada");
            textoDescripción.setText("");
            recompensa.setText("");
        }

        String textoRecompensa = "Recompensa IGOIhoigihoìHEORÌGHOIèhrrgìohoiHGOIHDAFJVBoùdehboigdafbgphdAÌOGHO`DFBDfoig`hODI`FHGBOÌADFBOIOHOI`HGOIHAODPIHGFOHAOFDGHOIHADFIOGHHOIAHDOIFHGOkdjhfgikaidhfbupibadifgbipiuahdfobuadifgbiupadfhbpuadbfipguhadf`hiudafbgoàdfhbbpiuadbfgòuadfo`gbapudifgy0aehgpiguaudgàdof`gha`dg`gao";
        recompensa.setText(textoRecompensa);
    }

    /**
     * Recupera los datos de la tarea seleccionada.
     */
    public void recuperarDatosTarea() {
        TareaHolder holder = TareaHolder.getInstance();
        tarea = holder.getTarea();
        if (tarea != null) {
            // Any logic needed here
        } else {
            System.err.println("El grupo es nulo.");
        }
    }

    /**
     * Recupera los datos del usuario.
     */
    public void recuperarDatosUsuario(){
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();

        if (u != null) { // Agregado aquí para evitar NullPointerException


        } else {
            System.err.println("El usuario es nulo.");
        }
    }
}
