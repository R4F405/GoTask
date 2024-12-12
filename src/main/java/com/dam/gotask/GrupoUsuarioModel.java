package com.dam.gotask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Clase que maneja las operaciones relacionadas con la obtención de la información
 * de un usuario en un grupo específico desde la base de datos.
 */
public class GrupoUsuarioModel extends DBUtil{


    /**
     * Recupera la información de un usuario en un grupo específico desde la base de datos.
     *
     * @param idusuario ID del usuario del que se desea recuperar la información.
     * @param idGrupo ID del grupo al que pertenece el usuario.
     * @return Objeto GrupoUsuario que contiene la información del usuario en el grupo.
     */
    public GrupoUsuario getGrupoUsuario (int idusuario, int idGrupo) {

        GrupoUsuario gu =new GrupoUsuario();

        try {
            String sql = "Select es_admin from grupousuario where id_usuario = ? and idgrupo=?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1, idusuario);
            ps.setInt(2, idGrupo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {


                boolean esAdmin = rs.getBoolean("es_admin");

                 gu = new GrupoUsuario(idusuario,idGrupo,esAdmin);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cerrarConexion();
        }

        return gu;

    }

}
