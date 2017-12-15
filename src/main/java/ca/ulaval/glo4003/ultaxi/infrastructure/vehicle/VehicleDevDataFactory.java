package ca.ulaval.glo4003.ultaxi.infrastructure.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleFactory;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiclePersistenceDto;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class VehicleDevDataFactory {

    public List<VehiclePersistenceDto> createMockData() {
        List<VehiclePersistenceDto> vehicles = Lists.newArrayList();

        VehiclePersistenceDto vehicle1 = new VehiclePersistenceDto(
            "CAR",
            "Metallic Blue",
            "Honda Civic Si",
            "T98765");
        vehicles.add(vehicle1);

        VehiclePersistenceDto vehicle2 = new VehiclePersistenceDto(
            "VAN",
            "Dark Grey",
            "Toyota Sienna XLE",
            "T31337");
        vehicles.add(vehicle2);

        VehiclePersistenceDto vehicle3 = new VehiclePersistenceDto(
            "LIMOUSINE",
            "Gloss Black",
            "Chrysler 300 New York Edition",
            "TS00700");
        vehicles.add(vehicle3);

        return vehicles;
    }
}
