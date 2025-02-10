package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2023.batch3.assessment.paf.bookings.models.Listings;
import vttp2023.batch3.assessment.paf.bookings.models.ListingsFull;
import vttp2023.batch3.assessment.paf.bookings.models.Reservations;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {
	
	@Autowired
	ListingsRepository listingsRepo;
	//TODO: Task 2
	public List<String> getCountries() {
		return listingsRepo.getCountries();

	}
	
	//TODO: Task 3
	public List<Listings> getListings(String country,Integer accommodates,Integer price ) {
		return listingsRepo.getListings(country, accommodates, price);
	}
	public List<Listings> getListings2(String country,Integer accommodates,Integer price ) {
		return listingsRepo.getListings2(country, accommodates, price);
	}

	//TODO: Task 4
	public Optional<ListingsFull> getListingsDetails(String accommodationId) {
		return listingsRepo.getListingsDetails(accommodationId);
	}

	//TODO: Task 5
	
	public Boolean isVacant(String accommodationId,Integer duration) throws Exception {
		Integer vacancies = listingsRepo.getVacancy(accommodationId);
		return vacancies >= duration;

	}

	@Transactional
	public void insertReservation(Reservations reservations) {
		listingsRepo.insertReservation(reservations);
		listingsRepo.updateVacancy(reservations.getAccId(), reservations.getDuration());
		
	}

}
