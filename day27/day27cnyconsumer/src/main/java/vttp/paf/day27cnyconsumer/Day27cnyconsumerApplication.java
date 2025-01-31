package vttp.paf.day27cnyconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import vttp.paf.day27cnyconsumer.service.MessagePoller;

@SpringBootApplication
@EnableScheduling // Ensures @Scheduled works
public class Day27cnyconsumerApplication  {

	@Autowired
	private MessagePoller messagePoller;
	
	public static void main(String[] args) {
		SpringApplication.run(Day27cnyconsumerApplication.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {

	// 	messagePoller.start(); //starts the message polling process
	// }
}
