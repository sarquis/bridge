package br.com.sqs.bridge.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sqs.bridge.model.entity.AReceber;

public interface AReceberRepository extends JpaRepository<AReceber, Long> {

    /*
     * [ ATENÇÃO! ]
     * 
     * [ a.createdBy = :createdBy ]
     * 
     * * * É obrigatório. * * *
     */

    @Query("""
    	SELECT a FROM AReceber a JOIN FETCH a.cliente \
    	WHERE a.createdBy = :createdBy \
    	ORDER BY a.id DESC \
    	""")
    List<AReceber> findByCreatedByWithCliente(@Param("createdBy") String createdBy);

    @Query("""
    	SELECT a FROM AReceber a JOIN FETCH a.cliente c \
    	WHERE a.createdBy = :createdBy AND c.nome LIKE %:clienteNome% \
    	ORDER BY c.nome ASC \
    	""")
    List<AReceber> findByCreatedByAndClienteNomeContainingWithCliente(@Param("clienteNome") String clienteNome,
	    @Param("createdBy") String createdBy);

    @Query("""
    	SELECT a FROM AReceber a JOIN FETCH a.cliente \
    	WHERE a.id = :id AND a.createdBy = :createdBy \
    	""")
    Optional<AReceber> findByIdAndCreatedByWithCliente(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Query("""
    	SELECT a FROM AReceber a \
    	WHERE a.id = :id AND a.createdBy = :createdBy \
    	""")
    Optional<AReceber> findByIdAndCreatedBy(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Query("""
    	SELECT SUM(a.valor) FROM AReceber a \
    	WHERE a.cliente.id = :clienteId \
    	""")
    BigDecimal valorTotalDoCliente(@Param("clienteId") Integer clienteId);

    @Query("""
    	SELECT a FROM AReceber a \
    	WHERE a.cliente.id = :clienteId \
    	""")
    List<AReceber> findByClienteId(@Param("clienteId") Integer clienteId);
}
