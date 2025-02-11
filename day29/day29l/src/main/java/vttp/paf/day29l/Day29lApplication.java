package vttp.paf.day29l;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import vttp.paf.day29l.bootstraps.MessageProcessor;

@SpringBootApplication
@EnableAsync
public class Day29lApplication implements CommandLineRunner {

	@Autowired
	MessageProcessor messageProcessor;
	@Autowired @Qualifier("myredis")
	RedisTemplate<String, String> redisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Day29lApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		messageProcessor.startPoller();
		// redisTemplate.convertAndSend(
		// 	"notifications", "Application started on %s".formatted(new Date().toString()));
	}

}
