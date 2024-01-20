package br.com.sqs.bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sqs.bridge.model.entity.AReceber;

public interface AReceberRepository extends JpaRepository<AReceber, Long> {

    @Query(" SELECT a FROM AReceber a JOIN FETCH a.cliente "
	   + " WHERE a.createdBy = :createdBy ORDER BY a.id DESC ")
    List<AReceber> findByCreatedByWithCliente(@Param("createdBy") String createdBy);

}
