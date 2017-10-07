package ca.ulaval.glo4003.ultaxi.domain.messaging.email;

public class Email {

    private String fromAddress;
    private String toAddress;
    private String subject;
    private String content;
    private String signature;
    private String fromAddressName;

    public Email(String fromAddress, String fromAddressName, String toAddress, String subject, String content,
        String signature) {
        this.fromAddress = fromAddress;
        this.fromAddressName = fromAddressName;
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
        this.signature = signature;
    }

    public Email(String toAddress, String subject, String content, String signature) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
        this.signature = signature;
    }

    public String getToAddress() {
        return this.toAddress;
    }

    public String getFromAddress() {
        return this.fromAddress;
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

    public String getFromAddressName() {
        return this.fromAddressName;
    }
}
