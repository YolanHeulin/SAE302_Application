package com.example.sae302_application;
import java.io.Serializable;
public class Site implements Serializable {
    public int id;
    public String nom;
    public String adresse;
    public String contactNom;
    public String contactTel;

    public Site(int id, String nom, String adresse, String contactNom, String contactTel) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.contactNom = contactNom;
        this.contactTel = contactTel;
    }
}
