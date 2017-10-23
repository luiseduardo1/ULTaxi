package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.infrastructure.context.exception.InvalidEmailSenderConfigurationFileTypeException;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSenderConfigurationPropertyFileReader;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSenderConfigurationReader;

public final class EmailSenderConfigurationReaderFactory {

    public static EmailSenderConfigurationReader getEmailSenderConfigurationFileReader(ULTaxiOptions options) {
        EmailSenderConfigurationReader emailSenderConfigurationFileReader;
        switch (options.getEmailSenderConfigurationFileType()) {
            case Properties:
                emailSenderConfigurationFileReader = new EmailSenderConfigurationPropertyFileReader(
                    options.getEmailSenderConfigurationFilePath().toString()
                );
                break;
            default:
                throw new InvalidEmailSenderConfigurationFileTypeException(
                    "The specified format is invalid or not supported by the application."
                );
        }

        return emailSenderConfigurationFileReader;
    }
}
