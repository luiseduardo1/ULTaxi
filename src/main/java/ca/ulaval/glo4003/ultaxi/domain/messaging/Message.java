package ca.ulaval.glo4003.ultaxi.domain.messaging;

public class Message {

    private String sentTo;
    private Reason reason;

    public Message() {
    }

    public Message(String sendTo, Reason reason) {
        this.sentTo = sendTo;
        this.reason = reason;
    }

    public String getSentTo() {
        return this.sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public Reason getReason() {
        return this.reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
}
