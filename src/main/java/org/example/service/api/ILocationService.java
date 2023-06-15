package org.example.service.api;

import org.example.core.dto.LocationCreateDTO;
import org.example.dao.entity.Location;

public interface ILocationService {

    Location find(Long id);

    Location save(LocationCreateDTO locationCreateDTO);




}
