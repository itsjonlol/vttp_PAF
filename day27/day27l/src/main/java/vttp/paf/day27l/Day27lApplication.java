package vttp.paf.day27l;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.paf.day27l.repo.TaskRepo;

@SpringBootApplication
public class Day27lApplication implements CommandLineRunner{
	@Autowired
	TaskRepo taskRepo;
	public static void main(String[] args) {
		SpringApplication.run(Day27lApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		// taskRepo.delete();
		taskRepo.deleteOne();
		// taskRepo.searchComments("enjoyable","fun times");
	}

}
