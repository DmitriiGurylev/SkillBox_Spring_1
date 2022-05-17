package main.java.org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import main.java.org.example.exceptions.BookShelfLoginException;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        if (loginService.authenticate(loginForm)) {
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login FAIL redirect back to login");
            throw new BookShelfLoginException("invalid username or password");
        }
    }

    @PostMapping("/sign-up")
    public String signUp(LoginForm loginForm) throws BookShelfLoginException {
        if (loginForm.getUsername().equals("") || loginForm.getPassword().equals("")) {
            logger.info("Can't create user with invalid username or password");
            throw new BookShelfLoginException("invalid username or password");
        } else {
            logger.info(loginService.signUp(loginForm) ? "user created" : "user exists already");
            logger.info("sign-up redirect to book shelf");
            return "redirect:/login";
        }
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/404";
    }

}
