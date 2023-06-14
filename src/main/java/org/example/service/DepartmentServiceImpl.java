package org.example.service;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.core.dto.DepartmentFindDTO;
import org.example.dao.api.IDepartmentDao;
import org.example.dao.entity.Department;
import org.example.dao.entity.Location;
import org.example.service.api.IDepartmentService;
import org.example.service.factory.DepartmentServiceFactory;
import org.example.service.factory.LocationServiceFactory;

import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceImpl implements IDepartmentService {

    private final IDepartmentDao departmentDao;


    public DepartmentServiceImpl(IDepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public Department find(Long id) {
        return departmentDao.find(id);
    }


    @Override
    public List<Department> findAll(DepartmentFindDTO departmentFindDTO) {
        List<Department> res = new ArrayList<>();
        Long mainId = departmentFindDTO.getId();

        if (departmentFindDTO.getId() != null) {
            res.add(departmentDao.find(mainId));
            return res;

        }

        res.addAll(departmentDao.findAll(departmentFindDTO.getFilters()));
        return res;

    }

    @Override
    public Department save(DepartmentCreateDTO departmentCreateDTO) {

        Department department = convertToDepartment(departmentCreateDTO);
        return departmentDao.save(department);
    }

    @Override
    public List<Department> findChildren(Long parentId) {
        return departmentDao.findChildren(parentId);
    }

    @Override
    public List<DepartmentDTO> save(List<DepartmentCreateDTO> list) {
        return null;
    }


    private static Department convertToDepartment(DepartmentCreateDTO departmentCreateDTO) {
        Location location;
        Department parent = null;
        Long id;

        if ((id = departmentCreateDTO.getLocationId()) != null) {
            location = LocationServiceFactory.getInstance().find(id);
            if (location == null) {
                throw new IllegalArgumentException("Локации с таким id не существует!");
            }
        } else {
            throw new IllegalArgumentException("Нужно указать id локации!");
        }


        if ((id = departmentCreateDTO.getParent_id()) != null) {
            parent = DepartmentServiceFactory.getInstance().find(id);
            if (parent == null) {
                throw new IllegalArgumentException("Родителя с id " + id + " не найдено");
            }
        }


        return new Department(
                departmentCreateDTO.getName(),
                parent,
                departmentCreateDTO.getPhoneNum(),
                location
        );
    }

}
