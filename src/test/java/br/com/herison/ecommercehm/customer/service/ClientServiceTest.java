package br.com.herison.ecommercehm.customer.service;

import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.mapper.ClientMapper;
import br.com.herison.ecommercehm.customer.model.Client;
import br.com.herison.ecommercehm.customer.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientDto clientDto;

    @BeforeEach
    void setup() {
        client = new Client();
        client.setCode(1L);
        client.setName("Herison Maciel");
        client.setEmail("herisson@example.com");

        clientDto = new ClientDto(
                "Herison Maciel",
                "0513849651303",
                "herisson@example.com",
                "8855223366", null);
    }

    @Test
    @DisplayName("Should save client successfully")
    void shouldSaveClientSuccessfully() {
        when(clientMapper.toEntity(clientDto)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toDto(client)).thenReturn(clientDto);

        ClientDto result = clientService.save(clientDto);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Herison Maciel");

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    @DisplayName("Should find client by ID successfully")
    void shouldFindClientById() {
        // given
        when(clientRepository.findByCodeAndActiveTrue(anyLong())).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientDto);

        // when
        ClientDto result = clientService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Herison Maciel");

        verify(clientRepository, times(1)).findByCodeAndActiveTrue(1L);
    }

    @Test
    @DisplayName("Should throw 404 when client not found")
    void shouldThrowWhenClientNotFound() {
        when(clientRepository.findByCodeAndActiveTrue(99L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("client.not-found-or-inactive"), any(), any(Locale.class)))
                .thenReturn("Client not found");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> clientService.findById(99L));

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getReason()).isEqualTo("Client not found");
    }

    @Test
    @DisplayName("Should delete existing client")
    void shouldDeleteExistingClient() {
        when(clientRepository.findByCodeAndActiveTrue(1L)).thenReturn(Optional.of(client));

        clientService.delete(1L);

        verify(clientRepository, times(1)).findByCodeAndActiveTrue(1L);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    @DisplayName("Should throw 404 when deleting non-existing client")
    void shouldThrowWhenDeletingNonExistingClient() {
        when(clientRepository.findByCodeAndActiveTrue(99L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("client.not-found-or-already-inactive"), any(), any(Locale.class)))
                .thenReturn("Client not found");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> clientService.delete(99L));

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getReason()).isEqualTo("Client not found");
    }
}
