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

    private static final String A_USERNAME = "user1234";
    private static final String A_PASSWORD = "password1234";
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
        driverDto.setUsername(A_USERNAME);
        driverDto.setPassword(A_PASSWORD);
    }

    @Test
    public void givenADriverWithAValidNonExistentSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenContinue() {
        String a_valid_social_insurance_number = "352342356";
        driverDto.setSocialInsuranceNumber(a_valid_social_insurance_number);
        willReturn(new DriverSearchQueryBuilderInMemory(givenDrivers())).given(userRepository).searchDrivers();

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    @Test(expected = SocialInsuranceNumberAlreadyExistException.class)
    public void
    givenADriverWithExistingSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenTrowSocialInsuranceNumberAlreadyExistException() {
        String a_valid_social_insurance_number = "972487086";
        driverDto.setSocialInsuranceNumber(a_valid_social_insurance_number);
        willReturn(new DriverSearchQueryBuilderInMemory(givenDrivers())).given(userRepository).searchDrivers();

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    private Map<String, User> givenDrivers() {
        Map<String, User> drivers = new HashMap<>();
        drivers.put("1", driverData.createDriver("Ronald", "Macdonald", "972487086"));
        return drivers;
    }

}