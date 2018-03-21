package ncadvanced2018.groupeone.parent.comparator.impl;

import ncadvanced2018.groupeone.parent.comparator.UserRolesComparator;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRolesComparatorImpl implements UserRolesComparator {

    @Override
    public int compare(User o1, User o2) {
        List<Role> listOne = o1.getRoles().stream()
                .sorted(Comparator.comparingLong(Role::getId)).collect(Collectors.toList());
        List<Role> listTwo = o2.getRoles().stream()
                .sorted(Comparator.comparingLong(Role::getId)).collect(Collectors.toList());

        listOne.removeAll(o2.getRoles());
        listTwo.removeAll(o1.getRoles());

        if (!listOne.isEmpty() && !listTwo.isEmpty()) {
            return (int) (listOne.get(0).getId() - listTwo.get(0).getId());
        } else if (listOne.isEmpty() && listTwo.isEmpty()) {
            return 0;
        } else if (listOne.isEmpty()) {
            return 1;
        } else {
            return -1;
        }
    }
}
