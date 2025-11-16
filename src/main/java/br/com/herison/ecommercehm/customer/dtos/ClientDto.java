package br.com.herison.ecommercehm.customer.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClientDto(
        Long code,
        @NotNull
        String name,
        @NotNull
        String document,
        @Email
        String email,
        @NotNull
        String phone,
        boolean active,
        List<AddressDto> addresses
) {}
