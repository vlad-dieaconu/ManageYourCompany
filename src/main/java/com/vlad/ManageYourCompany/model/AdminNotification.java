package com.vlad.ManageYourCompany.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AdminNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    private Date date;

    @OneToOne
    private User user;


    public AdminNotification() {
    }

    public AdminNotification(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
