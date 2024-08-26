package br.com.sqs.bridge.util;

import org.springframework.stereotype.Component;

@Component
public class BridgeMessage {

    private enum Type {
	SUCCESS, ERROR, DB_CONSTRAINT_BLOCK, NOT_ALLOWED
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

	if (e instanceof BridgeException) {
	    type = Type.NOT_ALLOWED;
	} else if (msg != null && msg.toUpperCase().contains("DUPLICATE ENTRY")) {
	    type = Type.DB_CONSTRAINT_BLOCK;
	    msg = DUPLICATE_ENTRY_MESSAGE.formatted(extrairValorDuplicadoDaMensagem(msg));
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

    public BridgeMessage handleSuccess(String msg) {
	return new BridgeMessage(Type.SUCCESS, msg);
    }

    public String getMsg() {
	return msg;
    }

    public boolean isSuccess() {
	return (type == Type.SUCCESS);
    }

    public boolean isError() {
	return (type == Type.ERROR);
    }

    public boolean isNotAllowed() {
	return (type == Type.NOT_ALLOWED);
    }

    public boolean isDbConstraintBlock() {
	return (type == Type.DB_CONSTRAINT_BLOCK);
    }

}
