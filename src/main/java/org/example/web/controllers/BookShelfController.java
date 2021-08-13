package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookRepository;
import org.example.app.services.BookService;
import org.example.app.services.ProjectRepository;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/books")
@Scope("request")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;
    List<Book> filteredBooks;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(model.toString());
        model.addAttribute("book", new Book());
        if (filteredBooks==null) {
            model.addAttribute("bookList", bookService.getAllBooks());
        }
        else {
            model.addAttribute("bookList", filteredBooks);
        }
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-id")
    public String removeBookById(@RequestParam(value = "bookIdToRemove") String bookIdToRemove) {
        if (bookService.removeBookById(bookIdToRemove)) {
            logger.info("Book removed by ID");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-author")
    public String removeBookByAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove) {
        if (bookService.removeBookByAuthor(bookAuthorToRemove)) {
            logger.info("Book removed by Author");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-title")
    public String removeBookByTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove) {
        if (bookService.removeBookByTitle(bookTitleToRemove)) {
            logger.info("Book removed by Title");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-size")
    public String removeBookBySize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove) {
        if (bookService.removeBookBySize(bookSizeToRemove)) {
            logger.info("Book removed by Size");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter-by-author")
    public String filterBooksByAuthor(@RequestParam(value = "bookAuthorToFilter") String bookAuthorToFilter) {
        filteredBooks = bookService.filterBooksByTitle(bookAuthorToFilter);
        logger.info("Books filtered by Author");
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter-by-title")
    public String filterBooksByTitle(@RequestParam(value = "bookTitleToFilter") String bookTitleToFilter) {
        filteredBooks = bookService.filterBooksByTitle(bookTitleToFilter);
        logger.info("Books filtered by Title");
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter-by-size")
    public String filterBooksBySize(@RequestParam(value = "bookSizeToFilter") Integer bookSizeToFilter) {
        filteredBooks = bookService.filterBooksBySize(bookSizeToFilter);
        logger.info("Books filtered by Size");
        return "redirect:/books/shelf";
    }

    @PostMapping("/show-all-values")
    public String showAllValues() {
        filteredBooks = null;
        logger.info("Books are not filtered now");
        return "redirect:/books/shelf";
    }
}
