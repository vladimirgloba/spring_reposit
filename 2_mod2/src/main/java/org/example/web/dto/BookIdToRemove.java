package org.example.web.dto;

import org.example.app.services.BookExists;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


public class BookIdToRemove {


    @NotNull(message = "the field is empty")
    @DecimalMin(value = "1.0", message = "invalid number < 0")
    @BookExists(message = "book not found")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
