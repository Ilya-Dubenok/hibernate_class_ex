package org.example;

import jakarta.servlet.http.HttpServlet;
import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.LocationDbDao;
import org.example.service.LocationServiceImpl;
import org.example.service.api.ILocationService;

import java.net.http.HttpRequest;

public class Main {
    public static void main(String[] args) {

        ILocationService serv = new LocationServiceImpl(LocationDbDao.getInstance());

        LocationDTO kukuevo = serv.save(new LocationCreateDTO("Kukuevo"));
        System.out.println(kukuevo.getId());
        System.out.println(kukuevo.getAddress());


    }
}