package vttp_day24ws.vttp_day24ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import vttp_day24ws.vttp_day24ws.service.MessagePoller;

@SpringBootApplication
@EnableAsync
public class VttpDay25wsApplication implements CommandLineRunner {

	@Autowired
	private MessagePoller messagePoller;


	@Autowired
    private ConfigurableEnvironment environment;

	

	@Autowired
	@Qualifier("myredis")
	RedisTemplate<String,String> template;

	public static void main(String[] args) {
		SpringApplication.run(VttpDay25wsApplication.class, args);
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		String name;
		
		if (args.length > 0) {
            name = args[0];  // Take the first argument as the name
            System.out.println("Name from command line: " + name);
			environment.getSystemProperties().put("app.name", args[0]);
			
        } else {
            System.out.println("No name provided. Using default.");
            name = "Default Name";  // Default value if no argument is provided
        }
		
		List<String> existingNames = template.opsForList().range("registrations",0,-1);
		if (existingNames != null && !existingNames.contains(name)) {
            template.opsForList().rightPush("registrations", name);
        } else {
            // Add the name to the list if it doesn't exist
           
            System.out.printf("Name '%s' added to the list.\n", name);
        }
		
		messagePoller.start();
	}

}
