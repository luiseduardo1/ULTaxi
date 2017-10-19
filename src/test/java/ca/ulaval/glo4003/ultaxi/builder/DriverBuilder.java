package ca.ulaval.glo4003.ultaxi.builder;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverBuilder {

    public Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", createDriver("Ronald", "Macdonald", "972487086"));
        drivers.put("2", createDriver("Marcel", "Lepic", "348624487"));
        drivers.put("3", createDriver("Lord", "Gargamel", "215136193"));

        return drivers;
    }

    public User createDriver(String firstName, String lastName, String sin) {
        Driver driver = new Driver();
        driver.setName(firstName);
        driver.setLastName(lastName);
        driver.setSin(sin);
        driver.setRole(Role.Driver);

        return driver;
    }
}
