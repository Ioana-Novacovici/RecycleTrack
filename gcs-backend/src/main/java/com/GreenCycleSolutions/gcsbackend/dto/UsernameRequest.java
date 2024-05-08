package com.GreenCycleSolutions.gcsbackend.dto;

import lombok.Data;

@Data
public class UsernameRequest {

    private String oldUsername;
    private String newUsername;
}
