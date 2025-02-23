package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clienteRepository;

    @Autowired
    private CreditService creditService;

    @Override
    public Client save(Client cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Client getClientById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<String> listCustomersAptosCreditoFixoHatch() {
        List<Client> clients = clienteRepository.findByAgeBetween(23, 49);
        List<String> result = new ArrayList<>();
        for (Client client : clients) {
            if (creditService.checkCredit(client, VehicleModel.HATCH)) {
                result.add(client.getName() + " - R$ " + client.getIncome());
            }
        }
        return result;
    }
}
