package ca.ulaval.glo4003.ultaxi.transfer.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.client.Client;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;

public class ClientAssembler {

    private final HashingStrategy hashingStrategy;

    public ClientAssembler(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public Client create(ClientDto clientDto) {
        return new Client(
            clientDto.getUsername(),
            clientDto.getPassword(),
            clientDto.getPhoneNumber(),
            clientDto.getEmailAddress(),
            hashingStrategy
        );
    }

    public ClientDto create(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setUsername(client.getUsername());
        clientDto.setPassword(client.getPassword());
        clientDto.setPhoneNumber(client.getPhoneNumber());
        clientDto.setEmailAddress(client.getEmailAddress());
        return clientDto;
    }
}
