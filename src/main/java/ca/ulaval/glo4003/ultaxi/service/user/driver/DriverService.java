package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;

import java.util.logging.Logger;

public class DriverService {

    private Logger logger = Logger.getLogger(DriverService.class.getName());
    private UserRepository userRepository;
    private DriverAssembler driverAssembler;

    public DriverService(UserRepository userRepository, DriverAssembler driverAssembler) {
        this.userRepository = userRepository;
        this.driverAssembler = driverAssembler;
    }

    public void addDriver(DriverDto driverDto) {
        logger.info(String.format("Add new driver %s", driverDto));
        User user = driverAssembler.create(driverDto);
        userRepository.save(user);
    }
}
