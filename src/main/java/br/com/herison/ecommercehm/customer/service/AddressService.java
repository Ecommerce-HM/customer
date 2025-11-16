package br.com.herison.ecommercehm.customer.service;

import br.com.herison.ecommercehm.customer.dtos.AddressDto;
import br.com.herison.ecommercehm.customer.mapper.AddressMapper;
import br.com.herison.ecommercehm.customer.model.Address;
import br.com.herison.ecommercehm.customer.model.Client;
import br.com.herison.ecommercehm.customer.repository.AddressRepository;
import br.com.herison.ecommercehm.customer.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final AddressMapper addressMapper;
    private final MessageSource messageSource;

    @Transactional
    public AddressDto save(AddressDto addressDto, Long clientId) {
        log.info("Saving address for client {}", clientId);

        Client client = clientRepository.findByCodeAndActiveTrue(clientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("client.not-found-or-inactive", null, Locale.getDefault())
                ));

        Address address = addressMapper.toEntity(addressDto);
        address.setClient(client);

        Address saved = addressRepository.save(address);
        log.info("Address {} saved successfully for client {}", saved.getId(), clientId);

        return addressMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AddressDto> findByAddress(Long clientId) {
        clientRepository.findByCodeAndActiveTrue(clientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("client.not-found-or-inactive", null, Locale.getDefault())
                ));

        List<Address> addresses = addressRepository.findByClientCode(clientId);

        if (addresses.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    messageSource.getMessage("address.not-found-for-client", null, Locale.getDefault())
            );
        }

        return addresses.stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("address.not-found", null, Locale.getDefault())
                ));

        addressRepository.delete(address);
        log.info("Address {} deleted successfully", id);
    }
}
