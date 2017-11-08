package ca.ulaval.glo4003.ultaxi.service.user.driver;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQuery;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.SearchResults;
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
    private DriverSearchQueryBuilder driverSearchQueryBuilder;
    @Mock
    private DriverSearchQuery driverSearchQuery;
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
        willReturn(driverSearchQuery).given(userRepository).searchDrivers(any());
        willReturn(searchResults).given(driverSearchQuery).execute();
        willReturn(givenDrivers()).given(searchResults).getResultsList();
    }

    @Test
    public void
    givenADriverWithAValidNonExistentSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenContinue() {
        String aValidSocialInsuranceNumber = "352342356";
        driverDto.setSocialInsuranceNumber(aValidSocialInsuranceNumber);

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    @Test(expected = SocialInsuranceNumberAlreadyExistException.class)
    public void
    givenADriverWithExistingSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenTrowSocialInsuranceNumberAlreadyExistException() {
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