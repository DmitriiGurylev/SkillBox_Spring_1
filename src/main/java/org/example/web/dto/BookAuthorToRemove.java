package org.example.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookAuthorToRemove {

    @NotNull
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String id) {
        this.author = id;
    }

}
