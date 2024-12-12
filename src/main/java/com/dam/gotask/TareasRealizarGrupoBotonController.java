package com.dam.gotask;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Controlador para manejar la interacción con el botón de tareas por realizar en un grupo.
 */
public class TareasRealizarGrupoBotonController {
    @javafx.fxml.FXML
    private Button TPorRealizarGButton;

    int idTarea;
    String NombreTarea;
    private TareasRealizarGrupoUsuarioController parentController2= new TareasRealizarGrupoUsuarioController();
    private TareasRealizarGrupoController parentController= new TareasRealizarGrupoController();
    Tarea t = new Tarea();
    GrupoUsuario gu = new GrupoUsuario();


    /**
     * Establece el controlador principal de la vista de tareas por realizar en un grupo.
     *
     * @param parentController Controlador principal de la vista de tareas por realizar en un grupo.
     */
    public void setParentController(TareasRealizarGrupoController parentController) {
        this.parentController = parentController;
    }

    /**
     * Establece el controlador principal de la vista de tareas por realizar en un grupo (para usuarios).
     *
     * @param parentController2 Controlador principal de la vista de tareas por realizar en un grupo (para usuarios).
     */
    public void setParentController(TareasRealizarGrupoUsuarioController parentController2) {
        this.parentController2 = parentController2;
    }

    /**
     * Inserta la información de la tarea en el botón.
     *
     * @param idTarea ID de la tarea.
     * @param nombre Nombre de la tarea.
     */
    public void Insertar(int idTarea, String nombre){
        // Asigna el valor de idGrupo al atributo id_Grupo de la clase.
        this.idTarea=idTarea;
        // Asigna el valor de nombre al atributo NombreGrupo de la clase.
        this.NombreTarea=nombre;
        // Configura las propiedades del botón 'GrupoBotton' con el nombre del grupo.
        TPorRealizarGButton.setText(NombreTarea);
        TPorRealizarGButton.setPrefWidth(1070);
        TPorRealizarGButton.setPrefHeight(88);
    }

    /**
     * Maneja el evento de clic en el botón de tareas por realizar en un grupo.
     *
     * @param actionEvent Evento de acción generado por el clic en el botón.
     */
    @javafx.fxml.FXML
    public void onTPorRealizarGButtonClick(ActionEvent actionEvent) {
        t.setIdTarea(this.idTarea);
        enviarDatosTarea(actionEvent);
        recuperarDatosGU();
        if (gu.isEs_Admin()==true){
            if (parentController != null) {
                parentController.cambiarContenidoAnchorPane();
            }
        }else{
            if (parentController != null) {
                parentController2.cambiarContenidoAnchorPane();
            }
        }


    }

    /**
     * Envía los datos de la tarea al controlador principal.
     *
     * @param event Evento de acción.
     */
    private void enviarDatosTarea(ActionEvent event) {

        int id = t.getIdTarea();

        this.t = new Tarea(id);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();



        // Paso 1: Obtener instancia de UsuarioHolder
        TareaHolder holder = TareaHolder.getInstance();
        // Paso 2: Establecer usuario
        holder.setTarea(this.t);

    }

    /**
     * Recupera los datos del usuario de grupo.
     */
    public void recuperarDatosGU() {
        GrupoUsuarioHolder holder = GrupoUsuarioHolder.getInstance();
        gu = holder.getGrupoUsuario();
        if (gu == null) {
            System.err.println("El usuario es nulo.");
        }
    }
}
