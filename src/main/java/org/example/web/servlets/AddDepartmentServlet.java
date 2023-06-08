package org.example.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.dao.DepartmentDbDao;
import org.example.service.DepartmentServiceImpl;
import org.example.service.api.IDepartmentService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/add_dep")
public class AddDepartmentServlet extends HttpServlet {

    private IDepartmentService service = new DepartmentServiceImpl(DepartmentDbDao.getInstance());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        DepartmentCreateDTO departmentCreateDTO = objectMapper.readValue(req.getInputStream(), DepartmentCreateDTO.class);

        DepartmentDTO departmentDTO = service.save(departmentCreateDTO);

        String resultToSend = objectMapper.writeValueAsString(departmentDTO);

        writer.write(resultToSend);




    }
}
