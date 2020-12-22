package com.learning.casestudy.subscriptionservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByBookId(String author);
}
