package org.example.core.dto;

import java.util.List;

public class DepartmentCreateDTO {


    private String name;

    private String phoneNum;

    private long locationId;

    private Long parent_id;

    private List<Long> children;

    public DepartmentCreateDTO() {
    }

    public DepartmentCreateDTO(String name, String phoneNum, long locationId, Long parent_id, List<Long> children) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.locationId = locationId;
        this.parent_id = parent_id;
        this.children = children;
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

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }
}
