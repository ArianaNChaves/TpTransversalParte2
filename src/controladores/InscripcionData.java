/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import transversalparte2.Conexion;
import transversalparte2.Entity.Alumno;
import transversalparte2.Entity.Inscripcion;
import transversalparte2.Entity.Materia;

/**
 *
 * @author Ariana
 */
public class InscripcionData {
    private Connection conexion = null;
    private AlumnoData alumnoData;
    private MateriaData materiaData;
    

    public InscripcionData() {
        conexion = Conexion.getConnection();
        alumnoData = new AlumnoData();
        materiaData = new MateriaData();
    }
    
    public void guardarInscripcion(Inscripcion inscripcion){
         String sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria) VALUES (?, ?, ?)";
    try{
        PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, inscripcion.getNota());
        ps.setInt(2, inscripcion.getAlumno().getIdAlumno());
        ps.setInt(3, inscripcion.getMateria().getIdMateria());
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            //Esto va en la vista, debe ir un cartelito que diga "si se creo el alumno"
           inscripcion.setIdInscripto(rs.getInt(1));
        }else{
            //Esto va en la vista, debe ir un cartelito de "no se creo el alumno"
        }
        ps.close();
        
        
    }catch(SQLException ex){
      //En la vista va un cartelito que diga "no se puedo ejecutar la consulta"
    }
    }
    
    public Inscripcion buscarInscripcion(int id){
        Inscripcion inscripcion = new Inscripcion();
        String sql = "SELECT nota, idAlumno, idMateria FROM inscripcion WHERE idInscripto = ?";
        PreparedStatement ps = null;
        try{
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                inscripcion.setIdInscripto(id);
                inscripcion.setNota(rs.getInt("nota"));
                inscripcion.setAlumno(alumnoData.buscarAlumno(rs.getInt("idAlumno")));
                inscripcion.setMateria(materiaData.buscarMateria(rs.getInt("idMateria")));
                
            }else{
                System.out.println("no se encontro");
            }
            
        }catch(SQLException ex){
            //cartelito "Error en buscarInscripcion"
        }
        return inscripcion;
    }
    
    public int updateNota(Alumno alumno, Materia materia, double nota) throws IOException {
        int result = 0;
        try {
            String consulta = "UPDATE  `incripcion` SET `nota`= ? WHERE `incripcion`.`idAlumno` = ? AND `idMateria`= ? ;";
            PreparedStatement ps = Conexion.getConnection().prepareStatement(consulta);
            ps.setDouble(1, nota);
            ps.setInt(2, alumno.getIdAlumno());
            ps.setInt(3, materia.getIdMateria());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result == 1) {
            // CARTEL DE SE HIZO CORRECTAMENTE
        } else {
            // CARTEL DE NO SE PUDO HACER
        }
        return result;
    }
    
    public List<Inscripcion> obtenerInscripciones() {
        
        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            String sql = "SELECT * FROM inscripcion";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripto(rs.getInt("idInscripto"));
                inscripcion.setNota(rs.getInt("nota"));
                inscripcion.setAlumno(alumnoData.buscarAlumno(rs.getInt("idAlumno")));
                inscripcion.setMateria(materiaData.buscarMateria(rs.getInt("id")));
            }
            ps.close();
            
            
        } catch (SQLException ex) {
            // CARTEL DE ERROR
        }
        return inscripciones;
    }

    public List<Inscripcion> obtenerInscripcionesPorAlumno(int dni) {
        
        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            String sql = "SELECT * FROM inscripcion WHERE dni = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripto(rs.getInt("idInscripto"));
                inscripcion.setNota(rs.getInt("nota"));
                inscripcion.setAlumno(alumnoData.buscarAlumno(rs.getInt("idAlumno")));
                inscripcion.setMateria(materiaData.buscarMateria(rs.getInt("id")));
                inscripciones.add(inscripcion);
            }
            ps.close();
            
            
        } catch (SQLException ex) {
            // CARTEL DE ERROR
        }
        return inscripciones;
    }
    
    public List<Alumno> obtenerAlumnosPorMateria(int idMateria) {
        List<Alumno> alumnos = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT idAlumno FROM inscripcion WHERE idMateria = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Alumno alumno = alumnoData.buscarAlumno(rs.getInt("idAlumno"));
                alumnos.add(alumno);
            }
            ps.close();
            
        }catch (SQLException ex) {
            // CARTEL DE ERROR
        }
        return alumnos;
    }
   
    public List<Materia> obtenerMateriasCursadas(int id) {
        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT idMateria FROM inscripcion WHERE idAlumno = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Materia materia = materiaData.buscarMateria(rs.getInt("idMateria"));
                materias.add(materia);
            }
            ps.close();
            
        }catch (SQLException ex) {
            // CARTEL DE ERROR
        }
        return materias;
    }
    
    public List<Materia> obtenerMateriasNoCursadas(int id) {
        List<Materia> materias = new ArrayList<>();
        try {
            String sql;
            sql = "SELECT M.idMateria, M.Nombre\n" +
                 "FROM Materia M\n" +
                 "LEFT JOIN Inscripcion I ON M.idMateria = I.idMateria AND I.idAlumno = ?\n" +
                 "WHERE I.idAlumno IS NULL;";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            System.out.println();
            while(rs.next()) {
                Materia materia = materiaData.buscarMateria(rs.getInt("idMateria"));
                System.out.println("materia" + materia);
                materias.add(materia);
            }
            ps.close();
            
        }catch (SQLException ex) {
            // CARTEL DE ERROR
        }
        return materias;
    }
    
}
