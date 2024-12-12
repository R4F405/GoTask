package com.dam.gotask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que maneja las operaciones relacionadas con los grupos en la base de datos.
 */
public class GrupoModel extends DBUtil {

    /** Lista de grupos recuperados de la base de datos. */
    ArrayList<Grupo> listaGrupo = new ArrayList<Grupo>();

    /** Lista de tareas asignadas a usuarios de un grupo específico. */
    ArrayList<Tarea> listaGTU = new ArrayList<>();

    /** Lista de usuarios pertenecientes a un grupo dado. */
    ArrayList<Usuarios> listaUG = new ArrayList<>();


    /**
     * Recupera los grupos asociados a un usuario específico.
     *
     * @param id ID del usuario para el que se recuperan los grupos.
     * @return Lista de grupos asociados al usuario.
     */
    public ArrayList<Grupo> getVerGrupos(int id) {

        // Código para recuperar grupos de la base de datos
        try {
            String sql = "select g.idgrupo,g.nombre_grupo from grupos g inner join grupousuario gu on gu.idgrupo=g.idgrupo where gu.id_usuario=?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int idgrupo = rs.getInt("idgrupo");
                String nombre = rs.getString("nombre_grupo");

                Grupo g = new Grupo(idgrupo, nombre);

                listaGrupo.add(g);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }

        return listaGrupo;
    }


