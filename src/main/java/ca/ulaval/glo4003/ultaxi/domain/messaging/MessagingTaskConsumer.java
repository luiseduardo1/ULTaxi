package ca.ulaval.glo4003.ultaxi.domain.messaging;

public interface MessagingTaskConsumer extends Runnable {

    @Override
    void run();

}
