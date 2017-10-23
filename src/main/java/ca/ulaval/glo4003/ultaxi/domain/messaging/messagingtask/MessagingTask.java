package ca.ulaval.glo4003.ultaxi.domain.messaging.messagingtask;

public interface MessagingTask extends Runnable {

    @Override
    void run();
}
