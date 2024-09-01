package flab.nutridiary.commom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClass();
    String message() default "유효하지 않은 타입입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}