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
 * Controlador para la vista de visualización de una tarea realizada por el usuario.
 */
public class VerTareaHechaController implements Initializable {
    @javafx.fxml.FXML
    private Button botonVolverLogin;
    @javafx.fxml.FXML
    private Label nombreTarea;
    @javafx.fxml.FXML
    private Label textoDescripción;
    @javafx.fxml.FXML
    private Label recompensa;

    Tarea tarea = new Tarea();
    TareaModel tm = new TareaModel();
    @javafx.fxml.FXML
    private AnchorPane VerTareasHechaAnchorPane;


    /**
     * Inicializa la vista y muestra los detalles de la tarea realizada por el usuario.
     *
     * @param url            La ubicación relativa del archivo FXML.
     * @param resourceBundle Los recursos utilizados para localizar el archivo FXML.
     */
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

    /**
     * Maneja el evento del botón para volver al inicio.
     *
     * @param actionEvent Evento de acción que desencadena el retorno al inicio.
     */
    @javafx.fxml.FXML
    public void botonVolverInicio(ActionEvent actionEvent) {

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/TareasRealizadasUsuario-view.fxml"));
            this.VerTareasHechaAnchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

}
