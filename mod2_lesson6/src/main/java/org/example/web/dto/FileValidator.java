package org.example.web.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;




@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UploadedFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UploadedFile file = (UploadedFile) target;

        if (file.getFile() != null && file.getFile().isEmpty()){
            errors.rejectValue("file", "file.empty");
        }
    }

    }






