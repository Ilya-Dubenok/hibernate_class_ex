package org.example.service.factory;

import org.example.dao.db.LocationDbDao;
import org.example.service.LocationServiceImpl;
import org.example.service.api.ILocationService;

public class LocationServiceFactory {

    private static final class Holder {
        private final static ILocationService instance = new LocationServiceImpl(LocationDbDao.getInstance());
    }

    private LocationServiceFactory() {

    }

    public static ILocationService getInstance() {
        return Holder.instance;
    }


}
