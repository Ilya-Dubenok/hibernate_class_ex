package org.example.core.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id","version"}, allowGetters = true)
public class DepartmentCreateDTO {


    private Long id;

    private Long version;

    private String name;

    private String phoneNum;

    private Long locationId;

    private Long parentId;


    public DepartmentCreateDTO() {
    }

    public DepartmentCreateDTO(String name, String phoneNum, Long locationId, Long parentId) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.locationId = locationId;
        this.parentId = parentId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
