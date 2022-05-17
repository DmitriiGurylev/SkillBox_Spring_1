package main.java.org.example.app.services;

import org.apache.log4j.Logger;
import main.java.org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        if (book != null && bookRepo.retreiveAll().stream().noneMatch(bookOfRep ->
                bookOfRep.getAuthor().equals(book.getAuthor()) &&
                        bookOfRep.getSize().equals(book.getSize()) &&
                        bookOfRep.getTitle().equals(book.getTitle()))) {
            bookRepo.store(book);
        }
    }

    public boolean removeBookById(int bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByAuthor(String bookAuthorToRemove) {
        return bookRepo.removeItemsByAuthor(bookAuthorToRemove);
    }

    public boolean removeBookByTitle(String bookTitleToRemove) {
        return bookRepo.removeItemsByTitle(bookTitleToRemove);
    }

    public boolean removeBookBySize(Integer bookSizeToRemove) {
        return bookRepo.removeItemsBySize(bookSizeToRemove);
    }

    public List<Book> filterBooksByAuthor(String bookAuthorToRemove) {
        return bookRepo.filterItemsByAuthor(bookAuthorToRemove);
    }

    public List<Book> filterBooksByTitle(String bookTitleToRemove) {
        return bookRepo.filterItemsByTitle(bookTitleToRemove);
    }

    public List<Book> filterBooksBySize(Integer bookSizeToFilter) {
        return bookRepo.filterItemsBySize(bookSizeToFilter);
    }

    public void defaultInit(){
        logger.info("default INIT in book service");
    }

    public void defaultDestroy(){
        logger.info("default DESTROY in book service");
    }


}