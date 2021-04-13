package com.msg.app.controller;

import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msg.app.model.Message;
import com.msg.app.service.MessageService;
import com.msg.app.service.MessageTransactionalService;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@Autowired
	MessageTransactionalService transactionalService;

	@PostMapping("/add")
	public ResponseEntity<Message> add(@RequestBody Message message) {
		Message addMessage = transactionalService.performTransactionUsingMessage(message,
				msg -> messageService.addMessage(msg));
		return ResponseEntity.ok(addMessage);

	}

	@PutMapping("/update")
	public String update(@RequestBody Message message) {
		Message updateMessage = transactionalService.performTransactionUsingMessage(message,
				msg -> messageService.updateMessage(msg));
		return "Updated message - " + updateMessage;
	}

	@RequestMapping("/get/{messageId}")
	public ResponseEntity<Message> getMessage(@PathVariable("messageId") String messageId) {
		Message message = transactionalService.performTransactionUsingMessageId(messageId,
				msgId -> messageService.getMessageById(msgId));
		return ResponseEntity.ok(message);
	}

	@DeleteMapping("/delete/{messageId}")
	public String delete(@PathVariable("messageId") String messageId) {
		Message deleteMessage = transactionalService.performTransactionUsingMessageId(messageId,
				msgId -> messageService.deleteMessage(msgId));
		boolean status = isNull(deleteMessage) ? false : true;
		return "MessageId " + messageId + " has " + (status ? "been deleted" : "NOT found");
	}

	@GetMapping("/get/all")
	public List<Message> getMessage() {
		return messageService.getAllMessages();
	}

}
