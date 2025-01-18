package vttp_paf_day24l.vttp_paf_day24l.utils;

public class Queries {
    public static final String SQL_CREATEACCOUNT = """
            insert into BankAccount(fullName,isActive,balance)
            values (?,?,?)

            """;

    public static final String SQL_SELECTALLBANKACCOUNT = """
            
            select * from BankAccount
            """;

    public static final String SQL_SELECTBYBANKACCOUNTID = """
            select * from BankAccount where id = ?
            """;

    public static final String SQL_DELETEBANKACCOUNTBYID = """
            
            update BankAccount set isActive = false where id = ?
            """;

    public static final String SQL_UPDATEBANKACCOUNTBYID = """
            update BankAccount set balance = ? where id = ?
            """;
    public static final String SQL_CHECKACCOUNTEXISTS = """
            
            select count(*) as cnt from BankAccount where id = ?
            """;

    public static final String SQL_CREATE_BOOK = """
                    
            insert into Book (title,quantity) values (?,?)
                    """;
    public static final String SQL_GET_BOOKS = "select * from Book";

    public static final String SQL_GET_BOOKS_BY_ID = "select * from Book where id = ?";

    public static final String SQL_UPDATE_BOOK_BY_ID = "update Book set title = ?, quantity = ? where id = ?";

    public static final String SQL_UPDATE_BOOK_STATUS_BY_ID = "update Book set is_active = ? where id = ?";

    public static final String SQL_INSERT_RESERVATION = """
                    
        insert into Reservation(full_name,reserve_date) values (?,?)
                    """;

    public static final String SQL_RESERVATION_DETAIL = """
                    insert into ReservationDetail(book_id,reservation_id) values (?,?)
                    """;

}
