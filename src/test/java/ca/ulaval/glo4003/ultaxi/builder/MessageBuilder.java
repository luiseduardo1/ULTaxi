package ca.ulaval.glo4003.ultaxi.builder;

import ca.ulaval.glo4003.ultaxi.domain.messaging.Message;
import ca.ulaval.glo4003.ultaxi.domain.messaging.Reason;

public class MessageBuilder {

    private String sendTo;
    private Reason reason;

    public MessageBuilder() {
    }

    public Message build() {
        Message message = new Message();
        message.setSentTo(sendTo);
        message.setReason(reason);
        return message;
    }

    public MessageBuilder withSendTo(String sendTo) {
        this.sendTo = sendTo;
        return this;
    }

    public MessageBuilder withReason(Reason reason) {
        this.reason = reason;
        return this;
    }

}
