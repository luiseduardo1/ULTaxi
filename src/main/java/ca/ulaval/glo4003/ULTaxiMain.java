package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ultaxi.domain.messaging.MessageQueue;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.DevelopmentServerFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ServerFactory;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiOptions;
import ca.ulaval.glo4003.ultaxi.infrastructure.context.ULTaxiServer;
import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.MessageQueueInMemory;
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
            MessageQueue messageQueue = createMessageQueue(options);
            Thread messagingThread = createMessagingThread(options, messageQueue);
            server = createServer(options, messageQueue);

            messagingThread.start();
            server.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static ULTaxiServer createServer(ULTaxiOptions options, MessageQueue messageQueue) throws Exception {
        if (server == null) {
            ServerFactory serverFactory;
            if (options.isDevelopmentMode()) {
                serverFactory = new DevelopmentServerFactory(options, messageQueue);
                return serverFactory.getServer();
            }
        }
        return null;
    }

    private static Thread createMessagingThread(ULTaxiOptions options, MessageQueue messageQueue) throws
        Exception {
        Thread messagingThread = null;
        if (options.isDevelopmentMode()) {
            messagingThread = MessagingThreadFactory.getMessagingThread(messageQueue, options);
        }

        return messagingThread;
    }

    private static MessageQueue createMessageQueue(ULTaxiOptions options) {
        MessageQueue messageQueue = null;
        if (options.isDevelopmentMode()) {
            messageQueue = new MessageQueueInMemory();
        }

        return messageQueue;
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
