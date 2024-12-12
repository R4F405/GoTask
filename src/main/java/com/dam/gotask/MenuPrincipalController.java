package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPrincipalController extends DBUtil implements Initializable {

    @FXML
    private AnchorPane Menu;
    @FXML
    private Pane MenuPane;
    @FXML
    private Pane menuPrincipalPane;
    @FXML
    private ImageView fotoPerfilImageView;
    @FXML
    private Circle circulo;
    @FXML
    private Button cerrarSesion;

    Usuarios u = new Usuarios();
    @FXML
    private Button salirAGrupos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            recuperarDatos();
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/GrupoPagina.fxml"));
            this.MenuPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

       Image img = new Image(getClass().getResource("/Imagenes/ImagenesUsuario/PERFIL.png").toString());
       circulo.setFill(new ImagePattern(img));
      //  circulo.setFill(u.getImagen());
        cargarImagenUsuario();

    }

    private void recuperarDatos() {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();

        if (u != null) {
            System.out.println("Nombre: " + u.getNombre());
            System.out.println("Correo: " + u.getCorreo());
            System.out.println("Contraseña: " + u.getPassword());
            System.out.println("ID: " + u.getId());
        } else {
            System.err.println("El usuario es nulo.");
        }
    }

    private void cargarImagenUsuario() {
        // Conexión a la base de datos
        try (Connection connection = getConexion()) {
            String sql = "SELECT imagen_Perfil FROM usuarios WHERE id_usuario = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int userId = u.getId();
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();

                // Procesar el resultado
                if (resultSet.next()) {
                    byte[] imageData = resultSet.getBytes("imagen_Perfil");
                    if (imageData != null && imageData.length > 0) {
                        try (InputStream inputStream = new ByteArrayInputStream(imageData)) {
                            Image image = new Image(inputStream);
                            // Verificar si la imagen se ha cargado correctamente
                            if (image.isError()) {
                                System.err.println("Error al cargar la imagen: " + image.getException());
                            } else {
                                // Ajustar la imagen dentro del círculo
                                circulo.setFill(new ImagePattern(image));
                                System.out.println("Imagen de perfil cargada correctamente.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.err.println("Error al convertir los datos de la imagen.");
                        }
                    } else {
                        System.out.println("La columna imagen_Perfil está vacía para el usuario con ID: " + userId);
                    }
                } else {
                    System.out.println("No se encontró ningún usuario con ID: " + userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al acceder a la base de datos.");
        }
    }
    @FXML
    public void irPerfil(Event event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/Perfil.fxml"));
            this.MenuPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void cambioMenu(ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas-paraTodos/MenuPrincipal2-view.fxml"));
            Parent nuevaEscenaRoot = loader.load();

            Stage stage = new Stage(); // Crear una nueva instancia de Stage

            // Configurar la nueva escena en el nuevo stage
            Scene nuevaEscena = new Scene(nuevaEscenaRoot);
            stage.setScene(nuevaEscena);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    @FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/IniciarSesion-view.fxml"));
            this.Menu.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void salirGrupos(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/GrupoPagina.fxml"));
            this.MenuPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambioPerfil(){

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/Perfil.fxml"));
            this.MenuPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}