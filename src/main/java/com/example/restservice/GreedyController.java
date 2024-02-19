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


	@GetMapping("/memory/consume")
	public String consumeMemory() { 
			try {
			// Define the size of the array to consume memory
			long arraySize = 100; // Adjust the size as needed

			// Allocate memory by creating a large array
			byte[] byteArray = new byte[(int) arraySize];
			System.out.println("Allocated " + (arraySize / (1024 * 1024)) + " MB of memory.");
			
			// Optionally, populate the array to ensure memory is actually used
			for (int i = 0; i < byteArray.length; i++) {
					byteArray[i] = (byte) i;
			}
			sleep(3);
			return "Freeing (consumed " + (arraySize / (1024 * 1024)) + " MB of memory)";
		} catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}
}
