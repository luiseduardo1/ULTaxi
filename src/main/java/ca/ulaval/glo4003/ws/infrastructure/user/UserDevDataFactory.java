package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class UserDevDataFactory {

    public List<User> createMockData() {
        List<User> users = Lists.newArrayList();
        User jobs = new User();
        jobs.setId("1");
        jobs.setName("Steve Jobs");
        jobs.setAddress("California");
        jobs.setTelephoneNumber("514-999-0000");
        users.add(jobs);

        User balmer = new User();
        balmer.setId("2");
        balmer.setName("Steve Balmer");
        balmer.setAddress("Manitoba");
        balmer.setTelephoneNumber("781-888-1111");
        users.add(balmer);

        User franklin = new User();
        franklin.setId("3");
        franklin.setName("Benjamin Franklin");
        franklin.setAddress("Washington");
        franklin.setTelephoneNumber("964-543-6475");
        users.add(franklin);

        return users;
    }

}
