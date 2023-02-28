package com.elijahcode.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {}




