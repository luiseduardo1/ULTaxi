package ca.ulaval.glo4003.ws.domain.email;

public class Email {

    private String recipientAddress;
    private String subject;
    private String content;
    private String signature;

    public Email(String recipientAddress, String subject, String content, String signature) {
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.content = content;
        this.signature = signature;
    }

    public String getRecipientAddress() {
        return this.recipientAddress;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getContent() {
        return this.content;
    }

    public String getSignature() {
        return this.signature;
    }
}
