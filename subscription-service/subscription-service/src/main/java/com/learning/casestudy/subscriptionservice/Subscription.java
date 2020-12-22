package com.learning.casestudy.subscriptionservice;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Subscription {

	@Id
	@GeneratedValue
	private Integer subscriptionId;
	private String subscriberName;
	@Temporal(TemporalType.DATE)
	private Date dateSubscribed;
	@Temporal(TemporalType.DATE)
	private Date dateReturned;
	private String bookId;
	
	public Subscription() {
	 
	}
	
	public Subscription(Integer subscriptionId, String subscriberName, Date dateSubscribed, Date dateReturned, String bookId) {
		super();
		this.subscriptionId = subscriptionId;
		this.subscriberName = subscriberName;
		this.dateSubscribed = dateSubscribed;
		this.dateReturned = dateReturned;
		this.bookId = bookId;
	}

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public Date getDateSubscribed() {
		return dateSubscribed;
	}

	public void setDateSubscribed(Date dateSubscribed) {
		this.dateSubscribed = dateSubscribed;
	}

	public Date getDateReturned() {
		return dateReturned;
	}

	public void setDateReturned(Date dateReturned) {
		this.dateReturned = dateReturned;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
		
}
