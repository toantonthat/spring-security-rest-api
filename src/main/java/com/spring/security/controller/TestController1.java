package com.spring.security.controller;

import com.spring.security.service.TestAsyncService1;
import com.spring.security.service.TestAsyncService2;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test1")
public class TestController1 {

  @Autowired
  private TestAsyncService1 asyncService1;

  @Autowired
  private TestAsyncService2 asyncService2;

  @GetMapping("/async1")
  public String callAsyncMethod1() {
    asyncService1.asyncMethodWithVoidReturnType();
    return "Async method called";
  }

  @GetMapping("/async2")
  public String callAsyncMethod2() throws ExecutionException, InterruptedException {
    System.out.println("Invoking an callAsyncMethod2 method. "
        + Thread.currentThread().getName());
    Future<String> future = asyncService1.asyncMethodWithReturnType();
    String result = future.get();
    System.out.println("result " + result);

    return result;
  }

  @GetMapping("/async3")
  public String callAsyncMethod3() throws ExecutionException, InterruptedException {
    System.out.println("Invoking an callAsyncMethod3 method. "
        + Thread.currentThread().getName());
    CompletableFuture<String> response1 = asyncService1.asyncGetData();
    CompletableFuture<String> response2 = asyncService2.asyncGetData();

    CompletableFuture<String> resultFuture = response1.thenCompose(value1 -> response2.thenApply(value2 -> value1 + value2));

    String result = StringUtils.SPACE;
    while (true) {
      if (resultFuture.isDone()) {
        System.out.println("Result from asynchronous process - " + resultFuture.get());
        result = resultFuture.get();
        break;
      }
      System.out.println("Continue doing something else. ");
      Thread.sleep(1000);
    }
    System.out.println("result " + result);

    return result;
  }

  @GetMapping("/async4")
  public String callAsyncMethod4() throws ExecutionException, InterruptedException {
    System.out.println("Invoking an callAsyncMethod4 method. "
        + Thread.currentThread().getName());
    Future<String> future = asyncService1.asyncMethodWithCustomExecutor();
    String result = future.get();
    System.out.println("result " + result);

    return result;
  }
}
