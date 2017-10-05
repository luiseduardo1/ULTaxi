package ca.ulaval.glo4003.ws.domain.vehicle;

import ca.ulaval.glo4003.ws.api.vehicle.dto.VehicleDto;
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