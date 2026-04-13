package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyResponse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("catchPhrase")
    private String catchPhrase;

    @JsonProperty("bs")
    private String bs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }
}