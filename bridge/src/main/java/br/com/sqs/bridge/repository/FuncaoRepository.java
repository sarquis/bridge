package br.com.sqs.bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sqs.bridge.model.entity.Funcao;

public interface FuncaoRepository extends JpaRepository<Funcao, Integer> {

    List<Funcao> findByNome(String nome);

}
