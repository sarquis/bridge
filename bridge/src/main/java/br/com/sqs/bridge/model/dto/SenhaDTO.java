package br.com.sqs.bridge.model.dto;

public class SenhaDTO {

    private String atual;
    private String nova;

    public String getNova() {
	return nova;
    }

    public void setNova(String nova) {
	this.nova = nova;
    }

    public String getAtual() {
	return atual;
    }

    public void setAtual(String atual) {
	this.atual = atual;
    }

}
