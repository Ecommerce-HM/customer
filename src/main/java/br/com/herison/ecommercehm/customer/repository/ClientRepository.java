package br.com.herison.ecommercehm.customer.repository;

import br.com.herison.ecommercehm.customer.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCodeAndActiveTrue(Long code);
}
