package org.example.core.dto;

import java.util.List;

public class DepartmentDTO {

    private  long id;

    private  String name;

    private  String phoneNum;

    private  long locationId;

    private List<Long> children;

    private Long parent;

    public DepartmentDTO() {
    }

    public DepartmentDTO(long id, String name, String phoneNum, long locationId, List<Long> children, Long parent) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.locationId = locationId;
        this.children = children;
        this.parent = parent;
    }

    public long getId() {
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

    public long getLocationId() {
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
