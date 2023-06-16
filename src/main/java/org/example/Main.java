package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dao.api.IDepartmentDao;
import org.example.dao.db.DepartmentDbDao;
import org.example.dao.entity.Department;
import org.example.service.api.IDepartmentService;
import org.example.service.api.ILocationService;
import org.example.service.factory.DepartmentServiceFactory;
import org.example.service.factory.LocationServiceFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {



        ILocationService instance = LocationServiceFactory.getInstance();
//        instance.save(
//                new LocationCreateDTO("Minsk")
//        );
//        instance.save(
//                new LocationCreateDTO("Gomel")
//        );
//        instance.save(
//                new LocationCreateDTO("Gdansk")
//        );

        IDepartmentService departmentService = DepartmentServiceFactory.getInstance();

        IDepartmentDao departmentDao = DepartmentDbDao.getInstance();

//        Stream.of(
//                new DepartmentCreateDTO(
//                        "Directory", "000", 1L, null
//                ),
//                new DepartmentCreateDTO(
//                        "Finance", "001", 2L, 1L
//                ),
//                new DepartmentCreateDTO(
//                        "Accounting", "003", 3L, 1L
//                ),
//                new DepartmentCreateDTO(
//                        "FakeDep", "004", 1L, null
//                ),
//                new DepartmentCreateDTO(
//                        "FakeDepChild", "005", 2L, 4L
//                )
//
//        ).forEach(departmentService::save);

//
//        Department dep = departmentService.find(1L);
//        System.out.println(dep);
//        List<Department> all = departmentDao.findAll(List.of("Directory","Deleted"));
//        List<Department> childer = departmentDao.findChildren(1L);
//
//        boolean success = departmentService.setInactive(4L);
//        System.out.println(success);
//        DepartmentUpdateDTO departmentUpdateDTO = new DepartmentUpdateDTO();
//        departmentUpdateDTO.setName("NewFinance");
//        departmentUpdateDTO.setId(2L);
//        departmentUpdateDTO.setLocationId(2L);
//        departmentUpdateDTO.setPhoneNum("0003");
//        departmentUpdateDTO.setParent_id(1L);
//        Department department = departmentService.update(
//                departmentUpdateDTO
//
//        );
//        System.out.println(department);

        List<Department> res = departmentDao.findAll(
                List.of( "fakedepchild","","newFinance ")
        );
        System.out.println(res);

//        boolean hasChildren = departmentDao.hasChildren(18L);
//        System.out.println(hasChildren);

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
//        ObjectMapper objectMapper = new ObjectMapper();
////        String string = objectMapper.writeValueAsString(departmentFindDTO);
////        System.out.println(string);
////        DepartmentFindDTO res = objectMapper.readValue("{\"filters\":[1,3,4]}", DepartmentFindDTO.class);
////        System.out.println(res);
//
//        DepartmentDTO departmentDTO = new DepartmentDTO();
//        String string = objectMapper.writeValueAsString(departmentDTO);
//        System.out.println(string);
//
//        List<DepartmentDTO> res = new ArrayList<>(
//                List.of(
//                        new DepartmentDTO(1L,"name","phone",3L,List.of(4L,2L),9L),
//                        new DepartmentDTO(),
//                        new DepartmentDTO()
//                )
//        );
//
//        String string2 = objectMapper.writeValueAsString(res);
//        System.out.println(string2);

    }
}