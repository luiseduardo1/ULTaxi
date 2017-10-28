package ca.ulaval.glo4003.ultaxi.infrastructure.vehicle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.utils.hashing.BcryptHashing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleRepositoryInMemoryTest {

    private static final String A_REGISTRATION_NUMBER = "T31337";

    @Mock
    private Vehicle vehicle;
    private VehicleRepository vehicleRepository;

    @Before
    public void setUp() {
        vehicleRepository = new VehicleRepositoryInMemory(new BcryptHashing());

        willReturn(A_REGISTRATION_NUMBER).given(vehicle).getRegistrationNumber();
    }

    @Test
    public void givenExistingRegistrationNumber_whenFindByRegistrationNumber_thenReturnsCorrespondingVehicle() {
        vehicleRepository.save(vehicle);

        Vehicle returnedVehicle = vehicleRepository.findByRegistrationNumber(A_REGISTRATION_NUMBER);

        assertEquals(A_REGISTRATION_NUMBER, returnedVehicle.getRegistrationNumber());
    }

    @Test
    public void givenNonExistingRegistrationNumber_whenFindByRegistrationNumber_thenReturnsNull() {
        Vehicle returnedVehicle = vehicleRepository.findByRegistrationNumber(A_REGISTRATION_NUMBER);

        assertNull(returnedVehicle);
    }

    @Test
    public void givenVehicle_whenSave_thenSavesVehicle() {
        vehicleRepository.save(vehicle);
        Vehicle savedVehicle = vehicleRepository.findByRegistrationNumber(A_REGISTRATION_NUMBER);

        assertEquals(vehicle, savedVehicle);
    }

    @Test(expected = VehicleAlreadyExistsException.class)
    public void givenExistingVehicle_whenSave_thenThrowsException() {
        vehicleRepository.save(vehicle);
        vehicleRepository.save(vehicle);
    }
}