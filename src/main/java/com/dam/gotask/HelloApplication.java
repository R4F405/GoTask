package com.dam.gotask;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 */
public class HelloApplication extends Application {

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param stage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Crea un FXMLLoader para cargar el archivo FXML de la vista de inicio de sesión.
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Vistas-paraTodos/IniciarSesion-view.fxml"));

        // Carga la escena desde el archivo FXML.
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);

        // Establece el título de la ventana principal.
        stage.setTitle("GoTask");

        // Establece la escena en el escenario principal y muestra la ventana.
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación).
     */
    public static void main(String[] args) {
        // Llama al método launch() para iniciar la aplicación JavaFX.
        launch();
    }
}
