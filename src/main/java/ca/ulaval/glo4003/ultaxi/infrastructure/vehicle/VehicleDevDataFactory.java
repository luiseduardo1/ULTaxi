package ca.ulaval.glo4003.ultaxi.infrastructure.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleFactory;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class VehicleDevDataFactory {

    public List<Vehicle> createMockData() {
        List<Vehicle> vehicles = Lists.newArrayList();

        Vehicle vehicle1 = VehicleFactory.getVehicle(
            VehicleType.Car,
            "Metallic Blue",
            "Honda Civic Si",
            "T98765");
        vehicles.add(vehicle1);

        Vehicle vehicle2 = VehicleFactory.getVehicle(
            VehicleType.Van,
            "Dark Grey",
            "Toyota Sienna XLE",
            "T31337");
        vehicles.add(vehicle2);

        Vehicle vehicle3 = VehicleFactory.getVehicle(
            VehicleType.Limousine,
            "Gloss Black",
            "Chrysler 300 New York Edition",
            "TS00700");
        vehicles.add(vehicle3);

        return vehicles;
    }
}
