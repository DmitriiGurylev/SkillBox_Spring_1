package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        if (book.getSize()<=0) {
            logger.info("size if less than 0 pages. This book won't be stored");
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-id")
    public String removeBookById(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        if (bookService.removeBookById(bookIdToRemove)) {
            logger.info("Book removed by ID");
        } else {
            logger.info("Book wasn't found in repo");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-author")
    public String removeBookByAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove) {
        if (bookService.removeBookByAuthor(bookAuthorToRemove)) {
            logger.info("Book removed by Author");
        } else {
            logger.info("Book wasn't found in repo");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-title")
    public String removeBookByTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove) {
        if (bookService.removeBookByTitle(bookTitleToRemove)) {
            logger.info("Book removed by Title");
        } else {
            logger.info("Book wasn't found in repo");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove-by-size")
    public String removeBookBySize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove) {
        if (bookService.removeBookBySize(bookSizeToRemove)) {
            logger.info("Book removed by Size");
        } else {
            logger.info("Book wasn't found in repo");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter-by-author")
    public String filterBooksByAuthor(@RequestParam(value = "bookAuthorToFilter") String bookAuthorToFilter, Model model) {
        logger.info("Books filtered by Author");
        model.addAttribute("book", new Book());
        model.addAttribute("booklist", bookService.filterBooksByAuthor(bookAuthorToFilter));
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter-by-title")
    public String filterBooksByTitle(@RequestParam(value = "bookTitleToFilter") String bookTitleToFilter, Model model) {
        logger.info("Books filtered by Title");
        model.addAttribute("book", new Book());
        model.addAttribute("booklist", bookService.filterBooksByTitle(bookTitleToFilter));
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter-by-size")
    public String filterBooksBySize(@RequestParam(value = "bookSizeToFilter") Integer bookSizeToFilter, Model model) {
        logger.info("Books filtered by Size");
        model.addAttribute("book", new Book());
        model.addAttribute("booklist", bookService.filterBooksBySize(bookSizeToFilter));
        return "redirect:/books/shelf";
    }

    @PostMapping("/show-all-values")
    public String showAllValues() {
        logger.info("Books are not filtered now");
        return "redirect:/books/shelf";
    }
}
