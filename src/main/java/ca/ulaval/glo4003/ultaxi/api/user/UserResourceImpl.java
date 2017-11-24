package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.user.ClientService;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;

import javax.ws.rs.core.Response;

public class UserResourceImpl implements UserResource {

    private final ClientService clientService;

    public UserResourceImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Response createClient(ClientDto clientDto) {
        clientService.addClient(clientDto);
        return Response.ok().build();
    }

    @Override
    @Secured(Role.CLIENT)
    public Response updateClient(String userToken, ClientDto clientDto) {
        clientService.updateClient(clientDto, userToken);
        return Response.ok().build();
    }
}
