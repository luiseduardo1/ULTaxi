package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingConfigurationFileType;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.nio.file.Path;
import java.nio.file.Paths;

@Parameters(separators = "=", commandDescription = "Run a ULTaxi server")
public class ULTaxiOptions {

    @Parameter(names = {"-d", "--development"},
        description = "a flag indicating if the server should be run in development mode")
    private boolean isDevelopmentMode = false;

    @Parameter(names = {"-p", "--server-port"},
        description = "the port on which to bind the server")
    private int serverPort = 0;

    @Parameter(names = "--email-configuration",
        arity = 2,
        description = "the configuration of the email server")
    private MessagingConfigurationOptions emailConfiguration = new MessagingConfigurationOptions(
        "emailSenderConfiguration.properties",
        "PROPERTIES"
    );

    @Parameter(names = "--sms-configuration",
        arity = 2,
        description = "the configuration of the SMS server")
    private MessagingConfigurationOptions smsConfiguration = new MessagingConfigurationOptions(
        "sms-sender-configuration.properties",
        "PROPERTIES"
    );

    @Parameter(names = {"-h", "--help"},
        description = "display this message",
        help = true)
    private boolean help;


    public Path getEmailSenderConfigurationFilePath() {
        return Paths.get(emailConfiguration.filePath);
    }

    public Path getSmsSenderConfigurationFilePath() {
        return Paths.get(smsConfiguration.filePath);
    }

    public MessagingConfigurationFileType getEmailSenderConfigurationFileType() {
        return toMessagingConfigurationFileType(emailConfiguration.fileType);
    }

    public MessagingConfigurationFileType getSmsSenderConfigurationFileType() {
        return toMessagingConfigurationFileType(smsConfiguration.fileType);
    }

    private MessagingConfigurationFileType toMessagingConfigurationFileType(String type) {
        return MessagingConfigurationFileType.valueOf(type.toUpperCase());
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean isDevelopmentMode() {
        return isDevelopmentMode;
    }
}
