package ca.ulaval.glo4003.ultaxi.integration.vehicle;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleType;
import ca.ulaval.glo4003.ultaxi.integration.IntegrationTest;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class VehicleResourceIT extends IntegrationTest {

    private static final String A_VALID_TYPE = VehicleType.CAR.name();
    private static final String A_VALID_COLOR = "Dark Red";
    private static final String A_VALID_MODEL = "Nissan Sentra";
    private static final String AN_INVALID_TYPE = null;
    private static final String AN_EXISTING_USERNAME = "driverUsername";
    private static final String A_NON_EXISTENT_USERNAME = "a_non_existing_user_142621";
    private static final String A_VALID_REGISTRATION_NUMBER = "TS00700";
    private static final String A_NON_EXISTENT_REGISTRATION_NUMBER = "NONEXISTENTVEHICLEREGISTRATIONNUMBER";
    private static final String VEHICLES_ASSOCIATION_ROUTE = String.format("%s/associate", VEHICLES_ROUTE);
    private static final String VEHICLES_DISSOCIATION_ROUTE = String.format("%s/dissociate", VEHICLES_ROUTE);

    @Before
    public void setUp() {
        authenticateAs(Role.ADMINISTRATOR);
    }

    @Test
    public void givenUnauthenticatedUser_whenCreateVehicle_thenReturnsUnauthorized() {
        String serializedVehicle = createSerializedValidVehicle();

        Response response = unauthenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.UNAUTHORIZED);
    }

    @Test
    public void givenNonAdministratorAuthenticatedUser_whenCreateVehicle_thenReturnsForbidden() {
        authenticateAs(Role.CLIENT);
        String serializedVehicle = createSerializedValidVehicle();

        Response response = authenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.FORBIDDEN);
    }

    @Test
    public void givenVehicleWithValidType_whenCreateVehicle_thenVehicleIsCreated() {
        String serializedVehicle = createSerializedValidVehicle();

        Response response = authenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.OK);
    }

    @Test
    public void givenAlreadyExistingVehicle_whenCreateVehicle_thenReturnsBadRequest() {
        String serializedVehicle = createSerializedValidVehicle();
        authenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        Response response = authenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenVehicleWithInvalidType_whenCreateVehicle_thenReturnsBadRequest() {
        String serializedVehicle = createSerializedVehicleWithInvalidType();

        Response response = authenticatedPost(VEHICLES_ROUTE, serializedVehicle);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenNonExistentUser_whenAssociatingVehicle_thenReturnsBadRequest() {
        String vehicleAssociation = createVehicleAssociationWithNonExistentUser();

        Response response = authenticatedPost(VEHICLES_ASSOCIATION_ROUTE, vehicleAssociation);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenNonExistentVehicle_whenAssociatingVehicle_thenReturnsBadRequest() {
        String vehicleAssociation = createVehicleAssociationWithNonExistentVehicle();

        Response response = authenticatedPost(VEHICLES_ASSOCIATION_ROUTE, vehicleAssociation);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    @Test
    public void givenNonExistentUser_whenDissociatingVehicle_thenReturnsBadRequest() {
        Response response = authenticatedPost(VEHICLES_DISSOCIATION_ROUTE, A_NON_EXISTENT_USERNAME);

        assertStatusCode(response, Status.BAD_REQUEST);
    }

    private String createSerializedValidVehicle() {
        return createSerializedVehicle(
            A_VALID_TYPE,
            A_VALID_COLOR,
            A_VALID_MODEL,
            generateRandomWord()
        );
    }

    private String createSerializedVehicleWithInvalidType() {
        return createSerializedVehicle(
            AN_INVALID_TYPE,
            A_VALID_COLOR,
            A_VALID_MODEL,
            generateRandomWord()
        );
    }

    private String createVehicleAssociationWithNonExistentVehicle() {
        return createVehicleAssociation(A_NON_EXISTENT_REGISTRATION_NUMBER, AN_EXISTING_USERNAME);
    }

    private String createVehicleAssociationWithNonExistentUser() {
        return createVehicleAssociation(A_VALID_REGISTRATION_NUMBER, A_NON_EXISTENT_USERNAME);
    }

    private String createVehicleAssociation(String registrationNumber, String username) {
        VehicleAssociationDto vehicleAssociationDto = new VehicleAssociationDto();
        vehicleAssociationDto.setRegistrationNumber(registrationNumber);
        vehicleAssociationDto.setUsername(username);
        return serializeDto(vehicleAssociationDto);
    }
}
