package ca.ulaval.glo4003.ultaxi.service.user.driver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverValidator;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;
import com.beust.jcommander.internal.Lists;
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

    private static final Driver A_DRIVER = new Driver("Lord", "Gargamel", new SocialInsuranceNumber("215136193"));
    @Mock
    private Driver driver;
    @Mock
    private DriverDto driverDto;
    @Mock
    private DriverAssembler driverAssembler;
    @Mock
    private DriverValidator driverValidator;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DriverSearchParameters driverSearchParameters;
    @Mock
    private SearchResults<Driver> driverSearchResults;
    private DriverService driverService;

    @Before
    public void setUp() {
        driverService = new DriverService(userRepository, driverAssembler, driverValidator);
    }

    @Test
    public void givenADriverWithValidName_whenAddDriver_thenDriverIsAdded() {
        willReturn(driver).given(driverAssembler).create(driverDto);

        driverService.addDriver(driverDto);

        verify(userRepository).save(driver);
    }

    @Test
    public void givenADriver_whenAddDriver__thenDriverValidatorIsCalled() {
        driverService.addDriver(driverDto);

        verify(driverValidator).checkSocialInsuranceNumberExistence(driverDto);
    }

    @Test(expected = EmptySearchResultsException.class)
    public void givenValidSearchQueryWithNoDriversAssociated_whenSearching_thenThrowsEmptySearchResultsException() {
        willThrow(new EmptySearchResultsException("No results found.")).given(userRepository).searchDrivers(any());

        driverService.searchBy(driverSearchParameters);
    }

    @Test
    public void
    givenSearchQueryWithFirstNameAndARepositoryContainingDrivers_whenSearching_thenReturnsAssociatedDrivers() {
        willReturn("arg").given(driverSearchParameters).getLastName();
        willReturn(driverSearchResults).given(userRepository).searchDrivers(any());
        willReturn(Lists.newArrayList(A_DRIVER)).given(driverSearchResults).getResults();

        List<DriverDto> driverDtos = driverService.searchBy(driverSearchParameters);

        assertEquals(1, driverDtos.size());
    }

    public Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", new Driver("Ronald", "Macdonald", new SocialInsuranceNumber("972487086")));
        drivers.put("2", new Driver("Marcel", "Lepic", new SocialInsuranceNumber("348624487")));
        drivers.put("3", A_DRIVER);

        return drivers;
    }
}