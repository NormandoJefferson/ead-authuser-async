package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserNameConstraintImpl.class) //  Classe que implementa a anotação
@Target({ElementType.METHOD, ElementType.FIELD}) // Pode ser adicionada em metodh ou em campo
@Retention(RetentionPolicy.RUNTIME) // Vai validar em tempo de execução
public @interface UserNameConstraint {

    String message() default "Invalid name"; // msg de erro
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}