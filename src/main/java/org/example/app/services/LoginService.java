package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginService {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    private final Logger logger = Logger.getLogger(LoginService.class);

    public boolean authenticate(LoginForm loginForm) {
        logger.info("try auth with user-form: " + loginForm);
        inMemoryUserDetailsManager.createUser(new User("root","123", new ArrayList<>()));
        return inMemoryUserDetailsManager.userExists(loginForm.getUsername())
                && inMemoryUserDetailsManager.loadUserByUsername(loginForm.getUsername())
                .getPassword().equals(loginForm.getPassword());
    }

    public boolean signUp(LoginForm loginForm) {
        if (inMemoryUserDetailsManager.userExists(loginForm.getUsername())) {
            return false;
        }
        else{
            inMemoryUserDetailsManager
                    .createUser(new User(loginForm.getUsername(), loginForm.getPassword(), new ArrayList<>()));
            logger.info("username: "+loginForm.getUsername()+", password: "+loginForm.getPassword());
            return true;
        }
    }
}
