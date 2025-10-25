package br.com.herison.ecommercehm.customer.controller;

import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> save(ClientDto clientDto){
        return ResponseEntity.ok(clientService.save(clientDto));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long code) {
        return ResponseEntity.ok(clientService.findById(code));
    }
}
