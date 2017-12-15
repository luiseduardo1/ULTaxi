package ca.ulaval.glo4003.ultaxi.infrastructure.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.search.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;
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
    private UserPersistenceDto driver;
    @Mock
    private UserPersistenceDto driver2;
    @Mock
    private UserPersistenceDto driver3;

    private DriverSearchQueryBuilder driverSearchQueryBuilder;

    @Before
    public void setUp() {
        driverSearchQueryBuilder = new DriverSearchQueryBuilderInMemory(givenDrivers());

        willReturn("Marcel").given(driver).getFirstName();
        willReturn("Macdonald").given(driver).getLastName();
        willReturn(new SocialInsuranceNumber(A_SOCIAL_INSURANCE_NUMBER)).given(driver).getSocialInsuranceNumber();
        willReturn(Role.DRIVER).given(driver).getRole();
        willReturn("Ronald").given(driver2).getFirstName();
        willReturn("Lepic").given(driver2).getLastName();
        willReturn(new SocialInsuranceNumber("972487086")).given(driver2).getSocialInsuranceNumber();
        willReturn(Role.DRIVER).given(driver2).getRole();
        willReturn("Lord").given(driver3).getFirstName();
        willReturn("Gargamel").given(driver3).getLastName();
        willReturn(new SocialInsuranceNumber("215136193")).given(driver3).getSocialInsuranceNumber();
        willReturn(Role.DRIVER).given(driver3).getRole();
    }

    @Test
    public void givenNoFilter_whenFindingAll_thenReturnsAllTheDrivers() {
        List<UserPersistenceDto> foundDrivers = driverSearchQueryBuilder.build().execute().getResults();

        assertEquals(TOTAL_NUMBER_OF_DRIVERS, foundDrivers.size());
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenFilterWithNoCorrespondingDriver_whenFindingAll_thenThrowsEmptySearchResultsException() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("beetlejuice");

        searchDriver.build().execute();
    }

    @Test
    public void givenFilterWithCapitalLetters_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("MaC");

        List<UserPersistenceDto> foundDrivers = searchDriver.build().execute().getResults();
        UserPersistenceDto foundDriver = foundDrivers.get(0);

        UserPersistenceDto expectedDriver = driver;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(),
                     foundDriver.getSocialInsuranceNumber().getNumber());
    }

    @Test
    public void givenAFirstNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withFirstName("onal");

        List<UserPersistenceDto> foundDrivers = searchDriver.build().execute().getResults();
        UserPersistenceDto foundDriver = foundDrivers.get(0);

        UserPersistenceDto expectedDriver = driver2;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(),
                     foundDriver.getSocialInsuranceNumber().getNumber());
    }

    @Test
    public void givenALastNameFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withLastName("rgam");

        List<UserPersistenceDto> foundDrivers = searchDriver.build().execute().getResults();
        UserPersistenceDto foundDriver = foundDrivers.get(0);

        UserPersistenceDto expectedDriver = driver3;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(),
                     foundDriver.getSocialInsuranceNumber().getNumber());
    }

    @Test
    public void givenASocialInsuranceNumberFilter_whenFindingAll_thenReturnsTheRightDriver() {
        DriverSearchQueryBuilder searchDriver = driverSearchQueryBuilder.withSocialInsuranceNumber(
            A_SOCIAL_INSURANCE_NUMBER);

        List<UserPersistenceDto> foundDrivers = searchDriver.build().execute().getResults();
        UserPersistenceDto foundDriver = foundDrivers.get(0);

        UserPersistenceDto expectedDriver = driver;
        assertEquals(1, foundDrivers.size());
        assertEquals(expectedDriver.getSocialInsuranceNumber().getNumber(),
                     foundDriver.getSocialInsuranceNumber().getNumber());
    }

    private Map<String, UserPersistenceDto> givenDrivers() {
        Map<String, UserPersistenceDto> drivers = new HashMap<>();
        drivers.put("1", driver);
        drivers.put("2", driver2);
        drivers.put("3", driver3);

        return drivers;
    }
}