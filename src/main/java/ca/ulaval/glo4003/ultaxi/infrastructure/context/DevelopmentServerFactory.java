package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.api.transportrequest.TransportRequestResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.UserAuthenticationResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.UserResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.driver.DriverResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.vehicle.VehicleResourceImpl;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.AuthenticationFilter;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.AuthorizationFilter;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSenderConfigurationReaderFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.JavaMailEmailSender;
import ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest.TransportRequestRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.TokenRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.JWTTokenManager;
import ca.ulaval.glo4003.ultaxi.infrastructure.vehicle.VehicleDevDataFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.vehicle.VehicleRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.service.user.UserService;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverValidator;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.UserAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.utils.hashing.BcryptHashing;

import java.util.List;

public class DevelopmentServerFactory extends ServerFactory {

    private final UserRepository userRepository = new UserRepositoryInMemory();
    private final TransportRequestRepository transportRequestRepository = new TransportRequestRepositoryInMemory();
    private final VehicleAssembler vehicleAssembler = new VehicleAssembler();
    private final TransportRequestAssembler transportRequestAssembler = new TransportRequestAssembler();
    private final TokenManager tokenManager = new JWTTokenManager();
    private final UserAssembler userAssembler = new UserAssembler(this.hashingStrategy);
    private final DriverAssembler driverAssembler = new DriverAssembler(this.hashingStrategy);
    private final DriverValidator driverValidator = new DriverValidator(userRepository);
    private final DriverService driverService = new DriverService(userRepository, driverAssembler, driverValidator);
    private final UserService userService;
    private final TokenRepository tokenRepository = new TokenRepositoryInMemory();
    private final UserAuthenticationService userAuthenticationService = new UserAuthenticationService(userRepository,
                                                                                                      userAssembler,
                                                                                                      tokenManager,
                                                                                                      tokenRepository);
    private final VehicleRepository vehicleRepository = new VehicleRepositoryInMemory(this.hashingStrategy);
    private final VehicleService vehicleService = new VehicleService(vehicleRepository,
                                                                     vehicleAssembler,
                                                                     userRepository);
    private final TransportRequestService transportRequestService = new TransportRequestService(
        transportRequestRepository,
        transportRequestAssembler
    );

    public DevelopmentServerFactory(ULTaxiOptions options, MessagingTaskQueue messageQueue) throws Exception {
        super(options, messageQueue);
        EmailSender emailSender = new JavaMailEmailSender(
            EmailSenderConfigurationReaderFactory.getEmailSenderConfigurationFileReader(options)
        );
        userService = new UserService(userRepository, userAssembler, messageQueueProducer, emailSender);
        setDevelopmentEnvironmentMockData();
    }

    private void setDevelopmentEnvironmentMockData() throws Exception {
        UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
        List<User> users = userDevDataFactory.createMockData(new BcryptHashing());
        users.forEach(userRepository::save);

        VehicleDevDataFactory vehicleDevDataFactory = new VehicleDevDataFactory();
        List<Vehicle> vehicles = vehicleDevDataFactory.createMockData();
        vehicles.forEach(vehicleRepository::save);
    }

    @Override
    public ServerFactory withDriverResource() {
        resources.add(new DriverResourceImpl(driverService));
        return this;
    }

    @Override
    public ServerFactory withUserResource() {
        resources.add(new UserResourceImpl(userService, userAuthenticationService));
        return this;
    }

    @Override
    public ServerFactory withUserAuthenticationResource() {
        resources.add(new UserAuthenticationResourceImpl(userAuthenticationService));
        return this;
    }

    @Override
    public ServerFactory withTransportRequestResource() {
        resources.add(new TransportRequestResourceImpl(transportRequestService, userAuthenticationService));
        return this;
    }

    @Override
    public ServerFactory withAuthenticationFilters() {
        requestFilters.add(new AuthenticationFilter(tokenManager));
        requestFilters.add(new AuthorizationFilter(userRepository, tokenManager));
        return this;
    }

    @Override
    protected ServerFactory withVehicleResource() {
        resources.add(new VehicleResourceImpl(vehicleService));
        return this;
    }
}
