package ca.ulaval.glo4003.ultaxi.transfer.user.client;

import ca.ulaval.glo4003.ultaxi.transfer.user.AuthenticationDto;

public class ClientDto extends AuthenticationDto {

    private String phoneNumber;
    private String emailAddress;
    private String currentTransportRequestId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }

    public String getCurrentTransportRequestId() {
        return currentTransportRequestId;
    }

    public void setCurrentTransportRequestId(String currentTransportRequestId) {
        this.currentTransportRequestId = currentTransportRequestId;
    }
}
