package br.com.herison.ecommercehm.customer.controller;

import br.com.herison.ecommercehm.customer.dtos.AddressDto;
import br.com.herison.ecommercehm.customer.model.Address;
import br.com.herison.ecommercehm.customer.model.Client;
import br.com.herison.ecommercehm.customer.repository.AddressRepository;
import br.com.herison.ecommercehm.customer.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/addresses/{clientId} - should create address for a client")
    void shouldCreateAddress() throws Exception{
        Client client = new Client();
        client.setName("Cliente Endereço");
        client.setEmail("email@cliente.com");
        client.setDocument("12345678901");
        client.setPhone("999999999");
        client = clientRepository.save(client);

        AddressDto addressDto = new AddressDto(
                null,
                "Fortaleza",
                "Meireles",
                "casa b",
                "565",
                "casa b",
                "60866444"
        );

        mockMvc.perform(post("/api/addresses/{clientId}", client.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicPlace", is("casa b")))
                .andExpect(jsonPath("$.city", is("Fortaleza")))
                .andExpect(jsonPath("$.neighborhood", is("Meireles")))
                .andExpect(jsonPath("$.number", is("565")))
                .andExpect(jsonPath("$.zipcode", is("60866444")));
    }

    @Test
    @DisplayName("GET /api/addresses/{code} - should return addresses of client")
    void shouldReturnAddressesByClientCode() throws Exception {
        Client client = new Client();
        client.setName("Cliente X");
        client.setEmail("x@cliente.com");
        client.setDocument("99999999999");
        client.setPhone("888888888");
        client = clientRepository.save(client);

        Address address = new Address();
        address.setClient(client);
        address.setCity("Fortaleza");
        address.setNeighborhood("Meireles");
        address.setZipcode("60866444");
        address.setNumber("565");
        address.setComplement("casa b");
        address.setPublicPlace("rua branca de neve");
        addressRepository.save(address);

        mockMvc.perform(get("/api/addresses/{code}", client.getCode()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", is("Fortaleza")));
    }

    @Test
    @DisplayName("DELETE /api/addresses/{id} - should delete existing address")
    void shouldDeleteAddress() throws Exception {
        Client client = new Client();
        client.setName("Cliente X");
        client.setEmail("x@cliente.com");
        client.setDocument("9999999123");
        client.setPhone("888888888");
        client = clientRepository.save(client);

        Address address = new Address();
        address.setClient(client);
        address.setCity("Fortaleza");
        address.setNeighborhood("Meireles");
        address.setZipcode("60866444");
        address.setNumber("565");
        address.setComplement("casa b");
        address.setPublicPlace("rua branca de neve");
        addressRepository.save(address);

        mockMvc.perform(delete("/api/addresses/{id}", address.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/addresses/{id} - should return 404 when address not found")
    void shouldReturn404WhenAddressNotFound() throws Exception {
        Long id = (Long) 999L;
        mockMvc.perform(delete("/api/addresses/{id}", id ))
                .andExpect(status().isNotFound());
    }

}
