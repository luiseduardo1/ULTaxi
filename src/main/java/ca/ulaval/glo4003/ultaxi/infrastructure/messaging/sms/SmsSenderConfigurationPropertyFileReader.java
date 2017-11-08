package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SmsSenderConfigurationPropertyFileReader implements SmsSenderConfigurationReader {

    private final String filename;

    public SmsSenderConfigurationPropertyFileReader(String filename) {
        this.filename = filename;
    }

    @Override
    public Properties read() {
        Properties smsSenderProperties = System.getProperties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(filename);

        try {
            smsSenderProperties.load(input);
        } catch (IOException exception) {
            System.out.println("Sms sender server configuration couldn't be loaded.");
        }
        return smsSenderProperties;
    }
}
