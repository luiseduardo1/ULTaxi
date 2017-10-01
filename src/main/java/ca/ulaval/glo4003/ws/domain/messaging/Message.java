package ca.ulaval.glo4003.ws.domain.messaging;

public class Message {

    private String sentTo;
    private String reason;

    public Message() {

    }

    public Message(String sendTo, String reason) {
        this.sentTo = sendTo;
        this.reason = reason;
    }

    public String getSentTo() {
        return this.sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
