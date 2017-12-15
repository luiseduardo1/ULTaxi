package ca.ulaval.glo4003.ultaxi.api.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidEmailAddressException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidHashingStrategyException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidPhoneNumberException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.InvalidUsernameException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.NonExistentUserException;
import ca.ulaval.glo4003.ultaxi.domain.user.exception.UserAlreadyExistsException;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import ca.ulaval.glo4003.ultaxi.service.user.client.ClientService;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;

import javax.ws.rs.core.Response;

public class ClientResourceImpl implements ClientResource {

    private final ClientService clientService;

    public ClientResourceImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Response createClient(ClientDto clientDto) throws InvalidEmailAddressException,
        InvalidHashingStrategyException, InvalidPasswordException, InvalidPhoneNumberException,
        InvalidUsernameException, UserAlreadyExistsException {
        clientService.addClient(clientDto);
        return Response.ok().build();
    }

    @Override
    @Secured(Role.CLIENT)
    public Response updateClient(String clientToken, ClientDto clientDto) throws InvalidEmailAddressException,
        InvalidHashingStrategyException, InvalidPasswordException, InvalidPhoneNumberException,
        InvalidUsernameException, NonExistentUserException, InvalidTokenException {
        clientService.updateClient(clientDto, clientToken);
        return Response.ok().build();
    }
}
