package vttp.paf.day25l;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.paf.day25l.service.MessagePoller;

@SpringBootApplication
public class Day25lApplication implements CommandLineRunner {

	@Autowired
	private MessagePoller messagePoller;
	
	public static void main(String[] args) {
		SpringApplication.run(Day25lApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		messagePoller.start();
	}

}
