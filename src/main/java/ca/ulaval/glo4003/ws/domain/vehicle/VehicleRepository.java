package ca.ulaval.glo4003.ws.domain.vehicle;

public interface VehicleRepository {

    Vehicle findByRegistrationNumber(String registrationNumber);

    void save(Vehicle vehicle);
}
