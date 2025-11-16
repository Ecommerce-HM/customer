package br.com.herison.ecommercehm.customer.dtos;

import jakarta.validation.constraints.NotNull;

public record AddressDto(
        Long id,
        @NotNull
        String city,
        @NotNull
        String neighborhood,
        @NotNull
        String publicPlace,
        @NotNull
        String number,
        String complement,
        @NotNull
        String zipcode
) {}
