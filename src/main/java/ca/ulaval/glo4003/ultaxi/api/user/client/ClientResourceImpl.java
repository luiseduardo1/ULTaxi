package ca.ulaval.glo4003.ultaxi.api.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.service.user.client.ClientService;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;

import javax.ws.rs.core.Response;

public class ClientResourceImpl implements ClientResource {

    private final ClientService clientService;

    public ClientResourceImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Response createClient(ClientDto clientDto) {
        clientService.addClient(clientDto);
        return Response.ok().build();
    }

    @Override
    @Secured(Role.CLIENT)
    public Response updateClient(String clientToken, ClientDto clientDto) {
        clientService.updateClient(clientDto, clientToken);
        return Response.ok().build();
    }
}
