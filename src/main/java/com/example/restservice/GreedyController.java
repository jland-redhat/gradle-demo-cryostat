package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
public class GreedyController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/sleep/{time}")
	public String sleep(@PathVariable(value = "time") Integer time) throws InterruptedException {
		System.out.println("Sleeping for " + time + " seconds...");
		Thread.sleep(time * 1000);
		return "Who dares to wake me up? (slept for " + time + " seconds)";

	}
}
