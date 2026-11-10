package com.rama.banking.dto;

public record LoginResponse(String token, String tokenType, long expiresInSeconds) { }
