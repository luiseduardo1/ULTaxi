package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiOptions;

public final class MessagingThreadFactory {

    public static Thread getMessagingThread(MessagingTaskQueue messageQueue, ULTaxiOptions options) {
        return new Thread(new MessagingTaskConsumerImpl(messageQueue));
    }
}
