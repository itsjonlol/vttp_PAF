package vttp_paf_day24l.vttp_paf_day24l;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp_paf_day24l.vttp_paf_day24l.repo.AccountRepo;

@SpringBootApplication
public class VttpPafDay24lApplication implements CommandLineRunner {

	@Autowired
	AccountRepo accountRepo;

	public static void main(String[] args) {
		SpringApplication.run(VttpPafDay24lApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Date date = accountRepo.getLastModifiedDate("abc123");
		System.out.println(date);
		Date date2 = accountRepo.getLastModifiedDate2("abc123");
		System.out.println(date2);
	}

	

}
