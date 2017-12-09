package ca.ulaval.glo4003.ultaxi.domain.user.client;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.TransportRequest;
import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.User;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class Client extends User {

    private String currentTransportRequestId;

    public Client(String username, String password, PhoneNumber phoneNumber, String emailAddress,
        HashingStrategy hashingStrategy) {
        super(username, password, phoneNumber, emailAddress, hashingStrategy);
    }

    @Override
    public Role getRole() {
        return Role.CLIENT;
    }

    public void assignTransportRequestId(TransportRequest transportRequest) {
        if (this.currentTransportRequestId != null) {

        }
        this.currentTransportRequestId = transportRequest.getId();
    }

    public boolean hasAnActiveTransportRequest() {
        return this.currentTransportRequestId != null;
    }
}
