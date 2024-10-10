package com.my_first_project.SCM.helper;


public class ResourceNotFoundException extends RuntimeException{
    
    public ResourceNotFoundException(String message){
        super(message);
    }
    public ResourceNotFoundException(){
        super("Resource Not Found");
    }
}
