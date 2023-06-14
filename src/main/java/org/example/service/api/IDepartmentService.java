package org.example.service.api;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.core.dto.DepartmentFindDTO;
import org.example.dao.entity.Department;

import java.util.List;

public interface IDepartmentService {

    Department find(Long id);

    List<Department> findAll(DepartmentFindDTO departmentFindDTO);

    Department save(DepartmentCreateDTO departmentCreateDTO);

    List<Department> findChildren(Long parentId);


    List<DepartmentDTO> save(List<DepartmentCreateDTO> list);


}
