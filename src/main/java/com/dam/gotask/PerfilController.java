package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerfilController extends DBUtil implements Initializable {


    @javafx.fxml.FXML
    private Text actualizarImagen;
    private Button eliminarCuentaButton;



    Usuarios u = new Usuarios();
    @javafx.fxml.FXML
    private Label nombreLabel;
    @javafx.fxml.FXML
    private Label correoLabel;
    @javafx.fxml.FXML
    private Label contrasenyaLabel;
    @javafx.fxml.FXML
    private Label mostrarContraseñaLabel;
    @javafx.fxml.FXML
    private ImageView lapizEditarNombre;
    @javafx.fxml.FXML
    private ImageView lapizEditarCorreo;
    @javafx.fxml.FXML
    private ImageView lapizEditarContra;
    @javafx.fxml.FXML
    private Button elimCuenta;
    @javafx.fxml.FXML
    private Circle circulo;
    @FXML
    private AnchorPane panelPerfil;
    @FXML
    private Label dineroLabel;
    @FXML
    private ImageView imagenEuro;
    @FXML
    private ImageView imagenDelPerfil;

    MenuPrincipalController mpc = new MenuPrincipalController();
    /**
     * Inicializa la vista del perfil de usuario con la información del usuario actual
     * y el saldo total de recompensas obtenidas.
     *
     * @param url             La ubicación relativa del archivo FXML.
     * @param resourceBundle El ResourceBundle que se puede pasar a la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recuperarDatos();
        Image img = new Image(getClass().getResource("/Imagenes/ImagenesUsuario/PERFIL.png").toString());
        circulo.setFill(new ImagePattern(img));

        cargarImagenUsuario();

        nombreLabel.setText(u.getNombre());
        correoLabel.setText(u.getCorreo());
        contrasenyaLabel.setText("********");
        mostrarContraseñaLabel.setText(u.getPassword());

        // Inicialmente ocultar el campo de texto de contraseña visible
        mostrarContraseñaLabel.setVisible(false);

        TareaModel t = new TareaModel();

        double recompensasTotales = t.getRecompensasTotales(u.getId());

        dineroLabel.setText(String.valueOf(recompensasTotales));


    }

    /**
     * Recupera los datos del usuario actual del objeto UsuarioHolder y los asigna a la variable 'u'.
     * Imprime los datos del usuario en la consola.
     */
    private void recuperarDatos() {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();

        if (u != null) {
            System.out.println("Nombre: " + u.getNombre());
            System.out.println("Correo: " + u.getCorreo());
            System.out.println("Contraseña: " + u.getPassword());
        } else {
            System.err.println("El usuario es nulo.");
        }
    }



    /**
     * Maneja el evento de clic para actualizar la imagen de perfil del usuario.
     *
     * @param event El evento de clic del mouse.
     */
    @FXML
    private void actualizarImagenClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de perfil");

        // Filtros para solo permitir imágenes
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File imagenSeleccionada = fileChooser.showOpenDialog(null);

        if (imagenSeleccionada != null) {
            Image imagenPerfil = new Image(imagenSeleccionada.toURI().toString());

            // Verificar que la imagen no sea nula
            if (imagenPerfil != null && !imagenPerfil.isError()) {
                imagenDelPerfil.setImage(imagenPerfil);

                try (FileInputStream fis = new FileInputStream(imagenSeleccionada)) {
                    Connection conn = this.getConexion();
                    if (conn != null) {
                        // Asumiendo que tienes una columna id_usuario para identificar al usuario
                        int userId = u.getId(); // Obtén el ID del usuario actual

                        String sql = "UPDATE usuarios SET imagen_Perfil = ? WHERE id_usuario = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setBinaryStream(1, fis, (int) imagenSeleccionada.length());
                        ps.setInt(2, userId);
                        ps.executeUpdate();
                        ps.close();
                        conn.close();

                        // Establecer la imagen en el Circle
                        circulo.setFill(new ImagePattern(imagenPerfil));
                        try {
                            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/Perfil.fxml"));
                            this.panelPerfil.getChildren().setAll(pane);
                        } catch (IOException ex) {
                            Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("aviso");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Se actualizarán todos los datos al refrescar pantalla");
                        successAlert.showAndWait();

                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Error al cargar la imagen seleccionada.");
            }
        } else {
            System.err.println("No se seleccionó ninguna imagen.");
        }

    }

    private void cargarImagenUsuario() {
        // Conexión a la base de datos
        try (Connection connection = getConexion()) {
            System.out.println("Conexión a la base de datos establecida.");

            String sql = "SELECT imagen_Perfil FROM usuarios WHERE id_usuario = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int userId = u.getId();
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();

                // Procesar el resultado
                if (resultSet.next()) {
                    byte[] imageData = resultSet.getBytes("imagen_Perfil");
                    if (imageData != null) {
                        System.out.println("Datos de imagen obtenidos de la base de datos para el usuario con ID: " + userId);
                        try (InputStream inputStream = new ByteArrayInputStream(imageData)) {
                            Image image = new Image(inputStream);
                            // Ajustar la imagen dentro del círculo
                            if (image != null && !image.isError()) {
                                circulo.setFill(new ImagePattern(image));
                                System.out.println("Imagen de perfil cargada y establecida en el círculo correctamente.");
                            } else {
                                System.err.println("Error: la imagen es nula o contiene errores.");
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

    /**
     * Guarda la imagen codificada en bytes en la base de datos.
     *
     * @param imagenBytes Los bytes de la imagen a guardar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    private void guardarImagenEnBD(byte[] imagenBytes) throws SQLException {
        // Establecer conexión con la base de datos
        Connection C = getConexion();

        // Crear la consulta SQL para actualizar la imagen en la base de datos
        String sql = "UPDATE usuarios SET imagen_Perfil = ? ";

        // Crear una sentencia preparada con la consulta SQL
        try (PreparedStatement stmt = C.prepareStatement(sql)) {
            // Establecer los parámetros de la sentencia preparada
            stmt.setBytes(1, imagenBytes);

            // Ejecutar la consulta SQL
            stmt.executeUpdate();
        } finally {
            // Manejo de recursos
        }
    }



    /**
     * Actualiza el nombre del usuario en la base de datos.
     *
     * @param nuevoNombre El nuevo nombre del usuario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    private void actualizarNombreEnBD(String nuevoNombre) throws SQLException {
        // Establecer conexión con la base de datos
        Connection C = getConexion();

        // Crear la consulta SQL para actualizar el nombre del usuario
        String sql = "UPDATE usuarios SET nombre = ? WHERE id_usuario = ?";

        // Crear una sentencia preparada con la consulta SQL
        try (PreparedStatement stmt = C.prepareStatement(sql)) {
            // Establecer los parámetros de la sentencia preparada
            stmt.setString(1, nuevoNombre);
            // Obtener el ID del usuario actual
            int idUsuario = obtenerIdUsuarioActual();
            stmt.setInt(2, idUsuario);

            // Ejecutar la consulta SQL
            stmt.executeUpdate();

            // Mostrar un mensaje de éxito si la actualización fue exitosa
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Éxito");
            successAlert.setHeaderText(null);
            successAlert.setContentText("El nombre se ha actualizado correctamente en la base de datos.");
            successAlert.showAndWait();
        } finally {
            // Cerrar la conexión a la base de datos, si es necesario
        }
    }

    /**
     * Obtiene el ID del usuario actual.
     *
     * @return El ID del usuario actual, o -1 si no se puede obtener.
     */
    private int obtenerIdUsuarioActual() {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        Usuarios u = holder.getUsuario();
        return u != null ? u.getId() : -1;
    }

    /**
     * Maneja el evento de eliminación de cuenta del usuario.
     * Muestra una alerta de confirmación y elimina la cuenta si se confirma.
     */
    @FXML
    private void eliminarCuenta() {
        // Crear un VBox para contener el texto y el checkbox
        VBox vbox = new VBox();
        CheckBox confirmarCheckBox = new CheckBox("Confirmar eliminación de la cuenta");
        vbox.getChildren().addAll(confirmarCheckBox);

        // Crear la alerta
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Cuenta");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar la cuenta?");
        alert.getDialogPane().setContent(vbox);

        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Verificar si el usuario hizo clic en Aceptar y marcó el checkbox
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (confirmarCheckBox.isSelected()) {
                    // Eliminar la cuenta si el usuario confirma y marca el checkbox
                    eliminarCuentaDeBD();
                    redirigirAInicioSesion();
                } else {
                    // Mostrar una alerta de error si el usuario no marca el checkbox
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No has confirmado la eliminación de tu cuenta.");
                    errorAlert.showAndWait();
                }
            }
        });
    }



    /**
     * Elimina la cuenta del usuario de la base de datos.
     * Muestra una alerta de éxito si la cuenta se elimina correctamente.
     * En caso de error, muestra una alerta de error.
     */
    private void eliminarCuentaDeBD() {
        try (Connection connection = getConexion()) {
            String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                int idUsuario = obtenerIdUsuarioActual();
                stmt.setInt(1, idUsuario);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Éxito");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La cuenta ha sido eliminada correctamente.");
                    successAlert.showAndWait();

                    redirigirAInicioSesion(); // Redirigir a la pantalla de inicio de sesión
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No se encontró la cuenta del usuario.");
                    errorAlert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Ocurrió un error al intentar eliminar la cuenta.");
            errorAlert.showAndWait();
        }
    }

    /**
     * Redirige a la pantalla de inicio de sesión.
     * Carga el archivo FXML correspondiente y muestra la escena en una nueva ventana.
     */
    private void redirigirAInicioSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IniciarSesion.fxml"));
            Parent root = loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show(); // Mostrar la ventana de inicio de sesión y esperar a que se cierre
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de edición del nombre del usuario.
     * Muestra un cuadro de diálogo para que el usuario ingrese el nuevo nombre.
     * Actualiza el nombre en la base de datos y en la interfaz de usuario.
     *
     * @param event El evento que desencadena la acción.
     */
    @FXML
    public void editarNombre(Event event) {
        // Crear un cuadro de diálogo de entrada de texto
        TextInputDialog dialog = new TextInputDialog(nombreLabel.getText());
        dialog.setTitle("Editar Nombre");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingrese su nuevo nombre:");

        // Mostrar el cuadro de diálogo y esperar a que el usuario ingrese el nuevo nombre
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nuevoNombre -> {
            try {
                // Actualizar el nombre en la base de datos
                actualizarNombreEnBD(nuevoNombre);
                // Actualizar el nombre en la interfaz de usuario
                nombreLabel.setText(nuevoNombre);
            } catch (SQLException e) {
                e.printStackTrace();
                // Mostrar un mensaje de error si hay un problema con la actualización en la base de datos
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("No se pudo actualizar el nombre en la base de datos.");
                errorAlert.showAndWait();
            }
        });
    }

    /**
     * Maneja el evento de edición del correo electrónico del usuario.
     * Muestra un cuadro de diálogo para que el usuario ingrese el nuevo correo electrónico.
     * Actualiza el correo en la base de datos y en la interfaz de usuario si el nuevo correo es válido.
     *
     * @param event El evento que desencadena la acción.
     */
    @FXML
    public void editarCorreo(Event event) {
        // Crear un cuadro de diálogo de entrada de texto
        TextInputDialog dialog = new TextInputDialog(correoLabel.getText());
        dialog.setTitle("Editar Correo");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingrese su nuevo correo:");

        // Mostrar el cuadro de diálogo y esperar a que el usuario ingrese el nuevo correo
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nuevoCorreo -> {
            try {
                // Validar el formato del correo electrónico
                if (esCorreoValido(nuevoCorreo)) {
                    // Actualizar el correo en la base de datos
                    actualizarCorreoEnBD(nuevoCorreo);
                    // Actualizar el correo en la interfaz de usuario
                    correoLabel.setText(nuevoCorreo);
                } else {
                    // Mostrar un mensaje de error si el formato del correo no es válido
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("El correo electrónico ingresado no es válido.");
                    errorAlert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Mostrar un mensaje de error si hay un problema con la actualización en la base de datos
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("No se pudo actualizar el correo en la base de datos.");
                errorAlert.showAndWait();
            }
        });
    }


    /**
     * Actualiza el correo electrónico del usuario en la base de datos.
     * Muestra una alerta de éxito si la actualización se realiza correctamente.
     *
     * @param nuevoCorreo El nuevo correo electrónico del usuario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    private void actualizarCorreoEnBD(String nuevoCorreo) throws SQLException {
        // Establecer conexión con la base de datos
        Connection C = getConexion();

        // Crear la consulta SQL para actualizar el correo del usuario
        String sql = "UPDATE usuarios SET correo = ? WHERE id_usuario = ?";

        // Crear una sentencia preparada con la consulta SQL
        try (PreparedStatement stmt = C.prepareStatement(sql)) {
            // Establecer los parámetros de la sentencia preparada
            stmt.setString(1, nuevoCorreo);
            // Aquí debes obtener el ID del usuario actual, puede ser de la sesión o cualquier otro método que utilices
            int idUsuario = obtenerIdUsuarioActual();
            stmt.setInt(2, idUsuario);

            // Ejecutar la consulta SQL
            stmt.executeUpdate();

            // Mostrar un mensaje de éxito si la actualización fue exitosa
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Éxito");
            successAlert.setHeaderText(null);
            successAlert.setContentText("El correo se ha actualizado correctamente en la base de datos.");
            successAlert.showAndWait();
        } finally {
            // Cerrar la conexión a la base de datos, si es necesario
        }
    }

    /**
     * Valida el formato de un correo electrónico utilizando una expresión regular.
     *
     * @param correo El correo electrónico a validar.
     * @return true si el formato del correo es válido, false de lo contrario.
     */
    private boolean esCorreoValido(String correo) {
        // Validar el formato del correo electrónico utilizando una expresión regular simple
        return correo.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Cambia a la pestaña de grupos en la interfaz.
     *
     * @param actionEvent El evento que desencadena la acción.
     * @throws IOException Si ocurre un error al cargar la interfaz de usuario.
     */
    @Deprecated
    public void volverAPestañaGrupos(ActionEvent actionEvent) throws IOException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/GrupoPagina.fxml"));
            this.panelPerfil.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método marcado como obsoleto. Se recomienda utilizar {@link #cambiarContra(Event)} en su lugar.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @Deprecated
    public void CambiarEscenaContra(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/RecuperarContraseña-view.fxml"));
            this.panelPerfil.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Cambia a la pantalla de cambio de contraseña.
     *
     * @param event El evento que desencadena la acción.
     */
    @FXML
    public void cambiarContra(Event event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Vistas-paraTodos/cambiarContraseñaFormato2.fxml"));
            this.panelPerfil.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
