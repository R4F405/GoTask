package com.dam.gotask;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador para la vista de añadir usuario a un grupo.
 * Implementa la interfaz {@link Initializable} para inicializar la vista.
 */
public class AnyadirUsuarioController extends DBUtil implements Initializable {

    /**
     * Botón para invitar a un usuario al grupo.
     */
    @FXML
    private Button invitarUsuarioButton;


    /**
     * Instancia del usuario actual.
     */
    private Usuarios u;

    /**
     * ComboBox para seleccionar el grupo al cual se invitará al usuario.
     */
    @FXML
    private ComboBox<String> comboBoxGrupos;

    /**
     * Panel de anclaje para la vista de añadir usuario.
     */
    @FXML
    private AnchorPane AnadirUsuarioPane;

    /**
     * Campo de texto para ingresar el nombre del usuario a invitar.
     */
    @FXML
    private TextField nombreTextField;

    /**
     * Constructor de la clase. Inicializa los modelos de datos.
     */
    public AnyadirUsuarioController() {
        u = new Usuarios();
    }

    /**
     * Inicializa el controlador de la vista.
     * Recupera los datos del usuario y los grupos administrados por él,
     * y los carga en el ComboBox.
     *
     * @param url el URL de la localización utilizada para resolver rutas relativas para el objeto raíz, o null si no se ha proporcionado.
     * @param resourceBundle los recursos utilizados para localizar el objeto raíz, o null si no se ha proporcionado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recuperarDatos();
        List<String> nombresGrupos = obtenerNombresGruposPorUsuario();
        comboBoxGrupos.getItems().addAll(nombresGrupos);

        // Opcionalmente, seleccionar el primer grupo por defecto
        if (!nombresGrupos.isEmpty()) {
            comboBoxGrupos.getSelectionModel().selectFirst();
        }
    }

    /**
     * Recupera los datos del usuario y los asigna a la variable de instancia {@link Usuarios}.
     * Utiliza el patrón singleton para obtener la instancia actual del usuario.
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
     * Obtiene los nombres de los grupos administrados por el usuario actual.
     *
     * @return una lista de nombres de grupos.
     */
    private List<String> obtenerNombresGruposPorUsuario() {
        List<String> nombresGrupos = new ArrayList<>();
        int idUsuario = u.getId();

        try (Connection connection = getConexion()) {
            String sql = "SELECT g.nombre_grupo FROM Grupos g " +
                    "INNER JOIN GrupoUsuario gu ON g.idgrupo = gu.idgrupo " +
                    "WHERE gu.id_usuario = ? AND gu.es_admin = true";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, idUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String nombreGrupo = rs.getString("nombre_grupo");
                        nombresGrupos.add(nombreGrupo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombresGrupos;
    }

    /**
     * Busca un usuario en la base de datos por su nombre.
     *
     * @param nombreUsuario el nombre del usuario a buscar.
     * @return una instancia de {@link Usuarios} si se encuentra el usuario, de lo contrario null.
     */
    private Usuarios buscarUsuarioPorNombre(String nombreUsuario) {
        Usuarios usuario = null;
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";

        try (Connection connection = getConexion();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuarios();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setEs_admin(rs.getBoolean("es_adminapp"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Agrega un usuario a un grupo en la base de datos.
     *
     * @param idGrupo   el ID del grupo.
     * @param idUsuario el ID del usuario a agregar.
     */
    public void agregarUsuarioAlGrupo(int idGrupo, int idUsuario) {

        try (Connection connection = getConexion()) {
            String sql = "INSERT INTO GrupoUsuario (idgrupo, id_usuario, es_admin) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, idGrupo);
                stmt.setInt(2, idUsuario);
                stmt.setBoolean(3, false);
                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    mostrarAlerta("Éxito", "Usuario añadido correctamente al grupo.");
                } else {
                    System.out.println("No se pudo agregar al usuario al grupo.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de clic en el botón "Invitar Usuario".
     * Busca al usuario por nombre y lo agrega al grupo seleccionado.
     *
     * @param event el evento que se produce cuando se hace clic en el botón.
     */
    @FXML
    public void invitarUsuario(Event event) {
        String nombreUsuario = nombreTextField.getText();
        String grupoSeleccionado = comboBoxGrupos.getValue();

        if (nombreUsuario.isEmpty() || grupoSeleccionado == null) {
            System.out.println("Por favor, complete todos los campos.");
            return;
        }

        // Obtener el ID del grupo seleccionado
        int idGrupo = obtenerIdGrupoPorNombreYUsuario(grupoSeleccionado);

        if (idGrupo != -1) {
            // Realizar la consulta para buscar al usuario por nombre
            Usuarios usuario = buscarUsuarioPorNombre(nombreUsuario);

            if (usuario != null) {
                // Si se encuentra el usuario, agregarlo al grupo
                agregarUsuarioAlGrupo(idGrupo, usuario.getId());
                System.out.println("El usuario ha sido agregado al grupo correctamente.");
            } else {
                System.out.println("No se encontró al usuario en la base de datos.");
            }
        } else {
            System.out.println("No se encontró el grupo en la base de datos.");
        }
    }

    /**
     * Obtiene el ID del grupo por su nombre y el ID del usuario.
     *
     * @param nombreGrupo el nombre del grupo.
     * @return el ID del grupo, o -1 si no se encuentra.
     */
    private int obtenerIdGrupoPorNombreYUsuario(String nombreGrupo) {
        int idGrupo = -1;
        int idUsuario = u.getId();

        try (Connection connection = getConexion()) {
            String sql = "SELECT gu.idgrupo FROM GrupoUsuario gu JOIN grupos ON gu.idgrupo = grupos.idgrupo WHERE nombre_grupo = ? AND id_usuario = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, nombreGrupo);
                stmt.setInt(2, idUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idGrupo = rs.getInt("idgrupo");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGrupo;
    }

    /**
     * Muestra una alerta informativa al usuario.
     *
     * @param titulo   el título de la alerta.
     * @param mensaje el mensaje de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
