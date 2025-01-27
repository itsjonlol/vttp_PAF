package vttp2023.batch4.paf.assessment.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ForexService {
	RestTemplate restTemplate = new RestTemplate();
	// TODO: Task 5 
	public float convert(String from, String to, float amount) {
		String currencyUrlUnformatted = "https://api.frankfurter.dev/v1/latest?base=%s";
		String currencyUrl = String.format(currencyUrlUnformatted,from.toUpperCase());
		System.out.println(to);
		try {
			ResponseEntity<String> currencyEntity = restTemplate.getForEntity(currencyUrl, String.class);
			String currencyJsonString = currencyEntity.getBody();
			InputStream is = new ByteArrayInputStream(currencyJsonString.getBytes());

			JsonReader reader = Json.createReader(is);
			JsonObject currencyJson = reader.readObject();
			Float sgdRate = (float) currencyJson.getJsonObject("rates").getJsonNumber(to.toUpperCase()).doubleValue();
			Float sgdAmount = amount*sgdRate;
			
			return sgdAmount;
		} catch (RestClientException e) {
			return -1000f;
		}

		

		
	}
}
