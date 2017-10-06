package ca.ulaval.glo4003.ws.domain.user.driver;

import ca.ulaval.glo4003.ws.api.user.driver.dto.DriverDto;

public class DriverAssembler {

    public Driver create(DriverDto driverDto) {
        Driver driver = new Driver();
        driver.setUserName(driverDto.getUserName());
        driver.setPassword(driverDto.getPassword());
        driver.setName(driverDto.getName());
        driver.setLastName(driverDto.getLastName());
        driver.setPhoneNumber(driverDto.getPhoneNumber());
        driver.setSin(driverDto.getSin());
        return driver;
    }

    public DriverDto create(Driver driver) {
        DriverDto driverDto = new DriverDto();
        driverDto.setUserName(driver.getUserName());
        driverDto.setPassword(driver.getPassword());
        driverDto.setName(driver.getName());
        driverDto.setLastName(driver.getLastName());
        driverDto.setPhoneNumber(driver.getPhoneNumber());
        driverDto.setSin(driver.getSin());
        return driverDto;
    }
}
