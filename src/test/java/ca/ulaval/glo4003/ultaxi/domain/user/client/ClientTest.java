package ca.ulaval.glo4003.ultaxi.domain.user.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;

import ca.ulaval.glo4003.ultaxi.domain.transportrequest.exception.ClientAlreadyHasAnActiveTransportRequestException;
import ca.ulaval.glo4003.ultaxi.domain.user.PhoneNumber;
import ca.ulaval.glo4003.ultaxi.utils.hashing.HashingStrategy;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    private static final String A_VALID_USERNAME = "Ronald Macdonald";
    private static final String A_VALID_PASSWORD = "mysupersecret";
    private static final String A_VALID_PHONE_NUMBER = "234-235-5678";
    private static final String A_VALID_EMAIL_ADDRESS = "ronald.macdonald@ulaval.ca";

    @Mock
    private HashingStrategy hashingStrategy;

    private Client client;
    private PhoneNumber phoneNumber = new PhoneNumber(A_VALID_PHONE_NUMBER);
    private String hash = RandomStringUtils.randomAlphabetic(10);
    private String transportRequestId = UUID.randomUUID().toString();


    @Before
    public void setUp() {
        willReturn(hash).given(hashingStrategy).hashWithRandomSalt(anyString());
        client = createValidClient();
    }

    @Test
    public void givenAClientWithNoTransportRequestId_whenAssignTransportRequestId_thenTransportRequestIdIsAssigned() {
        client.assignTransportRequestId(transportRequestId);

        assertEquals(transportRequestId, client.getCurrentTransportRequestId());
    }

    @Test
    public void givenAClientWithNoTransportRequestId_whenAssignNullTransportRequestId_thenTransportRequestIdIsNull() {
        client.assignTransportRequestId(null);

        assertEquals(null, client.getCurrentTransportRequestId());
    }

    @Test(expected = ClientAlreadyHasAnActiveTransportRequestException.class)
    public void givenAClientWithATransportRequestId_whenAssignTransportRequestId_thenThrowsException() {
        client.assignTransportRequestId(transportRequestId);

        client.assignTransportRequestId(transportRequestId);
    }

    private Client createValidClient() {
        return new Client(A_VALID_USERNAME, A_VALID_PASSWORD, phoneNumber, A_VALID_EMAIL_ADDRESS, hashingStrategy);
    }
}