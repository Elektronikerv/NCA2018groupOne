package ncadvanced2018.groupeone.parent.validator;

import ncadvanced2018.groupeone.parent.dto.MonthCalendarDay;
import ncadvanced2018.groupeone.parent.validator.annotation.IntervalBetweenWorkingTimes;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.temporal.ChronoUnit;

public class IntervalBetweenWorkingTimesValidator implements ConstraintValidator <IntervalBetweenWorkingTimes, MonthCalendarDay> {

    @Value("4")
    private int MIN_WORKING_TIME;

    @Override
    public void initialize(IntervalBetweenWorkingTimes constraint) {
    }

    @Override
    public boolean isValid(MonthCalendarDay monthCalendarDay, ConstraintValidatorContext context) {
        return ChronoUnit.HOURS.between(monthCalendarDay.getEndWork(), monthCalendarDay.getStartWork()) <= -MIN_WORKING_TIME;
    }

}
