package com.proyecto.estructuras.models;

public class Estudiante implements Comparable<Estudiante>{
    private int id; // la cedula xd
    private String nombre;
    private Integer puntaje;
    private boolean residencia;

    public Estudiante(){}

    public Estudiante(int id, String nombre, int puntaje, boolean residencia) {
        this.id = id;
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.residencia = residencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public boolean getResidencia() {
        return residencia;
    }

    public void setResidencia(boolean residencia) {
        this.residencia = residencia;
    }

    @Override
    public int compareTo(Estudiante o) {
        return this.puntaje.compareTo(o.getPuntaje());
    }
}