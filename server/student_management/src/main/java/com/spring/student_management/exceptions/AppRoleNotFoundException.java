package com.spring.student_management.exceptions;


public class AppRoleNotFoundException extends Exception {
    private static final long serialVersionUID = -6244219400357501402L;
    public AppRoleNotFoundException(long roleId) {
        super(String.format("Cannot find role with id %d", roleId));
    }
}
