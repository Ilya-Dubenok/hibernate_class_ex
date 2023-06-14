package org.example.service.api;

import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.entity.Location;

import java.util.List;

public interface ILocationService {

    Location find(Long id);

    Location save(LocationCreateDTO locationCreateDTO);


    List<LocationDTO> save(List<LocationCreateDTO> list);


}
