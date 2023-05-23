package com.SouthParkReview.myAPI.exceptions;

public class CitizenNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1;

    public CitizenNotFoundException(String message) {
        super(message);
    }
}
