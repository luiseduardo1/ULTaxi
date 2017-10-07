package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4003.ultaxi.builder.MessageBuilder;
import ca.ulaval.glo4003.ultaxi.domain.messaging.Message;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;
import org.junit.Before;
import org.junit.Test;

public class MessageQueueInMemoryTest {

    private static final String A_RECIPIENT_ADDRESS = "test@example.com";
    private static final String A_REASON = "Registration";

    private MessageQueue messageQueue;

    @Before
    public void setUp() {
        messageQueue = new MessageQueueInMemory();
    }

    @Test
    public void givenAMessage_whenMessageIsEnqueued_thenMessageHasSameParameters()
        throws InterruptedException {
        Message messageToBeEnqueued = given(
            aMessage().withSendTo(A_RECIPIENT_ADDRESS).withReason(A_REASON));

        messageQueue.enqueue(messageToBeEnqueued);

        Message messageDequeued = messageQueue.peek();
        assertEquals(messageToBeEnqueued, messageDequeued);
    }

    private MessageBuilder aMessage() {
        return new MessageBuilder();
    }

    private Message given(MessageBuilder builder) {
        return builder.build();
    }
}