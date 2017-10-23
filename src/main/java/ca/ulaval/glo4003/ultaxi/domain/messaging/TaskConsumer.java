package ca.ulaval.glo4003.ultaxi.domain.messaging;

public interface TaskConsumer extends Runnable {

    @Override
    void run();
}
