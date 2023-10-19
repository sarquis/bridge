package br.com.sqs.bridge.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario",
       uniqueConstraints = @UniqueConstraint(name = "unique_usuario_email", columnNames = { "email" }))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "senha", nullable = false, length = 68)
    private String senha;

    @Column(name = "ativo", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean ativo;

    @ManyToMany(fetch = FetchType.LAZY,
		cascade = { CascadeType.PERSIST,
			    CascadeType.MERGE,
			    CascadeType.DETACH,
			    CascadeType.REFRESH })
    @JoinTable(name = "usuario_funcao",
	       joinColumns = @JoinColumn(name = "usuario_id",
					 foreignKey = @ForeignKey(name = "fk_usuario_funcao")),
	       inverseJoinColumns = @JoinColumn(name = "funcao_id",
						foreignKey = @ForeignKey(name = "fk_funcao_usuario")))
    private List<Funcao> funcoes;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

    public Boolean getAtivo() {
	return ativo;
    }

    public void setAtivo(Boolean ativo) {
	this.ativo = ativo;
    }

    public List<Funcao> getFuncoes() {
	return funcoes;
    }

    public void setFuncoes(List<Funcao> funcoes) {
	this.funcoes = funcoes;
    }

    @Override
    public String toString() {
	return "Usuario [id=" + id + ", email=" + email + ", ativo=" + ativo + ", funcoes=" + funcoes + "]";
    }

}
