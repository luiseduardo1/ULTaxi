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
        Driver driver = new Driver();
        driver.setUsername(driverDto.getUsername());
        driver.setPassword(driverDto.getPassword(), hashingStrategy);
        driver.setName(driverDto.getName());
        driver.setLastName(driverDto.getLastName());
        SocialInsuranceNumber socialInsuranceNumber = new SocialInsuranceNumber(driverDto.getSocialInsuranceNumber());
        driver.setSocialInsuranceNumber(socialInsuranceNumber);
        PhoneNumber phoneNumber = new PhoneNumber(driverDto.getPhoneNumber());
        driver.setPhoneNumber(phoneNumber);
        driver.setEmailAddress(driverDto.getEmailAddress());
        return driver;
    }

    public DriverDto create(Driver driver) {
        DriverDto driverDto = new DriverDto();
        driverDto.setUsername(driver.getUsername());
        driverDto.setPassword(driver.getPassword());
        driverDto.setName(driver.getName());
        driverDto.setLastName(driver.getLastName());
        driverDto.setSocialInsuranceNumber(driver.getSocialInsuranceNumber().getNumber());
        driverDto.setPhoneNumber(driver.getPhoneNumber().getNumber());
        driverDto.setEmailAddress(driver.getEmailAddress());
        return driverDto;
    }
}
