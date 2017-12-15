package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceTest {

    public static final String A_LAST_NAME = "Macdonald";
    public static final String ANOTHER_LAST_NAME = "Gargamel";

    @Mock
    private Driver driver;
    @Mock
    private Driver driver2;
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

        willReturn(driver).given(driverAssembler).create(driverDto);
        willReturn(driverDto).given(driverAssembler).create(driver);
        willReturn(driverDto).given(driverAssembler).create(driver2);
        willReturn(A_LAST_NAME).given(driver).getLastName();
        willReturn(Role.DRIVER).given(driver).getRole();
        willReturn(ANOTHER_LAST_NAME).given(driver2).getLastName();
        willReturn(Role.DRIVER).given(driver2).getRole();
    }

    @Test
    public void givenADriverWithValidName_whenAddDriver_thenDriverIsAdded() {
        driverService.addDriver(driverDto);

        verify(userRepository).save(driver);
    }

    @Test
    public void givenADriver_whenAddDriver_thenDriverValidatorIsCalled() {
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
        willReturn(Lists.newArrayList(driver)).given(driverSearchResults).getResults();

        List<DriverDto> driverDtos = driverService.searchBy(driverSearchParameters);

        assertEquals(1, driverDtos.size());
    }
}