package ca.ulaval.glo4003.ws.api.user.dto;

public class UserDto {

    public String id;
    public String telephoneNumber;
    public String address;
    public String name;

    @Override
    public String toString() {
        return "UserDto{" + "id='" + id + '\'' + ", telephoneNumber='" + telephoneNumber + '\''
            + ", address='" + address + '\'' + ", name='" + name + '\'' + '}';
    }
}
