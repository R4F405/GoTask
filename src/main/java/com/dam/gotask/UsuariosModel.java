package com.dam.gotask;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Modelo para la gestión de usuarios en la base de datos.
 */
public class UsuariosModel extends DBUtil{


    /**
     * Encripta la contraseña utilizando el algoritmo MD5.
     *
     * @param contraseña Contraseña a encriptar.
     * @return Contraseña encriptada.
     * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo de encriptación.
     */
    private String encriptarContraseña(String contraseña) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(contraseña.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }



    /**
     * Obtiene un usuario mediante su email y contraseña encriptada.
     *
     * @param email      Email del usuario.
     * @param contraseña Contraseña encriptada del usuario.
     * @return Objeto Usuarios si se encuentra el usuario, de lo contrario null.
     */
    public Usuarios getUsuarioLogin(String email, String contraseña) {
        Usuarios u = null;
        try {
            String contraseñaEncriptada = encriptarContraseña(contraseña);
            String sql = "SELECT id_usuario, nombre FROM usuarios WHERE correo=? AND password=?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, contraseñaEncriptada); // Encriptamos la contraseña antes de realizar la consulta
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombre");

                u = new Usuarios(idUsuario, nombreUsuario, email, contraseñaEncriptada);
            }
           // u = new Usuarios(12, "q", "q", "q");
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            this.cerrarConexion();
        }
        return u;
    }



    /**
     * Actualiza la contraseña de un usuario.
     *
     * @param email      Email del usuario.
     * @param contraseña Nueva contraseña del usuario.
     * @return Cantidad de filas afectadas por la actualización.
     */
    public int updateCambiarContraseña (String email,String contraseña) {

        int exito = 0;

        try {
            String contraseñaEncriptada = encriptarContraseña(contraseña);
            String sql = "UPDATE usuarios SET password = ? WHERE correo = ?";

            PreparedStatement ps = this.getConexion().prepareStatement(sql);


            ps.setString(1, contraseñaEncriptada); // Encriptamos la nueva contraseña antes de actualizarla
            ps.setString(2, email);

            exito = ps.executeUpdate();

        } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
        } finally {
            // Cerramos conexi�n
            this.cerrarConexion();
        }

        return exito;
    }




    /**
     * Verifica si una contraseña ya ha sido utilizada anteriormente.
     *
     * @param nuevaContraseña Nueva contraseña a verificar.
     * @return true si la contraseña ya ha sido utilizada anteriormente, de lo contrario false.
     */
    public boolean verificarContraseñaRepetida(String nuevaContraseña) {
        boolean contraseñaRepetida = false;
        try {
            String sql = "SELECT COUNT(*) AS count FROM contraseñasrepetidas WHERE contraseña_anterior = ?";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ps.setString(1, nuevaContraseña);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    contraseñaRepetida = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cerrarConexion();
        }
        return contraseñaRepetida;
    }


    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return Lista de usuarios.
     */
    public ArrayList<Usuarios> getUsuarios() {

        Usuarios u = null;

        ArrayList<Usuarios> lu = new ArrayList<Usuarios>();

        try {
            String sql = "select * from usuarios";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String password = rs.getString("password");


                u = new Usuarios(id,nombre,correo,password);

                lu.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Cerramos conexi�n
            this.cerrarConexion();
        }

        return lu;
    }




    /**
     * Añade un nuevo usuario a la base de datos.
     *
     * @param nombre     Nombre del usuario.
     * @param email      Email del usuario.
     * @param contraseña Contraseña del usuario.
     * @param foto       Foto del usuario.
     * @return Cantidad de filas afectadas por la inserción.
     */
    public int setAñadirUsuarios(String nombre,String email,String contraseña, String foto) {

        int exito = 0;

        try {
            String contraseñaEncriptada = encriptarContraseña(contraseña);
            String sql = "INSERT INTO usuarios (nombre, correo, password, imagen_Perfil) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = this.getConexion().prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, contraseñaEncriptada); // Encriptamos la contraseña antes de insertarla
            ps.setString(4, foto);

            exito = ps.executeUpdate();


        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            // Cerramos conexi�n
            this.cerrarConexion();
        }

        return exito;
    }


    /**
     * Obtiene los grupos administrados por el usuario.
     *
     * @return Lista de nombres de grupos administrados por el usuario.
     */
    public ArrayList<String> obtenerGruposAdmin() {
        ArrayList<String> gruposAdmin = new ArrayList<>();

        try (Connection C = getConexion()) {
            String sql = "SELECT nombre_grupo FROM grupos WHERE id_administrador = ?";
            try (PreparedStatement stmt = C.prepareStatement(sql)) {
                int idUsuario = 1;
                stmt.setInt(1, idUsuario);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nombreGrupo = rs.getString("nombre_grupo");
                    gruposAdmin.add(nombreGrupo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.cerrarConexion();
        }

        return gruposAdmin;
    }
}