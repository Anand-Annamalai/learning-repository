package com.msg.app.model;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String messageId;
	private String messageDes;
	private String messageType;

	public Message() {
		super();
	}

	public Message(String messageId, String messageDes, String messageType) {
		super();
		this.messageId = messageId;
		this.messageDes = messageDes;
		this.messageType = messageType;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageDes() {
		return messageDes;
	}

	public void setMessageDes(String messageDes) {
		this.messageDes = messageDes;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", messageDes=" + messageDes + ", messageType=" + messageType + "]";
	}

}
