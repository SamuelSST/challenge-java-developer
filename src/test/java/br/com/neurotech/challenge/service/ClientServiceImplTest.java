package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.controller.dto.response.ClientResponseDTO;
import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CreditService creditService;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(1L);
        client.setName("Samuel");
        client.setAge(30);
        client.setIncome(10000.0);
    }

    @Test
    void testSaveClient() {
        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.save(client);

        assertNotNull(savedClient);
        assertEquals("Samuel", savedClient.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testGetClientById() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));

        Client foundClient = clientService.getClientById(1L);

        assertNotNull(foundClient);
        assertEquals("Samuel", foundClient.getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetClientByIdNotFound() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Client foundClient = clientService.getClientById(1L);

        assertNull(foundClient);
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testListCustomersEligibleForFixedCreditHatch() {
        Client client1 = new Client();
        client1.setName("Client 1");
        client1.setAge(30);
        client1.setIncome(10000.0);

        Client client2 = new Client();
        client2.setName("Client 2");
        client2.setAge(35);
        client2.setIncome(2000.0);

        List<Client> clients = Arrays.asList(client1, client2);
        when(clientRepository.findByAgeBetween(23, 49)).thenReturn(clients);
        when(creditService.checkCredit(client1, VehicleModel.HATCH)).thenReturn(true);
        when(creditService.checkCredit(client2, VehicleModel.HATCH)).thenReturn(false);

        List<ClientResponseDTO> eligibleClients = clientService.listCustomersAptosCreditoFixoHatch();

        assertNotNull(eligibleClients);

        assertEquals(1, eligibleClients.size());

        ClientResponseDTO firstClient = eligibleClients.get(0);
        String formattedClient = firstClient.getName() + " - R$ " + firstClient.getIncome();

        assertEquals("Client 1 - R$ 10000.0", formattedClient);

        verify(clientRepository, times(1)).findByAgeBetween(23, 49);
        verify(creditService, times(1)).checkCredit(client1, VehicleModel.HATCH);
        verify(creditService, times(1)).checkCredit(client2, VehicleModel.HATCH);
    }

    @Test
    void testListCustomersEligibleForFixedCreditHatchNoClients() {
        when(clientRepository.findByAgeBetween(23, 49)).thenReturn(Arrays.asList());

        List<ClientResponseDTO> eligibleClients = clientService.listCustomersAptosCreditoFixoHatch();

        assertNotNull(eligibleClients);
        assertTrue(eligibleClients.isEmpty());
        verify(clientRepository, times(1)).findByAgeBetween(23, 49);
    }
}
