package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.SmsSendingFailureException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSmsSender implements SmsSender {

    private final String securityIdentifier;
    private final String authenticationToken;

    public TwilioSmsSender(String securityIdentifier, String authenticationToken) {
        this.securityIdentifier = securityIdentifier;
        this.authenticationToken = authenticationToken;
    }

    @Override
    public void sendSms(Sms sms) {
        PhoneNumber sourcePhoneNumber = new PhoneNumber(toTwilioPhoneNumber(sms.getSourcePhoneNumber()));
        PhoneNumber destinationPhoneNumber = new PhoneNumber(toTwilioPhoneNumber(sms.getDestinationPhoneNumber()));
        Message message = Message
            .creator(destinationPhoneNumber, sourcePhoneNumber, sms.getMessageBody())
            .create();
        handleMessageStatus(message);
    }

    private String toTwilioPhoneNumber(String phoneNumber) {
        return String.format("+1%s", phoneNumber);
    }

    private void handleMessageStatus(Message message) {
        if (message.getStatus() == Message.Status.FAILED) {
            throw new SmsSendingFailureException(
                String.format("Could not send SMS. Failed with the following error: %d%s",
                              message.getErrorCode(),
                              message.getErrorMessage())
            );
        }
    }
}
