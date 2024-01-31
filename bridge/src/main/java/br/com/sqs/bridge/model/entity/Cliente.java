package br.com.sqs.bridge.model.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.sqs.bridge.util.BridgeString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cliente",
       uniqueConstraints = @UniqueConstraint(name = "unique_cliente_nome",
					     columnNames = { "nome", "created_by" }))
public class Cliente extends EntityAuditable {

    // @OneToMany >> FetchType.LAZY (padrão)
    @OneToMany(mappedBy = "cliente",
	       cascade = { CascadeType.DETACH, CascadeType.MERGE,
			   CascadeType.PERSIST, CascadeType.REFRESH })
    private List<AReceber> dividas;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "saldo", nullable = false, columnDefinition = "decimal(15,2)")
    private BigDecimal saldo;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = BridgeString.trim(nome);
    }

    public List<AReceber> getDividas() {
	return dividas;
    }

    public void setDividas(List<AReceber> dividas) {
	this.dividas = dividas;
    }

    public BigDecimal getSaldo() {
	return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
	this.saldo = saldo;
    }

    public String getObservacoes() {
	return observacoes;
    }

    public void setObservacoes(String observacoes) {
	this.observacoes = observacoes;
    }

}
