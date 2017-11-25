package ca.ulaval.glo4003.ultaxi.service.vehicle;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.Driver;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleAssociationException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.InvalidVehicleDissociationException;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssociationDto;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

    private static final String A_USERNAME = "a_username";

    @Mock
    private Vehicle vehicle;
    @Mock
    private Driver driver;
    @Mock
    private VehicleDto vehicleDto;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleAssembler vehicleAssembler;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VehicleAssociationDto vehicleAssociationDto;

    private VehicleService vehicleService;

    @Before
    public void setUp() throws Exception {
        vehicleService = new VehicleService(vehicleRepository, vehicleAssembler, userRepository);
        willReturn(vehicle).given(vehicleRepository).findByRegistrationNumber(anyString());
        willReturn(driver).given(userRepository).findByUsername(anyString());
        willReturn(Role.DRIVER).given(driver).getRole();
    }

    @Test
    public void givenNewVehicleWithValidType_whenAddVehicle_thenVehicleIsAdded() {
        willReturn(vehicle).given(vehicleAssembler).create(vehicleDto);

        vehicleService.addVehicle(vehicleDto);

        verify(vehicleRepository).save(vehicle);
    }

    @Test
    public void givenValidVehicleAssociation_whenAssociatingVehicle_thenVehicleIsAssociated() {
        vehicleService.associateVehicle(vehicleAssociationDto);

        verify(driver).associateVehicle(vehicle);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenInvalidUserRole_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociationException() {
        willReturn(Role.CLIENT).given(driver).getRole();

        vehicleService.associateVehicle(vehicleAssociationDto);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenNonExistentUser_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociationException() {
        willReturn(null).given(userRepository).findByUsername(anyString());

        vehicleService.associateVehicle(vehicleAssociationDto);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenNonExistentVehicle_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociationException() {
        willReturn(null).given(vehicleRepository).findByRegistrationNumber(anyString());

        vehicleService.associateVehicle(vehicleAssociationDto);
    }

    @Test(expected = InvalidVehicleAssociationException.class)
    public void givenNullVehicleAssociation_whenAssociatingVehicle_thenThrowsInvalidVehicleAssociationException() {
        vehicleService.associateVehicle(null);
    }

    @Test
    public void givenValidUsername_whenDissociatingVehicle_thenVehicleIsDissociated() {
        vehicleService.dissociateVehicle(A_USERNAME);

        verify(driver).dissociateVehicle();
    }

    @Test(expected = InvalidVehicleDissociationException.class)
    public void givenNullUsername_whenDissociatingVehicle_thenThrowsInvalidVehicleDissociationException() {
        vehicleService.dissociateVehicle(null);
    }

    @Test(expected = InvalidVehicleDissociationException.class)
    public void givenInvalidUserRole_whenDissociatingVehicle_thenThrowsInvalidVehicleDissociationException() {
        willReturn(Role.CLIENT).given(driver).getRole();

        vehicleService.dissociateVehicle(A_USERNAME);
    }

    @Test(expected = InvalidVehicleDissociationException.class)
    public void givenNonExistentUser_whenDissociatingVehicle_thenThrowsInvalidVehicleDissociationException() {
        willReturn(null).given(userRepository).findByUsername(anyString());

        vehicleService.dissociateVehicle(A_USERNAME);
    }
}