package ca.ulaval.glo4003.ultaxi.transfer.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class ClientAssembler {

    private final HashingStrategy hashingStrategy;

    public ClientAssembler(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public Client create(ClientDto clientDto) {
        Client client = new Client(
            clientDto.getUsername(),
            clientDto.getPassword(),
            new PhoneNumber(clientDto.getPhoneNumber()),
            clientDto.getEmailAddress(),
            hashingStrategy
        );
        client.assignTransportRequestId(clientDto.getCurrentTransportRequestId());
        return client;
    }

    public ClientDto create(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setUsername(client.getUsername());
        clientDto.setPassword(client.getPassword());
        clientDto.setPhoneNumber(client.getPhoneNumber().getNumber());
        clientDto.setEmailAddress(client.getEmailAddress());
        clientDto.setCurrentTransportRequestId(client.getCurrentTransportRequestId());
        return clientDto;
    }
}
