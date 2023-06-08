package org.example.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.LocationDbDao;
import org.example.service.LocationServiceImpl;
import org.example.service.api.ILocationService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/add_loc")
public class AddLocationsServlet extends HttpServlet {

    private ILocationService service = new LocationServiceImpl(LocationDbDao.getInstance());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        LocationCreateDTO locationCreateDTO = objectMapper.readValue(req.getInputStream(), LocationCreateDTO.class);

        LocationDTO locationDTO = service.save(locationCreateDTO);

        String resultToSend = objectMapper.writeValueAsString(locationDTO);

        writer.write(resultToSend);




    }
}
