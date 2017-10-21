package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.SocialInsuranceNumberAlreadyExistException;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class DriverValidatorTest {

    private static final String A_USERNAME = "user1234";
    private static final String A_PASSWORD = "password1234";
    @Mock
    private UserRepository userRepository;
    @Mock
    private DriverSearchQueryBuilder driverSearchQueryBuilder;
    private DriverDto driverDto;
    private DriverValidator driverValidator;

    @Before
    public void setUp() {
        driverValidator = new DriverValidator(userRepository);
        driverDto = new DriverDto();
        driverDto.setUsername(A_USERNAME);
        driverDto.setPassword(A_PASSWORD);
    }

    @Test
    public void givenADriverWithAValidNonExistentSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenContinue() {
        String aValidSocialInsuranceNumber = "352342356";
        driverDto.setSocialInsuranceNumber(aValidSocialInsuranceNumber);
        willReturn(driverSearchQueryBuilder).given(userRepository).searchDrivers();
        willReturn(driverSearchQueryBuilder).given(driverSearchQueryBuilder).withSocialInsuranceNumber(anyString());
        willReturn(givenDrivers()).given(driverSearchQueryBuilder).findAll();

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    @Test(expected = SocialInsuranceNumberAlreadyExistException.class)
    public void
    givenADriverWithExistingSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenTrowSocialInsuranceNumberAlreadyExistException() {
        String aValidSocialInsuranceNumber = "972487086";
        driverDto.setSocialInsuranceNumber(aValidSocialInsuranceNumber);
        willReturn(driverSearchQueryBuilder).given(userRepository).searchDrivers();
        willReturn(driverSearchQueryBuilder).given(driverSearchQueryBuilder).withSocialInsuranceNumber(anyString());
        willReturn(givenDrivers()).given(driverSearchQueryBuilder).findAll();

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    private List<Driver> givenDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Ronald", "Macdonald", "972487086"));
        return drivers;
    }

}