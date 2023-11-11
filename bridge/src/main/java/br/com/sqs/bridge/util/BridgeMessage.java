package br.com.sqs.bridge.util;

public class BridgeMessage {

    public enum Type {
	ERROR, ALERT, INFO
    }

    private Type type;
    private String msg;
    private boolean show;

    public BridgeMessage() {
	this.show = false;
    }

    public BridgeMessage(Type type, String msg) {
	this.type = type;
	this.msg = msg;
	this.show = true;
    }

    public String getMsg() {
	return msg;
    }

    public boolean isShow() {
	return show;
    }

    public boolean isError() {
	return (type == Type.ERROR);
    }

    public boolean isAlert() {
	return (type == Type.ALERT);
    }

    public boolean isInfo() {
	return (type == Type.INFO);
    }

}
