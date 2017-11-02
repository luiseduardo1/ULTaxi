package ca.ulaval.glo4003.ultaxi.api.vehicle;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;

import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleTypeException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
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

    @Test
    public void givenVehicleToAssociate_whenAssociatingVehicle_thenReturnsOk() {
        Response response = vehicleResource.associateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenVehicleToDissociate_whenAssociatingVehicle_thenReturnsOk() {
        Response response = vehicleResource.dissociateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonExistentUser_whenAssociatingVehicle_thenReturnsBadRequest() {
        willThrow(new NonExistentUserException("Non existing user"))
            .given(vehicleService)
            .associateVehicle(vehicleAssociationDto);

        Response response = vehicleResource.associateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonExistentVehicle_whenAssociatingVehicle_thenReturnsBadRequest() {
        willThrow(new NonExistentVehicleException("Non existing vehicle"))
            .given(vehicleService)
            .associateVehicle(vehicleAssociationDto);

        Response response = vehicleResource.associateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonValidAssociation_whenAssociatingVehicle_thenReturnsBadRequest() {
        willThrow(new InvalidVehicleAssociationException("Invalid vehicle association"))
            .given(vehicleService)
            .associateVehicle(vehicleAssociationDto);

        Response response = vehicleResource.associateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonExistentUser_whenDissociatingVehicle_thenReturnsBadRequest() {
        willThrow(new NonExistentUserException("Non existing user"))
            .given(vehicleService)
            .dissociateVehicle(vehicleAssociationDto);

        Response response = vehicleResource.dissociateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonExistentVehicle_whenDissociatingVehicle_thenReturnsBadRequest() {
        willThrow(new NonExistentVehicleException("Non existing vehicle"))
            .given(vehicleService)
            .dissociateVehicle(vehicleAssociationDto);

        Response response = vehicleResource.dissociateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNonValidAssociation_whenDissociatingVehicle_thenReturnsBadRequest() {
        willThrow(new InvalidVehicleAssociationException("Invalid vehicle association"))
            .given(vehicleService)
            .dissociateVehicle(vehicleAssociationDto);

        Response response = vehicleResource.dissociateVehicle(vehicleAssociationDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}