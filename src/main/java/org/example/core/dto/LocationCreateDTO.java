package org.example.core.dto;

public class LocationCreateDTO {

    private String address;

    public LocationCreateDTO() {
    }

    public LocationCreateDTO(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
