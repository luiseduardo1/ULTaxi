package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiOptions;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.EmailSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.EmailSenderConfigurationReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.exception.InvalidMessagingConfigurationFileTypeException;

public final class MessagingConfigurationReaderFactory {

    public static EmailSenderConfigurationReader getEmailSenderConfigurationFileReader(ULTaxiOptions options) {
        EmailSenderConfigurationReader emailSenderConfigurationFileReader;
        switch (options.getEmailSenderConfigurationFileType()) {
            case PROPERTIES:
                emailSenderConfigurationFileReader = new EmailSenderConfigurationPropertyFileReader(
                    options
                        .getEmailSenderConfigurationFilePath()
                        .toString()
                );
                break;
            default:
                throw new InvalidMessagingConfigurationFileTypeException(
                    "The specified format is invalid or not supported by the application."
                );
        }

        return emailSenderConfigurationFileReader;
    }
}
