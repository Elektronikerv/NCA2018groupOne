package ncadvanced2018.groupeone.parent.validator;

import ncadvanced2018.groupeone.parent.dto.MonthCalendarDay;
import ncadvanced2018.groupeone.parent.validator.annotation.IntervalBeetweenWorkingTimes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.temporal.ChronoUnit;

public class IntervalBeetweenWorkingTimesValidator implements ConstraintValidator <IntervalBeetweenWorkingTimes, MonthCalendarDay> {

    private final int MIN_WORKING_TIME = 4;

    @Override
    public void initialize(IntervalBeetweenWorkingTimes constraint) {
    }

    @Override
    public boolean isValid(MonthCalendarDay monthCalendarDay, ConstraintValidatorContext context) {
        return ChronoUnit.HOURS.between(monthCalendarDay.getEndWork(), monthCalendarDay.getStartWork()) < -MIN_WORKING_TIME;
    }

}
