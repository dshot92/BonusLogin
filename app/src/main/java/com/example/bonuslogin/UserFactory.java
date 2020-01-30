/*
    Corso IUM - AA 2019 - 2020
    65577 - Daniele Stochino
    Esercitazione Bonus
 */

package com.example.bonuslogin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserFactory {

    private static UserFactory singleton;
    private List<User> users = new ArrayList<>();
    DatePickerFragment datePickerFragment = new DatePickerFragment();

    private UserFactory() {
    }

    public static UserFactory getInstance() {

        if (singleton == null) {
            singleton = new UserFactory();
        }
        return singleton;
    }

    public List<User> getUsers() {

        User user = new User();
        user.setUsername("dshot");
        user.setPassword("asd");
        user.setCity("Girasole");
        user.setDate(Calendar.getInstance());
        user.getDate().set(Calendar.YEAR,1992);
        user.getDate().set(Calendar.MONTH, Calendar.MARCH);
        user.getDate().set(Calendar.DAY_OF_MONTH,16);
        users.add(user);

        User user1 = new User();
        user1.setUsername("star");
        user1.setPassword("wars");
        user1.setCity("Hollywood");
        user1.setDate(Calendar.getInstance());
        user1.getDate().set(Calendar.YEAR,1977);
        user1.getDate().set(Calendar.MONTH, Calendar.MAY);
        user1.getDate().set(Calendar.DAY_OF_MONTH,4);
        users.add(user1);

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("admin");
        user2.setCity("Cagliari");
        user2.setDate(Calendar.getInstance());
        user2.getDate().set(Calendar.YEAR,1990);
        user2.getDate().set(Calendar.MONTH, Calendar.APRIL);
        user2.getDate().set(Calendar.DAY_OF_MONTH,24);
        users.add(user2);

        return users;
    }

    public void addUsers(User u) {
        users.add(u);
    }

    public void modifyPass(User user) {
        for (User u: users) {
            if (u.getUsername().equals(user.getUsername()) && u.getCity().equals(user.getCity())){
                users.remove(u);
                users.add(user);
            }
        }
    }

}
