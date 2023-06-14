package org.example.service;

import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.api.ILocationDao;
import org.example.dao.entity.Location;
import org.example.service.api.ILocationService;

import java.util.List;

public class LocationServiceImpl implements ILocationService {

    private final ILocationDao locationDao;


    public LocationServiceImpl(ILocationDao locationDao) {
        this.locationDao = locationDao;
    }


    @Override
    public Location find(Long id) {
        return locationDao.find(id);
    }

    @Override
    public Location save(LocationCreateDTO locationCreateDTO) {

        Location location = convertToLocation(locationCreateDTO);
        return locationDao.save(location);

    }

    @Override
    public List<LocationDTO> save(List<LocationCreateDTO> list) {
        return null;
    }

    private static Location convertToLocation(LocationCreateDTO locationCreateDTO) {

        String address = locationCreateDTO.getAddress();
        if (address == null || address.isBlank()) {
            throw  new IllegalArgumentException("Нельзя вводить пустой адрес!");
        }

        return new Location(address);

    }

}
