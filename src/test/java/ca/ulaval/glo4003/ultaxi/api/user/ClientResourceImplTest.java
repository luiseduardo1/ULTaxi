package ca.ulaval.glo4003.ultaxi.api.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.ultaxi.api.user.client.ClientResource;
import ca.ulaval.glo4003.ultaxi.api.user.client.ClientResourceImpl;
import ca.ulaval.glo4003.ultaxi.service.user.client.ClientService;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class ClientResourceImplTest {

    private static final String A_VALID_TOKEN = "Valid token";

    @Mock
    private ClientService clientService;
    @Mock
    private ClientDto clientDto;

    private ClientResource clientResource;

    @Before
    public void setUp() {
        clientResource = new ClientResourceImpl(clientService);
    }

    @Test
    public void givenUserWithValidName_whenCreateUser_thenReturnsOk() {
        Response response = clientResource.createClient(clientDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenANewCreatedUser_whenRegistering_thenDelegateToUserService() {
        clientResource.createClient(clientDto);
        verify(clientService).addClient(clientDto);
    }

    @Test
    public void givenAnAuthenticatedUser_updateClient_thenReturnsOK() {
        Response response = clientResource.updateClient(A_VALID_TOKEN, clientDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAUserResource_whenUpdatingClient_thenDelegateToUserService() {
        clientResource.updateClient(A_VALID_TOKEN, clientDto);

        verify(clientService).updateClient(clientDto, A_VALID_TOKEN);
    }
}



