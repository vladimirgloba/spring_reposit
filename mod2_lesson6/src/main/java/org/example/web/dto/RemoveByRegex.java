package org.example.web.dto;

import org.example.app.services.BookExists;
import org.example.app.services.DataBaseExists;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RemoveByRegex {

    @NotNull(message = "the field is empty")
    @Size(min = 1, message = "size must be > 0")
    @DataBaseExists(message = "book not found")
    private String regex;

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
