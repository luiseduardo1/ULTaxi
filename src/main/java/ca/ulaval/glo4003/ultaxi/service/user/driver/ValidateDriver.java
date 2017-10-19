package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverSearchQueryBuilder;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.SinAlreadyExistException;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;



public class ValidateDriver {

    private UserRepository userRepository;

    public ValidateDriver(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void verifySin(DriverDto driverDto){
        DriverSearchQueryBuilder driverSearchQueryBuilder = userRepository.searchDrivers().withSin(driverDto.getSin());
        driverSearchQueryBuilder.findAll();
        throw new SinAlreadyExistException("Sin already exist.");
    }
}
