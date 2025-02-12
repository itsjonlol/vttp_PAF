package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.exceptions.BookingErrorException;
import vttp2023.batch3.assessment.paf.bookings.models.Listings;
import vttp2023.batch3.assessment.paf.bookings.models.ListingsFull;
import vttp2023.batch3.assessment.paf.bookings.models.Reservations;
import static vttp2023.batch3.assessment.paf.bookings.utils.DocumentToListingsFull.toListingsFullFromDoc;
import static vttp2023.batch3.assessment.paf.bookings.utils.MongoConstants.C_LISTINGS;
import static vttp2023.batch3.assessment.paf.bookings.utils.MongoConstants.F_ACCOMMODATES;
import static vttp2023.batch3.assessment.paf.bookings.utils.MongoConstants.F_ACCOMMODATION_ID;
import static vttp2023.batch3.assessment.paf.bookings.utils.MongoConstants.F_ADDRESS_COUNTRIES;
import static vttp2023.batch3.assessment.paf.bookings.utils.MongoConstants.F_PRICE;
import static vttp2023.batch3.assessment.paf.bookings.utils.SQLConstants.SQL_CHECK_VACANCY;
import static vttp2023.batch3.assessment.paf.bookings.utils.SQLConstants.SQL_INSERT_RESERVATIONS;
import static vttp2023.batch3.assessment.paf.bookings.utils.SQLConstants.SQL_UPDATE_VACANCY;

@Repository
public class ListingsRepository {
	@Autowired
	MongoTemplate template;
	@Autowired
    JdbcTemplate JDBCtemplate;

	//TODO: Task 2
	/*
	 * db.listings.distinct("address.country")
	 */
	public List<String> getCountries() {
		List<String> countries = template.findDistinct(new Query(), F_ADDRESS_COUNTRIES, C_LISTINGS, String.class);

		countries.forEach(c -> System.out.println(c));
        return countries;
	}
	
	//TODO: Task 3
	/*
	 * db.listings.find({
    $and:[
        {"address.country":<country>},
        {accommodates: <accommodates>}, 
        {price: {$gte: 1,$lte:<price>}}
    ]
}).projection({_id:1,name:1,accommodates:1,price:1,"image":"$images.picture_url"})
.sort({price:-1})
	 */
	public List<Listings> getListings(String country, Integer accommodates, Integer price) {
		// Create criteria to filter listings
		Criteria criteria = new Criteria(); 
		criteria = criteria.andOperator(
			// Use regex for case-insensitive match on suburb
			Criteria.where(F_ADDRESS_COUNTRIES).regex(country, "i"), 
			Criteria.where(F_ACCOMMODATES).is(accommodates), 
			Criteria.where(F_PRICE).gte(1).lt(price)
		);
	
		// Build the query
		Query query = Query.query(criteria)
			.with(Sort.by(Sort.Direction.DESC, F_PRICE)); // Sort by price in descending order
	
		query.fields()
			.include("_id")
			.include("name")
			.include("accommodates")
			.include("price")
			.include("images.picture_url");
	
		// Else use aggregation
		List<Document> resultsDoc = template.find(query, Document.class, C_LISTINGS);
		List<Listings> resultsListings = resultsDoc.stream()
			.map(d -> {
				Listings listings = new Listings();
				listings.setAccommodationId(d.getString("_id"));
				listings.setName(d.getString("name"));
				listings.setPrice(d.getDouble("price").intValue());
				//document class for object
				listings.setImage(d.get("images", Document.class).getString("picture_url"));
				return listings;
			})
			.toList();
		
		return resultsListings;
	}
	//if want to use aggregation, can project directly, so dont need to use document class for image
	/*
	 * db.listings.aggregate([
    {
        $match:{
            "address.country": {$regex:"australia",$options:"i"},
            "accommodates":{$lt:10},
            "price":{$gt:1,$lt:10000}
        
        }
    },
    {
        $sort:{price:-1}
    },
    {
        $project:{
            _id:1,
            name:1,
            price:1,
            imageurl: "$images.picture_url"
        }
    }
])
	 */
	public List<Listings> getListings2(String country,Integer accommodates,Integer price) {

		Criteria criteria = new Criteria();
		criteria = criteria.andOperator(
			Criteria.where(F_ADDRESS_COUNTRIES).regex(country, "i"), 
			Criteria.where(F_ACCOMMODATES).is(accommodates), 
			Criteria.where(F_PRICE).gte(1).lt(price)
		);
		MatchOperation matchStage = Aggregation.match(criteria);
		SortOperation sortStage = Aggregation.sort(Sort.Direction.DESC, F_PRICE);
		ProjectionOperation projectStage = Aggregation.project("_id","name","price")
			.and("$images.picture_url").as("imageurl");

		Aggregation pipeline = Aggregation.newAggregation(
                matchStage,
                sortStage,
                projectStage
        );
        
        // Execute the aggregation
        AggregationResults<Document> results = template.aggregate(pipeline, C_LISTINGS, Document.class);
		List<Document> documents = results.getMappedResults();


		return documents.stream()
			.map(d -> {
				Listings listings = new Listings();
				listings.setAccommodationId(d.getString("_id"));
				listings.setName(d.getString("name"));
				listings.setPrice(d.getDouble("price").intValue());
				//now dont need to get inner document first.
				listings.setImage(d.getString("imageurl"));
				return listings;

			})
			.toList();
	}

