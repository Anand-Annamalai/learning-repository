package com.msg.app.service;

import static com.msg.app.config.MessageConstants.MESSAGE_CACHE_KEY_GEN_NAME;
import static com.msg.app.config.MessageConstants.MESSAGE_CACHE_NAME;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.msg.app.model.Message;

@Component
@CacheConfig(cacheNames = MESSAGE_CACHE_NAME, keyGenerator = MESSAGE_CACHE_KEY_GEN_NAME)
public class MessageService {
	private static Map<String, Message> messageStore = new HashMap<>();
	static { loadMessage(); }

	public List<Message> getAllMessages() {
		return messageStore.values()
				.stream()
				.collect(Collectors.toList());
	}
	
	public Message addMessage(Message message) {
		System.out.println(new Date() +", Add message: " + message);
		return messageStore.put(message.getMessageId(), message);
	}

	//@Cacheable(keyGenerator = MESSAGE_CACHE_KEY_GEN_NAME) 
	// overriding custom key generator here for demonstration purpose
	@Cacheable(key = "#messageId")
	public Message getMessageById(String messageId) {
		System.out.println(new Date() +", Get message: " + messageId);
		return messageStore.getOrDefault(messageId, 
				new Message("000", "No message found", null));

	}

	@CachePut(key = "#message.messageId")
	public Message updateMessage(Message message) {
		System.out.println(new Date() +", Update message: " + message);
		return messageStore.put(message.getMessageId(), message);
	}

	@CacheEvict(key = "#messageId")
	public Message deleteMessage(String messageId) {
		if (messageStore.containsKey(messageId)) {
			System.out.println("Delete message: " + messageId);
			return messageStore.remove(messageId);
		}
		return null;
	}

	private static void loadMessage() {
		IntStream.range(1, 5)
			.forEach(id -> {
				Message message = new Message(id + "", 
						"Message Discription-" + id, 
						(id % 2 == 0 ? "Private" : "Public"));
				messageStore.put(message.getMessageId(), message);
		});
	}
}
