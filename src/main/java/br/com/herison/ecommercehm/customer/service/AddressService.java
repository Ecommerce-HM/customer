package br.com.herison.ecommercehm.customer.service;

import br.com.herison.ecommercehm.customer.dtos.AddressDto;
import br.com.herison.ecommercehm.customer.mapper.AddressMapper;
import br.com.herison.ecommercehm.customer.model.Address;
import br.com.herison.ecommercehm.customer.model.Client;
import br.com.herison.ecommercehm.customer.repository.AddressRepository;
import br.com.herison.ecommercehm.customer.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public AddressDto save(AddressDto addressDto, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Address address = addressMapper.toEntity(addressDto);
        address.setClient(client);

        Address saved = addressRepository.save(address);
        return addressMapper.toDto(saved);
    }

    @Transactional
    public List<AddressDto> findByClient(Long clientId) {
        List<Address> addresses = addressRepository.findByClientCode(clientId);
        return addresses.stream()
                .map(addressMapper::toDto)
                .toList();
    }
}
