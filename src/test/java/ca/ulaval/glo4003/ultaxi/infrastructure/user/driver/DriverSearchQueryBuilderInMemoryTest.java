package ca.ulaval.glo4003.ultaxi.infrastructure.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DriverSearchQueryBuilderInMemoryTest {

    private static final int TOTAL_NUMBER_OF_DRIVERS = 3;
    private static final String A_SOCIAL_INSURANCE_NUMBER = "348624487";

    @Mock
    private Driver driver;
    @Mock
    private Driver driver2;
    @Mock
    private Driver driver3;

    private DriverSearchQueryBuilder driverSearchQueryBuilder;

    @Before
    public void setUp() {
        driverSearchQueryBuilder = new DriverSearchQueryBuilderInMemory(givenDrivers());

        willReturn("Marcel").given(driver).getFirstName();
        willReturn("Macdonald").given(driver).getLastName();
        willReturn(A_SOCIAL_INSURANCE_NUMBER).given(driver).getSocialInsuranceNumber();
        willReturn(Role.DRIVER).given(driver).getRole();
        willReturn("Ronald").given(driver2).getFirstName();
        willReturn("Lepic").given(driver2).getLastName();
        willReturn("972487086").given(driver2).getSocialInsuranceNumber();
        willReturn(Role.DRIVER).given(driver2).getRole();
        willReturn("Lord").given(driver3).getFirstName();
        willReturn("Gargamel").given(driver3).getLastName();
        willReturn("215136193").given(driver3).getSocialInsuranceNumber();
        willReturn(Role.DRIVER).given(driver3).getRole();
    }

    @Test
    public void givenNoFilter_whenFindingAll_thenReturnsAllTheDrivers() {
        List<Driver> foundDrivers = driverSearchQueryBuilder.findAll();

        assertEquals(TOTAL_NUMBER_OF_DRIVERS, foundDrivers.size());
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenFilterWithNoCorrespondingDriver_whenFindingAll_thenThrowsEmptySearchResultsException() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("beetlejuice");

        searchDriver.findAll();
    }

    @Test
    public void givenFilterWithCapitalLetters_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("MaC");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = driver;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber(), foundDriver.getSocialInsuranceNumber());
    }

    @Test
    public void givenAFirstNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("onal");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = driver2;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber(), foundDriver.getSocialInsuranceNumber());
    }

    @Test
    public void givenALastNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("rgam");

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = driver3;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber(), foundDriver.getSocialInsuranceNumber());
    }

    @Test
    public void givenASocialInsuranceNumberFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withSocialInsuranceNumber
            (A_SOCIAL_INSURANCE_NUMBER);

        List<Driver> foundDrivers = searchDriver.findAll();
        Driver foundDriver = foundDrivers.get(0);

        Driver expectedDriver = driver;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber(), foundDriver.getSocialInsuranceNumber());
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", driver);
        drivers.put("2", driver2);
        drivers.put("3", driver3);

        return drivers;
    }
}