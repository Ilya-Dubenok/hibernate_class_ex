package org.example.dao.api;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.dao.entity.Department;

import java.util.List;

public interface IDepartmentDao {

    Department save(Department department);

    Department find(Long id);

    List<Department> findAll(List<String> filters);

    List<Department> findChildren(Long parentId);


    List<DepartmentDTO> save(List<DepartmentCreateDTO> list);


}
