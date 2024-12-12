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

public class VerTareaGrupoUsuarioController implements Initializable {
    @javafx.fxml.FXML
    private Button botonVolverLogin;
    @javafx.fxml.FXML
    private Label nombreTarea;
    @javafx.fxml.FXML
    private Label textoDescripción;
    @javafx.fxml.FXML
    private AnchorPane Menu;
    @javafx.fxml.FXML
    private Label realizador;
    @javafx.fxml.FXML
    private Label recompensa;


    Usuarios u = new Usuarios();
    Tarea tarea = new Tarea();
    TareaModel tm = new TareaModel();

    @javafx.fxml.FXML
    public void botonVolverInicio(ActionEvent actionEvent) {

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/TareasRealizarGrupo-view.fxml"));
            this.Menu.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recuperarDatosTarea();
        recuperarDatosUsuario();



        Tarea t = tm.verTarea(tarea.getIdTarea());

        Usuarios u2 = tm.getNombreUsuarioTarea(tarea.getIdTarea());

        if (tarea != null) {
            nombreTarea.setText(t.getTitulo());
            textoDescripción.setText(t.getNota());
            recompensa.setText(String.valueOf(t.getRecompensa()));
            realizador.setText(u2.getNombre());
        } else {
            nombreTarea.setText("Tarea no encontrada");
            textoDescripción.setText("");
            recompensa.setText("");
        }

    }

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
