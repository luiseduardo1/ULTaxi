package ca.ulaval.glo4003.ws.domain.request;

public class Request {

    private String id ;
    private String location;
    private String note;
    private String VehiculeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVehiculeType() {
        return VehiculeType;
    }

    public void setVehiculeType(String vehiculeType) {
        VehiculeType = vehiculeType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
