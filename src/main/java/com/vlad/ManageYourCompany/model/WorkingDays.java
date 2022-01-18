package com.vlad.ManageYourCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WorkingDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true,nullable = false)
    private Long id;


    private Date date;
    private String details;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public WorkingDays() {
    }

    public WorkingDays(Date date, String details, User user) {
        this.date = date;
        this.details = details;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
