package br.com.herison.ecommercehm.customer.dtos;

public record AddressDto(
        String city,
        String neighborhood,
        String publicPlace,
        String number,
        String complement,
        String zipcode
) {}
