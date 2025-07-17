package guarantorsproject.com.codingassessment.validateaddress.model;

public class CensusComponents {
    private String fromAddress;
    private String streetName;
    private String streetType;
    private String city;
    private String state;
    private String zip;

    public String getFromAddress() { return fromAddress; }
    public void setFromAddress(String fromAddress) { this.fromAddress = fromAddress; }

    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }

    public String getStreetType() { return streetType; }
    public void setStreetType(String streetType) { this.streetType = streetType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
}
