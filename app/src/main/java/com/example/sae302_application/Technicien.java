package com.example.sae302_application;

import java.io.Serializable;

public class Technicien implements Serializable {
    public int id;
    public String nom;
    public String telephone;

    public Technicien(int id, String nom, String telephone) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
    }
}