package ca.ulaval.glo4003.ultaxi.infrastructure.user.driver;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverSearchQueryBuilderInMemoryTest {

    private DriverSearchQueryBuilder driverSearchQueryBuilder;

    @Before
    public void setUp() {
        driverSearchQueryBuilder = new DriverSearchQueryBuilderInMemory(givenDrivers());
    }

    @Test
    public void givenSomeDriversNoFilter_whenFindingAll_thenReturnsAllTheDrivers() {
        List<Driver> foundDrivers = driverSearchQueryBuilder.findAll();

        assertEquals(3, foundDrivers.size());
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenFilterWithNoCorrespondingDriver_whenFindingAll_thenThrowsEmptySearchResultsException() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("beetlejuice");

        searchDriver.findAll();
    }

    @Test
    public void givenSomeDriversAndAFirstNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("onal");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) createDriver("Ronald", "Beaubrun", "972487086");
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSin(), foundDriver.getSin());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    @Test
    public void givenSomeDriversAndALastNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("rgam");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) createDriver("Lord", "Gargamel", "215136193");
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSin(), foundDriver.getSin());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    @Test
    public void givenSomeDriversAndASinFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withSin("348624487");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) createDriver("Marcel", "Lepic", "348624487");
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSin(), foundDriver.getSin());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", createDriver("Ronald", "Beaubrun", "972487086"));
        drivers.put("2", createDriver("Marcel", "Lepic", "348624487"));
        drivers.put("3", createDriver("Lord", "Gargamel", "215136193"));

        return drivers;
    }

    private User createDriver(String firstName, String lastName, String sin) {
        Driver driver = new Driver();
        driver.setName(firstName);
        driver.setLastName(lastName);
        driver.setSin(sin);
        driver.setRole(Role.Driver);

        return driver;
    }
}