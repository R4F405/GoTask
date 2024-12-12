package com.dam.gotask;

import javafx.scene.control.Label;

/**
 * Controlador para la vista de visualización de usuarios.
 */
public class VerUsuariosController {
    @javafx.fxml.FXML
    private Label UsuarioLabel;

    private int idUsuario;
    private String NombreUsuario;

    /**
     * Inserta los datos del usuario en la vista.
     *
     * @param IU El ID del usuario.
     * @param NU El nombre del usuario.
     */
    public void insertar(int IU, String NU){
        idUsuario=IU;
        NombreUsuario=NU;
        UsuarioLabel.setText(NombreUsuario);
    }

}
