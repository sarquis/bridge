package br.com.sqs.bridge.repository;

import java.math.BigDecimal;
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

    @Query("""
    	SELECT p FROM Pagamento p JOIN FETCH p.cliente \
    	WHERE p.createdBy = :createdBy \
    	ORDER BY p.id DESC \
    	""")
    List<Pagamento> findByCreatedByWithCliente(@Param("createdBy") String createdBy);

    @Query("""
    	SELECT p FROM Pagamento p JOIN FETCH p.cliente c \
    	WHERE p.createdBy = :createdBy AND c.nome LIKE %:clienteNome% \
    	ORDER BY c.nome ASC \
    	""")
    List<Pagamento> findByCreatedByAndClienteNomeContainingWithCliente(@Param("clienteNome") String clienteNome,
	    @Param("createdBy") String createdBy);

    @Query("""
    	SELECT p FROM Pagamento p JOIN FETCH p.cliente \
    	WHERE p.id = :id AND p.createdBy = :createdBy \
    	""")
    Optional<Pagamento> findByIdAndCreatedByWithCliente(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Query("""
    	SELECT p FROM Pagamento p \
    	WHERE p.id = :id AND p.createdBy = :createdBy \
    	""")
    Optional<Pagamento> findByIdAndCreatedBy(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Query("""
    	SELECT SUM(p.valor) FROM Pagamento p \
    	WHERE p.cliente.id = :clienteId \
    	""")
    BigDecimal valorTotalDoCliente(@Param("clienteId") Integer clienteId);

    @Query("""
    	SELECT p FROM Pagamento p \
    	WHERE p.cliente.id = :clienteId \
    	""")
    List<Pagamento> findByClienteId(@Param("clienteId") Integer clienteId);
}
