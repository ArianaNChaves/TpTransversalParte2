/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import transversalparte2.Conexion;
import transversalparte2.Entity.Alumno;

/**
 *
 * @author Ariana
 */
public class AlumnoData {
    private Connection conexion = null;

    public AlumnoData() {
        conexion = Conexion.getConnection();
    }
    
    public void guardarAlumno(Alumno alumno){
    String sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado) VALUES (?, ?, ?, ?, ?)";
    try{
        PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, alumno.getDNI());
        ps.setString(2, alumno.getApellido());
        ps.setString(3, alumno.getNombre());
        ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
        ps.setBoolean(5, alumno.estado());
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            //Esto va en la vista, debe ir un cartelito que diga "si se creo el alumno"
            alumno.setIdAlumno(rs.getInt(1));
        }else{
            //Esto va en la vista, debe ir un cartelito de "no se creo el alumno"
        }
        ps.close();
        
        
    }catch(SQLException ex){
      //En la vista va un cartelito que diga "no se puedo ejecutar la consulta"
    }
    
}
    public Alumno buscarAlumno(int id){
        Alumno alumno = new Alumno();
        String sql = "SELECT dni, apellido, nombre, fechaNacimiento, estado FROM alumno WHERE idAlumno = ?";
        PreparedStatement ps = null;
        try{
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setDNI(rs.getInt("dni"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(Utils.boolToInt(rs.getBoolean("estado")));
                
                
            }else{
                //en vistas, cartelito que diga "no se pudo buscar el alumno" o "no existe el alumno"
            }
            ps.close();
            
        }catch(SQLException ex){
            //cartelito "error en buscar alumno"
        }
        return alumno;
    }
    
    public Alumno buscarAlumnoPorDni(int dni) {
        Alumno alumno = null;
        String sql = "SELECT idAlumno, dni, apellido, nombre, fechaNacimiento FROM alumno WHERE dni=? AND estado = 1";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("dni"));
                alumno.setDNI(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(Utils.boolToInt(rs.getBoolean("estado")));

            } else {
                // mensajito de error en la vista
            }
            ps.close();
        } catch (SQLException ex) {
            // mensajito de error en la vista
        }
        return alumno;
    }
      
    public List<Alumno> listarAlumnos() {

        List<Alumno> alumnos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM alumno WHERE estado = 1 ";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();

                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDNI(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(Utils.boolToInt(rs.getBoolean("estado")));
                alumnos.add(alumno);
            }
            ps.close();

        } catch (SQLException ex) {
            // mensajito de error en vista
        }
        return alumnos;
    }
    
    public void modificarAlumno(Alumno alumno) {

        String sql = "UPDATE alumno SET dni = ? , apellido = ?, nombre = ?, fechaNacimiento = ? WHERE  idAlumno = ?";
        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, alumno.getDNI());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setInt(5, alumno.getIdAlumno());
            int exito = ps.executeUpdate();

            if (exito == 1) {
                // Cartelito de SE MODIFICO CORRECTAMENTE
            } else {
                // Cartelito de EL ALUMNO NO EXISTE
            }

        } catch (SQLException ex) {
            // Cartelito de ERROR
     }

    }
    
    public void eliminarAlumno(int id) {

        try {
            String sql = "UPDATE alumno SET estado = 0 WHERE idAlumno = ? ";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                // cartelito de SE ELIMINO EL ALUMNO
            }
            ps.close();
        } catch (SQLException e) {
            // Cartelito de ERROR
        }
    }
    
    public void activarAlumno(int id) {

        try {
            String sql = "UPDATE alumno SET estado = 1 WHERE idAlumno = ? ";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();
            ps.close();
            if (fila == 1) {
                // cartelito DE SE ACTIVO EL ALUMNO
            }

        } catch (SQLException e) {

            // CARTELITO DE ERROR
       }
    }
}
