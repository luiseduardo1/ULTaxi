package ca.ulaval.glo4003.ultaxi.api.vehicle;

import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class VehicleResourceImplTest {

    private static final String A_USERNAME = "a_username";

    @Mock
    private VehicleService vehicleService;
    @Mock
    private VehicleDto vehicleDto;
    @Mock
    private VehicleAssociationDto vehicleAssociationDto;

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
    public void givenVehicleToAssociate_whenAssociatingVehicle_thenReturnsOk() {
        Response response = vehicleResource.associateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenVehicleToDissociate_whenAssociatingVehicle_thenReturnsOk() {
        Response response = vehicleResource.dissociateVehicle(A_USERNAME);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}