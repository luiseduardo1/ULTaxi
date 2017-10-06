package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class UserDevDataFactory {

    public List<User> createMockData() {
        List<User> users = Lists.newArrayList();
        User jobs = new User();
        jobs.setName("Steve Jobs");
        users.add(jobs);

        User balmer = new User();
        balmer.setName("Steve Balmer");
        users.add(balmer);

        User franklin = new User();
        franklin.setName("Benjamin Franklin");
        users.add(franklin);

        return users;
    }
}
