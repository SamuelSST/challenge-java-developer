package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.controller.dto.request.ClientRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testRegisterCustomer() throws Exception {
        ClientRequestDTO clientDto = new ClientRequestDTO("Samuel Soares", 24, 10500.0);
        Client clientSaved = new Client(1L, "Samuel Soares", 24, 10500.0);

        when(clientService.save(Mockito.any(Client.class))).thenReturn(clientSaved);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Samuel Soares\", \"age\":24, \"income\":10500.0}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/client/1"));
    }

    @Test
    public void testGetClientById_Success() throws Exception {
        Client client = new Client(1L, "Samuel Soares", 24, 10500.0);

        when(clientService.getClientById(1L)).thenReturn(client);

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samuel Soares"))
                .andExpect(jsonPath("$.age").value(24))
                .andExpect(jsonPath("$.income").value(10500.0));
    }

    @Test
    public void testGetClientById_NotFound() throws Exception {
        when(clientService.getClientById(999L)).thenReturn(null);

        mockMvc.perform(get("/client/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado com o ID fornecido."));
    }
}