package org.example.service.factory;

import org.example.dao.db.DepartmentDbDao;
import org.example.service.DepartmentServiceImpl;
import org.example.service.api.IDepartmentService;

public class DepartmentServiceFactory {

    private static class Holder {
        private static final IDepartmentService instance = new DepartmentServiceImpl(DepartmentDbDao.getInstance());

    }

    private DepartmentServiceFactory() {

    }

    public static IDepartmentService getInstance() {
        return Holder.instance;
    }

}
