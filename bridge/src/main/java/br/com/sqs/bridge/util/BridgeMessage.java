package br.com.sqs.bridge.util;

import org.springframework.stereotype.Component;

@Component
public class BridgeMessage {

    public enum Type {
	ERROR, NOT_ALLOWED
    }

    private Type type;
    private String msg;

    public BridgeMessage() {
    }

    private BridgeMessage(Type type, String msg) {
	this.type = type;
	this.msg = msg;
    }

    public BridgeMessage handleExepection(Exception e) {
	Type type;
	String msg = e.getMessage();

	final String SYSTEM_FAILURE_MESSAGE = "Ocorreu uma falha no sistema. Por favor, entre em contato com o suporte técnico.";
	final String DUPLICATE_ENTRY_MESSAGE = "\"%s\" já existe.";

	if (msg != null && msg.toUpperCase().contains("DUPLICATE ENTRY")) {
	    type = Type.NOT_ALLOWED;
	    msg = String.format(DUPLICATE_ENTRY_MESSAGE, extrairValorDuplicadoDaMensagem(msg));
	} else {
	    type = Type.ERROR;
	    msg = SYSTEM_FAILURE_MESSAGE;
	}

	return new BridgeMessage(type, msg);
    }

    private String extrairValorDuplicadoDaMensagem(String msg) {
	String valor = msg.substring(msg.indexOf("'") + 1);
	return valor.substring(0, valor.indexOf("'"));
    }

    public String getMsg() {
	return msg;
    }

    public boolean isError() {
	return (type == Type.ERROR);
    }

    public boolean isNotAllowed() {
	return (type == Type.NOT_ALLOWED);
    }
}
