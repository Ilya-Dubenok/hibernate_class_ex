package org.example.endpoint.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.core.dto.DepartmentUpdateDTO;
import org.example.dao.entity.Department;
import org.example.service.api.IDepartmentService;
import org.example.endpoint.web.servlets.utils.EntityToDtoConverter;
import org.example.service.factory.DepartmentServiceFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/depart")
public class DepartmentServlet extends HttpServlet {

    private final IDepartmentService service = DepartmentServiceFactory.getInstance();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        DepartmentCreateDTO departmentCreateDTO = objectMapper.readValue(req.getInputStream(), DepartmentCreateDTO.class);


        try {
            Department department = service.save(departmentCreateDTO);

            departmentCreateDTO = EntityToDtoConverter.convertToDepartmentCreateDTO(department);
        } catch (IllegalArgumentException e) {

            resp.sendError(400, e.getMessage());
            return;
        }


        String resultToSend = objectMapper.writeValueAsString(departmentCreateDTO);

        resp.setStatus(HttpServletResponse.SC_CREATED);

        writer.write(resultToSend);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        if (null != id) {
            try {

                Department department = service.find(Long.valueOf(id));
                DepartmentDTO res = EntityToDtoConverter.convertToDepartmentDTO(department);
                String resultToSend = objectMapper.writeValueAsString(res);

                resp.setStatus(HttpServletResponse.SC_OK);

                writer.write(resultToSend);

            } catch (
                    NumberFormatException e
            ) {
                resp.sendError(400, "Хреновое значение id");

            } catch (IllegalArgumentException e) {
                resp.sendError(400, e.getMessage());

            }

            return;

        }

        String[] filters = req.getParameterValues("filters");


        List<Department> all = service.findAll(null == filters ? null : List.of(filters));

        List<DepartmentDTO> res = EntityToDtoConverter.convertToDepartmentDTOList(all);

        String resultToSend = objectMapper.writeValueAsString(res);

        writer.write(resultToSend);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idString = req.getParameter("id");
        if (null == idString) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не передано id");
            return;

        }

        String versionString = req.getParameter("version");

        if (null == versionString) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не передана версия");
            return;

        }

        long id;
        long version;
        boolean deleted;

        try {

            id = Long.parseLong(idString);
            version = Long.parseLong(versionString);
            deleted = service.setInactive(id, version);

        } catch (NumberFormatException e) {

            resp.sendError(400, "Хреновое id или версия");
            return;
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
            return;
        }


        if (deleted) {
            resp.setStatus(HttpServletResponse.SC_OK);

        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        String idString = req.getParameter("id");
        String versionString = req.getParameter("version");

        if (null == idString) {
            resp.sendError(400, "Нужен id");
            return;
        }

        if (null == versionString) {
            resp.sendError(400, "Нужен version");
            return;
        }

        Long id, version;

        try {
            id = Long.valueOf(idString);
            version = Long.valueOf(versionString);
        } catch (NumberFormatException e) {
            resp.sendError(400, "в id или в version передано необрабатываемое значение");
            return;
        }


        DepartmentUpdateDTO departmentUpdateDTO = objectMapper.readValue(req.getInputStream(), DepartmentUpdateDTO.class);

        DepartmentDTO departmentDTO;


        try {
            Department department = service.update(id, version, departmentUpdateDTO);
            departmentDTO = EntityToDtoConverter.convertToDepartmentDTO(department);

        } catch (IllegalArgumentException e) {

            resp.sendError(400, e.getMessage());
            return;
        }

        String resultToSend = objectMapper.writeValueAsString(departmentDTO);

        resp.setStatus(HttpServletResponse.SC_OK);

        writer.write(resultToSend);


    }
}
