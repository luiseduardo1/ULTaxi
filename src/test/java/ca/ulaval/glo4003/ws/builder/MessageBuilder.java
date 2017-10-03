package ca.ulaval.glo4003.ws.builder;

import ca.ulaval.glo4003.ws.domain.messaging.Message;

public class MessageBuilder {

    private String sendTo;
    private String reason;

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

    public MessageBuilder withReason(String reason) {
        this.reason = reason;
        return this;
    }

}