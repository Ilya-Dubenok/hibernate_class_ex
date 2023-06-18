package org.example.core.dto;

import java.util.List;

public class DepartmentDTO {

    private  Long id;

    private Long version;

    private  String name;

    private  String phoneNum;

    private  Long locationId;

    private Long parentId;

    private List<Long> children;


    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, Long version, String name, String phoneNum, Long locationId, List<Long> children, Long parentId) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.phoneNum = phoneNum;
        this.locationId = locationId;
        this.children = children;
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

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
