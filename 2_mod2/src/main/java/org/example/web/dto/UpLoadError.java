package org.example.web.dto;

public class UpLoadError {
    private String message;

    public UpLoadError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
