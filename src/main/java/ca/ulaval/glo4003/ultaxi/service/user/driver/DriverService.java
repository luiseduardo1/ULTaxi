package ca.ulaval.glo4003.ultaxi.service.user.driver;

import static java.util.stream.Collectors.toList;

import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverValidator;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DriverService {

    private final Logger logger = Logger.getLogger(DriverService.class.getName());
    private final UserRepository userRepository;
    private final DriverAssembler driverAssembler;
    private final DriverValidator driverValidator;
    private final UserPersistenceAssembler userPersistenceAssembler;

    public DriverService(UserRepository userRepository, DriverAssembler driverAssembler, DriverValidator
        driverValidator, UserPersistenceAssembler userPersistenceAssembler) {
        this.userRepository = userRepository;
        this.driverAssembler = driverAssembler;
        this.driverValidator = driverValidator;
        this.userPersistenceAssembler = userPersistenceAssembler;
    }

    public void addDriver(DriverDto driverDto) {
        logger.info(String.format("Add new driver %s", driverDto));
        driverValidator.checkSocialInsuranceNumberExistence(driverDto);
        Driver driver = driverAssembler.create(driverDto);
        UserPersistenceDto userPersistenceDto = userPersistenceAssembler.create(driver);
        userRepository.save(userPersistenceDto);
    }

    public List<UserPersistenceDto> searchBy(DriverSearchParameters driverSearchParameters) {
        return new ArrayList<>(userRepository
            .searchDrivers(driverSearchParameters)
            .getResults());
    }
}
