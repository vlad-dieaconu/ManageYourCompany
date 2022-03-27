package com.vlad.ManageYourCompany.controller.payload;

import java.util.Date;

public class WorkingDayRequest {

    private Date date;
    private String details;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "WorkingDayRequest{" +
                "date=" + date +
                ", details='" + details + '\'' +
                '}';
    }
}
