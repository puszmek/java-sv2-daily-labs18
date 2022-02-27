package day02;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class BooksRepository {

    private JdbcTemplate jdbcTemplate;

    public BooksRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertBook(String writer, String title, int price, int pieces) {
        jdbcTemplate.update("INSERT INTO books (writer, title, price, pieces) VALUES(?, ?, ?, ?)", writer, title, price, pieces);
    }

    public List<Book> findBooksByAuthor(String prefix) {
        return jdbcTemplate.query("SELECT * FROM books WHERE writer LIKE ?",
                (rs, rowNum) ->
                        new Book(rs.getLong("id"), rs.getString("writer"), rs.getString("title"), rs.getInt("price"), rs.getInt("pieces")),
                prefix + "%");
    }

    public void updatePieces(long id, int pieces) {
        jdbcTemplate.update("UPDATE books SET pieces = pieces + ? WHERE id = ?", pieces, id);
    }
}
