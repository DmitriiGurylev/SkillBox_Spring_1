package org.example.web.dto;

import javax.validation.constraints.NotNull;

public class BookToRemove {

    @NotNull
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String id) {
        this.param = id;
    }

}
