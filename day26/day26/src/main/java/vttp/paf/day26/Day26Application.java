package vttp.paf.day26;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.paf.day26.repo.SeriesRepo;

@SpringBootApplication
public class Day26Application implements CommandLineRunner {

	@Autowired
	SeriesRepo seriesRepo;
	public static void main(String[] args) {
		SpringApplication.run(Day26Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		List<Document> results = seriesRepo.findSeriesByName("love");
	
		// results.stream()
		// .limit(3)
		// .forEach(d -> {
		// 	// System.out.println(d.toJson());
		// 	System.out.printf("Name: %s\n",d.getString("name"));
		// 	//genres: ["a","b","c"]
		// 	List<String> genres = d.getList("genres",String.class);
		// 	for (String g : genres) {
		// 		System.out.printf("%s ",g);
		// 	System.out.println();
		// 	//schedule: {time: "20:30"}
		// 	// Document schedule = (Document) d.get("schedule)");
		// 	// System.out.printf("time: %s\n",schedule.getString("time"));
		// 	}

			results.stream()
		.limit(3)
		.forEach(d -> {
			System.out.println(d.toJson());
	
		});
		

		
		
	}
	

}
