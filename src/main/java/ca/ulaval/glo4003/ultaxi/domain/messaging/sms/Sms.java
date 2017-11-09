package ca.ulaval.glo4003.ultaxi.domain.messaging.sms;

public class Sms {

    private final String destinationPhoneNumber;
    private final String sourcePhoneNumber;
    private final String messageBody;

    public Sms(String destinationPhoneNumber, String sourcePhoneNumber, String messageBody) {
        this.destinationPhoneNumber = destinationPhoneNumber;
        this.sourcePhoneNumber = sourcePhoneNumber;
        this.messageBody = messageBody;
    }

    public String getDestinationPhoneNumber() {
        return destinationPhoneNumber;
    }

    public String getSourcePhoneNumber() {
        return sourcePhoneNumber;
    }

    public String getMessageBody() {
        return messageBody;
    }
}