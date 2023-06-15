package org.example.dao.api;

import org.example.dao.entity.Location;


public interface ILocationDao {

    Location find(Long id);
    Location save(Location location);



}
