package com.spring.security.controller;

import com.spring.security.service.TestAsyncService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test2")
public class TestController2 {

  @Autowired
  private TestAsyncService1 asyncService;

  @GetMapping("/async1")
  public String callAsyncMethod1() {
    asyncService.asyncMethodWithVoidReturnType();
    return "Async method called";
  }

  @GetMapping("/async2")
  public String callAsyncMethod2() {
    asyncService.asyncMethodWithReturnType();
    return "Async method called";
  }
}
