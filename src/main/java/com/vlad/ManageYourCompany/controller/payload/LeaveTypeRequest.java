package com.vlad.ManageYourCompany.controller.payload;

import com.vlad.ManageYourCompany.model.LeaveType;

import java.util.Date;


public class LeaveTypeRequest {

    private Date startDay;
    private Date endDay;

        private String leavingType;

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public String getLeavingType() {
        return leavingType;
    }

    public void setLeavingType(String leavingType) {
        this.leavingType = leavingType;
    }
}
