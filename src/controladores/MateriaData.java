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
import transversalparte2.Entity.Materia;

/**
 *
 * @author Ariana
 */
public class MateriaData {
    private Connection conexion = null;

    public MateriaData() {
        conexion = Conexion.getConnection();
    }
    
    public void guardarMateria(Materia materia){
        String sql = "INSERT INTO materia (nombre, anio, estado) VALUES (?, ?, ?)";
    try{
        PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, materia.getNombre());
        ps.setInt(2, materia.getAnio());
        ps.setBoolean(3, materia.estado());
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            //Esto va en la vista, debe ir un cartelito que diga "si se creo el alumno"
           materia.setIdMateria(rs.getInt(1));
        }else{
            //Esto va en la vista, debe ir un cartelito de "no se creo el alumno"
        }
        ps.close();
        
        
    }catch(SQLException ex){
      //En la vista va un cartelito que diga "no se puedo ejecutar la consulta"
    }
    }
    
    public Materia buscarMateria(int id){
        Materia materia = new Materia();
        String sql = "SELECT nombre, anio, estado FROM materia WHERE idMateria = ?";
        PreparedStatement ps = null;
        try{
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                materia.setAnio(rs.getInt("anio"));
                materia.setNombre(rs.getString("nombre"));
                materia.setEstado(Utils.boolToInt(rs.getBoolean("estado")));
                
                
            }else{
                //en vistas, cartelito que diga "no se pudo buscar el alumno" o "no existe el alumno"
            }
            ps.close();
            
        }catch(SQLException ex){
            //cartelito "error en buscar alumno"
        }
        return materia;
    }
    
    public void modificarMateria(Materia materia) {
        String sql = "UPDATE materia SET nombre = ?, año = ? WHERE  idMateria = ? AND estado = 1";
        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement(sql);

            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnio());
            ps.setInt(3, materia.getIdMateria());

            ps.executeUpdate();

           // CARTEL DE MODIFICACION EXITOSA

        } catch (SQLException ex) {
            // CARTEL DE ERROR
       }
    }
    
    public void eliminarMateria(int id) {

        try {
            String sql = "UPDATE materia SET estado = 0 WHERE idMateria = ? ";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            // CARTEL DE ELIMINADO CORRECTAMENTE
        } catch (SQLException e) {
            // CARTEL DE ERROR
       }
    }
    
    public List<Materia> listarMaterias() {

        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT * FROM materia WHERE estado = 1 ";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();

                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("año"));
                materia.setEstado(Utils.boolToInt(rs.getBoolean("estado")));
                materias.add(materia);
            }
            ps.close();

        } catch (SQLException ex) {
            // CARTEL DE ERROR
        }
        return materias;
    }
    
}
