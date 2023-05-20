/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transversalparte2;

import controladores.AlumnoData;
import controladores.InscripcionData;
import controladores.MateriaData;
import java.time.LocalDate;
import java.time.Month;
import transversalparte2.Entity.Alumno;
import transversalparte2.Entity.Inscripcion;
import transversalparte2.Entity.Materia;

/**
 *
 * @author Ariana
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AlumnoData alumnoData = new AlumnoData();
        MateriaData materiaData = new MateriaData();
        InscripcionData inscripcionData = new InscripcionData();
        Alumno alumno = new Alumno(1235467, "Chaves", "Ariana", LocalDate.now(), 1);
        Materia materia = new Materia("Matematica", 2, 1);
        Inscripcion inscripcion = new Inscripcion(8, alumno, materia);
        //test
        //Alumno
        alumnoData.guardarAlumno(alumno);
        System.out.println(alumnoData.buscarAlumno(2));
        //Materia
        materiaData.guardarMateria(materia);
        System.out.println(materiaData.buscarMateria(1));
        //Inscripcion
        inscripcionData.guardarInscripcion(inscripcion);
        System.out.println(inscripcionData.buscarInscripcion(1));
        
    }
    
}
