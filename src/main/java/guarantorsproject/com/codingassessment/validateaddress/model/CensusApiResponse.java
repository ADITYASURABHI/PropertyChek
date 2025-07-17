package guarantorsproject.com.codingassessment.validateaddress.model;

import java.util.List;

public class CensusApiResponse {
    private Result result;
    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }

    public static class Result {
        private List<CensusMatch> addressMatches;
        public List<CensusMatch> getAddressMatches() { return addressMatches; }
        public void setAddressMatches(List<CensusMatch> addressMatches) { this.addressMatches = addressMatches; }
    }
}