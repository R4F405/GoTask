/**
 * Modela las operaciones relacionadas con las tareas en la base de datos.
 * Proporciona métodos para añadir nuevas tareas, obtener la lista de tareas de un usuario
 * y calcular la recompensa total acumulada por un usuario.
 */
package com.dam.gotask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TareaModel extends DBUtil {

    ArrayList<Tarea> listaTareasUsuario = new ArrayList<>();

    /**
     * Añade una nueva tarea con título, descripción y recompensa a la base de datos.
     *
     * @param titulo     El título de la tarea.
     * @param nota       La descripción de la tarea.
     * @param recompensa La recompensa asociada a la tarea.
     */
    public void anyadirTarea1(String titulo, String nota, String recompensa) {
        try {
            Connection C = getConexion();
            String sql = ("insert into tareas (nombre_tarea, descripcion, recompensa) values (? , ?, ?);");
            PreparedStatement ps = C.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, nota);
            ps.setString(3, recompensa);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.cerrarConexion();
        }
    }

    /**
     * Añade una nueva tarea con título y descripción a la base de datos.
     *
     * @param titulo El título de la tarea.
     * @param nota   La descripción de la tarea.
     */
    public void anyadirTarea2(String titulo, String nota) {
        try {
            Connection C = getConexion();
            String sql = ("insert into tareas (nombre_tarea, descripcion) values (? , ?);");
            PreparedStatement ps = C.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, nota);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.cerrarConexion();
        }
    }

    /**
     * Obtiene la lista de tareas de un usuario.
     *
     * @return La lista de tareas del usuario.
     */
    public ArrayList<Tarea> getListaTareas() {
        return listaTareasUsuario;
    }

    /**
     * Calcula la recompensa total acumulada por un usuario.
     *
     * @param id El identificador del usuario.
     * @return La recompensa total acumulada por el usuario.
     */
    public double getRecompensasTotales(int id) {
        double resultado = 0.00;
        try {
            String sql = "SELECT CalcularTotalRecompensas(?)";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultado = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cerrarConexion();
        }
        return resultado;
    }

    public void asignarTarea(int id_grupo, int id_usuario, int id_tareas, boolean tareaRealizada) throws SQLException {
        try {
            Connection C = getConexion();
            String sql = ("INSERT INTO tareas_asignadas (id_grupo, id_usuario, id_tareas, fecha, tarea_realizada)  VALUES ( ?, ?, ?, now(), ?)");
            PreparedStatement ps = C.prepareStatement(sql);
            ps.setInt(1, id_grupo);
            ps.setInt(2, id_usuario);
            ps.setInt(3, id_tareas);
            ps.setBoolean(4, tareaRealizada);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.cerrarConexion();
        }
    }
    public int getVerIdTarea(String nombreTarea) {
        int t = 0;
        try {
            String sql = "SELECT id_tarea FROM gotask.tareas WHERE nombre_tarea = ?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setString(1, nombreTarea);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {  // Comprobamos si hay resultados
                t = rs.getInt("id_tarea");  // Obtenemos el valor de la columna "id_tarea"

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cerrarConexion();
        }
        return t;
    }

    public Tarea verTarea (int idTarea){
        Tarea t=null ;

        try {
            String sql = "select nombre_tarea,descripcion,recompensa from tareas where id_tarea=?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1,idTarea);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){



                String nombreTarea = rs.getString("nombre_tarea");
                String Descripcion = rs.getString("descripcion");
                int recompensa = rs.getInt("recompensa");
                t = new Tarea(idTarea,nombreTarea,Descripcion,recompensa);


            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }



        return  t;
    }

    public Usuarios getNombreUsuarioTarea ( int idTarea) {

        Usuarios u = new Usuarios();

        try {
            String sql = "select u.nombre from usuarios u inner join tareas_asignadas ta on u.id_usuario=ta.id_usuario where id_tareas=?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setInt(1,idTarea);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){



                String nombreUsuario = rs.getString("nombre");

                u = new Usuarios(nombreUsuario);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }

        return u;

    }


    public void finalizarTarea(int idTarea){

        boolean finalizar = true;


        try {
            Connection C = getConexion();
            String sql = ("update tareas_asignadas set tarea_realizada=? where id_tareas=?");
            PreparedStatement ps = C.prepareStatement(sql);
            ps.setBoolean(1,finalizar);
            ps.setInt(2, idTarea);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.cerrarConexion();
        }



    }

}
