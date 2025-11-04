package br.com.herison.ecommercehm.customer.controller.documentation;

import br.com.herison.ecommercehm.customer.dtos.AddressDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Addresses", description = "Management of addresses linked to clients")
public interface AddressSwagger {

    @Operation(summary = "Creates or updates an address for a specific client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address created or updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<AddressDto> createAddresses(
            @RequestBody @Parameter(description = "Address to be created or updated", required = true)
            AddressDto addressDto,

            @PathVariable @Parameter(description = "Client ID to associate address with", required = true)
            Long clientId
    );

    @Operation(summary = "Search addresses by customer code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDto.class)))),
            @ApiResponse(responseCode = "404", description = "No addresses found")
    })
    ResponseEntity<List<AddressDto>> getAddressById(
            @PathVariable @Parameter(description = "Customer ID to search addresses", required = true)
            Long code
    );

    @Operation(summary = "Remove an address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address removed"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "Address ID to remove", required = true)
            Long id
    );
}
