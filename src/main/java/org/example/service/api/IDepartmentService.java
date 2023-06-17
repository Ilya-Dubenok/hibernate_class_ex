package org.example.service.api;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentFindDTO;
import org.example.core.dto.DepartmentUpdateDTO;
import org.example.dao.entity.Department;

import java.util.List;

public interface IDepartmentService {

    Department find(Long id);

    List<Department> findAll(DepartmentFindDTO departmentFindDTO);

    Department save(DepartmentCreateDTO departmentCreateDTO);

    Department update(Long id, Long version, DepartmentUpdateDTO departmentUpdateDTO);

    List<Department> findChildren(Long parentId);

    //TODO add version
    boolean setInactive(Long id);



}
