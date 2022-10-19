package org.example.app.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class DataBaseExistsValidator implements ConstraintValidator<DataBaseExists, String> {
    @Autowired
    BookService bookService;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Override
    public void initialize(DataBaseExists baseExists) {
    }

    @Override
    public boolean isValid(String inputString, ConstraintValidatorContext cxt) {
        if (inputString == null) {
            logger.info("inputString == null");
            return false;
        } else {
            inputString=inputString.trim();
            logger.info("in else");
            if (inputString.length() == 0) {
                logger.info("inputString.length() == 0");
                return false;
            }
        }
        if (isDigit(inputString)) {
            return bookService.existsSize(Integer.parseInt(inputString));
        } else
            return (bookService.existsAuthor(inputString) || bookService.existsTitle(inputString));
    }

    private boolean isDigit(String str) {
        try {
            logger.info("before int value = Integer.parseInt(str)");
            int value = Integer.parseInt(str);
            logger.info("after int value = Integer.parseInt(str)");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}