/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import transversalparte2.Conexion;
import transversalparte2.Entity.Inscripcion;

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
                //Cartelito que diga "no se pudo hacer la inscripcion
            }
            
        }catch(SQLException ex){
            //cartelito "Error en buscarInscripcion"
        }
        return inscripcion;
    }
    
}
