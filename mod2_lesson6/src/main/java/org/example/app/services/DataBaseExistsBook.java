package org.example.app.services;
//DataBaseExistsBook
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class DataBaseExistsBook implements ConstraintValidator<BookExists, Integer> {
    private final Logger logger = Logger.getLogger(BookService.class);
    @Autowired
    BookService bookService;

    @Override
    public void initialize(BookExists baseExists) {
    }



    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext cxt) {
        try {
            logger.info("String inputString = String.valueOf(id)"+ " id= "+id);
            String inputString = String.valueOf(id);

            if (inputString == null) {
                logger.info("inputString == nul");
                return false;
            } else {
                if (inputString.length() == 0) {
                    logger.info("inputString.length() == 0");
                    return false;
                }
            }
            if (isDigit(inputString)) {
                return bookService.existsBookById(Integer.valueOf(inputString));
            } else
                return false;
        }
        catch (Exception e){
            e.printStackTrace();
            logger.info("Exception e");
            return false;

        }

    }

    private boolean isDigit(String str) {
        try {
            logger.info("before Integer.valueOf(str)" + " str = "+str);
            Integer value = Integer.valueOf(str);
            logger.info("after Integer.valueOf(str)");
            return true;
        } catch (NumberFormatException e) {

            return false;
        }
    }
}
