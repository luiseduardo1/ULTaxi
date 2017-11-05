package ca.ulaval.glo4003.ultaxi.infrastructure.messaging.sms;

import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.Sms;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.SmsSender;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.SmsSendingFailureException;
import ca.ulaval.glo4003.ultaxi.domain.messaging.sms.exception.UnrecoverableSmsSendingFailureException;
import com.twilio.Twilio;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Properties;

public class TwilioSmsSender implements SmsSender {

    private final String securityIdentifier;
    private final String authenticationToken;

    public TwilioSmsSender(SmsSenderConfigurationReader smsSenderConfigurationReader) {
        Properties configurationProperties = smsSenderConfigurationReader.read();
        this.securityIdentifier = configurationProperties.getProperty("sms.twilio.sid");
        this.authenticationToken = configurationProperties.getProperty("sms.twilio.auth_token");
    }

    @Override
    public void sendSms(Sms sms) {
        Twilio.init(securityIdentifier, authenticationToken);
        PhoneNumber sourcePhoneNumber = new PhoneNumber(toTwilioPhoneNumber(sms.getSourcePhoneNumber()));
        PhoneNumber destinationPhoneNumber = new PhoneNumber(toTwilioPhoneNumber(sms.getDestinationPhoneNumber()));
        try {
            Message
                .creator(destinationPhoneNumber, sourcePhoneNumber, sms.getMessageBody())
                .create();

        } catch (ApiException exception) {
            handleMessageException(exception);
        } catch (ApiConnectionException exception) {
            throw new SmsSendingFailureException(
                String.format("Could not send SMS. Failed with: %s", exception.getMessage())
            );
        }
    }

    private String toTwilioPhoneNumber(String phoneNumber) {
        return String.format("+1%s", phoneNumber);
    }

    private void handleMessageException(ApiException exception) {
        String errorMessage = String.format(
            "Could not send SMS: %s. Failed with the following error code: %d (see %s for more details).",
            exception.getMessage(),
            exception.getCode(),
            exception.getMoreInfo()
        );
        switch (exception.getCode()) {
            case 11205:
                throw new SmsSendingFailureException(errorMessage);
            case 30001:
                throw new SmsSendingFailureException(errorMessage);
            default:
                throw new UnrecoverableSmsSendingFailureException(errorMessage);
        }
    }
}
