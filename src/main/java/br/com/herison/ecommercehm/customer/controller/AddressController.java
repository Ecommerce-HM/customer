package br.com.herison.ecommercehm.customer.controller;

import br.com.herison.ecommercehm.customer.controller.documentation.AddressSwagger;
import br.com.herison.ecommercehm.customer.dtos.AddressDto;
import br.com.herison.ecommercehm.customer.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController implements AddressSwagger {

    private final AddressService addressService;

    @PostMapping("/{clientId}")
    public ResponseEntity<AddressDto> createAddresses(@Valid @RequestBody AddressDto addressDto, @PathVariable("clientId") Long clientId){
        return ResponseEntity.ok(addressService.save(addressDto, clientId));
    }

    @GetMapping("/{code}")
    public ResponseEntity<List<AddressDto>> getAddressById(@PathVariable Long code) {
        return ResponseEntity.ok(addressService.findByAddress(code));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
