package vttp_paf_day24l.vttp_paf_day24l.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vttp_paf_day24l.vttp_paf_day24l.model.Book;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_CREATE_BOOK;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_GET_BOOKS;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_GET_BOOKS_BY_ID;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_UPDATE_BOOK_BY_ID;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_UPDATE_BOOK_STATUS_BY_ID;
@Repository
public class BookRepo {

    @Autowired
    JdbcTemplate template;

    public Boolean insertBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE_BOOK, new String[] {"id"});
                ps.setString(1,book.getTitle());
                ps.setInt(2,book.getQuantity());
                return ps;

            }
            
        };
        int createdBookId = template.update(psc,keyHolder);
       

        return createdBookId > 0;
    }

    public List<Book> getAllBooks() {
        List<Book> books = template.query(SQL_GET_BOOKS,BeanPropertyRowMapper.newInstance(Book.class));

        return books;
    }

    public Book getBookById(int bookId) {
        Book book = template.queryForObject(SQL_GET_BOOKS_BY_ID, BeanPropertyRowMapper.newInstance(Book.class),bookId);

        return book;

    }

    public Boolean updateBook(Book book) {
        int bUpdated = template.update(SQL_UPDATE_BOOK_BY_ID,book.getTitle(),book.getQuantity(),book.getId());
        return bUpdated > 0;

    }

    public Boolean updateBookStatus(Book book) {
        int bUpdated = template.update(SQL_UPDATE_BOOK_STATUS_BY_ID,book.getIsActive(), book.getId());
        return bUpdated >0;
    }


    
}
