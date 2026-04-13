package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoResponse {
    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lng")
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public double getLatitude() {
        try {
            return Double.parseDouble(lat != null ? lat : "0");
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getLongitude() {
        try {
            return Double.parseDouble(lng != null ? lng : "0");
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}