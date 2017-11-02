package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;

public class VehicleAssociator {

    public void associate(Vehicle vehicle, User user) {
        if (user.getRole() != Role.DRIVER) {
            throw new InvalidVehicleAssociationException("Can't make one-to-one Association");
        }
        Driver driver = (Driver) user;

        if (driver.getVehicle() == null && vehicle.getDriver() == null) {
            vehicle.setDriver(driver);
            driver.setVehicle(vehicle);
            driver.setVehicleType(vehicle.getType());
        } else {
            throw new InvalidVehicleAssociationException("Can't make one-to-one association");
        }
    }

    public void dissociate(Vehicle vehicle, User user) {
        if (user.getRole() != Role.DRIVER) {
            throw new InvalidVehicleAssociationException("Can't dissociate vehicle from driver");
        }
        Driver driver = (Driver) user;

        if (driver.getVehicle() != null && vehicle.getDriver() != null) {
            vehicle.setDriver(null);
            driver.setVehicle(null);
            driver.setVehicleType(null);
        } else {
            throw new InvalidVehicleAssociationException("Can't dissociate vehicle from driver");
        }
    }
}
