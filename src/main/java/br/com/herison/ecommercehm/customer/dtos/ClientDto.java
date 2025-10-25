package br.com.herison.ecommercehm.customer.dtos;

import java.util.List;

public record ClientDto(
        String name,
        String document,
        String email,
        String phone,
        List<AddressDto> addresses
) {}
