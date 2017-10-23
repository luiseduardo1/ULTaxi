package ca.ulaval.glo4003.ultaxi.domain.messaging;

public interface TaskReceiver extends Runnable{

    @Override
    void run();
}
