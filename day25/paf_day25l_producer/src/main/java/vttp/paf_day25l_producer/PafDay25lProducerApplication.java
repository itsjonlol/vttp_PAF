package vttp.paf_day25l_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.paf_day25l_producer.service.ProducerService;

@SpringBootApplication
public class PafDay25lProducerApplication  {

	@Autowired
	static ProducerService producerService;

	public static void main(String[] args) {
		SpringApplication.run(PafDay25lProducerApplication.class, args);
		// for (int i = 0;i <1000;i++) {
		// 	Todo todo = new Todo();
		// 	todo.setId(i);
		// 	todo.setTaskName("Task " + i);
		// 	producerService.sendMessage(todo);
		// }
		
	}

	// @Override
	// public void run(String... args) throws Exception {
	// 	// TODO Auto-generated method stub
	// 	for (int i = 0;i <1000;i++) {
	// 		Todo todo = new Todo();
	// 		todo.setId(i);
	// 		todo.setTaskName("Task " + i);
	// 		producerService.sendMessage(todo);
	// 	}
	// }



}
