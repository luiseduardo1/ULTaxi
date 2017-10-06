package ca.ulaval.glo4003.ws.api.vehicle;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;

import ca.ulaval.glo4003.ws.api.vehicle.dto.VehicleDto;
import ca.ulaval.glo4003.ws.domain.vehicle.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ws.domain.vehicle.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class VehicleResourceImplTest {

    @Mock
    private VehicleService vehicleService;
    @Mock
    private VehicleDto vehicleDto;

    private VehicleResource vehicleResource;

    @Before
    public void setUp() {
        vehicleResource = new VehicleResourceImpl(vehicleService);
    }

    @Test
    public void givenNewValidVehicle_whenCreateVehicle_thenReturnsOk() {
        Response response = vehicleResource.createVehicle(vehicleDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAlreadyExistingVehicle_whenCreateVehicle_thenReturnsBadRequest() {
        willThrow(new VehicleAlreadyExistsException("Vehicle already exists."))
            .given(vehicleService)
            .addVehicle(vehicleDto);
        Response response = vehicleResource.createVehicle(vehicleDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenVehicleWithInvalidType_whenCreateVehicle_thenReturnsBadRequest() {
        willThrow(new InvalidVehicleTypeException("Vehicle has an invalid type."))
            .given(vehicleService)
            .addVehicle(vehicleDto);

        Response response = vehicleResource.createVehicle(vehicleDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}