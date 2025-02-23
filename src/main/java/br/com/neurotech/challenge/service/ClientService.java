package br.com.neurotech.challenge.service;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.Client;

import java.util.List;

public interface ClientService {

	Client save(Client client);
	
	Client getClientById(Long id);

	List<String> listCustomersAptosCreditoFixoHatch();
}
