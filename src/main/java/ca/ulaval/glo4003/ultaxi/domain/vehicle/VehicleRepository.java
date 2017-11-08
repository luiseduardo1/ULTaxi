package ca.ulaval.glo4003.ultaxi.domain.vehicle;

public interface VehicleRepository {

    Vehicle findByRegistrationNumber(String registrationNumber);

    void save(Vehicle vehicle);

    void update(Vehicle vehicle);
}
