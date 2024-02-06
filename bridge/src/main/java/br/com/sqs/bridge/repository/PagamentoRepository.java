package br.com.sqs.bridge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sqs.bridge.model.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    /*
     * [ ATENÇÃO! ]
     * 
     * [ a.createdBy = :createdBy ]
     * 
     * * * É obrigatório. * * *
     */

    @Query("  SELECT a FROM Pagamento a JOIN FETCH a.cliente "
	   + " WHERE a.createdBy = :createdBy "
	   + " ORDER BY a.id DESC ")
    List<Pagamento> findByCreatedByWithCliente(@Param("createdBy") String createdBy);

    @Query("  SELECT a FROM Pagamento a JOIN FETCH a.cliente c "
	   + " WHERE a.createdBy = :createdBy AND c.nome LIKE %:clienteNome% "
	   + " ORDER BY c.nome ASC ")
    List<Pagamento> findByCreatedByAndClienteNomeContainingWithCliente(@Param("clienteNome") String clienteNome,
	    @Param("createdBy") String createdBy);

    @Query("  SELECT a FROM Pagamento a JOIN FETCH a.cliente "
	   + " WHERE a.id = :id AND a.createdBy = :createdBy ")
    Optional<Pagamento> findByIdAndCreatedByWithCliente(Long id, String createdBy);

    @Query("  SELECT a FROM Pagamento a "
	   + " WHERE a.id = :id AND a.createdBy = :createdBy ")
    Optional<Pagamento> findByIdAndCreatedBy(Long id, String createdBy);

}
