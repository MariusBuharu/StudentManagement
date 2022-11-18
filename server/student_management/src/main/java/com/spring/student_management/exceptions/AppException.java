package com.spring.student_management.exceptions;


public class AppException extends Exception {
    private static final long serialVersionUID = -8390868795553694825L;
    public AppException(Exception e) {
        super(e);
    }
    public AppException(String errorMsg) {
        super(errorMsg);
    }
}
