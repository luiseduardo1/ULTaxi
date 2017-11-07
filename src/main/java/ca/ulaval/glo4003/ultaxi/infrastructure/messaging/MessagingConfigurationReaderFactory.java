package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiOptions;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.EmailSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.email.EmailSenderConfigurationReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.exception.InvalidMessagingConfigurationFileTypeException;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms.SmsSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms.SmsSenderConfigurationReader;

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

    public static SmsSenderConfigurationReader getSmsSenderConfigurationFileReader(ULTaxiOptions options) {
        SmsSenderConfigurationReader smsSenderConfigurationReader;
        switch (options.getSmsSenderConfigurationFileType()) {
            case PROPERTIES:
                smsSenderConfigurationReader = new SmsSenderConfigurationPropertyFileReader(
                    options
                        .getSmsSenderConfigurationFilePath()
                        .toString()
                );
                break;
            default:
                throw new InvalidMessagingConfigurationFileTypeException(
                    "The specified format is invalid or not supported by the application."
                );
        }

        return smsSenderConfigurationReader;
    }
}
