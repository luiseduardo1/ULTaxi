package ca.ulaval.glo4003.ultaxi.infrastructure.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleFactory;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiclePersistenceDto;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;

public class VehicleDevDataFactory {

    public List<VehiclePersistenceDto> createMockData() {
        List<VehiclePersistenceDto> vehicles = Lists.newArrayList();

        VehiclePersistenceDto vehiclePersistenceDto1 = new VehiclePersistenceDto(
            "CAR",
            "Metallic Blue",
            "Honda Civic Si",
            "T98765");
        vehicles.add(vehiclePersistenceDto1);

        VehiclePersistenceDto vehiclePersistenceDto2 = new VehiclePersistenceDto(
            "VAN",
            "Dark Grey",
            "Toyota Sienna XLE",
            "T31337");
        vehicles.add(vehiclePersistenceDto2);

        VehiclePersistenceDto vehiclePersistenceDto3 = new VehiclePersistenceDto(
            "LIMOUSINE",
            "Gloss Black",
            "Chrysler 300 New York Edition",
            "TS00700");
        vehicles.add(vehiclePersistenceDto3);

        return vehicles;
    }
}
