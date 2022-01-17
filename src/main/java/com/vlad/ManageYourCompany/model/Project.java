package com.vlad.ManageYourCompany.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nume;
    @NotBlank
    private String locatie;

    private Integer numarResurseNecesare;

    private Integer numarActualResurse;

    public Project() {
    }

    public Project(String nume, String locatie, Integer numarResurseNecesare) {
        this.nume = nume;
        this.locatie = locatie;
        this.numarResurseNecesare = numarResurseNecesare;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getNumarResurseNecesare() {
        return numarResurseNecesare;
    }

    public void setNumarResurseNecesare(Integer numarResurseNecesare) {
        this.numarResurseNecesare = numarResurseNecesare;
    }

    public Integer getNumarActualResurse() {
        return numarActualResurse;
    }

    public void setNumarActualResurse(Integer numarActualResurse) {
        this.numarActualResurse = numarActualResurse;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
