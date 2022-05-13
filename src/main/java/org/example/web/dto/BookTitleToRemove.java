package org.example.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookTitleToRemove {

    @NotNull
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String id) {
        this.title = id;
    }

}
