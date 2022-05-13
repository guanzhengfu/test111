package com.example.test111.validate.validator;

import com.example.test111.bean.ErrorResult;
import com.example.test111.validate.constraint.ProductCreateRequestConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductCreateRequestValidator implements
    ConstraintValidator<ProductCreateRequestConstraint, ErrorResult> {

  @Override
  public boolean isValid(ErrorResult errorResult,
      ConstraintValidatorContext constraintValidatorContext) {
    constraintValidatorContext.disableDefaultConstraintViolation();
    HibernateConstraintValidatorContext actualContext =
        constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
    return true;
  }
}
