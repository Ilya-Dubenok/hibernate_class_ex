package org.example.endpoint.web.servlets.utils;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.dao.entity.Department;
import org.example.dao.entity.Location;
import org.example.service.factory.DepartmentServiceFactory;

import java.util.ArrayList;
import java.util.List;

public class EntityToDtoConverter {

    public static DepartmentCreateDTO convertToDepartmentCreateDTO(Department department) {

        DepartmentCreateDTO res = new DepartmentCreateDTO(
                department.getName(),
                department.getPhone(),
                department.getLocation().getId(),
                department.getParent() == null ? null : department.getParent().getId()

        );
        res.setId(department.getId());
        return res;
    }

    public static DepartmentDTO convertToDepartmentDTO(Department department) {
        DepartmentDTO res = new DepartmentDTO();

        res.setId(department.getId());
        res.setName(department.getName());
        res.setPhoneNum(department.getPhone());

        Location location = department.getLocation();
        if (location != null) {
            res.setLocationId(location.getId());

        }

        Department parent = department.getParent();
        if (null != parent) {
            res.setParent(department.getParent().getId());
        }

        List<Department> children = DepartmentServiceFactory.getInstance().findChildren(department.getId());
        List<Long> childrenIdList = children.stream().map(Department::getId).toList();
        res.setChildren(childrenIdList);
        return res;

    }


    public static List<DepartmentDTO> convertToDepartmentDTOList(List<Department> list) {

        List<DepartmentDTO> res = new ArrayList<>();

        for (Department department : list) {
            res.add(convertToDepartmentDTO(department));
        }

        return res;

    }

}
