package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.MessagingThread;
import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiOptions;

public final class MessagingThreadFactory {

    public static Thread getMessagingThread(MessageQueue messageQueue, ULTaxiOptions options) {
        EmailSenderConfigurationReader emailSenderConfigurationReader = EmailSenderConfigurationReaderFactory
            .getEmailSenderConfigurationFileReader(options);
        EmailSender emailSender = new EmailSender(emailSenderConfigurationReader);
        return new Thread(new MessagingThread(messageQueue, emailSender));
    }
}
