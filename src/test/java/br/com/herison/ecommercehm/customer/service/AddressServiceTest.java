package br.com.herison.ecommercehm.customer.service;

import br.com.herison.ecommercehm.customer.dtos.AddressDto;
import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.mapper.AddressMapper;
import br.com.herison.ecommercehm.customer.model.Address;
import br.com.herison.ecommercehm.customer.model.Client;
import br.com.herison.ecommercehm.customer.repository.AddressRepository;
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

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private AddressService addressService;

    private Client client;
    private ClientDto clientDto;

    private Address address;
    private AddressDto addressDto;

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

        address = new Address();
        address.setClient(client);
        address.setId(1L);
        address.setCity("Fortaleza");
        address.setNeighborhood("Meireles");
        address.setZipcode("60866444");
        address.setNumber("565");
        address.setComplement("casa b");
        address.setPublicPlace("rua branca de neve");

        addressDto = new AddressDto(
                1L,
                "Fortaleza",
                "Meireles",
                "casa b",
                "565",
                "casa b",
                "60866444"
        );

    }

    @Test
    @DisplayName("Should save address successfully")
    void shouldSaveAddressSuccessfully(){
        when(clientRepository.findByCodeAndActiveTrue(1L)).thenReturn(Optional.of(client));
        when(addressMapper.toEntity(addressDto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(addressDto);

        AddressDto result = addressService.save(addressDto, 1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.city()).isEqualTo("Fortaleza");

        verify(clientRepository, times(1)).findByCodeAndActiveTrue(1L);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    @DisplayName("Should find address by ID successfully")
    void shouldFindByAddress(){
        when(clientRepository.findByCodeAndActiveTrue(1L)).thenReturn(Optional.of(client));
        when(addressRepository.findByClientCode(1L)).thenReturn(List.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressDto);

        var result = addressService.findByAddress(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(List.of(addressDto));

        verify(clientRepository, times(1)).findByCodeAndActiveTrue(1L);
        verify(addressRepository, times(1)).findByClientCode(1L);
    }

    @Test
    @DisplayName("Should throw 404 when address not found")
    void shouldThrowWhenAddressNotFound() {
        when(clientRepository.findByCodeAndActiveTrue(1L)).thenReturn(Optional.of(client));
        when(addressRepository.findByClientCode(1L)).thenReturn(List.of());
        when(messageSource.getMessage(eq("address.not-found-for-client"), any(), any(Locale.class)))
                .thenReturn("No address found for this customer");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> addressService.findByAddress(1L));

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getReason()).isEqualTo("No address found for this customer");

        verify(clientRepository, times(1)).findByCodeAndActiveTrue(1L);
        verify(addressRepository, times(1)).findByClientCode(1L);
    }

    @Test
    @DisplayName("Should delete existing address")
    void shouldDeleteExistingAddress() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        addressService.delete(1L);

        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    @DisplayName("Should throw 404 when deleting non-existing address")
    void shouldThrowWhenDeletingNonExistingClient() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("address.not-found"), any(), any(Locale.class)))
                .thenReturn("Address not found");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> addressService.delete(99L));

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getReason()).isEqualTo("Address not found");
    }

}
