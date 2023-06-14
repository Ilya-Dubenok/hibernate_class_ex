package org.example.core.dto;

import java.util.List;

public class DepartmentFindDTO {

    private Long id;

    private List<String> filters;

    public DepartmentFindDTO() {
    }

    public DepartmentFindDTO(Long id, List<String> filters) {
        this.id = id;
        this.filters = filters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }
}
