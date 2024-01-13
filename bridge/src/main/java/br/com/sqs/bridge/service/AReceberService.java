package br.com.sqs.bridge.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sqs.bridge.model.entity.AReceber;
import br.com.sqs.bridge.repository.AReceberRepository;

@Service
public class AReceberService {

    private AReceberRepository repository;

    public AReceberService(AReceberRepository repository) {
	this.repository = repository;
    }

    @Transactional
    public void salvar(AReceber aReceber) {

	// TODO fazer tratamento do cliente.

	// verificar se o cliente já existe.

	// se existir usar o que ja existe.

	// se nao cadastrar o cliente.

	// por fim salvar o a receber.

	repository.save(aReceber);
    }

}
