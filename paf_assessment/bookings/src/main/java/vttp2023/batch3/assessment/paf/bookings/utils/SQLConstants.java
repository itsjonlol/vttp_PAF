package vttp2023.batch3.assessment.paf.bookings.utils;

public class SQLConstants {
    
    public static final String SQL_CHECK_VACANCY= "select vacancy from acc_occupancy where acc_id = ?";

    public static final String SQL_UPDATE_VACANCY= """
            
        UPDATE acc_occupancy
        SET vacancy = vacancy - ?
        WHERE acc_id = ?;
            """;

    public static final String SQL_INSERT_RESERVATIONS = """
            insert into reservations(resv_id,name,email,acc_id,arrival_date,duration)
            values (?,?,?,?,?,?);
            """;
}
