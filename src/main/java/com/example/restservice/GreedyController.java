package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
public class GreedyController {

	Logger logger = LoggerFactory.getLogger(GreedyController.class);


	@GetMapping("/sleep/{time}")
	public String sleep(@PathVariable(value = "time") Integer time) throws InterruptedException {
		System.out.println("Sleeping for " + time + " seconds...");
		Thread.sleep(time * 1000);
		return "Who dares to wake me up? (slept for " + time + " seconds)";

	}


	@GetMapping("/memory/consume/{mbs}/{time}")
	public String consumeMemory(@PathVariable(value = "mbs") Integer arraySize, @PathVariable(value = "time") Integer time) { 
			try {

			// Allocate memory by creating a large array
			byte[] byteArray = new byte[(int) arraySize];
			logger.info("Allocated " + (arraySize / (1024 * 1024)) + " MB of memory.");
			
			// Optionally, populate the array to ensure memory is actually used
			for (int i = 0; i < byteArray.length; i++) {
					byteArray[i] = (byte) i;
			}
			sleep(time);
			return "Freeing (consumed " + arraySize + " MB of memory)";
		} catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}
}
 