package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private static final String A_USERNAME = "ronald";
    private static final String A_NAME = "Ronald";
    private static final String A_LAST_NAME = "Mcdonald";
    private static final String A_SOCIAL_INSURANCE_NUMBER = "119796274";
    private static final String A_DIFFERENT_CASED_USERNAME = "rOnAld";
    private static final String ORIGINAL_EMAIL_ADDRESS = "ronald.macdonald@ulaval.ca";
    private static final String UPDATED_EMAIL_ADDRESS = "ro.mac@ulaval.ca";

    @Mock
    private User user;
    @Mock
    private Driver driver;
    @Mock
    private DriverSearchParameters driverSearchParameters;
    @Mock
    private SocialInsuranceNumber socialInsuranceNumber;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepositoryInMemory();

        willReturn(A_USERNAME).given(user).getUsername();
        willReturn(A_USERNAME).given(driver).getUsername();
        willReturn(A_NAME).given(driver).getFirstName();
        willReturn(A_LAST_NAME).given(driver).getLastName();
        willReturn(socialInsuranceNumber).given(driver).getSocialInsuranceNumber();
        willReturn(A_SOCIAL_INSURANCE_NUMBER).given(socialInsuranceNumber).getNumber();
        willReturn(Role.DRIVER).given(driver).getRole();
    }

    @Test
    public void givenUser_whenSave_thenUserHasSameParameters() {
        userRepository.save(user);
        User savedUser = userRepository.findByUsername(user.getUsername());

        assertEquals(user, savedUser);
    }

    @Test
    public void givenNonExistingUser_whenFindByUsername_thenReturnsNull() {
        User returnedUser = userRepository.findByUsername(user.getUsername());

        assertNull(returnedUser);
    }

    @Test
    public void givenUserWithDifferentNameCasing_whenFindByUsername_thenReturnsTheUser() {
        userRepository.save(user);

        User savedUser = userRepository.findByUsername(A_DIFFERENT_CASED_USERNAME);

        assertNotNull(savedUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenExistingUser_whenSave_thenThrowsException() {
        userRepository.save(user);
        userRepository.save(user);
    }

    @Test
    public void givenUserToUpdate_whenUpdatingUser_thenNoExceptionIsThrown() {
        userRepository.save(user);
        userRepository.update(user);
    }

    @Test(expected = NonExistentUserException.class)
    public void givenNonExistentUserToUpdate_whenUpdatingUser_thenThrowsException() {
        userRepository.update(user);
    }

    @Test
    public void givenExistingUser_whenUpdate_thenUserHasUpdatedParameters() {
        user.setEmailAddress(ORIGINAL_EMAIL_ADDRESS);
        User sameUserWithAnotherEmailAddress = user;
        sameUserWithAnotherEmailAddress.setEmailAddress(UPDATED_EMAIL_ADDRESS);

        userRepository.save(user);
        userRepository.update(sameUserWithAnotherEmailAddress);

        User updatedUser = userRepository.findByUsername(user.getUsername());
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(sameUserWithAnotherEmailAddress.getEmailAddress(), updatedUser.getEmailAddress());
    }

    @Test
    public void
    givenDriverSearchParametersWithExistingUserNameSet_whenSearchingDrivers_thenReturnsTheCorrespondingDrivers() {
        willReturn("ona").given(driverSearchParameters).getFirstName();
        userRepository.save(driver);

        List<Driver> searchResults = userRepository
            .searchDrivers(driverSearchParameters)
            .getResults();

        assertEquals(1, searchResults.size());
        assertEquals(driver, searchResults.get(0));
    }

    @Test
    public void
    givenDriverSearchParametersWithExistingLastNameSet_whenSearchingDrivers_thenReturnsTheCorrespondingDrivers() {
        willReturn("dOna").given(driverSearchParameters).getLastName();
        userRepository.save(driver);

        List<Driver> searchResults = userRepository
            .searchDrivers(driverSearchParameters)
            .getResults();

        assertEquals(1, searchResults.size());
        assertEquals(driver, searchResults.get(0));
    }

    @Test
    public void
    givenDriverSearchParametersWithExistingSocialAssuranceNumberSet_whenSearchingDrivers_thenReturnsTheCorrespondingDrivers() {
        willReturn(A_SOCIAL_INSURANCE_NUMBER).given(driverSearchParameters).getSocialInsuranceNumber();
        userRepository.save(driver);

        List<Driver> searchResults = userRepository
            .searchDrivers(driverSearchParameters)
            .getResults();

        assertEquals(1, searchResults.size());
        assertEquals(driver, searchResults.get(0));
    }

    @Test
    public void givenNonSetDriverSearchParameters_whenSearchingDrivers_thenReturnsAllTheDrivers() {
        userRepository.save(driver);

        List<Driver> searchResults = userRepository
            .searchDrivers(driverSearchParameters)
            .getResults();

        assertEquals(1, searchResults.size());
        assertEquals(driver, searchResults.get(0));
    }

    @Test(expected = EmptySearchResultsException.class)
    public void
    givenDriverSearchParametersWithNoCorrespondingDrivers_whenSearchingDrivers_thenThrowsEmptySearchResultsException() {
        willReturn("NONEXISTINGNAME").given(driverSearchParameters).getFirstName();
        userRepository.save(driver);

        userRepository
            .searchDrivers(driverSearchParameters)
            .getResults();
    }
}