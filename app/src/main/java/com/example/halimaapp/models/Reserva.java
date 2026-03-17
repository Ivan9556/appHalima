package com.example.halimaapp.models;

import com.google.gson.annotations.SerializedName;

import kotlinx.serialization.Serializable;

public class  Reserva {
    // @SerializedName le dice a GSON: "nombre" del JSON va a esta variable
    private String id;
    @SerializedName("nombre")
    private String nom;
    @SerializedName("apellidos")
    private String ape;
    public Reserva() {
    }

    public Reserva(String id, String name, String ape) {
        this.id = id;
        this.nom = name;
        this.ape= ape;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", ape='" + ape + '\'' +
                '}';
    }
}
