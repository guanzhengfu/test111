package com.example.test111.validate.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.test111.validate.validator.ProductCreateRequestValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ProductCreateRequestValidator.class)
public @interface ProductCreateRequestConstraint {

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
