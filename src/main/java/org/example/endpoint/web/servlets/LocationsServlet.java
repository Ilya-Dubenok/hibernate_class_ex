package org.example.endpoint.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.entity.Location;
import org.example.service.api.ILocationService;
import org.example.service.factory.LocationServiceFactory;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/loc")
public class LocationsServlet extends HttpServlet {

    private final ILocationService  service = LocationServiceFactory.getInstance();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        LocationCreateDTO locationCreateDTO = objectMapper.readValue(req.getInputStream(), LocationCreateDTO.class);
        Location location;
        try {

             location = service.save(locationCreateDTO);
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
            return;
        }


        String resultToSend = objectMapper.writeValueAsString(
                new LocationDTO(
                        location.getId(), location.getName()
                )
        );

        writer.write(resultToSend);


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        if (null == req.getParameter("id")) {
            resp.sendError(400, "no id paramenter!");
            return;
        }

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "bad id format");
            return;
        }

        Location location = service.find(id);
        LocationDTO res = new LocationDTO();

        if (location != null) {
            res.setId(location.getId());
            res.setAddress(location.getName());
        }

        String result = objectMapper.writeValueAsString(res);

        writer.write(result);


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        LocationDTO locationDTO = objectMapper.readValue(req.getInputStream(), LocationDTO.class);

        try {
            Location location = service.update(locationDTO);
            locationDTO = new LocationDTO(location.getId(), location.getName());
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
            return;
        }

        String result = objectMapper.writeValueAsString(locationDTO);

        writer.write(result);


    }
}
