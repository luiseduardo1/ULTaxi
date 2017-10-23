package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.DevelopmentServerFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.MessagingThreadFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ServerFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiOptions;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiServer;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessageQueueInMemory;
import com.beust.jcommander.JCommander;

import java.util.Optional;

public final class ULTaxiMain {

    private static final int DEFAULT_PORT = 0;
    private static final String DEFAULT_URI = "http://localhost";
    private static ULTaxiServer server = null;

    private ULTaxiMain() {
        throw new AssertionError("Instantiating main class...");
    }

    public static void main(String[] args) {
        ULTaxiOptions options = new ULTaxiOptions();
        JCommander
            .newBuilder()
            .addObject(options)
            .build()
            .parse(args);
        try {
            createMainSystems(options).ifPresent(Thread::start);
            server.start();
            server.join();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            server.destroy();
        }
    }

    public static void start() throws Exception {
        ULTaxiOptions options = new ULTaxiOptions();
        createMainSystems(options).ifPresent(Thread::start);
        server.start();
        server.join();
    }

    public static Optional<Thread> createMainSystems(ULTaxiOptions options) throws Exception {
        Thread messagingThread = null;
        if (server == null) {
            ServerFactory serverFactory;
            if (options.isDevelopmentMode()) {
                MessageQueue messageQueue = new MessageQueueInMemory();
                serverFactory = new DevelopmentServerFactory(options, messageQueue);
                messagingThread = MessagingThreadFactory.getMessagingThread(messageQueue, options);
                server = serverFactory.getServer();
            }
        }

        return Optional.ofNullable(messagingThread);
    }

    public static void stop() throws Exception {
        if (server != null) {
            server.tearDown();
            server = null;
        }
    }

    public static String getBaseURI() {
        return Optional
            .ofNullable(server)
            .map(ULTaxiServer::getBaseURI)
            .orElse(DEFAULT_URI);
    }

    public static int getPort() {
        return Optional
            .ofNullable(server)
            .map(ULTaxiServer::getPort)
            .orElse(DEFAULT_PORT);
    }
}
