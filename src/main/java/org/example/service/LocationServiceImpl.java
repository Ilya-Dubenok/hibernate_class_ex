package org.example.service;

import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.api.ILocationDao;
import org.example.dao.entity.Location;
import org.example.dao.utils.DataBaseExceptionMapper;
import org.example.service.api.ILocationService;

import javax.persistence.PersistenceException;


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
        try {

            return locationDao.save(location);
        } catch (PersistenceException e) {

            DataBaseExceptionMapper.throwHandledExceptionIfApplied(e);

            throw e;

        }
    }



    @Override
    public Location update(LocationDTO dto) {
        Long id = dto.getId();
        Location update = locationDao.find(id);
        if (update == null) {
            throw new IllegalArgumentException("Указан неверный id");
        }

        update.setName(dto.getAddress());
        return locationDao.update(update);


    }

    private static Location convertToLocation(LocationCreateDTO locationCreateDTO) {

        String address = locationCreateDTO.getAddress();
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Нельзя вводить пустой адрес!");
        }

        return new Location(address);

    }

}
