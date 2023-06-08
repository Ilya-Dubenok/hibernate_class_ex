package org.example.dao.api;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;

import java.util.List;

public interface IDepartmentDao {

    DepartmentDTO save(DepartmentCreateDTO departmentCreateDTO);


    List<DepartmentDTO> save(List<DepartmentCreateDTO> list);


}
