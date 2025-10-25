package br.com.herison.ecommercehm.customer.dtos;

import br.com.herison.ecommercehm.customer.model.Client;

public record AddressDto(
        String city,
        String neighborhood,
        String publicPlace,
        String number,
        String complement,
        String zipcode
) {}
