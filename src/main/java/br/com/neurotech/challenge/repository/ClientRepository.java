package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByAgeBetween(int ageMin, int ageMax);
}
