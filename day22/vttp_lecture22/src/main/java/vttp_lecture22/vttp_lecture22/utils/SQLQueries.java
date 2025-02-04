package vttp_lecture22.vttp_lecture22.utils;

public class SQLQueries {
    
    public static final String SQL_AUTHOR_SUMMARY = """
            
    select count(title) as number_books,author,avg(stars) as bk_avg,avg(price) from kindle_data_v2 kdv
    group by author
    having author != "" and number_books > ?
    order by number_books DESC 
    limit ?
            """;
}
