package com.learning.casestudy.subscriptionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="api-gateway-service") 
@RibbonClient(name="book-service")
public interface BookServiceProxy {
     @GetMapping("/book-service/books")
     public Book[] retrieveBooks();
}
