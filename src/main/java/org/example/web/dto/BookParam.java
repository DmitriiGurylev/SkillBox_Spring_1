package org.example.web.dto;

import javax.validation.constraints.NotEmpty;

public class BookParam {

    @NotEmpty
    private Integer id;

    @NotEmpty
    private Integer size;

    @NotEmpty
    private String author;

    @NotEmpty
    private String title;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
