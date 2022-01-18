package com.vlad.ManageYourCompany.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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

    @OneToMany(mappedBy = "project")
    private List<User> employees;

    private Integer numarResurseNecesare;

    private Integer numarActualResurse;

    public Project() {
    }

    public Project(String nume, String locatie, Integer numarResurseNecesare) {
        this.nume = nume;
        this.locatie = locatie;
        this.numarResurseNecesare = numarResurseNecesare;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
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
