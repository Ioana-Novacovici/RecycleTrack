package com.GreenCycleSolutions.gcsbackend.exception;

public class AddressAlreadyExistsException extends RuntimeException {

    public AddressAlreadyExistsException() {
        super("The address already exists, can not have the same address twice.");
    }
}
