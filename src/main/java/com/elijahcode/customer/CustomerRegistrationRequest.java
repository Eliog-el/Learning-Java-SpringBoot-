package com.elijahcode.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age,
        String password
) {}