	//TODO: Task 4
	/*
	 * b.listings.aggregate([
    {
        $match: {_id: <id>}
    },
    {
        $project: {
            _id:1,
            description:1,
            price:1,
            image:"$images.picture_url",
            address_street:"$address.street",
            address_suburb:"$address.suburb",
            address_country:"$address.country",
           
            amenities:1
        }
    }
])
	 */
	

	
	public Optional<ListingsFull> getListingsDetails(String accommodationId) {
		MatchOperation matchStage = Aggregation.match(Criteria.where(F_ACCOMMODATION_ID).is(accommodationId));
		

        // Build the aggregation pipeline

		ProjectionOperation projectStage = Aggregation.project()
		.and("_id").as("_id") 
		.and("description").as("description") 
		.and("amenities").as("amenities") 
		.and("price").as("price")
		.and("$images.picture_url").as("image")
		.and("$address.street").as("address_street")
		.and("address.suburb").as("address_suburb")
		.and("$address.country").as("address_country");

		Aggregation pipeline = Aggregation.newAggregation(
                matchStage,
                projectStage
        );

        // Execute the aggregation
        AggregationResults<Document> results = template.aggregate(pipeline, C_LISTINGS, Document.class);
		List<Document> documents = results.getMappedResults();
		System.out.println(documents.size());
		
		Document result = documents.get(0);

        // Get the result
        // Document result = results.getUniqueMappedResult();
		

		if (result == null) {
			
			return Optional.empty();
		}
		ListingsFull listingsFull = toListingsFullFromDoc(result);
		return Optional.of(listingsFull);
	}
	//TODO: Task 5

	

    public Integer getVacancy(String accommodationId) throws Exception {
        SqlRowSet rs = JDBCtemplate.queryForRowSet(SQL_CHECK_VACANCY, accommodationId);

        if (rs.next()) { // Move the cursor to the first row
			return rs.getInt("vacancy");
		} else {
			// Handle the case where no rows are returned
			throw new Exception("Unable to find accommodation"); // Or throw an exception if needed
		}
    }
	// insert into reservations(resv_id,name,email,acc_id,arrival_date,duration)
	// values (?,?,?,?,?,?);
	public Boolean insertReservation(Reservations reservations) {
		int iUpdated = JDBCtemplate.update(SQL_INSERT_RESERVATIONS,reservations.getResvId(),reservations.getName(),
		reservations.getEmail(),reservations.getAccId(),reservations.getArrivalDate(),reservations.getDuration());
		if (iUpdated<=0) {
			throw new BookingErrorException("failed to update");
		}
		return iUpdated >0;
	}

	public Boolean updateVacancy(String accommodationId,Integer duration) {
		int iUpdated = JDBCtemplate.update(SQL_UPDATE_VACANCY,duration,accommodationId);
		if (iUpdated<=0) {
			throw new BookingErrorException("failed to update");
		}
		return iUpdated > 0;
	}
}
