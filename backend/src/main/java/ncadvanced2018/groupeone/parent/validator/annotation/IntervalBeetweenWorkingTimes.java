package ncadvanced2018.groupeone.parent.validator.annotation;

import ncadvanced2018.groupeone.parent.validator.IntervalBeetweenWorkingTimesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IntervalBeetweenWorkingTimesValidator.class})
public @interface IntervalBeetweenWorkingTimes {

    String message() default "Difference beetween start and end must be more 4 hours";

    Class <?>[] groups() default {};

    Class <? extends Payload>[] payload() default {};

}