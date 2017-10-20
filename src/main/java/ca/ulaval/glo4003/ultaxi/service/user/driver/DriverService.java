package ca.ulaval.glo4003.ultaxi.service.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverDto;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverSearchParameters;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DriverService {

    private final Logger logger = Logger.getLogger(DriverService.class.getName());
    private final UserRepository userRepository;
    private final DriverAssembler driverAssembler;

    public DriverService(UserRepository userRepository, DriverAssembler driverAssembler) {
        this.userRepository = userRepository;
        this.driverAssembler = driverAssembler;
    }

    public void addDriver(DriverDto driverDto) {
        logger.info(String.format("Add new driver %s", driverDto));
        User user = driverAssembler.create(driverDto);
        userRepository.save(user);
    }

    public List<DriverDto> searchBy(DriverSearchParameters driverSearchParameters) {
        return userRepository
            .searchDrivers()
            .withFirstName(driverSearchParameters.getFirstName())
            .withLastName(driverSearchParameters.getLastName())
            .withSocialInsuranceNumber(driverSearchParameters.getSocialInsuranceNumber())
            .findAll()
            .stream()
            .map(driverAssembler::create)
            .collect(Collectors.toList());
    }
}
