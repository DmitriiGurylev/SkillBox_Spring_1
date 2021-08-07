package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemsByAuthor(String bookAuthorToRemove){
        List<Book> repoAuthor = retreiveAll().stream()
                .filter(book -> book.getAuthor().toLowerCase(Locale.ROOT).equals(bookAuthorToRemove.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        for (Book book : repoAuthor) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
        }
        return false;
    }

    @Override
    public boolean removeItemsByTitle(String bookTitleToRemove){
        List<Book> repoTitle = retreiveAll().stream()
                .filter(book -> book.getTitle().toLowerCase(Locale.ROOT).equals(bookTitleToRemove.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        for (Book book : repoTitle) {
            logger.info("remove book completed: " + book);
            return repo.remove(book);
        }
        return false;
    }

    @Override
    public boolean removeItemsBySize(Integer bookSizeToRemove){
        List<Book> repoSize = retreiveAll().stream()
                .filter(book -> book.getSize().equals(bookSizeToRemove))
                .collect(Collectors.toList());
        for (Book book : repoSize) {
            logger.info("remove book completed: " + book);
            return repo.remove(book);
        }
        return false;
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
}
