package com.spring.security.controller;

import com.spring.security.service.TestAsyncService3;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test2")
public class TestController2 {

  @Autowired
  private TestAsyncService3 asyncService;

  @GetMapping("/async1")
  public String callAsyncMethod1() {
    System.out.println("Async method called 1");
    CompletableFuture<String>  completableFuture = asyncService.callServiceBWithReturnType();
    String result = "";
    try {
      // Get the result of the async call
      result = completableFuture.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return "Failed to get result";
    }
    return result;
  }

  @GetMapping("/async2")
  public String callAsyncMethod2() {
    System.out.println("Async method called 2");
    asyncService.callServiceBVoidReturnType();
    return "Request to Service B has been made";
  }
}
