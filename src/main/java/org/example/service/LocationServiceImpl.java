package org.example.service;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.api.IDepartmentDao;
import org.example.dao.api.ILocationDao;
import org.example.service.api.IDepartmentService;
import org.example.service.api.ILocationService;

import java.util.List;

public class LocationServiceImpl implements ILocationService {

    private final ILocationDao locationDao;


    public LocationServiceImpl(ILocationDao locationDao) {
        this.locationDao = locationDao;
    }


    @Override
    public LocationDTO save(LocationCreateDTO locationCreateDTO) {
        LocationDTO res = locationDao.save(locationCreateDTO);
        if (res == null) {
            throw new RuntimeException("Ошибка в создании локации");
        }
        return res;
    }

    @Override
    public List<LocationDTO> save(List<LocationCreateDTO> list) {
        return null;
    }
}
