package org.example.dao.db;

import org.example.dao.api.ILocationDao;
import org.example.dao.entity.Location;
import org.example.dao.utils.HibernateUtil;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class LocationDbDao implements ILocationDao {


    private static class Holder {
        private static final LocationDbDao instance = new LocationDbDao();

    }


    @Override
    public Location find(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);
        Root<Location> root = query.from(Location.class);
        query.select(root).where(cb.equal(root.get("id"), id));
        List<Location> resultList = em.createQuery(query).getResultList();
        em.close();
        if (resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public Location save(Location location) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
            em.close();
        } catch (
                PersistenceException e
        ) {
            if (e.getCause().getClass().equals(ConstraintViolationException.class)) {
                throw new IllegalArgumentException("Задано не уникальное имя для локации!\n" + e.getMessage());
            } else {
                throw e;
            }
        } finally {
            em.close();
        }

        return location;
    }


    public static ILocationDao getInstance() {
        return Holder.instance;

    }
}
