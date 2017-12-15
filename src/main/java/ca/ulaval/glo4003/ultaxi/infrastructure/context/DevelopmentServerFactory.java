package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.api.rate.RateResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.transportrequest.TransportRequestResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.UserAuthenticationResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.client.ClientResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.user.driver.DriverResourceImpl;
import ca.ulaval.glo4003.ultaxi.api.vehicle.VehicleResourceImpl;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.email.EmailSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.rate.RateRepository;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequestRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.domain.user.UserRepository;
import ca.ulaval.glo4003.ultaxi.domain.user.driver.DriverValidator;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.Vehicle;
import ca.ulaval.glo4003.ultaxi.domain.vehicle.VehicleRepository;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.AuthenticationFilter;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.AuthorizationFilter;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingConfigurationReaderFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.JavaMailEmailSender;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms.SmsSenderStub;
import ca.ulaval.glo4003.ultaxi.infrastructure.rate.RateRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest.TransportRequestDevDataFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.transportrequest.TransportRequestRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.TokenRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.JWTTokenManager;
import ca.ulaval.glo4003.ultaxi.infrastructure.vehicle.VehicleDevDataFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.vehicle.VehicleRepositoryInMemory;
import ca.ulaval.glo4003.ultaxi.service.rate.RateService;
import ca.ulaval.glo4003.ultaxi.service.transportrequest.TransportRequestService;
import ca.ulaval.glo4003.ultaxi.service.user.UserAuthenticationService;
import ca.ulaval.glo4003.ultaxi.service.user.client.ClientService;
import ca.ulaval.glo4003.ultaxi.service.user.driver.DriverService;
import ca.ulaval.glo4003.ultaxi.service.vehicle.VehicleService;
import ca.ulaval.glo4003.ultaxi.transfer.rate.DistanceRateAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.transportrequest.TransportRequestTotalAmountAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.user.driver.DriverAssembler;
import ca.ulaval.glo4003.ultaxi.transfer.vehicle.VehicleAssembler;
import ca.ulaval.glo4003.ultaxi.utils.distancecalculator.DistanceCalculatorStrategy;
import ca.ulaval.glo4003.ultaxi.utils.distancecalculator.HaversineDistance;
import ca.ulaval.glo4003.ultaxi.utils.hashing.BcryptHashing;

import java.util.List;
import java.util.Random;

public class DevelopmentServerFactory extends ServerFactory {

    private final UserRepository userRepository = new UserRepositoryInMemory();
    private final TransportRequestRepository transportRequestRepository = new TransportRequestRepositoryInMemory();
    private final VehicleAssembler vehicleAssembler = new VehicleAssembler();
    private final TransportRequestAssembler transportRequestAssembler = new TransportRequestAssembler();
    private final TransportRequestTotalAmountAssembler
            transportRequestTotalAmountAssembler = new TransportRequestTotalAmountAssembler();
    private DistanceCalculatorStrategy distanceCalculatorStrategy = new HaversineDistance();
    private final DistanceRateAssembler distanceRateAssembler = new DistanceRateAssembler();
    private final TokenManager tokenManager = new JWTTokenManager();
    private final ClientAssembler clientAssembler = new ClientAssembler(this.hashingStrategy);
    private final DriverAssembler driverAssembler = new DriverAssembler(this.hashingStrategy);
    private final DriverValidator driverValidator = new DriverValidator(userRepository);
    private final DriverService driverService = new DriverService(userRepository, driverAssembler, driverValidator);
    private final ClientService clientService;
    private final TokenRepository tokenRepository = new TokenRepositoryInMemory();
    private final RateRepository rateRepository = new RateRepositoryInMemory();
    private final UserAuthenticationService userAuthenticationService = new UserAuthenticationService(userRepository,
                                                                                                      clientAssembler,
                                                                                                      tokenManager,
                                                                                                      tokenRepository);
    private final VehicleRepository vehicleRepository = new VehicleRepositoryInMemory(this.hashingStrategy);
    private final VehicleService vehicleService = new VehicleService(vehicleRepository,
                                                                     vehicleAssembler,
                                                                     userRepository);
    private final TransportRequestService transportRequestService;
    private final RateService rateService = new RateService(rateRepository, distanceRateAssembler);

    public DevelopmentServerFactory(ULTaxiOptions options, MessagingTaskQueue messagingTaskQueue) throws Exception {
        super(options, messagingTaskQueue);
        EmailSender emailSender = new JavaMailEmailSender(
                MessagingConfigurationReaderFactory.getEmailSenderConfigurationFileReader(options)
        );
        SmsSender smsSender = new SmsSenderStub(new Random());
        clientService = new ClientService(
            userRepository, clientAssembler, messagingTaskProducer, emailSender, userAuthenticationService
        );
        transportRequestService = new TransportRequestService(
                transportRequestRepository,
                transportRequestAssembler,
                userRepository,
                userAuthenticationService,
                messagingTaskProducer,
                smsSender,
                transportRequestTotalAmountAssembler,
                distanceCalculatorStrategy
        );


        setDevelopmentEnvironmentMockData();
    }

    private void setDevelopmentEnvironmentMockData() throws Exception {
        UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
        List<User> users = userDevDataFactory.createMockData(new BcryptHashing());
        users.forEach(userRepository::save);

        VehicleDevDataFactory vehicleDevDataFactory = new VehicleDevDataFactory();
        List<Vehicle> vehicles = vehicleDevDataFactory.createMockData();
        vehicles.forEach(vehicleRepository::save);

        TransportRequestDevDataFactory transportRequestDevDataFactory = new TransportRequestDevDataFactory();
        List<TransportRequest> transportRequests = transportRequestDevDataFactory.createMockData();
        transportRequests.forEach(transportRequestRepository::save);
    }

    @Override
    public ServerFactory withDriverResource() {
        resources.add(new DriverResourceImpl(driverService));
        return this;
    }

    @Override
    public ServerFactory withUserResource() {
        resources.add(new ClientResourceImpl(clientService));
        return this;
    }

    @Override
    public ServerFactory withUserAuthenticationResource() {
        resources.add(new UserAuthenticationResourceImpl(userAuthenticationService));
        return this;
    }

    @Override
    public ServerFactory withTransportRequestResource() {
        resources.add(new TransportRequestResourceImpl(transportRequestService));
        return this;
    }

    @Override
    public ServerFactory withAuthenticationFilters() {
        requestFilters.add(new AuthenticationFilter(tokenManager));
        requestFilters.add(new AuthorizationFilter(userRepository, tokenManager, tokenRepository));
        return this;
    }

    @Override
    protected ServerFactory withVehicleResource() {
        resources.add(new VehicleResourceImpl(vehicleService));
        return this;
    }

    @Override
    protected ServerFactory withRateResource() {
        resources.add(new RateResourceImpl(rateService));
        return this;
    }
}
