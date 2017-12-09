package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskProducer;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingTaskProducerImpl;
import ca.ulaval.glo4003.ultaxi.utils.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.glo4003.ultaxi.utils.distanceCalculator.HaversineDistance;
import ca.ulaval.glo4003.ultaxi.utils.hashing.BcryptHashing;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.HashSet;
import java.util.Set;

public abstract class ServerFactory {

    protected final Set<Object> resources = new HashSet<>();
    protected final Set<ContainerRequestFilter> requestFilters = new HashSet<>();
    protected HashingStrategy hashingStrategy = new BcryptHashing();
    protected ULTaxiOptions options;
    protected MessagingTaskProducer messagingTaskProducer;

    public ServerFactory(ULTaxiOptions options, MessagingTaskQueue messagingTaskQueue) {
        this.messagingTaskProducer = new MessagingTaskProducerImpl(messagingTaskQueue);
        this.options = options;
    }

    protected abstract ServerFactory withDriverResource();

    protected abstract ServerFactory withUserResource();

    protected abstract ServerFactory withUserAuthenticationResource();

    protected abstract ServerFactory withTransportRequestResource();

    protected abstract ServerFactory withAuthenticationFilters();

    protected abstract ServerFactory withVehicleResource();

    protected abstract ServerFactory withRateResource();

    public ULTaxiServer getServer() throws Exception {
        return this
            .withAuthenticationFilters()
            .withVehicleResource()
            .withUserResource()
            .withDriverResource()
            .withTransportRequestResource()
            .withUserAuthenticationResource()
            .withRateResource()
            .createServer();
    }

    private ULTaxiServer createServer() {
        return new ULTaxiServer(resources, requestFilters, options);
    }
}
