package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.dto.DepartmentDTO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {


//        LocationCreateDTO minsk = new LocationCreateDTO("Misnk");
//        LocationCreateDTO neMinsk = new LocationCreateDTO("ne_minsk");
//
//        ILocationService instance = LocationServiceFactory.getInstance();
//        instance.save(minsk);
//        instance.save(neMinsk);

//        DepartmentCreateDTO createDTO = new DepartmentCreateDTO(
//                "CHILD2", "333", 2L, 3L
//        );
//
//        Department save = DepartmentServiceFactory.getInstance().save(createDTO);
//
//        List<Department> res = DepartmentServiceFactory.getInstance().findChildren(3L);
//        System.out.println(res);
//        DepartmentFindDTO departmentFindDTO = new DepartmentFindDTO(
//
//        );
//        departmentFindDTO.setId(2L);
//        departmentFindDTO.setFilters(List.of());
//
        ObjectMapper objectMapper = new ObjectMapper();
//        String string = objectMapper.writeValueAsString(departmentFindDTO);
//        System.out.println(string);
//        DepartmentFindDTO res = objectMapper.readValue("{\"filters\":[1,3,4]}", DepartmentFindDTO.class);
//        System.out.println(res);

        DepartmentDTO departmentDTO = new DepartmentDTO();
        String string = objectMapper.writeValueAsString(departmentDTO);
        System.out.println(string);

        List<DepartmentDTO> res = new ArrayList<>(
                List.of(
                        new DepartmentDTO(1L,"name","phone",3L,List.of(4L,2L),9L),
                        new DepartmentDTO(),
                        new DepartmentDTO()
                )
        );

        String string2 = objectMapper.writeValueAsString(res);
        System.out.println(string2);

    }
}