    /**
     * Crea un nuevo grupo con el nombre especificado.
     *
     * @param nombre Nombre del nuevo grupo.
     */
    public void getCrearGrupo(String nombre) {

        try {

            String sql = "insert into grupos (nombre_grupo) values (?)";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);

            ps.setString(1, nombre);

            ps.execute();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }


    }


    /**
     * Recupera el ID de un grupo dado su nombre.
     *
     * @param nombre Nombre del grupo del que se quiere recuperar el ID.
     * @return Objeto Grupo con el ID y nombre del grupo.
     */
    public Grupo getVerIdGrupo(String nombre) {
        Grupo g = new Grupo();
        try {

            String sql = "select idgrupo,nombre_grupo from grupos where nombre_grupo = ?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);

            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idgrupo = rs.getInt("idgrupo");
                String nombreGrupo = rs.getString("nombre_grupo");
                g = new Grupo(idgrupo, nombreGrupo);
            }
            return g;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }
        return g;
    }


    /**
     * Recupera las tareas asignadas a un usuario en un grupo específico.
     *
     * @param idGrupo ID del grupo del que se recuperan las tareas.
     * @param idUsuario ID del usuario para el que se recuperan las tareas.
     * @return Lista de tareas asignadas al usuario en el grupo.
     */
    public ArrayList<Tarea> getVerUsuariosTareas(int idGrupo, int idUsuario) {

        try {
            String sql = "select t.id_tarea,t.nombre_tarea,t.descripcion,t.recompensa from tareas t inner join tareas_asignadas ta on t.id_tarea = ta.id_tareas where ta.id_grupo=? and ta.id_usuario=? and tarea_realizada=0";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                int idTareas = rs.getInt("id_tarea");
                String nombreTarea = rs.getString("nombre_tarea");
                String Descripcion = rs.getString("descripcion");
                int recompensa = rs.getInt("recompensa");
                Tarea t = new Tarea(idTareas, nombreTarea, Descripcion, recompensa);

                listaGTU.add(t);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }


        return listaGTU;
    }


    /**
     * Recupera las tareas realizadas por todos los usuarios en un grupo específico.
     *
     * @param idGrupo ID del grupo del que se recuperan las tareas realizadas.
     * @return Lista de tareas realizadas por los usuarios en el grupo.
     */
    public ArrayList<Tarea> getVerTareasRealizadasGrupo(int idGrupo) {

        try {
            String sql = "select t.id_tarea,t.nombre_tarea,t.descripcion,t.recompensa from tareas t inner join tareas_asignadas ta on t.id_tarea = ta.id_tareas where ta.id_grupo=? and tarea_realizada=1";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1, idGrupo);


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                int idTareas = rs.getInt("id_tarea");
                String nombreTarea = rs.getString("nombre_tarea");
                String Descripcion = rs.getString("descripcion");
                int recompensa = rs.getInt("recompensa");
                Tarea t = new Tarea(idTareas, nombreTarea, Descripcion, recompensa);

                listaGTU.add(t);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }


        return listaGTU;
    }


    /**
     * Recupera las tareas realizadas por un usuario en un grupo específico.
     *
     * @param idGrupo ID del grupo del que se recuperan las tareas realizadas por el usuario.
     * @param idUsuario ID del usuario para el que se recuperan las tareas realizadas.
     * @return Lista de tareas realizadas por el usuario en el grupo.
     */
    public ArrayList<Tarea> getVerTareasRealizadasUsuario(int idGrupo, int idUsuario) {

        try {
            String sql = "select t.id_tarea,t.nombre_tarea,t.descripcion,t.recompensa from tareas t inner join tareas_asignadas ta on t.id_tarea = ta.id_tareas where ta.id_grupo=? and ta.id_usuario=? and tarea_realizada=1";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                int idTareas = rs.getInt("id_tarea");
                String nombreTarea = rs.getString("nombre_tarea");
                String Descripcion = rs.getString("descripcion");
                int recompensa = rs.getInt("recompensa");
                Tarea t = new Tarea(idTareas, nombreTarea, Descripcion, recompensa);

                listaGTU.add(t);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }


        return listaGTU;
    }


    /**
     * Recupera las tareas por realizar en un grupo específico.
     *
     * @param idGrupo ID del grupo del que se recuperan las tareas por realizar.
     * @return Lista de tareas por realizar en el grupo.
     */
    public ArrayList<Tarea> getVerTareasPorRealizarGrupo(int idGrupo) {

        try {
            String sql = "select t.id_tarea,t.nombre_tarea,t.descripcion,t.recompensa from tareas t inner join tareas_asignadas ta on t.id_tarea = ta.id_tareas where ta.id_grupo=? and ta.tarea_realizada=0";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1, idGrupo);



            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                int idTareas = rs.getInt("id_tarea");
                String nombreTarea = rs.getString("nombre_tarea");
                String Descripcion = rs.getString("descripcion");
                int recompensa = rs.getInt("recompensa");
                Tarea t = new Tarea(idTareas, nombreTarea, Descripcion, recompensa);

                listaGTU.add(t);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }
        return listaGTU;

    }


    /**
     * Añade un nuevo usuario a un grupo especificado.
     *
     * @param idGrupo ID del grupo al que se añade el usuario.
     * @param idUsuario ID del usuario que se añade al grupo.
     * @param es_Admin Booleano que indica si el usuario es administrador del grupo.
     */
    public void GetAnyadirUsuarioNuevoGrupo(int idGrupo, int idUsuario, boolean es_Admin){

        try {

            String sql = "insert into grupousuario (idgrupo,id_usuario,es_admin) values (?,?,?)";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);


            ps.setInt(1,idGrupo);
            ps.setInt(2,idUsuario);
            ps.setBoolean(3,es_Admin);
            ps.execute();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }

    }


    /**
     * Recupera la lista de usuarios pertenecientes a un grupo especificado.
     *
     * @param idGrupo ID del grupo del que se recuperan los usuarios.
     * @return Lista de usuarios pertenecientes al grupo.
     */
    public  ArrayList<Usuarios> getVerUsuariosGrupo (int idGrupo){
        try {
            String sql = "select gu.id_usuario,u.nombre from grupousuario gu inner join usuarios u on u.id_usuario=gu.id_usuario where gu.idgrupo=?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1,idGrupo);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int idUsuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombre");


                Usuarios u = new Usuarios(idUsuario,nombreUsuario);

                listaUG.add(u);



            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }


        return listaUG;


    }
}
