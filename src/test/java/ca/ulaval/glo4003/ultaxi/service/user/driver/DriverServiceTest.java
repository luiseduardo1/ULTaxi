package ca.ulaval.glo4003.ultaxi.service.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.driver.DriverSearchQueryBuilderInMemory;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceTest {

    @Mock
    private Driver driver;
    @Mock
    private DriverDto driverDto;
    @Mock
    DriverAssembler driverAssembler;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DriverSearchQueryBuilder driverSearchQueryBuilder;
    @Mock
    private DriverSearchParameters driverSearchParameters;

    private DriverService driverService;

    @Before
    public void setUp() {
        driverService = new DriverService(userRepository, driverAssembler);
    }

    @Test
    public void givenADriverWithValidName_whenAddDriver_thenDriverIsAdded() {
        willReturn(driver).given(driverAssembler).create(driverDto);

        driverService.addDriver(driverDto);

        verify(userRepository).save(driver);
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenValidSearchQueryWithNoDriversAssociated_whenSearching_thenThrowsEmptySearchResultsException() {
        willReturn(driverSearchQueryBuilder).given(userRepository).searchDrivers();
        willReturn(driverSearchQueryBuilder).given(driverSearchQueryBuilder).withFirstName(anyString());
        willReturn(driverSearchQueryBuilder).given(driverSearchQueryBuilder).withLastName(anyString());
        willReturn(driverSearchQueryBuilder).given(driverSearchQueryBuilder).withSocialInsuranceNumber(anyString());
        willThrow(new EmptySearchResultsException("No results found.")).given(driverSearchQueryBuilder).findAll();

        driverService.searchBy(driverSearchParameters);
    }

    @Test
    public void
    givenSearchQueryWithFirstNameAndARepositoryContainingDrivers_whenSearching_thenReturnsAssociatedDrivers() {
        willReturn("arg").given(driverSearchParameters).getLastName();
        willReturn(new DriverSearchQueryBuilderInMemory(givenDrivers())).given(userRepository).searchDrivers();

        List<DriverDto> driverDtos = driverService.searchBy(driverSearchParameters);

        assertEquals(1, driverDtos.size());
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", createDriver("Ronald", "Macdonald", "972487086"));
        drivers.put("2", createDriver("Marcel", "Lepic", "348624487"));
        drivers.put("3", createDriver("Lord", "Gargamel", "215136193"));

        return drivers;
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