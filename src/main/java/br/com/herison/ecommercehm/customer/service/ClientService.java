package br.com.herison.ecommercehm.customer.service;

import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.mapper.ClientMapper;
import br.com.herison.ecommercehm.customer.model.Client;
import br.com.herison.ecommercehm.customer.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);

        if (client.getAddresses() != null) {
            client.getAddresses().forEach(address -> address.setClient(client));
        }

        Client saved = clientRepository.save(client);
        return clientMapper.toDto(saved);
    }

    @Transactional
    public ClientDto findById(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return clientMapper.toDto(client);
    }

}
