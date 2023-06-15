package org.example.dao.api;

import org.example.dao.entity.Department;

import java.util.List;

public interface IDepartmentDao {

    Department save(Department department);

    Department find(Long id);

    Department findNotActive(Long id);


    boolean setInactive(Long id);

    List<Department> findAll(List<String> filters);


    List<Department> findChildren(Long parentId);

    Department update(Department copy);




    boolean hasChildren(Long parentId);
}
