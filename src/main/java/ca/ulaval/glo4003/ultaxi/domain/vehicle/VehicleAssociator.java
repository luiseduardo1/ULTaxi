package ca.ulaval.glo4003.ultaxi.domain.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;

public class VehicleAssociator {

    public void associate(Vehicle vehicle, User user) {
        if (user.getRole() == Role.DRIVER &&
            ((Driver) user).getVehicle() == null && vehicle.getDriver() == null) {
            vehicle.setDriver((Driver) user);
            ((Driver) user).setVehicle(vehicle);
        } else {
            throw new InvalidVehicleAssociationException("Can't make " +
                                                             "one-to-one Association");
        }
    }

    public void disassociate(Vehicle vehicle, User user) {
        if (user.getRole() == Role.DRIVER &&
            ((Driver) user).getVehicle() != null && vehicle.getDriver() != null) {
            vehicle.setDriver(null);
            ((Driver) user).setVehicle(null);
        } else {
            throw new InvalidVehicleAssociationException("Can't dissociate " +
                                                             "vehicle from driver");
        }
    }
}
