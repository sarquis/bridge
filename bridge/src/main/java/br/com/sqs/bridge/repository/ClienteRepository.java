package br.com.sqs.bridge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sqs.bridge.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByCreatedByOrderByNome(String createdBy);

    Cliente findByNomeAndCreatedBy(String nome, String createdBy);

    List<Cliente> findByNomeContainingAndCreatedByOrderByNomeAsc(String nome, String createdBy);

    Optional<Cliente> findByIdAndCreatedBy(Integer id, String createdBy);

}
