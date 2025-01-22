package vttp.paf.day27_chukws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import vttp.paf.day27_chukws.repo.CommentRepo;

@SpringBootApplication
public class Day27ChukwsApplication implements CommandLineRunner {

	@Autowired
	Environment environment;

	@Autowired
	CommentRepo commentRepo;
	public static void main(String[] args) {
		SpringApplication.run(Day27ChukwsApplication.class, args);
	}

	// /Users/jonathansuherman/Downloads/bgg 2/comment.json
	// /Users/jonathansuherman/Downloads/bgg2/comment.json

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		String path = environment.getProperty("load");
		String fileName = path.substring(path.lastIndexOf("/") +1);
		String collectionName = fileName.substring(0,fileName.indexOf("."));
		 
		System.out.println(path);
		System.out.println(collectionName);
		commentRepo.deleteCollection(collectionName);

		commentRepo.addCollection(collectionName, path);
		commentRepo.createTextIndex(collectionName);
		commentRepo.searchComments(collectionName, "enjoyable","fun times");

	}

}
