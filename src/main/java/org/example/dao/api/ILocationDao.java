package org.example.dao.api;

import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.entity.Location;

import java.util.List;

public interface ILocationDao {

    Location find(Long id);
    Location save(Location location);


    List<LocationDTO> save(List<LocationCreateDTO> list);


}
