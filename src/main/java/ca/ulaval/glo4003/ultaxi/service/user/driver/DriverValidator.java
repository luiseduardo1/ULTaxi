package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.EmptySearchResultsException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.SocialInsuranceNumberAlreadyExistException;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;

import java.util.List;

public class DriverValidator {

    private UserRepository userRepository;

    public DriverValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkSocialInsuranceNumberExistence(DriverDto driverDto) {
        try {
            DriverSearchQueryBuilder driverSearchQueryBuilder = userRepository.searchDrivers()
                .withSocialInsuranceNumber(driverDto.getSocialInsuranceNumber());
            List<Driver> drivers = driverSearchQueryBuilder.findAll();
            for (Driver driver : drivers) {
                if (driver.getSocialInsuranceNumber() == driverDto.getSocialInsuranceNumber()) {
                    throw new SocialInsuranceNumberAlreadyExistException("Social insurance number already exist.");
                }
            }
        } catch (EmptySearchResultsException exception) {
            return;
        }
    }
}
