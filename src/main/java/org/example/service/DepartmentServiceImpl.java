package org.example.service;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.dao.api.IDepartmentDao;
import org.example.service.api.IDepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements IDepartmentService {

    private final IDepartmentDao departmentDao;


    public DepartmentServiceImpl(IDepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public DepartmentDTO save(DepartmentCreateDTO departmentCreateDTO) {
        DepartmentDTO res = departmentDao.save(departmentCreateDTO);
        if (null == res) {
            throw new RuntimeException("Ошибка при создании департамента");
        }
        return res;
    }

    @Override
    public List<DepartmentDTO> save(List<DepartmentCreateDTO> list) {
        return null;
    }
}
