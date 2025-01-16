package vttp_day24ws.vttp_day24ws;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import vttp_day24ws.vttp_day24ws.service.AppNameService;
import vttp_day24ws.vttp_day24ws.service.MessagePoller;

@SpringBootApplication
@EnableAsync
public class VttpDay25wsApplication implements CommandLineRunner {

	@Autowired
	private MessagePoller messagePoller;

	@Autowired
	AppNameService appNameService;

	@Autowired
	@Qualifier("myredis")
	RedisTemplate<String,String> template;
	public static void main(String[] args) {
		SpringApplication.run(VttpDay25wsApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		String name;
		Properties props = new Properties();
		if (args.length > 0) {
            name = args[0];  // Take the first argument as the name
            System.out.println("Name from command line: " + name);
			appNameService.setAppName(name);
        } else {
            System.out.println("No name provided. Using default.");
            name = "Default Name";  // Default value if no argument is provided
        }
		

		template.opsForList().rightPush("registrations", name);
		messagePoller.start();
	}

}
