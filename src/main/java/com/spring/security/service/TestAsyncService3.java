package com.spring.security.service;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestAsyncService3 {

  @Autowired
  private RestTemplate restTemplate;

  @Async
  public CompletableFuture<String> callServiceBWithReturnType() {
    // Service B's endpoint
    String url = "http://localhost:8085/test1/async3";
    String response = restTemplate.getForObject(url, String.class);
    return CompletableFuture.completedFuture(response);
  }

  @Async
  public void callServiceBVoidReturnType() {
    // Service B's endpoint
    String url = "http://localhost:8085/test1/async1";
    restTemplate.getForObject(url, String.class);
  }
}
