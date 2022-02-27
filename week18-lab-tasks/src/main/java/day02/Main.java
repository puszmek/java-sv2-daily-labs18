package day02;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/bookstore?useUnicode=true");
            dataSource.setUser("root");
//            dataSource.setPassword("root");
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot reach database!", sqle);
        }

        Flyway flyway = Flyway.configure().locations("db/migration/bookstore").dataSource(dataSource).load();
//        flyway.clean();
        flyway.migrate();

        BooksRepository booksRepository = new BooksRepository(dataSource);
//        booksRepository.insertBook("Jane Austen", "Emma", 3200, 9);
//        booksRepository.insertBook("Jane Austen", "Buszkeseg es balitelet", 3300, 4);
//        booksRepository.insertBook("Jane Austen", "Ertelem es erzelem", 3000, 3);
        booksRepository.updatePieces(1, 30);
        System.out.println(booksRepository.findBooksByAuthor("Jane"));
    }
}