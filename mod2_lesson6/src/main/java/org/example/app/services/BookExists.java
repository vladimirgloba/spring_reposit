package org.example.app.services;
//
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataBaseExistsBook.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BookExists {

    String message() default "{BookExists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
