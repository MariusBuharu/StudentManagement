package com.spring.student_management.exceptions;


public class AddressNotFoundException extends Exception {
    public AddressNotFoundException(long addressId) {
        super(String.format("Address with id %d not found!", addressId));
    }
}
