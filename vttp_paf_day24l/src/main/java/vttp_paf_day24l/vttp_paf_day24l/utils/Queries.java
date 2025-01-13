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
                    
            insert into book (title,quantity) values (?,?)
                    """;
    public static final String SQL_GET_BOOKS = "select * from book";

    public static final String SQL_GET_BOOKS_BY_ID = "select * from book where id = ?";

    public static final String SQL_UPDATE_BOOK_BY_ID = "update book set title = ?, quantity = ? where id = ?";

    public static final String SQL_UPDATE_BOOK_STATUS_BY_ID = "update book set isActive = ? where id = ?";

}
