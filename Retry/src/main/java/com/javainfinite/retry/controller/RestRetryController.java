package com.javainfinite.retry.controller;

import com.javainfinite.retry.exception.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/RestRetry")
public class RestRetryController {

    @RequestMapping(value = "/retry")
    @Retryable(value = {RetryException.class}, maxAttempts = 4, label = "retry API", backoff = @Backoff(delay = 5000))
    public void RetryTest() throws RetryException {
        System.out.println("Retrying Attempt...");
        int i = 0;
        if (i == 0) {
            throw new RetryException("Failed retry Again....");
        }
    }

    @RequestMapping(value = "/retryExclude")
    @Retryable(value = {RetryException.class}, maxAttempts = 4, exclude = RetryException.class, label = "retry API", backoff = @Backoff(delay = 5000))
    public void RetryTestExclude() throws Exception {
        System.out.println("Retrying Attempt...");
        int i = 0;
        if (i == 0) {
            throw new RetryException("Failed retry Again....");
        }

    }

    @Recover
    public void connectionException(RetryException e) {
        System.out.println("Retry failure");
    }

}
