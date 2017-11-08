package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.search.SearchResults;
import ca.ulaval.glo4003.ultaxi.domain.search.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.SocialInsuranceNumberAlreadyExistException;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import java.util.List;

public class DriverValidator {

    private UserRepository userRepository;

    public DriverValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkSocialInsuranceNumberExistence(DriverDto driverDto) {
        try {
            DriverSearchParameters driverSearchParameters = new DriverSearchParameters(
                driverDto.getSocialInsuranceNumber(),
                null,
                null
            );
            SearchResults<Driver> searchResults = userRepository.searchDrivers(driverSearchParameters);

            List<Driver> drivers = searchResults.getResultsList();
            for (Driver driver : drivers) {
                if (driver.getSocialInsuranceNumber().equals(driverDto.getSocialInsuranceNumber())) {
                    throw new SocialInsuranceNumberAlreadyExistException("Social insurance number already exist.");
                }
            }
        } catch (EmptySearchResultsException exception) {
            // Nothing to do...
        }
    }
}
