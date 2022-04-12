package com.vlad.ManageYourCompany.controller.payload;

import java.util.Date;

public class ProjectCommitsRequest {

    private String commit;
    private Date date;

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
