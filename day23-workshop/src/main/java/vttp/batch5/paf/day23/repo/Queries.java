package vttp.batch5.paf.day23.repo;

public class Queries {
    
    

    public static final String SQL_ADD_PURCHASEORDER = """
            
            INSERT INTO purchaseorders(username,address,delivery_date) 
            VALUES (?,?,?);
            """;

    public static final String SQL_UPDATE_RSVP = """
    
    UPDATE rsvp 
    SET email = ?, phone = ?, confirmdate = ?, comments = ?
    WHERE email like ?
    """;
}
