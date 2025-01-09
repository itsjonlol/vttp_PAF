package main.java.vttp_lecture22ws.vttp_lecture22ws.repo;

public class Queries {
    
    public static final String SQL_GET_ALL_RSVP = """
                SELECT * FROM rsvp
            """;

    public static final String SQL_RSVP_BY_EMAIL = """
        SELECT * FROM rsvp WHERE email = ?
    """;

    //cannot insert duplicate keys
    public static final String SQL_ADD_RSVP = """
            
            INSERT INTO rsvp(email,phone,confirmdate,comments)
            VALUES (?,?,?,?);
            """;

    public static final String SQL_UPDATE_RSVP = """
    
    UPDATE rsvp 
    SET email = ?, phone = ?, confirmdate = ?, comments = ?
    WHERE rsvp_id = ?
    """;

    public static final String SQL_COUNT_RSVP = """
            
        SELECT count(*) rsvp_count from rsvp;
            """;

}

