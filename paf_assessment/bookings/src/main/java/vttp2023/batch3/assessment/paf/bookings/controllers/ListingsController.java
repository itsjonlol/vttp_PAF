package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.Listings;
import vttp2023.batch3.assessment.paf.bookings.models.ListingsFull;
import vttp2023.batch3.assessment.paf.bookings.models.Reservations;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

@Controller
public class ListingsController {
	@Autowired
	ListingsService listingsService;

	//TODO: Task 2

	@GetMapping("/")
	public ModelAndView getView1() {
		ModelAndView mav = new ModelAndView("view1");
		Listings listings = new Listings();
		mav.addObject("listings",listings);
		List<String> countries = listingsService.getCountries();
		mav.addObject("countries",countries);
		return mav;
	}
	
	
	//TODO: Task 3
	@GetMapping("/search")
	public ModelAndView getView2(@RequestParam MultiValueMap<String,String> params,HttpSession session) {
		ModelAndView mav = new ModelAndView("view2");
		String country = params.getFirst("country");
		Integer accommodates = Integer.parseInt(params.getFirst("accommodates"));
		Integer price = Integer.parseInt(params.getFirst("price"));
		List<String> errorMessages = new ArrayList<>();
		if (country == null) {
			
			errorMessages.add("Country cannot be null or empty");
			
		}
		if (accommodates <1 || accommodates >10) {
		
			errorMessages.add("Accommodates must be between 1 and 10");
		
		}
		if (price<1 || price>10000) {
			errorMessages.add("Price must be between 1 and 10000");
			
		}
		if (!errorMessages.isEmpty()) {
        mav.setViewName("view1");
        mav.setStatus(HttpStatus.BAD_REQUEST);  // Use the correct status
        mav.addObject("errorMessages", errorMessages);  // Add all error messages
    }
		List<Listings> listings = listingsService.getListings2(country, accommodates, price);
		
		
		mav.addObject("listings",listings);
		session.setAttribute("country", country);
		session.setAttribute("accommodates", accommodates.toString());
    	session.setAttribute("price", price.toString());
		// System.out.println(session.getAttribute("country"));
		// System.out.println(session.getAttribute("accommodates"));
		// System.out.println(session.getAttribute("price"));
		
		// mav.addObject("countrysession",session.getAttribute("country"));
		// mav.addObject("accommodatessession",session.getAttribute("accommodates"));
		// mav.addObject("pricesession",session.getAttribute("price"));
		
		

		
		
    	

		return mav;
	}


	//TODO: Task 4
	@GetMapping("/details/{accommodation_id}")
	public ModelAndView getView3(@PathVariable("accommodation_id") String accommodationId,HttpSession session) {
		ModelAndView mav = new ModelAndView("view3");
		Optional<ListingsFull> optListingsFull = listingsService.getListingsDetails(accommodationId);
		System.out.println(optListingsFull.isEmpty());
		if (optListingsFull.isEmpty()) {
			mav.addObject("errorMessage","No record found bro");
			mav.setViewName("error");
			mav.addObject("errorMessage", "Accommodation not found");
			// mav.addObject("listingdetails", new ListingsFull());
			// mav.addObject("reservations", new Reservations());
			mav.setStatus(HttpStatusCode.valueOf(404));
			return mav;
		}
		mav.addObject("listingdetails",optListingsFull.get());
		mav.addObject("reservations", new Reservations());
		mav.setStatus(HttpStatusCode.valueOf(200));
		mav.addObject("countrysession",session.getAttribute("country"));
		mav.addObject("accommodatessession",session.getAttribute("accommodates"));
		mav.addObject("pricesession",session.getAttribute("price"));
		
		return mav;
	}

	//TODO: Task 5
	@PostMapping("/book/{accommodation_id}")
	public ModelAndView getView4(@PathVariable("accommodation_id") String accommodationId,@Valid @ModelAttribute("reservations") Reservations reservations,
        BindingResult result) throws Exception {
		ModelAndView mav = new ModelAndView("view4");

		// Fetch listing details
		Optional<ListingsFull> optListingsFull = listingsService.getListingsDetails(accommodationId);
		if (optListingsFull.isEmpty()) {
			mav.setViewName("error");
			mav.setStatus(HttpStatusCode.valueOf(404));
			mav.addObject("errorMessage", "Accommodation not found");
			return mav;
		}
	
		// If validation errors exist
		if (result.hasErrors()) {
			mav.setViewName("view3");
			mav.addObject("listingdetails", optListingsFull.get());
			return mav;
		}
	
		// Perform business logic (e.g., vacancy check)
		Boolean isVacant = listingsService.isVacant(accommodationId, reservations.getDuration());
		if (!isVacant) {
			mav.setViewName("view3");
			//need to add the table again to the model
			mav.addObject("listingdetails", optListingsFull.get());
			mav.addObject("errorBooking", "No vacancy available for the selected duration");
			return mav;
		}
		reservations.setAccId(accommodationId);
		listingsService.insertReservation(reservations);
		mav.addObject("reference",reservations.getResvId());
		return mav;
		
	}


}
