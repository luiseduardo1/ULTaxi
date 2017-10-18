package ca.ulaval.glo4003.ultaxi.service.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

    @Mock
    private Vehicle vehicle;
    @Mock
    private VehicleDto vehicleDto;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleAssembler vehicleAssembler;

    private VehicleService vehicleService;

    @Before
    public void setUp() throws Exception {
        vehicleService = new VehicleService(vehicleRepository, vehicleAssembler);
    }

    @Test
    public void givenNewVehicleWithValidType_whenAddVehicle_thenVehicleIsAdded() {
        willReturn(vehicle).given(vehicleAssembler).create(vehicleDto);

        vehicleService.addVehicle(vehicleDto);

        verify(vehicleRepository).save(vehicle);
    }
}