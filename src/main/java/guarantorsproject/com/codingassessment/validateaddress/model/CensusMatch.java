package guarantorsproject.com.codingassessment.validateaddress.model;

public class CensusMatch {
    private String matchedAddress;
    private CensusComponents addressComponents;

    public String getMatchedAddress() { return matchedAddress; }
    public void setMatchedAddress(String matchedAddress) { this.matchedAddress = matchedAddress; }

    public CensusComponents getAddressComponents() { return addressComponents; }
    public void setAddressComponents(CensusComponents addressComponents) { this.addressComponents = addressComponents; }
}