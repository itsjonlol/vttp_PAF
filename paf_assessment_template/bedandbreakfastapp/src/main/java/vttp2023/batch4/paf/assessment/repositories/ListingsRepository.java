package vttp2023.batch4.paf.assessment.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;

@Repository
public class ListingsRepository {
	
	// You may add additional dependency injections

	@Autowired
	private MongoTemplate template;

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred }) 
	 *
	 *db.listings.distinct( "address.suburb" , { "address.suburb" : { $nin : ["", null] } });
	 */
	public List<String> getSuburbs(String country) {
		Query query = new Query();
        query.addCriteria(Criteria.where("address.suburb").nin("", null));

        return template.findDistinct(query, "address.suburb", "listings", String.class);
	}

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred }) 
	 *db.listings.find({
    $and:[
        {"address.suburb":"Alexandria"},
        {accommodates: {$gte: persons}}, 
        {min_nights: {$lte: duration}},
        {price: {$lte: pricerange}} // Price should be less than or equal to 100
    ]
})
	.projection({_id:1,name:1,accommodates:1,price:1,min_nights:1})
	.sort({price:-1});
	 *
	 */
	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {

		// Create criteria to filter listings
		Criteria criteria = new Criteria(); 
		criteria = criteria.andOperator(
			// Use regex for case-insensitive match on suburb
			Criteria.where("address.suburb").regex(suburb, "i"), 
			Criteria.where("accommodates").gte(persons), 
			Criteria.where("min_nights").lte(duration), 
			Criteria.where("price").lte(priceRange) 
		);
	
		// Build the query
		Query query = Query.query(criteria)
			.with(Sort.by(Sort.Direction.DESC, "price")); // Sort by price in descending order
	
		query.fields()
			.include("_id")
			.include("name")
			.include("accommodates")
			.include("price");
	
		// Execute the query on the 'listings' collection
		List<Document> results = template.find(query, Document.class, "listings");
	
		// Check the result size for debugging
		System.out.println("Results size: " + results.size());
	
		// Map documents to AccommodationSummary objects
		List<AccommodationSummary> summaryResults = results.stream()
			.map(d -> {
				AccommodationSummary accommodationSummary = new AccommodationSummary();
				accommodationSummary.setId(d.getString("_id"));
				accommodationSummary.setName(d.getString("name"));
				accommodationSummary.setAccomodates(d.getInteger("accommodates")); // Ensure this is the correct type (integer)
				accommodationSummary.setPrice(d.get("price", Number. class).floatValue ()); // Ensure price is handled correctly
				return accommodationSummary;
			})
			.toList();
	
		// Return the list of summaries
		return summaryResults;
	}
	

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}

}
