package com.vlad.ManageYourCompany.exceptions;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Long id){
        super("Could not find project with id: "+id);
    }
    public ProjectNotFoundException(String message){
        super(message);
    }
}
