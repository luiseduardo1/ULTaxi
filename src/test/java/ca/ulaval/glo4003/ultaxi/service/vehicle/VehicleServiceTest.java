package ca.ulaval.glo4003.ultaxi.service.vehicle;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleAssociator;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    @Mock
    private VehicleAssociator vehicleAssociator;
    @Mock
    private UserRepository userRepository;

    private VehicleService vehicleService;

    @Before
    public void setUp() throws Exception {
        vehicleService = new VehicleService(vehicleRepository, vehicleAssembler, vehicleAssociator,
            userRepository);
    }

    @Test
    public void givenNewVehicleWithValidType_whenAddVehicle_thenVehicleIsAdded() {
        willReturn(vehicle).given(vehicleAssembler).create(vehicleDto);

        vehicleService.addVehicle(vehicleDto);

        verify(vehicleRepository).save(vehicle);
    }
}