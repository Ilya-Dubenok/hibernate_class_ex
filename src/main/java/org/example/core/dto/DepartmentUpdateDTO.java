package org.example.core.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentUpdateDTO {

    private Long id;

    private String name;

    private String phoneNum;

    private Long locationId;

    private Long parent_id;

    public DepartmentUpdateDTO() {
    }

    public DepartmentUpdateDTO(Long id, String name, String phoneNum, Long locationId, Long parent_id) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.locationId = locationId;
        this.parent_id = parent_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }
}
