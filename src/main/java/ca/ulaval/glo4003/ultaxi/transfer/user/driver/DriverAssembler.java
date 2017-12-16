package ca.ulaval.glo4003.ultaxi.transfer.user.driver;

import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.SocialInsuranceNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class DriverAssembler {

    private final HashingStrategy hashingStrategy;

    public DriverAssembler(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public Driver create(DriverDto driverDto) {

        return new Driver(
            driverDto.getUsername(),
            driverDto.getPassword(),
            new PhoneNumber(driverDto.getPhoneNumber()),
            driverDto.getEmailAddress(),
            hashingStrategy,
            driverDto.getFirstName(),
            driverDto.getLastName(),
            new SocialInsuranceNumber(driverDto.getSocialInsuranceNumber())
        );
    }

    public DriverDto create(Driver driver) {
        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(driver.getUsername());
        driverDto.setPassword(driver.getPassword());
        driverDto.setPhoneNumber(driver.getPhoneNumber().getNumber());
        driverDto.setEmailAddress(driver.getEmailAddress());
        driverDto.setFirstName(driver.getFirstName());
        driverDto.setLastName(driver.getLastName());
        driverDto.setSocialInsuranceNumber(driver.getSocialInsuranceNumber().getNumber());
        return driverDto;
    }
}
