package com.core.domain.annotation.validation;

import com.core.domain.validator.DateTimeValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = DateTimeValueValidator.class)
public @interface DateTimeValue {

    String message() default "잘못된 데이터 형식입니다. (yyyy-MM-dd)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
