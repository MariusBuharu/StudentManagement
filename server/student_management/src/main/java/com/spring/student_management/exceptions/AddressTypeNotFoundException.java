package com.spring.student_management.exceptions;


public class AddressTypeNotFoundException extends Exception {
    public AddressTypeNotFoundException(long addressTypeUser) {
        super(String.format("Address type with id %s not found!", addressTypeUser));
    }
}
