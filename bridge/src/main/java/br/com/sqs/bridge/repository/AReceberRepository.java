package br.com.sqs.bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sqs.bridge.model.entity.AReceber;

public interface AReceberRepository extends JpaRepository<AReceber, Long> {

    List<AReceber> findByCreatedByOrderByIdDesc(String createdBy);

}
