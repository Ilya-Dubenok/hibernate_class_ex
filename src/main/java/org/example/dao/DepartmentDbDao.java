package org.example.dao;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.dao.api.IDepartmentDao;
import org.example.dao.db.ds.DataSourceConnector;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDbDao implements IDepartmentDao {

    private final DataSource dataSource;


    private static class Holder {
        private static final DepartmentDbDao instance = new DepartmentDbDao(DataSourceConnector.getInstance().getDataSource());

    }

    private DepartmentDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public DepartmentDTO save(DepartmentCreateDTO departmentCreateDTO) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement generalPrepSt = connection.prepareStatement(
                        "INSERT INTO app.departments(name,phone,location_id) " +
                                " VALUES(?,?,?) RETURNING id;"
                );
                PreparedStatement childAndParentPrepSt = connection.prepareStatement(
                        "INSERT INTO app.parents_children(par_id,child_id) VALUES(?,?);"
                )
        ) {

            connection.setAutoCommit(false);

            generalPrepSt.setString(1, departmentCreateDTO.getName());
            generalPrepSt.setString(2, departmentCreateDTO.getPhoneNum());
            generalPrepSt.setLong(3, departmentCreateDTO.getLocationId());


            DepartmentDTO departmentDTO = null;

            try (ResultSet set1 = generalPrepSt.executeQuery()) {

                if (null != set1) {

                    if (set1.next()) {
                        departmentDTO = new DepartmentDTO(set1.getLong(1),
                                departmentCreateDTO.getName(),
                                departmentCreateDTO.getPhoneNum(),
                                departmentCreateDTO.getLocationId(),
                                departmentCreateDTO.getChildren(),
                                departmentCreateDTO.getParent_id()
                        );
                    }
                }
            }
            for (Long child : departmentCreateDTO.getChildren()) {

                childAndParentPrepSt.setLong(1, departmentDTO.getId());
                childAndParentPrepSt.setLong(2, child);
                childAndParentPrepSt.execute();

            }
            Long parentId = departmentCreateDTO.getParent_id();
            if (null != parentId) {
                childAndParentPrepSt.setLong(1, parentId);
                childAndParentPrepSt.setLong(2, departmentDTO.getId());
                childAndParentPrepSt.execute();

            }

            connection.commit();

            connection.setAutoCommit(true);

            return departmentDTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DepartmentDTO> save(List<DepartmentCreateDTO> list) {
        return null;
    }



    public static IDepartmentDao getInstance() {
        return Holder.instance;

    }
}
