package ca.ulaval.glo4003.ultaxi.service.user.driver;

import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.builder.DriverBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.SocialInsuranceNumberAlreadyExistException;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.driver.DriverSearchQueryBuilderInMemory;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DriverValidatorTest {

    @Mock
    private UserRepository userRepository;

    private DriverValidator driverValidator;

    private DriverBuilder driverData;

    private DriverDto driverDto;

    @Before
    public void setUp() {
        driverValidator = new DriverValidator(userRepository);
        driverData = new DriverBuilder();
        driverDto = new DriverDto();
        String A_USERNAME = "user1234";
        String A_PASSWORD = "password1234";
        driverDto.setUsername(A_USERNAME);
        driverDto.setPassword(A_PASSWORD);
    }

    @Test
    public void givenADriverWithAValidNonExistentSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenContinue() {
        String A_VALID_SOCIAL_INSURANCE_NUMBER = "352342356";
        driverDto.setSocialInsuranceNumber(A_VALID_SOCIAL_INSURANCE_NUMBER);
        willReturn(new DriverSearchQueryBuilderInMemory(givenDrivers())).given(userRepository).searchDrivers();

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    @Test(expected = SocialInsuranceNumberAlreadyExistException.class)
    public void
    givenADriverWithExistingSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenTrowSocialInsuranceNumberAlreadyExistException() {
        String A_VALID_SOCIAL_INSURANCE_NUMBER = "972487086";
        driverDto.setSocialInsuranceNumber(A_VALID_SOCIAL_INSURANCE_NUMBER);
        willReturn(new DriverSearchQueryBuilderInMemory(givenDrivers())).given(userRepository).searchDrivers();

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", driverData.createDriver("Ronald", "Macdonald", "972487086"));
        return drivers;
    }

}