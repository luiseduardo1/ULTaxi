package ca.ulaval.glo4003.ultaxi.api.user;

import ca.ulaval.glo4003.ultaxi.service.user.ClientService;
import ca.ulaval.glo4003.ultaxi.transfer.user.client.ClientDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceImplTest {

    private static final String A_VALID_TOKEN = "Valid token";

    @Mock
    private ClientService clientService;
    @Mock
    private ClientDto clientDto;

    private UserResource userResource;

    @Before
    public void setUp() {
        userResource = new UserResourceImpl(clientService);
    }

    @Test
    public void givenUserWithValidName_whenCreateUser_thenReturnsOk() {
        Response response = userResource.createClient(clientDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenANewCreatedUser_whenRegistering_thenDelegateToUserService() {
        userResource.createClient(clientDto);
        verify(clientService).addClient(clientDto);
    }

    @Test
    public void givenAnAuthenticatedUser_updateClient_thenReturnsOK() {
        Response response = userResource.updateClient(A_VALID_TOKEN, clientDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenAUserResource_whenUpdatingClient_thenDelegateToUserService() {
        userResource.updateClient(A_VALID_TOKEN, clientDto);

        verify(clientService).updateClient(clientDto, A_VALID_TOKEN);
    }
}



