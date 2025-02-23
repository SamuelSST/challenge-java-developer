package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.controller.dto.response.ClientResponseDTO;
import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @Autowired
    private ClientService clientService;

    @Operation(
            summary = "Verifica a elegibilidade de crédito automotivo",
            description = "Este endpoint verifica se o cliente é elegível para crédito automotivo com base no modelo do veículo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente apto para crédito"),
            @ApiResponse(responseCode = "403", description = "Cliente não apto para crédito"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}/{model}")
    public ResponseEntity<String> checkAutomotiveCredit(
            @Parameter(description = "ID do cliente para verificar a elegibilidade") @PathVariable Long id,
            @Parameter(description = "Modelo do veículo para verificar elegibilidade") @PathVariable VehicleModel model) {

        Client client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente com ID " + id + " não encontrado.");
        }

        boolean eligible = creditService.checkCredit(client, model);
        if (eligible) {
            return ResponseEntity.ok("Cliente apto para crédito automotivo");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Cliente não apto para crédito automotivo");
        }
    }

    @Operation(summary = "Lista clientes aptos para crédito fixo no modelo Hatch",
            description = "Este endpoint retorna uma lista de clientes que são elegíveis para crédito fixo com base no modelo de veículo Hatch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado para o critério de crédito.")
    })
    @GetMapping("/fixed-credit/hatch")
    public ResponseEntity<List<ClientResponseDTO>> listCustomersEligibleForFixedCreditHatch() {
        List<ClientResponseDTO> clients = clientService.listCustomersAptosCreditoFixoHatch();
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(clients);
    }
}