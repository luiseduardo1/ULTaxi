package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;

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

    private static final String A_SOCIAL_INSURANCE_NUMBER = "972487086";
    private static final String ANOTHER_SOCIAL_INSURANCE_NUMBER = "352342356";

    @Mock
    private UserRepository userRepository;
    @Mock
    private DriverSearchQueryBuilder driverSearchQueryBuilder;
    @Mock
    private Driver driver;

    private DriverDto driverDto;
    private DriverValidator driverValidator;

    @Before
    public void setUp() {
        driverValidator = new DriverValidator(userRepository);
        driverDto = new DriverDto();

        willReturn(driverSearchQueryBuilder).given(userRepository).searchDrivers();
        willReturn(driverSearchQueryBuilder).given(driverSearchQueryBuilder).withSocialInsuranceNumber(anyString());
        willReturn(givenDrivers()).given(driverSearchQueryBuilder).findAll();
        willReturn(A_SOCIAL_INSURANCE_NUMBER).given(driver).getSocialInsuranceNumber();
    }

    @Test
    public void
    givenADriverWithAValidNonExistentSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenContinue() {
        driverDto.setSocialInsuranceNumber(ANOTHER_SOCIAL_INSURANCE_NUMBER);

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    @Test(expected = SocialInsuranceNumberAlreadyExistException.class)
    public void
    givenADriverWithExistingSocialInsuranceNumber_whenCheckSocialInsuranceNumberExistence_thenTrowSocialInsuranceNumberAlreadyExistException() {
        driverDto.setSocialInsuranceNumber(A_SOCIAL_INSURANCE_NUMBER);

        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
    }

    private List<Driver> givenDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);
        return drivers;
    }
}