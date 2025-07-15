package com.proyecto.estructuras.models;

public class Estudiante {
    private int id; // la cedula xd
    private String nombre;
    private int puntaje;
    private boolean residencia;

    public Estudiante(int id, String nombre, int puntaje, boolean residencia) {
        this.id = id;
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.residencia = residencia;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public boolean getEstado() {
        return residencia;
    }

    public void setEstado(boolean residencia) {
        this.residencia = residencia;
    }
}