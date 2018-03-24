package ncadvanced2018.groupeone.parent.validator.annotation;


import ncadvanced2018.groupeone.parent.validator.OrderWorkingTimesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {OrderWorkingTimesValidator.class})
public @interface OrderWorkingTimes {

    String message() default "End time must be greater then start time";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};

}