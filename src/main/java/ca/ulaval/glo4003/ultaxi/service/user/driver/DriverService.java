package ca.ulaval.glo4003.ultaxi.service.user.driver;

import static java.util.stream.Collectors.toList;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverValidator;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import java.util.List;
import java.util.logging.Logger;

public class DriverService {

    private final Logger logger = Logger.getLogger(DriverService.class.getName());
    private final UserRepository userRepository;
    private final DriverAssembler driverAssembler;
    private final DriverValidator driverValidator;

    public DriverService(UserRepository userRepository, DriverAssembler driverAssembler, DriverValidator
        driverValidator) {
        this.userRepository = userRepository;
        this.driverAssembler = driverAssembler;
        this.driverValidator = driverValidator;
    }

    public void addDriver(DriverDto driverDto) {
        logger.info(String.format("Add new driver %s", driverDto));
        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
        User user = driverAssembler.create(driverDto);
        userRepository.save(user);
    }

    public List<DriverDto> searchBy(DriverSearchParameters driverSearchParameters) {
        return userRepository
            .searchDrivers(driverSearchParameters)
            .getResults()
            .stream()
            .map(driverAssembler::create)
            .collect(toList());
    }
}
