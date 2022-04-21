package com.vlad.ManageYourCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Entity
public class ProjectCommits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Lob
    private String commit;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public ProjectCommits() {
    }

    public ProjectCommits(Date date, String commit) {
        this.date = date;
        this.commit = commit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
