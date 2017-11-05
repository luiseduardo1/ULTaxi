package ca.ulaval.glo4003.ultaxi.domain.transportrequest;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.InvalidTransportRequestAssignationException;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;

public class TransportRequestAssignator {

    public void assignToDriver(TransportRequest transportRequest, User user) {
        if (user.getRole() != Role.DRIVER) {
            throw new InvalidTransportRequestAssignationException("Can't make one-to-one Assignation");
        }
        Driver driver = (Driver) user;

        if (driver.getTransportRequest() == null && transportRequest.isAvailable() && driver.getVehicleType() == transportRequest.getVehicleType()) {
            driver.setTransportRequest(transportRequest);
            transportRequest.setUnavailable();
        } else {
            throw new InvalidTransportRequestAssignationException("Can't make one-to-one assignation");
        }
    }
}
