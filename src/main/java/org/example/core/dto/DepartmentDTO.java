package org.example.core.dto;

import java.util.List;

public class DepartmentDTO {

    private  Long id;

    private  String name;

    private  String phoneNum;

    private  Long locationId;

    private Long parent;

    private List<Long> children;


    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String name, String phoneNum, Long locationId, List<Long> children, Long parent) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.locationId = locationId;
        this.children = children;
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
