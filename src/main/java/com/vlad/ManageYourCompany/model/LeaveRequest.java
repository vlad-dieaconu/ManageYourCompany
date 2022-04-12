package com.vlad.ManageYourCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;
    private Date endDate;

    private boolean approved;
    private boolean seenBySuperior;

    private int numberOfDays;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LeaveType leaveType;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public LeaveRequest() {
    }

    public LeaveRequest(Date startDate, Date endDate, int numberOfDays, LeaveType leaveType, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfDays = numberOfDays;
        this.leaveType = leaveType;
        this.user = user;
    }

    public LeaveRequest(Date startDate, Date endDate, LeaveType leaveType, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.user = user;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date to) {
        this.endDate = to;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date from) {
        this.startDate = from;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isSeenBySuperior() {
        return seenBySuperior;
    }


    public void setSeenBySuperior(boolean seenBySuperior) {
        this.seenBySuperior = seenBySuperior;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
