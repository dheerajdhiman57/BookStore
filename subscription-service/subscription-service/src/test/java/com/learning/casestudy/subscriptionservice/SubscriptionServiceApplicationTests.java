package com.learning.casestudy.subscriptionservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class SubscriptionServiceApplicationTests {

	@Autowired
	SubscriptionController subscriptionController;
	
	@Mock
	BookServiceProxy bookServiceProxy;
	
	@Autowired
	SubscriptionService subscriptionService;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testRetrieveSubscriptions() {
		
		List<Subscription> subscriptions = subscriptionController.retrieveSubscriptions();
		assertThat(subscriptions.size()).isEqualTo(1);
		Subscription subscription = subscriptions.get(0);
		assertThat(subscription.getSubscriptionId()).isNotEqualTo(0);
		assertThat(subscription.getBookId()).isNotBlank();
		assertThat(subscription.getSubscriberName()).isNotBlank();
		assertThat(subscription.getDateSubscribed()).isNotNull();
		assertThat(subscription.getDateReturned()).isNotNull();		
	}
	
	@Test
	public void testCreateSubscriptions() throws Exception {
		
		Subscription subscription = new Subscription(99999, "John", formatter.parse("2020-12-21"), formatter.parse("2020-12-21"), "B1001");	
		ResponseEntity<HttpStatus> statusResponse = subscriptionController.createSubscription(subscription);
		HttpStatus status = statusResponse.getBody();
		assertThat(status).isEqualTo(HttpStatus.CREATED);
		
		boolean subscriptionNotFound = true;
		List<Subscription> subscriptions = subscriptionController.retrieveSubscriptions();
		for (Subscription subscription1 : subscriptions) {
			if (subscription1.getSubscriptionId() == 101) {
				assertThat(subscription1.getBookId()).isEqualTo("B1001");
				assertThat(subscription1.getSubscriberName()).isEqualTo("Mathews");
				assertThat(subscription1.getDateSubscribed()).isEqualTo("2020-12-21");
				assertThat(subscription1.getDateReturned()).isEqualTo("2020-12-21");
				subscriptionNotFound = false;
			}
		}
		
		assertFalse(subscriptionNotFound);
	}

	@Test
	public void testCreateSubscriptions_WhenInvalidBookId() throws ParseException {
		
		Subscription subscription = new Subscription(101, "Mathews", formatter.parse("2020-12-21"), formatter.parse("2020-12-21"), "B1009");	
		ResponseEntity<HttpStatus> statusResponse = subscriptionController.createSubscription(subscription);
		HttpStatus status = statusResponse.getBody();
		assertThat(status).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		
		boolean subscriptionFound = false;
		List<Subscription> subscriptions = subscriptionController.retrieveSubscriptions();
		for (Subscription subscription1 : subscriptions) {
			if (subscription1.getSubscriptionId() == 101) {

				subscriptionFound = true;
			}
		}
		
		assertFalse(subscriptionFound);
	}
	
	@Test
	public void testCreateSubscriptions_WhenAvailableCountIsZero() throws Exception {

		subscriptionService.setBookServiceProxy(bookServiceProxy);
		Book book1 = new Book("B1001", "Test Book", "Test Author", 0, 10);
		when(bookServiceProxy.retrieveBooks()).thenReturn(new Book[] {book1});

		Subscription subscription = new Subscription(101, "Mathews", formatter.parse("2020-12-21"), formatter.parse("2020-12-21"), "B1001");
		HttpStatus status = subscriptionService.createSubscription(subscription);
		assertThat(status).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		
		boolean subscriptionFound = false;
		List<Subscription> subscriptions = subscriptionController.retrieveSubscriptions();
		for (Subscription subscription1 : subscriptions) {
			if (subscription1.getSubscriptionId() == 101) {

				subscriptionFound = true;
			}
		}
		assertFalse(subscriptionFound);
	}
}
