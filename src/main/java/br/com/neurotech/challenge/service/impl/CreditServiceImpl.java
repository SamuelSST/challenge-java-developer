package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService {

    @Override
    public boolean checkCredit(Client client, VehicleModel model) {
        if (client == null) {
            return false;
        }

        return switch (model) {
            case HATCH ->
                    client.getIncome() >= 5000 && client.getIncome() <= 15000;
            case SUV ->
                    client.getIncome() > 8000 && client.getAge() > 20;
            default -> false;
        };
    }
}
