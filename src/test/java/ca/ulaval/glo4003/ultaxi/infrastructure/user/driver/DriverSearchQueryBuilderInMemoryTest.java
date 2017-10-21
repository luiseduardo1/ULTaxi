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

    private static final int UNFILTERED_NUMBER_OF_DRIVERS = 3;
    private DriverSearchQueryBuilder driverSearchQueryBuilder;

    @Before
    public void setUp() {
        driverSearchQueryBuilder = new DriverSearchQueryBuilderInMemory(givenDrivers());
    }

    @Test
    public void givenSomeDriversNoFilter_whenFindingAll_thenReturnsAllTheDrivers() {
        List<Driver> foundDrivers = driverSearchQueryBuilder.findAll();

        assertEquals(UNFILTERED_NUMBER_OF_DRIVERS, foundDrivers.size());
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

        Driver expectedDriver = (Driver) aDriver();
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber(), foundDriver.getSocialInsuranceNumber());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    @Test
    public void givenSomeDriversAndALastNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("rgam");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) aThirdDriver();
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber(), foundDriver.getSocialInsuranceNumber());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    @Test
    public void givenSomeDriversAndASocialInsuranceNumberFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withSocialInsuranceNumber("348624487");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) anotherDriver();
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber(), foundDriver.getSocialInsuranceNumber());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", aDriver());
        drivers.put("2", anotherDriver());
        drivers.put("3", aThirdDriver());

        return drivers;
    }

    private User aDriver() {
        return createDriver("Ronald", "Macdonald", "972487086");
    }

    private User anotherDriver() {
        return createDriver("Marcel", "Lepic", "348624487");
    }

    private User aThirdDriver() {
        return createDriver("Lord", "Gargamel", "215136193");
    }

    private User createDriver(String firstName, String lastName, String socialInsuranceNumber) {
        Driver driver = new Driver();
        driver.setName(firstName);
        driver.setLastName(lastName);
        driver.setSocialInsuranceNumber(socialInsuranceNumber);
        driver.setRole(Role.Driver);

        return driver;
    }
}