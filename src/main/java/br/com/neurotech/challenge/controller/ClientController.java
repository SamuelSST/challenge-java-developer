package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.controller.dto.request.ClientRequestDTO;
import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Registra um novo cliente", description = "Este endpoint permite registrar um novo cliente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados fornecidos.")
    })
    @PostMapping
    public ResponseEntity<Void> registerCustomer(
            @Parameter(description = "Os dados do cliente a serem registrados.")
            @RequestBody @Valid ClientRequestDTO clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setAge(clientDto.getAge());
        client.setIncome(clientDto.getIncome());

        Client clientSave = clientService.save(client);
        URI location = URI.create("/client/" + clientSave.getId());
        return ResponseEntity.created(location).header(HttpHeaders.LOCATION, location.toString()).build();

    }

    @Operation(summary = "Recupera um cliente pelo ID", description = "Este endpoint permite recuperar os dados de um cliente a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o ID fornecido.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getClientById(
            @Parameter(description = "ID do cliente a ser recuperado.")
            @PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente não encontrado com o ID fornecido.");
        }
    }
}