package ca.ulaval.glo4003.ultaxi.domain.messaging.email;

import ca.ulaval.glo4003.ultaxi.domain.messaging.email.exception.EmailSendingFailureException;

public interface EmailSender {

    void sendEmail(Email email) throws EmailSendingFailureException;
}
