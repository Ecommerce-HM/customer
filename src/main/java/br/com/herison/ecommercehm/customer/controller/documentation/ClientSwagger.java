package br.com.herison.ecommercehm.customer.controller.documentation;


import br.com.herison.ecommercehm.customer.dtos.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Customer", description = "Customer management system")
public interface ClientSwagger {

    @Operation(summary = "Register a new customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<ClientDto> createClient(
            @RequestBody @Parameter(description = "Customer to be created", required = true)
            ClientDto clientDto
    );

    @Operation(summary = "Search customer by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    ResponseEntity<ClientDto> getClientById(
            @PathVariable @Parameter(description = "Customer code", required = true)
            Long code
    );

    @Operation(summary = "Deactivate a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "Customer code to delete", required = true)
            Long code
    );
}
