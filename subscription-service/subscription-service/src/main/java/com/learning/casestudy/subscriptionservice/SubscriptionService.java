package com.learning.casestudy.subscriptionservice;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	BookServiceProxy bookServiceProxy;
	
	public List<Subscription> retrieveSubscriptions() {

		List<Subscription> subscriptionList = subscriptionRepository.findAll();
		return subscriptionList;
	}
	
    @HystrixCommand(fallbackMethod="fallbackForBookRetrieval")
	public HttpStatus createSubscription(Subscription subscription) {
		Book[] books = bookServiceProxy.retrieveBooks();
		Book bookToBeUpdated = null;
		for (Book book : books) {
			if (book.getBookId().equals(subscription.getBookId())) {
				bookToBeUpdated = book;
				break;
			}
		}
		
		if (bookToBeUpdated == null || bookToBeUpdated.getAvailableCopies() == 0) {
			return HttpStatus.UNPROCESSABLE_ENTITY;
		}
		
		bookToBeUpdated.setAvailableCopies(bookToBeUpdated.getAvailableCopies() - 1);
		try {

			updateSubscription(subscription);
		} catch (Exception e) {

			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		kafkaTemplate.send("spring-kafka-book-subscription-group", bookToBeUpdated.getBookId());
		return HttpStatus.CREATED;
	}
    
    public HttpStatus fallbackForBookRetrieval(Subscription subscription) {
		return HttpStatus.INTERNAL_SERVER_ERROR;
    }
   
	@Transactional
	private void updateSubscription(Subscription subscription) {
		subscriptionRepository.save(subscription);
	}
}
