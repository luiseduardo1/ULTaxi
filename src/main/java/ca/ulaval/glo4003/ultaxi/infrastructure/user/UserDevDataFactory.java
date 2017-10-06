package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class UserDevDataFactory {

    public List<User> createMockData() {
        List<User> users = Lists.newArrayList();
        User jobs = new User();
        jobs.setUserName("Steve Jobs");
        users.add(jobs);

        User balmer = new User();
        balmer.setUserName("Steve Balmer");
        users.add(balmer);

        User franklin = new User();
        franklin.setUserName("Benjamin Franklin");
        users.add(franklin);

        return users;
    }
}
