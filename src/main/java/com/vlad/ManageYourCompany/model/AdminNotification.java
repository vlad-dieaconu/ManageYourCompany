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

    private String type;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public AdminNotification() {
    }

    public AdminNotification(String description, Date date) {
        this.description =  description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
