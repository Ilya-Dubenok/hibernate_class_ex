package org.example.service;

import org.example.core.dto.*;
import org.example.dao.api.IDepartmentDao;
import org.example.dao.entity.Department;
import org.example.dao.entity.Location;
import org.example.dao.utils.DataBaseExceptionMapper;
import org.example.service.api.IDepartmentService;
import org.example.service.factory.DepartmentServiceFactory;
import org.example.service.factory.LocationServiceFactory;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public List<Department> findAll(List<String> filters) {

        return new ArrayList<>(departmentDao.findAll(filters));

    }

    @Override
    public Department save(DepartmentCreateDTO departmentCreateDTO) {
        Long parentId = departmentCreateDTO.getParentId();
        if (parentId != null && departmentDao.findNotActive(parentId) != null) {

            throw new IllegalArgumentException("Указан deprecated id");
        }

        Department department = convertToDepartment(departmentCreateDTO);
        try {
            return departmentDao.save(department);

        } catch (PersistenceException e) {

            DataBaseExceptionMapper.throwHandledExceptionIfApplied(e);

            throw e;

        }
    }

    @Override
    public Department update(Long id, Long version, DepartmentUpdateDTO departmentUpdateDTO) {

        Department departmentToUpdate = departmentDao.find(id);

        if (departmentToUpdate == null) {

            throw new IllegalArgumentException("Указан неверный id");

        }
        if (!Objects.equals(
                version,
                ZonedDateTime.of(departmentToUpdate.getDateTimeUpdated(), ZoneId.systemDefault()).toInstant().toEpochMilli()
        )) {

            throw new IllegalArgumentException("Версия объекта уже была обновлена");

        }
        Department parent;
        Location location;

        String newName = departmentUpdateDTO.getName();
        String newPhone = departmentUpdateDTO.getPhoneNum();

        Long parentId = departmentUpdateDTO.getParent_id();


        if (parentId != null) {
            parent = departmentDao.find(parentId);
            if (parent == null) {

                throw new IllegalArgumentException("Не найден id для родителя");
            }

            if (id.equals(parentId)) {
                throw new IllegalArgumentException("Хватит баловаться!");
            }

            if (departmentDao.findChildren(id).stream().anyMatch(x -> x.getId().equals(parentId))) {

                throw new IllegalArgumentException("Ну зачем??");
            }

            departmentToUpdate.setParent(parent);

        } else {
            departmentToUpdate.setParent(null);
        }

        Long locationId = departmentUpdateDTO.getLocationId();

        if (locationId != null) {
            location = LocationServiceFactory.getInstance().find(locationId);
            if (location == null) {

                throw new IllegalArgumentException("Такой локации не существует");
            }

            departmentToUpdate.setLocation(location);

        } else {
            departmentToUpdate.setLocation(null);
        }

        departmentToUpdate.setName(newName);


        departmentToUpdate.setPhone(newPhone);

        try {

            return departmentDao.update(departmentToUpdate);
        } catch (OptimisticLockException e) {
            throw new IllegalArgumentException("Версия объекта уже неактуальна");
        } catch (PersistenceException e) {

            DataBaseExceptionMapper.throwHandledExceptionIfApplied(e);

            throw e;

        }


    }

    @Override
    public List<Department> findChildren(Long parentId) {
        return departmentDao.findChildren(parentId);
    }

    @Override
    public boolean setInactive(Long id, Long version) {
        try {
            return departmentDao.setInactive(id, version);

        } catch (PersistenceException e) {

            DataBaseExceptionMapper.throwHandledExceptionIfApplied(e);

            throw e;

        }

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


        if ((id = departmentCreateDTO.getParentId()) != null) {
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
