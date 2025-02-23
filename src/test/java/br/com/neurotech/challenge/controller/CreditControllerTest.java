package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.controller.dto.response.ClientResponseDTO;
import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class CreditControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @Mock
    private CreditService creditService;

    @InjectMocks
    private CreditController creditController;

    private Client client;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(creditController).build();

        client = new Client();
        client.setId(1L);
        client.setName("Samuel");
        client.setAge(24);
        client.setIncome(10500.0);
    }

    @Test
    void testCheckAutomotiveCredit_Success() throws Exception {
        when(clientService.getClientById(anyLong())).thenReturn(client);
        when(creditService.checkCredit(any(), any())).thenReturn(true);

        mockMvc.perform(get("/credit/{id}/{model}", 1L, VehicleModel.HATCH))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente apto para crédito automotivo"));
    }

    @Test
    void testCheckAutomotiveCredit_NotFound() throws Exception {
        when(clientService.getClientById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/credit/{id}/{model}", 1L, VehicleModel.HATCH))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente com ID 1 não encontrado."));
    }

    @Test
    void testCheckAutomotiveCredit_Forbidden() throws Exception {
        when(clientService.getClientById(anyLong())).thenReturn(client);
        when(creditService.checkCredit(any(), any())).thenReturn(false);

        mockMvc.perform(get("/credit/{id}/{model}", 1L, VehicleModel.HATCH))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Cliente não apto para crédito automotivo"));
    }

    @Test
    public void testListCustomersEligibleForFixedCreditHatch_Success() throws Exception {
        ClientResponseDTO client1 = new ClientResponseDTO(1L, "Cliente 1", 10500.0);
        ClientResponseDTO client2 = new ClientResponseDTO(2L, "Cliente 2", 12000.0);

        when(clientService.listCustomersAptosCreditoFixoHatch()).thenReturn(Arrays.asList(client1, client2));

        mockMvc.perform(get("/credit/fixed-credit/hatch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Cliente 1"))
                .andExpect(jsonPath("$[0].income").value(10500.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Cliente 2"))
                .andExpect(jsonPath("$[1].income").value(12000.0));
    }


    @Test
    public void testListCustomersEligibleForFixedCreditHatch_NoContent() throws Exception {
        when(clientService.listCustomersAptosCreditoFixoHatch()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/credit/fixed-credit/hatch"))
                .andExpect(status().isNoContent());
    }
}