package ca.ulaval.glo4003.ultaxi.infrastructure.vehicle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.willReturn;

import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.NonExistentVehicleException;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.exception.VehicleAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehiclePersistenceDto;
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
    private VehiclePersistenceDto vehiclePersistenceDto;

    private VehicleRepository vehicleRepository;

    @Before
    public void setUp() {
        vehicleRepository = new VehicleRepositoryInMemory(new BcryptHashing());

        willReturn(A_REGISTRATION_NUMBER).given(vehiclePersistenceDto).getRegistrationNumber();
    }

    @Test
    public void givenExistingRegistrationNumber_whenFindByRegistrationNumber_thenReturnsCorrespondingVehicle() {
        vehicleRepository.save(vehiclePersistenceDto);

        VehiclePersistenceDto returnedVehiclePersistenceDto =
            vehicleRepository.findByRegistrationNumber(A_REGISTRATION_NUMBER);

        assertEquals(A_REGISTRATION_NUMBER, returnedVehiclePersistenceDto.getRegistrationNumber());
    }

    @Test
    public void givenNonExistingRegistrationNumber_whenFindByRegistrationNumber_thenReturnsNull() {
        VehiclePersistenceDto returnedVehiclePersistenceDto =
            vehicleRepository.findByRegistrationNumber(A_REGISTRATION_NUMBER);

        assertNull(returnedVehiclePersistenceDto);
    }

    @Test
    public void givenVehicle_whenSave_thenSavesVehicle() {
        vehicleRepository.save(vehiclePersistenceDto);
        VehiclePersistenceDto savedVehiclePersistenceDto =
            vehicleRepository.findByRegistrationNumber(A_REGISTRATION_NUMBER);

        assertEquals(vehiclePersistenceDto, savedVehiclePersistenceDto);
    }

    @Test(expected = VehicleAlreadyExistsException.class)
    public void givenExistingVehicle_whenSave_thenThrowsException() {
        vehicleRepository.save(vehiclePersistenceDto);
        vehicleRepository.save(vehiclePersistenceDto);
    }

    @Test
    public void givenExistentVehicle_whenUpdating_thenNoThrowsException() {
        vehicleRepository.save(vehiclePersistenceDto);
        vehicleRepository.update(vehiclePersistenceDto);
    }

    @Test(expected = NonExistentVehicleException.class)
    public void givenNonExistentVehicle_whenUpdating_thenThrowsException() {
        vehicleRepository.update(vehiclePersistenceDto);
    }
}