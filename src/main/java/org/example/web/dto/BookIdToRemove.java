package org.example.web.dto;

import javax.validation.constraints.NotEmpty;

public class BookIdToRemove {

    @NotEmpty
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
