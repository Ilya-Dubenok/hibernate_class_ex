package org.example.dao.db;

import org.example.core.dto.DepartmentCreateDTO;
import org.example.core.dto.DepartmentDTO;
import org.example.dao.api.IDepartmentDao;
import org.example.dao.entity.Department;
import org.example.dao.utils.HibernateUtil;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class DepartmentDbDao implements IDepartmentDao {


    private static class Holder {
        private static final DepartmentDbDao instance = new DepartmentDbDao();

    }


    @Override
    public Department save(Department department) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(department);
            em.getTransaction().commit();
            em.close();
        } catch (
                PersistenceException e
        ) {
            if (e.getCause().getClass().equals(ConstraintViolationException.class)) {
                throw new IllegalArgumentException("Задано не уникальное имя для департамента!\n" + e.getMessage());
            } else {
                throw e;
            }
        } finally {
            em.close();
        }
        return department;

    }


    @Override
    public Department find(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
        Root<Department> root = criteria.from(Department.class);
        criteria.select(root).where(cb.equal(root.get("id"), id));

        List<Department> resultList = em.createQuery(criteria).getResultList();
        em.close();
        if (resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;


    }

    @Override
    public List<Department> findAll(List<String> filters) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
        Root<Department> root = criteria.from(Department.class);
        criteria.select(root);
        if (filters != null && filters.size() > 0) {
            criteria.where(
                    root.get("name").in(filters)
            );

        }

        List<Department> resultList = em.createQuery(criteria).getResultList();
        em.close();
        return resultList;

    }


    @Override
    public List<Department> findChildren(Long parentId) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> query = cb.createQuery(Department.class);
        Root<Department> root = query.from(Department.class);
        //<Parent,Child>
        Join<Department, Department> children = root.join("parent");
        query.select(root).where(
                cb.equal(children.get("id"), parentId)
        );

        List<Department> res = em.createQuery(query).getResultList();
        em.close();
        return res;

    }

    @Override
    public List<DepartmentDTO> save(List<DepartmentCreateDTO> list) {
        return null;
    }


    public static IDepartmentDao getInstance() {
        return Holder.instance;

    }
}
