package br.com.herison.ecommercehm.customer.controller;

import br.com.herison.ecommercehm.customer.controller.documentation.ClientSwagger;
import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import br.com.herison.ecommercehm.customer.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController implements ClientSwagger {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto){
        return ResponseEntity.ok(clientService.save(clientDto));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long code) {
        return ResponseEntity.ok(clientService.findById(code));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable("code") Long code){
        clientService.delete(code);
        return ResponseEntity.noContent().build();
    }
}
