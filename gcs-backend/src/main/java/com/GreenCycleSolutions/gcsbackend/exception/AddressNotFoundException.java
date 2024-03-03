package com.GreenCycleSolutions.gcsbackend.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
        super("No address found");
    }
}
