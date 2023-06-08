package org.example.dao.api;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;

import java.util.List;

public interface ILocationDao {

    LocationDTO save(LocationCreateDTO locationCreateDTO);


    List<LocationDTO> save(List<LocationCreateDTO> list);


}
