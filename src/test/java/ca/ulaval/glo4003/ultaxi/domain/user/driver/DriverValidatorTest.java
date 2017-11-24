package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.SocialInsuranceNumberAlreadyExistException;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class DriverValidatorTest {

    private static final String A_USERNAME = "user1234";
    private static final String A_PASSWORD = "password1234";
    @Mock
    private UserRepository userRepository;
    @Mock
    private SearchResults<Driver> searchResults;
    private DriverDto driverDto;
    private DriverValidator driverValidator;

    @Before
    public void setUp() {
        driverValidator = new DriverValidator(userRepository);
        driverDto = new DriverDto();
        driverDto.setUsername(A_USERNAME);
        driverDto.setPassword(A_PASSWORD);
        willReturn(searchResults).given(userRepository).searchDrivers(any());
        willReturn(givenDrivers()).given(searchResults).getResults();
    }

    @Test
    public void
    givenADriverWithAValidNonExistentSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenContinue() {
        willThrow(new EmptySearchResultsException("Empty search results.")).given(userRepository).searchDrivers(any());
        String aValidSocialInsuranceNumber = "352342356";
        driverDto.setSocialInsuranceNumber(aValidSocialInsuranceNumber);

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    @Test(expected = SocialInsuranceNumberAlreadyExistException.class)
    public void
    givenADriverWithExistingSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenThrowsSocialInsuranceNumberAlreadyExistException() {
        willReturn(searchResults).given(userRepository).searchDrivers(any());
        willReturn(givenDrivers()).given(searchResults).getResults();

        String aValidSocialInsuranceNumber = "972487086";
        driverDto.setSocialInsuranceNumber(aValidSocialInsuranceNumber);

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    private List<Driver> givenDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Ronald", "Macdonald", "972487086"));
        return drivers;
    }

}