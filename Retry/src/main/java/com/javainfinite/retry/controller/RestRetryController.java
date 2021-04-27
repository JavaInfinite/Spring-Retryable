package com.javainfinite.retry.controller;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/RestRetry")
public class RestRetryController {
	
	@RequestMapping(value="/retry")
	@Retryable(value = { Exception.class }, maxAttempts = 4, label="retry API", backoff = @Backoff(delay = 5000))
	public void RetryTest() throws Exception {
		System.out.println("Retrying Attempt...");
		int i=0;
		if(i==0) {
			throw new ArithmeticException("Failed retry Again....");
		}
	}
	
	@RequestMapping(value="/retryExclude")
	@Retryable(value = { ArithmeticException.class }, maxAttempts = 4, exclude=ArithmeticException.class, label="retry API", backoff = @Backoff(delay = 5000))
	public void RetryTestExclude() throws Exception {
		System.out.println("Retrying Attempt...");
		try {
			int i = 10/0;
		} catch(ArithmeticException e) {
			System.out.println("Arithmetic Exception...Will not retry");
		} catch (Exception e) {
			System.out.println("Exception Exception...");
		}
	}
	
	@Recover
    public void connectionException(ArithmeticException e) {
        System.out.println("Retry failure");
    }

}
