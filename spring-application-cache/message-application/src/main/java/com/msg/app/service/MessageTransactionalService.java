package com.msg.app.service;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.msg.app.model.Message;

import net.sf.ehcache.TransactionController;
import net.sf.ehcache.transaction.TransactionTimeoutException;

@Service
public class MessageTransactionalService {
	
	@Autowired
	private EhCacheManagerFactoryBean cacheManager;
	
	@Autowired
	private TransactionController transactionController;
	
	@Bean
	public TransactionController getTransactionController() {
		return cacheManager.getObject().getTransactionController();
	}

	public Message performTransactionUsingMessage(Message message, Function<Message, Message> function) {
		Message responseMsg = null;
		try {
			transactionController.begin();
			responseMsg = function.apply(message);
		} catch (TransactionTimeoutException exp) {
			transactionController.rollback();
			throw exp;
		} finally {
			transactionController.commit();
		}
		return responseMsg;
	}
	
	public Message performTransactionUsingMessageId(String messageId, Function<String, Message> function) {
		Message responseMsg = null;
		try {
			transactionController.begin();
			responseMsg = function.apply(messageId);
		} catch (TransactionTimeoutException exp) {
			transactionController.rollback();
			throw exp;
		} finally {
			transactionController.commit();
		}
		return responseMsg;
	}
}
