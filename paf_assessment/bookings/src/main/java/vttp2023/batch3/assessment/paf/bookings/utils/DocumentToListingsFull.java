package vttp2023.batch3.assessment.paf.bookings.utils;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import vttp2023.batch3.assessment.paf.bookings.models.ListingsFull;

public class DocumentToListingsFull {
    
    public static ListingsFull toListingsFullFromDoc(Document document) {
        ListingsFull listingsFull = new ListingsFull();
        listingsFull.setAccommodationId(document.getString("_id"));
        listingsFull.setDescription(document.getString("description"));
        listingsFull.setPrice(document.getDouble("price").intValue());
       
        listingsFull.setImage(document.getString("image"));

        List<String> address = new ArrayList<>();
        String address_street = document.getString("address_street");
        String address_suburb = document.getString("address_suburb");
        String address_country = document.getString("address_country");
        address.add(address_street);
        address.add(address_suburb);
        address.add(address_country);
        listingsFull.setAddress(address);

        List<String> amenities = document.getList("amenities", String.class);
        listingsFull.setAmenities(amenities);
        return listingsFull;
    }
}
