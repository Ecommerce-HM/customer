package br.com.herison.ecommercehm.customer.service;

import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.mapper.ClientMapper;
import br.com.herison.ecommercehm.customer.model.Client;
import br.com.herison.ecommercehm.customer.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final MessageSource messageSource;

    @Transactional
    public ClientDto save(ClientDto clientDto) {
        log.info("Saving client: {}", clientDto.toString());

        Client client = clientMapper.toEntity(clientDto);

        if (client.getAddresses() != null) {
            client.getAddresses().forEach(address -> address.setClient(client));
        }

        Client saved = clientRepository.save(client);
        log.info("Client saved successfully: {}", saved.getCode());
        return clientMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long code){
        Client client = clientRepository.findByCodeAndActiveTrue(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("client.not-found-or-inactive", null, Locale.getDefault())
                ));

        return clientMapper.toDto(client);
    }

    @Transactional
    public void delete(Long code) {
        Client client = clientRepository.findByCodeAndActiveTrue(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("client.not-found-or-already-inactive", null, Locale.getDefault())
                ));

        client.setActive(false);
        clientRepository.save(client);
        log.info("Client {} marked as inactive", code);
    }
}
