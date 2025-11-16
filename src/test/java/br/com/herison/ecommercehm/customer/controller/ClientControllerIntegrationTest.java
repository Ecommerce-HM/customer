package br.com.herison.ecommercehm.customer.controller;


import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.model.Client;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private  ClientRepository clientRepository;
    @Autowired
    private  ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/clients - should create client successfully")
    void shouldCreateClientSuccessfully() throws Exception {
        ClientDto dto = new ClientDto(
                1L,
                "Herison Maciel",
                "6845149865",
                "herison@example.com",
                "89598426454",true, null);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Herison Maciel")))
                .andExpect(jsonPath("$.document", is("6845149865")))
                .andExpect(jsonPath("$.email", is("herison@example.com")))
                .andExpect(jsonPath("$.phone", is("89598426454")));
    }

    @Test
    @DisplayName("GET /api/clients/{id} - should return client when exists")
    void shouldReturnClientWhenExists() throws Exception {
        Client client = new Client();
        client.setName("Herison Maciel");
        client.setDocument("684514985");
        client.setEmail("herison@example.com");
        client.setPhone("89598426454");
        client = clientRepository.save(client);

        mockMvc.perform(get("/api/clients/{id}", client.getCode()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Herison Maciel")))
                .andExpect(jsonPath("$.document", is("684514985")))
                .andExpect(jsonPath("$.email", is("herison@example.com")))
                .andExpect(jsonPath("$.phone", is("89598426454")));
    }

    @Test
    @DisplayName("GET /api/clients/{id} - should return 404 when client not found")
    void shouldReturn404WhenClientNotFound() throws Exception {
        mockMvc.perform(get("/api/clients/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/clients/{id} - should delete client when exists")
    void shouldDeleteClientWhenExists() throws Exception {
        Client client = new Client();
        client.setName("Delete Me");
        client.setDocument("123546");
        client.setEmail("delete@example.com");
        client.setPhone("89598426454");
        client = clientRepository.save(client);

        mockMvc.perform(delete("/api/clients/{id}", client.getCode()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/clients/{id} - should return 404 when client not found")
    void shouldReturn404WhenDeletingNonExistingClient() throws Exception {
        mockMvc.perform(delete("/api/clients/{id}", 888L))
                .andExpect(status().isNotFound());
    }

}
