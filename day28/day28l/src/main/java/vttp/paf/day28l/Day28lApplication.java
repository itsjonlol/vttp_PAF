package vttp.paf.day28l;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.paf.day28l.repo.BGGRepo;
import vttp.paf.day28l.repo.SeriesRepo;

@SpringBootApplication
public class Day28lApplication implements CommandLineRunner{
	@Autowired
	private BGGRepo bggRepo;
	@Autowired
	private SeriesRepo seriesRepo;
	public static void main(String[] args) {
		SpringApplication.run(Day28lApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		List<Document> results = bggRepo.findGamesByName("carcassonne");

		// results.forEach(d -> {
		// 	System.out.println(d.toJson());
		// });
		// List<Document> results2 = bggRepo.groupCommentsByUser();
		// results2.forEach(d-> {
		// 	System.out.println(d.toJson());
		// });
		List<Document> results3 = seriesRepo.listSeriesByGenres();
		results3.forEach(d-> {
			System.out.println(d.toJson());
		});
	}

}
