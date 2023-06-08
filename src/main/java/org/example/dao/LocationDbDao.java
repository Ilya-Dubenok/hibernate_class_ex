package org.example.dao;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.core.dto.LocationCreateDTO;
import org.example.core.dto.LocationDTO;
import org.example.dao.api.IDepartmentDao;
import org.example.dao.api.ILocationDao;
import org.example.dao.db.ds.DataSourceConnector;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class LocationDbDao implements ILocationDao {

    private final DataSource dataSource;


    private static class Holder {
        private static final LocationDbDao instance = new LocationDbDao(DataSourceConnector.getInstance().getDataSource());

    }

    private LocationDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public LocationDTO save(LocationCreateDTO locationCreateDTO) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO app.locations(name) VALUES (?) RETURNING id;"
                )

                ) {

            LocationDTO res = null;

            ps.setString(1, locationCreateDTO.getAddress());
            ResultSet set = ps.executeQuery();

            if (null == set || !set.next()) {
                throw new RuntimeException("Ошибка при создании места");
            }
            res = new LocationDTO(set.getLong(1), locationCreateDTO.getAddress());


            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<LocationDTO> save(List<LocationCreateDTO> list) {
        return null;
    }






    public static ILocationDao getInstance() {
        return Holder.instance;

    }
}
