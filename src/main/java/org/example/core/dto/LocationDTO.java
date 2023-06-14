package org.example.core.dto;

public class LocationDTO {

    private Long id;

    private String address;

    public LocationDTO() {
    }

    public LocationDTO(Long id, String address) {
        this.id = id;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
