package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueueProducer;
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
    protected MessageQueueProducer messageQueueProducer;

    public ServerFactory(ULTaxiOptions options, MessageQueue messageQueue) {
        this.messageQueueProducer = new MessageQueueProducer(messageQueue);
        this.options = options;
    }

    protected abstract ServerFactory withDriverResource();

    protected abstract ServerFactory withUserResource();

    protected abstract ServerFactory withUserAuthenticationResource();

    protected abstract ServerFactory withTransportRequestResource();

    protected abstract ServerFactory withAuthenticationFilters();

    protected abstract ServerFactory withVehicleResource();

    public ULTaxiServer getServer() throws Exception {
        return this
            .withAuthenticationFilters()
            .withVehicleResource()
            .withUserResource()
            .withDriverResource()
            .withTransportRequestResource()
            .withUserAuthenticationResource()
            .createServer();
    }

    private ULTaxiServer createServer() {
        return new ULTaxiServer(resources, requestFilters, options);
    }
}
