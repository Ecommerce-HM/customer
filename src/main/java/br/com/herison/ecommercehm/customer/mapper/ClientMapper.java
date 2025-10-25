package br.com.herison.ecommercehm.customer.mapper;

import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface ClientMapper {

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "active", ignore = true)
    Client toEntity(ClientDto dto);

    ClientDto toDto(Client client);
}
