package br.com.herison.ecommercehm.customer.mapper;

import br.com.herison.ecommercehm.customer.dtos.AddressDto;
import br.com.herison.ecommercehm.customer.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "client", ignore = true)
    Address toEntity(AddressDto dto);

    AddressDto toDto(Address address);
}
