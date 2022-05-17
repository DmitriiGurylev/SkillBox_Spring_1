package main.java.org.example.web.controllers;

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
        model.addAttribute("bookParam", new BookParam());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookParam", new BookParam());
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("book can't be saved");
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("book saved");
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_id")
    public String removeById(@RequestParam String removeInput,  Model model) {
        if (removeInput.isEmpty() || !inputIsDigit(removeInput)) {
            model.addAttribute("book", new Book());
            if (!inputIsDigit(removeInput)) {
                model.addAttribute("removeInputIdError", true);
            }
            model.addAttribute("removeInputId", removeInput);
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong ID or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookById(Integer.parseInt(removeInput));
            logger.info("Book removed by ID");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_author")
    public String removeByAuthor(@RequestParam String removeInput,  Model model) {
        if (removeInput.isEmpty()) {
            model.addAttribute("book", new Book());
            model.addAttribute("removeInputAuthor", removeInput);
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Author name or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookByAuthor(removeInput);
            logger.info("Books removed by Author");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_title")
    public String removeByTitle(@RequestParam String removeInput,  Model model) {
        if (removeInput.isEmpty()) {
            model.addAttribute("book", new Book());
            model.addAttribute("removeInputTitle", removeInput);
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Title name or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookByTitle(removeInput);
            logger.info("Books removed by Title");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processRemovingForm", params="remove_by_size")
    public String removeBySize(@RequestParam String removeInput,  Model model) {
        if (removeInput.isEmpty() || !inputIsDigit(removeInput)) {
            model.addAttribute("book", new Book());
            if (!inputIsDigit(removeInput)) {
                model.addAttribute("removeInputSizeError", true);
            }
            model.addAttribute("removeInputSize", removeInput);
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Size number or Not Found");
            return "book_shelf";
        } else {
            bookService.removeBookBySize(Integer.valueOf(removeInput));
            logger.info("Book removed by Size number");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping(value="/processFilteringForm", params="filter_by_author")
    public String filterByAuthor(@RequestParam String filterInput,  Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("filterInputAuthor", filterInput);
        model.addAttribute("bookList", bookService.getAllBooks());
        if (filterInput.isEmpty()) {
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Author name or Not Found");
        } else {
            model.addAttribute("bookList", bookService.filterBooksByAuthor(filterInput));
            logger.info("Books filtered by Author name");
        }
        return "book_shelf";
    }

    @PostMapping(value="/processFilteringForm", params="filter_by_title")
    public String filterByTitle(@RequestParam String filterInput,  Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("filterInputTitle", filterInput);
        model.addAttribute("bookList", bookService.getAllBooks());
        if (filterInput.isEmpty()) {
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Title name or Not Found");
        } else {
            model.addAttribute("bookList",   bookService.filterBooksByTitle(filterInput));
            logger.info("Books filtered by Title name");
        }
        return "book_shelf";
    }

    @PostMapping(value="/processFilteringForm", params="filter_by_size")
    public String filterBySize(@RequestParam String filterInput, Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("filterInputSize", filterInput);
        model.addAttribute("bookList", bookService.getAllBooks());
        if (filterInput.isEmpty() || !inputIsDigit(filterInput)) {
            if (!inputIsDigit(filterInput)) {
                model.addAttribute("filterInputSizeError", true);
            }
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("Wrong Size number or Not Found");
        } else {
            model.addAttribute("bookList",  bookService.filterBooksBySize(Integer.valueOf(filterInput)));
            logger.info("Books filtered by Size number");
        }
        return "book_shelf";
    }

    @PostMapping("/show-all-values")
    public String showAllValues() {
        logger.info("Books are not filtered now");
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file,  Model model) throws Exception {
        if (file.getOriginalFilename().equals("")) {
            model.addAttribute("emptyFile", true);
            model.addAttribute("book", new Book());
            model.addAttribute("bookParam", new BookParam());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
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

    private boolean inputIsDigit(String input) {
        return input.chars().allMatch(Character::isDigit);
    }

    private boolean inputIsLetter(String input) {
        return input.chars().allMatch(Character::isLetter);
    }

}
