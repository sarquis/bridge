package br.com.sqs.bridge.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.sqs.bridge.util.BridgeString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cliente",
       indexes = { @Index(name = "idx_created_by", columnList = "created_by") },
       uniqueConstraints = @UniqueConstraint(name = "unique_cliente_nome",
					     columnNames = { "nome", "created_by" }))
public class Cliente extends EntityAuditable {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "saldo", nullable = false, columnDefinition = "decimal(15,2)")
    private BigDecimal saldo;

    /**
     * Data da última verificação (recalculo) de saldo.
     * 
     * Quando o usuário clicar em "editar" para visualizar os detalhes do cliente, o
     * sistema irá recalcular o saldo, somando todos os registros a receber e
     * pagamentos.
     * 
     * Essa operação só ocorrerá em intervalos mínimos de 30 dias. Este campo serve
     * para controlar e garantir esse intervalo mínimo.
     */
    @Column(name = "ultima_verificacao_saldo")
    private LocalDateTime ultimaVerificacaoSaldo;

    /**
     * Guarda a diferença encontrada no momento da verificação (recalculo) do saldo.
     */
    @Column(name = "ultimaDiferencaSaldo", columnDefinition = "decimal(15,2)")
    private BigDecimal ultimaDiferencaSaldo;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = BridgeString.trim(nome);
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

    public LocalDateTime getUltimaVerificacaoSaldo() {
	return ultimaVerificacaoSaldo;
    }

    public void setUltimaVerificacaoSaldo(LocalDateTime ultimaVerificacaoSaldo) {
	this.ultimaVerificacaoSaldo = ultimaVerificacaoSaldo;
    }

    public BigDecimal getUltimaDiferencaSaldo() {
	return ultimaDiferencaSaldo;
    }

    public void setUltimaDiferencaSaldo(BigDecimal ultimaDiferencaSaldo) {
	this.ultimaDiferencaSaldo = ultimaDiferencaSaldo;
    }

}
