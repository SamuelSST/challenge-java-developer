package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.controller.dto.response.ClientResponseDTO;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.Client;

import java.util.List;

public interface ClientService {

	Client save(Client client);
	
	Client getClientById(Long id);

	List<ClientResponseDTO> listCustomersAptosCreditoFixoHatch();
}
