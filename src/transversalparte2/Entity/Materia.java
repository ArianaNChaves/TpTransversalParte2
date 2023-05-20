/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transversalparte2.Entity;

import controladores.Utils;
import java.sql.Connection;

/**
 *
 * @author Ariana
 */
public class Materia {
    private Integer idMateria;
    private String nombre;
    private Integer anio;
    private Integer estado;

    public Materia() {
    }

    public Materia(Integer idMateria, String nombre, Integer anio, Integer estado) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.anio = anio;
        this.estado = estado;
    }

    public Materia(String nombre, Integer anio, Integer estado) {
        this.nombre = nombre;
        this.anio = anio;
        this.estado = estado;
    }
    public Boolean estado(){
        return Utils.intToBool(estado);
    }
    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Materia{" + "nombre=" + nombre + ", anio=" + anio + ", estado=" + estado + '}';
    }

   
    
    
    
}
