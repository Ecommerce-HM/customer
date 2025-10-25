package br.com.herison.ecommercehm.customer.dtos;

import br.com.herison.ecommercehm.customer.model.Address;

import java.util.List;

public record ClientDto(
        String name,
        String cpf,
        String email,
        String phone,
        List<AddressDto> addresses
) {}
