package br.com.sqs.bridge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sqs.bridge.entity.Funcao;
import br.com.sqs.bridge.repository.FuncaoRepository;

@Service
public class FuncaoService {

    private FuncaoRepository repository;
    private static final String FUNCAO_PADRAO = "ROLE_USUARIO";

    public FuncaoService(FuncaoRepository repository) {
	this.repository = repository;
    }

    public List<Funcao> obterFuncaoPadrao() {
	return repository.findByNome(FUNCAO_PADRAO);
    }

}
