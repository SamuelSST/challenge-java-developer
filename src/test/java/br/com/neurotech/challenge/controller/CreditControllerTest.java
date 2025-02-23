package br.com.neurotech.challenge.controller;

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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
}