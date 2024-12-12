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

public class VerTareaController implements Initializable {
    @javafx.fxml.FXML
    private Label nombreTarea;
    @javafx.fxml.FXML
    private Label textoDescripción;
    @javafx.fxml.FXML
    private Label recompensa;

    @javafx.fxml.FXML
    private Button botonVolverLogin;
    @javafx.fxml.FXML
    private Button tareaRealizadaBtn;
    @javafx.fxml.FXML
    private AnchorPane Menu;

    Tarea tarea = new Tarea();
    TareaModel tm = new TareaModel();
    //a
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recuperarDatosTarea();

        Tarea t = tm.verTarea(tarea.getIdTarea());

        if (tarea != null) {
            nombreTarea.setText(t.getTitulo());
            textoDescripción.setText(t.getNota());
            recompensa.setText("Recompensa: " + t.getRecompensa());
        } else {
            nombreTarea.setText("Tarea no encontrada");
            textoDescripción.setText("");
            recompensa.setText("");
        }

        String textoRecompensa = "";
        recompensa.setText(textoRecompensa);
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

    @javafx.fxml.FXML
    public void finalizarTareaBtn(ActionEvent actionEvent) {

        tm.finalizarTarea(tarea.getIdTarea());

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/TareasRealizarUsuario-view.fxml"));
            this.Menu.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @javafx.fxml.FXML
    public void botonVolverInicio(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/TareasRealizarUsuario-view.fxml"));
            this.Menu.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

