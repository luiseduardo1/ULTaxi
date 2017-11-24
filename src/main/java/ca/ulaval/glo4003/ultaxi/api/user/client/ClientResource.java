package ca.ulaval.glo4003.ultaxi.api.user.client;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;
import ca.ulaval.glo4003.ultaxi.http.authentication.filtering.Secured;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clients")
public interface ClientResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createClient(ClientDto clientDto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Role.CLIENT)
    Response updateClient(@HeaderParam(value = "Authorization") String clientToken, ClientDto clientDto);
}
