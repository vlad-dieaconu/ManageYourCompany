package com.vlad.ManageYourCompany.exceptions;

public class LeaveRequestNotFoundException extends RuntimeException{
    public LeaveRequestNotFoundException(Long id){
        super("Could not find leave request with id: "+id);
    }
}
