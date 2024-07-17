package com.spring.security.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class TestAsyncService1 {

  @Async
  public void asyncMethodWithVoidReturnType() {
    // Simulate long-running task
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Async method completed");
    System.out.println("Execute method asynchronously. "
        + Thread.currentThread().getName());
  }

  @Async
  public Future<String> asyncMethodWithReturnType() {
    System.out.println("Execute method asynchronously - "
        + Thread.currentThread().getName());
    try {
      Thread.sleep(5000);
      System.out.println("Done");
      return new AsyncResult<>("hello world !!!!");
    } catch (InterruptedException e) {
      //
    }
    return null;
  }

  @Async
  public CompletableFuture<String> asyncGetData() throws InterruptedException {
    System.out.println("TestAsyncService1: Execute asyncGetData method asynchronously " + Thread.currentThread()
        .getName());
    Thread.sleep(4000);
    return new AsyncResult<>(super.getClass().getSimpleName() + " response !!! ").completable();
  }
}
