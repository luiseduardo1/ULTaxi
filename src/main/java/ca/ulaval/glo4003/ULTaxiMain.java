package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessagingTaskQueue;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.DevelopmentServerFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ServerFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiOptions;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiServer;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingTaskQueueInMemory;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessagingThreadFactory;
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
            MessagingTaskQueue messagingTaskQueue = createMessagingTaskQueue(options);
            Thread messagingThread = createMessagingThread(options, messagingTaskQueue);
            server = createServer(options, messagingTaskQueue);

            messagingThread.start();
            server.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static ULTaxiServer createServer(ULTaxiOptions options, MessagingTaskQueue messagingTaskQueue) throws
        Exception {
        ServerFactory serverFactory = new DevelopmentServerFactory(options, messagingTaskQueue);
        return serverFactory.getServer();
    }

    private static Thread createMessagingThread(ULTaxiOptions options, MessagingTaskQueue messagingTaskQueue) throws
        Exception {
        return MessagingThreadFactory.getMessagingThread(messagingTaskQueue, options);
    }

    private static MessagingTaskQueue createMessagingTaskQueue(ULTaxiOptions options) {
        return new MessagingTaskQueueInMemory();
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
