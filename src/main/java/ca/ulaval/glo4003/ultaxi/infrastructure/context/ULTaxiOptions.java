package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import com.beust.jcommander.Parameter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ULTaxiOptions {

    @Parameter(names = {"-d", "--development"},
        description = "a flag indicating if the server should be run in development mode")
    private boolean isDevelopmentMode = true;
    @Parameter(names = {"-p", "--server-port"},
        description = "the port on which to bind the server")
    private int serverPort = 8000;
    @Parameter(names = {"--configuration-file"},
        description = "the name of the configuration containing the parameters of the email sender")
    private Path emailSenderConfigurationFilePath = Paths.get("emailSenderConfiguration.properties");
    @Parameter(names = {"--configuration-filetype"},
        description = "the type of the configuration file of the email sender")
    private EmailSenderConfigurationFileType emailSenderConfigurationFileType = EmailSenderConfigurationFileType
        .Properties;


    public EmailSenderConfigurationFileType getEmailSenderConfigurationFileType() {
        return emailSenderConfigurationFileType;
    }

    public Path getEmailSenderConfigurationFilePath() {
        return emailSenderConfigurationFilePath;
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean isDevelopmentMode() {
        return isDevelopmentMode;
    }
}
