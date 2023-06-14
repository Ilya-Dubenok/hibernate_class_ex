package org.example.endpoint.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.core.dto.DepartmentFindDTO;
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

        writer.write(resultToSend);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        if (null != req.getParameter("id")) {
            //TODO ПРОВЕРКИ ID И ЧЕГО_НИБУДЬ ЕЩЕ ХЗ ЧЕГО УЖЕ ЧАС НОЧИ БЛЯТЬ ЗАВТРА НА РАБОТУ
            Department department = service.find(Long.valueOf(req.getParameter("id")));
            DepartmentDTO res = EntityToDtoConverter.convertToDepartmentDTO(department);
            String resultToSend = objectMapper.writeValueAsString(res);

            writer.write(resultToSend);
            return;

        }

        String[] filters = req.getParameterValues("filters");
        DepartmentFindDTO departmentFindDTO = new DepartmentFindDTO();
        if (filters != null) {
            departmentFindDTO.setFilters(
                    List.of(filters)
            );
        } else {
            departmentFindDTO.setFilters(null);

        }

        List<Department> all = service.findAll(departmentFindDTO);

        List<DepartmentDTO> res = EntityToDtoConverter.convertToDepartmentDTOList(all);

        String resultToSend = objectMapper.writeValueAsString(res);

        writer.write(resultToSend);
    }
}
