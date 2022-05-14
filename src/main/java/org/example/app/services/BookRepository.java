package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private ApplicationContext context;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        return jdbcTemplate.query(
                "SELECT * FROM books",
                (ResultSet rs, int rowNum) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setAuthor(rs.getString("author"));
                    book.setTitle(rs.getString("title"));
                    book.setSize(rs.getInt("size"));
                    return book;
                });
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES (:author, :title, :size)", parameterSource);
        logger.info("stored new book: " + book);
//        book.setId(context.getBean(IdProvider.class).provideId(book));
//        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed");
        return true;
    }

    @Override
    public boolean removeItemsByAuthor(String bookAuthorToRemove){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", bookAuthorToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
        logger.info("remove book completed");
        return true;
    }

    @Override
    public boolean removeItemsByTitle(String bookTitleToRemove){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", bookTitleToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
        logger.info("remove book completed");
        return true;
    }

    @Override
    public boolean removeItemsBySize(Integer bookSizeToRemove){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size", bookSizeToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
        logger.info("remove book completed");
        return true;
    }

    @Override
    public List<Book> filterItemsByAuthor(String bookAuthorToFilter){
        List<Book> repoAuthor = retreiveAll().stream()
                .filter(book -> book.getAuthor().toLowerCase(Locale.ROOT).equals(bookAuthorToFilter.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        logger.info("filter books completed: " + bookAuthorToFilter.toLowerCase(Locale.ROOT));
        return repoAuthor;
    }

    @Override
    public List<Book> filterItemsByTitle(String bookTitleToFilter){
        List<Book> repoTitle = retreiveAll().stream()
                .filter(book -> book.getTitle().toLowerCase(Locale.ROOT).equals(bookTitleToFilter.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        logger.info("filter books completed: " + bookTitleToFilter.toLowerCase(Locale.ROOT));
        return repoTitle;
    }

    @Override
    public List<Book> filterItemsBySize(Integer bookSizeToFilter){
        List<Book> repoSize = retreiveAll().stream()
                .filter(book -> book.getSize().equals(bookSizeToFilter))
                .collect(Collectors.toList());
        logger.info("filter books completed: " + bookSizeToFilter);
        return repoSize;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void defaultInit(){
        logger.info("default INIT in book repo bean");
    }

    public void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
}
