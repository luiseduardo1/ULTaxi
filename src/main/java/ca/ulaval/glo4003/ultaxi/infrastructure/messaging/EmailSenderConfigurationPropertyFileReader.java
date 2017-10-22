package ca.ulaval.glo4003.ultaxi.infrastructure.messaging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSenderConfigurationPropertyFileReader implements EmailSenderConfigurationReader {

    private final String filename;

    public EmailSenderConfigurationPropertyFileReader(String filename) {
        this.filename = filename;
    }

    @Override
    public Properties read() {
        Properties emailSenderProperties = System.getProperties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(filename);

        try {
            emailSenderProperties.load(input);
        } catch (IOException exception) {
            System.out.println("Email sender server configuration couldn't be loaded.");
        }
        return emailSenderProperties;
    }
}
