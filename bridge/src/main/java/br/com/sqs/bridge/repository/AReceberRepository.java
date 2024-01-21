package br.com.sqs.bridge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sqs.bridge.model.entity.AReceber;

public interface AReceberRepository extends JpaRepository<AReceber, Long> {

    @Query(" SELECT a FROM AReceber a JOIN FETCH a.cliente "
	   + " WHERE a.createdBy = :createdBy ORDER BY a.id DESC ")
    List<AReceber> findByCreatedByWithCliente(@Param("createdBy") String createdBy);

    @Query(" SELECT a FROM AReceber a JOIN FETCH a.cliente c "
	   + " WHERE a.createdBy = :createdBy AND c.nome LIKE %:clienteNome% "
	   + " ORDER BY c.nome ASC ")
    List<AReceber> findByCliente(@Param("clienteNome") String clienteNome, @Param("createdBy") String createdBy);

    Optional<AReceber> findByIdAndCreatedBy(Long id, String createdBy);
}
