package ca.ulaval.glo4003.ws.domain.messaging;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageProducerServiceTest {

    @Mock
    private MessageQueue messageQueue;
    @Mock
    private Message message;

    private MessageProducerService messageProducerService;

    @Before
    public void setUp() throws Exception {
        messageProducerService = new MessageProducerService(messageQueue);
    }

    @Test
    public void givenANewMessageToSend_whenEnqueueMessage_thenMessageIsPersisted()
        throws InterruptedException {
        messageProducerService.enqueueMessage(message);

        verify(messageQueue).enqueue(message);
    }
}
