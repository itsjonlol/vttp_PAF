package main.java.vttp_lecture22ws.vttp_lecture22ws.repo;

public class Queries {
    
    public static final String SQL_GET_ALL_RSVP = """
                SELECT * FROM rsvp
            """;

    public static final String SQL_RSVP_BY_EMAIL = """
        SELECT * FROM rsvp WHERE email = ?
    """;

    public static final String SQL_ADD_RSVP = """
            
            INSERT INTO rsvp 
            VALUES (?,?,?,?);
            """;

    public static final String SQL_UPDATE_RSVP = """
    
    UPDATE rsvp 
    SET email = ?, phone = ?, confirmdate = ?, comments = ?
    WHERE email like ?
    """;


}

