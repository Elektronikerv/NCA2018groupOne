package ncadvanced2018.groupeone.parent.validator;

import ncadvanced2018.groupeone.parent.dto.MonthCalendarDay;
import ncadvanced2018.groupeone.parent.validator.annotation.OrderWorkingTimes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderWorkingTimesValidator implements ConstraintValidator <OrderWorkingTimes, MonthCalendarDay> {

    @Override
    public void initialize(OrderWorkingTimes constraint) {
    }

    @Override
    public boolean isValid(MonthCalendarDay monthCalendarDay, ConstraintValidatorContext context) {
        return monthCalendarDay.getEndWork().compareTo(monthCalendarDay.getStartWork()) > 0;
    }

}
