package ca.ulaval.glo4003.ultaxi.infrastructure.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.search.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DriverSearchQueryBuilderInMemoryTest {

    private static final int UNFILTERED_NUMBER_OF_DRIVERS = 3;
    private DriverSearchQueryBuilder driverSearchQueryBuilder;

    @Before
    public void setUp() {
        driverSearchQueryBuilder = new DriverSearchQueryBuilderInMemory(givenDrivers());
    }

    @Test
    public void givenSomeDriversNoFilter_whenFindingAll_thenReturnsAllTheDrivers() {
        List<Driver> foundDrivers = driverSearchQueryBuilder.build().execute().getResults();

        assertEquals(UNFILTERED_NUMBER_OF_DRIVERS, foundDrivers.size());
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenFilterWithNoCorrespondingDriver_whenFindingAll_thenThrowsEmptySearchResultsException() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("beetlejuice");

        searchDriver.build().execute();
    }

    @Test
    public void givenFilterWithCapitalLetters_whenFindingAll_thenReturnsCorrectDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("MaC");

        List<Driver> foundDrivers = searchDriver.build().execute().getResults();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) aDriver();
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(), foundDriver.getSocialInsuranceNumber().getNumber());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    @Test
    public void givenSomeDriversAndAFirstNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("onal");

        List<Driver> foundDrivers = searchDriver.build().execute().getResults();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) aDriver();
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(), foundDriver.getSocialInsuranceNumber().getNumber());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    @Test
    public void givenSomeDriversAndALastNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("rgam");

        List<Driver> foundDrivers = searchDriver.build().execute().getResults();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) aThirdDriver();
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(), foundDriver.getSocialInsuranceNumber().getNumber());
        assertEquals(expectedDriver.getName(), foundDriver.getName());
        assertEquals(expectedDriver.getLastName(), foundDriver.getLastName());
    }

    @Test
    public void givenSomeDriversAndASocialInsuranceNumberFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withSocialInsuranceNumber("348624487");

        List<Driver> foundDrivers = searchDriver.build().execute().getResults();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = (Driver) anotherDriver();
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(), foundDriver.getSocialInsuranceNumber().getNumber());
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
        return new Driver("Ronald", "Macdonald", new SocialInsuranceNumber("972487086"));
    }

    private User anotherDriver() {
        return new Driver("Marcel", "Lepic", new SocialInsuranceNumber("348624487"));
    }

    private User aThirdDriver() {
        return new Driver("Lord", "Gargamel", new SocialInsuranceNumber("215136193"));
    }
}