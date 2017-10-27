package ca.ulaval.glo4003.ultaxi.domain.messaging.email;

public class Email {

    private String toAddress;
    private String subject;
    private String content;
    private String signature;

    public Email(String toAddress, String subject, String content, String signature) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
        this.signature = signature;
    }

    public String getToAddress() {
        return this.toAddress;
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
