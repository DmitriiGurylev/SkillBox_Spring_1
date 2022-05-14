package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Controller

@RequestMapping(value = "/books")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookToRemove", new BookToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_id")
    public String removeById(@Valid BookToRemove bookToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong ID or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookById(Integer.parseInt(bookToRemove.getParam()));
            logger.info("Book removed by ID");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_author")
    public String removeByAuthor(@Valid BookToRemove bookToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Author name or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookByAuthor(bookToRemove.getParam());
            logger.info("Books removed by Author");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_title")
    public String removeByTitle(@Valid BookToRemove bookToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Title name or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookByAuthor(bookToRemove.getParam());
            logger.info("Books removed by Title");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_size")
    public String removeBySize(@Valid BookToRemove bookToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Size number or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookById(Integer.parseInt(bookToRemove.getParam()));
            logger.info("Book removed by Size number");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/filter-by-author")
    public String filterBooksByAuthor(@RequestParam(value = "bookAuthorToFilter") String bookAuthorToFilter, Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("booklist", bookService.filterBooksByAuthor(bookAuthorToFilter));
        logger.info("Books filtered by Author");
//        return "redirect:/books/shelf";
        return "book_shelf";
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

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        // create Dir
        String rootPath = "/home/gur/Desktop";
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // create File to save at Server
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        logger.info("new file saved at: "+ serverFile.getAbsolutePath());
        return "redirect:/books/shelf";
    }

}
