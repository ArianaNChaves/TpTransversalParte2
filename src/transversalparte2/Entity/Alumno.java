/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transversalparte2.Entity;

import controladores.Utils;
import java.sql.Connection;
import java.time.LocalDate;

/**
 *
 * @author Ariana
 */
public class Alumno {
    private Integer idAlumno;
    private Integer DNI;
    private String apellido;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Integer estado;


    public Alumno() {
    }

    public Alumno(Integer idAlumno, Integer DNI, String apellido, String nombre, LocalDate fechaNacimiento, Integer estado) {
        this.idAlumno = idAlumno;
        this.DNI = DNI;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    public Alumno(Integer DNI, String apellido, String nombre, LocalDate fechaNacimiento, Integer estado) {
        this.DNI = DNI;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }
    public Boolean estado(){
        return Utils.intToBool(estado);
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Integer getDNI() {
        return DNI;
    }

    public void setDNI(Integer DNI) {
        this.DNI = DNI;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Alumno{" + "DNI=" + DNI + ", apellido=" + apellido + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", estado=" + estado + '}';
    }
    
    
}
