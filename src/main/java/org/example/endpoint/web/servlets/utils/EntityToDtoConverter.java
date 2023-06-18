package org.example.endpoint.web.servlets.utils;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.dao.entity.Department;
import org.example.dao.entity.Location;
import org.example.service.factory.DepartmentServiceFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntityToDtoConverter {

    public static DepartmentCreateDTO convertToDepartmentCreateDTO(Department department) {
        if (department == null) {
            return new DepartmentCreateDTO();
        }

        Location loc;
        Department parId;
        DepartmentCreateDTO res = new DepartmentCreateDTO(
                department.getName(),
                department.getPhone(),
                (loc = department.getLocation()) == null ? null : loc.getId(),
                (parId = department.getParent()) == null ? null : parId.getId()

        );
        res.setId(department.getId());
        res.setVersion(
                getVersionFromDateTimeUpdated(department.getDateTimeUpdated())
        );
        return res;
    }

    public static DepartmentDTO convertToDepartmentDTO(Department department) {
        if (department == null) {
            return new DepartmentDTO();
        }

        DepartmentDTO res = new DepartmentDTO();

        res.setId(department.getId());
        res.setVersion(getVersionFromDateTimeUpdated(department.getDateTimeUpdated()));
        res.setName(department.getName());
        res.setPhoneNum(department.getPhone());

        Location location = department.getLocation();
        if (location != null) {
            res.setLocationId(location.getId());

        }

        Department parent = department.getParent();
        if (null != parent) {
            res.setParentId(department.getParent().getId());
        }

        List<Department> children = DepartmentServiceFactory.getInstance().findChildren(department.getId());
        List<Long> childrenIdList = children.stream().map(Department::getId).toList();
        res.setChildren(childrenIdList);
        return res;

    }

    private static long getVersionFromDateTimeUpdated(LocalDateTime dateTimeUpdated) {
        return ZonedDateTime.of(dateTimeUpdated, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    public static List<DepartmentDTO> convertToDepartmentDTOList(List<Department> list) {

        List<DepartmentDTO> res = new ArrayList<>();

        for (Department department : list) {
            res.add(convertToDepartmentDTO(department));
        }

        return res;

    }

}
