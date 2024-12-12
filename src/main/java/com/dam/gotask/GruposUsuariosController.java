package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Controlador para la vista de grupos de usuarios.
 */
public class GruposUsuariosController {
    @FXML
    private Button GrupoBotton;
    @FXML
    private AnchorPane botonAnchorPane;
    Usuarios u = new Usuarios();
    GrupoUsuarioModel gum = new GrupoUsuarioModel();
    private GrupoUsuario gu = new GrupoUsuario();
    private int idGrupo;
    private String NombreGrupo;
    private MenuPrincipalController mpc = new MenuPrincipalController();
    Grupo g = new Grupo();

    /**
     * Constructor vacío de la clase GruposUsuariosController.
     */
    public GruposUsuariosController() {
    }

    /**
     * Constructor de la clase GruposUsuariosController que recibe un MenuPrincipalController.
     *
     * @param mpc El controlador de menú principal asociado.
     */
    public GruposUsuariosController(MenuPrincipalController mpc) {
        this.mpc = mpc;
    }

    /**
     * Método de inicialización de FXML.
     */
    @FXML
    public void initialize() {
    }

    /**
     * Inserta un grupo en la vista.
     *
     * @param idGrupo  ID del grupo.
     * @param nombre   Nombre del grupo.
     */
    public void Insertar(int idGrupo, String nombre) {
        this.idGrupo = idGrupo;
        this.NombreGrupo = nombre;
        GrupoBotton.setText(NombreGrupo);
        GrupoBotton.setPrefWidth(300);
        GrupoBotton.setPrefHeight(88);
        GrupoBotton.setFont(Font.font("Regular", 40));
    }

    /**
     * Maneja el evento de clic en el botón de grupo.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    public void onGrupoBottonClick(ActionEvent actionEvent) {
        recuperarDatosUsuario();
        gu = gum.getGrupoUsuario(this.u.getId(),this.idGrupo );

        enviarDatosGU(actionEvent);

        g.setIdGrupo(this.idGrupo);

        enviarDatosGrupo(actionEvent);
        if (mpc != null) {
            mpc.cambioMenu(actionEvent);
        } else {
            System.out.println("MenuPrincipalController es nulo. No se puede llamar a cambioMenu.");
        }
    }

    /**
     * Envía los datos del grupo seleccionado.
     *
     * @param event El evento de acción.
     */
    private void enviarDatosGrupo(ActionEvent event) {
        int id = g.getIdGrupo();
        this.g = new Grupo(id);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        // Paso 1: Obtener instancia de GrupoHolder
        GrupoHolder holder = GrupoHolder.getInstance();
        // Paso 2: Establecer grupo
        holder.setGrupo(this.g);
    }

    public void enviarDatosGU(ActionEvent event) {

        int idGrupo = gu.getIdGrupo();
        int idUsuario = gu.getIdUsuario();
        boolean admin = gu.isEs_Admin();

        this.gu = new GrupoUsuario(idGrupo,idUsuario,admin);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        // Paso 1: Obtener instancia de UsuarioHolder
        GrupoUsuarioHolder holder = GrupoUsuarioHolder.getInstance();
        // Paso 2: Establecer usuario
        holder.setGrupoUsuario(this.gu);

    }
    public void recuperarDatosUsuario(){
        UsuarioHolder holder = UsuarioHolder.getInstance();
        u = holder.getUsuario();

        if (u != null) { // Agregado aquí para evitar NullPointerException


        } else {
            System.err.println("El usuario es nulo.");
        }
    }
}
