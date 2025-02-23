package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.Client;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.VehicleModel;

public interface CreditService {

	boolean checkCredit(Client client, VehicleModel model);
}
