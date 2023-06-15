package org.example.dao.db;

import org.example.dao.api.IDepartmentDao;
import org.example.dao.entity.Department;
import org.example.dao.utils.HibernateUtil;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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
        criteria.select(root).where(cb.and(cb.equal(root.get("id"), id), cb.equal(root.get("isActive"), true)));

        List<Department> resultList = em.createQuery(criteria).getResultList();
        em.close();
        if (resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;


    }

    @Override
    public Department findNotActive(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
        Root<Department> root = criteria.from(Department.class);
        criteria.select(root).where(cb.and(cb.equal(root.get("id"), id), cb.equal(root.get("isActive"), false)));

        List<Department> resultList = em.createQuery(criteria).getResultList();
        em.close();
        if (resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;


    }

    @Override
    public boolean setInactive(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Department> criteriaUpdate = cb.createCriteriaUpdate(Department.class);
        Root<Department> root = criteriaUpdate.from(Department.class);

        Expression<String> expression = root.get("name");
        criteriaUpdate.set("name", cb.concat("(DELETED)", expression));

        criteriaUpdate.set("isActive", false);

        criteriaUpdate.where(
                cb.equal(root.get("id"), id),
                cb.equal(root.get("isActive"), true)
        );
        em.getTransaction().begin();

        int i = em.createQuery(criteriaUpdate).executeUpdate();
        em.getTransaction().commit();
        em.close();

        return i > 0;
    }

    @Override
    public List<Department> findAll(List<String> filters) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
        Root<Department> root = criteria.from(Department.class);
        List<Predicate> restrictions = new ArrayList<>();
        restrictions.add(
                cb.equal(root.get("isActive"), true)
        );

        if (filters != null && filters.size() > 0) {
            restrictions.add(
                    root.get("name").in(filters)
            );
        }

        criteria.where(restrictions.toArray(new Predicate[0]));

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
        children.on(
                cb.and(
                        cb.equal(children.get("id"), parentId),
                        cb.equal(children.get("isActive"), true),
                        cb.equal(root.get("isActive"), true)

                )
        );
        query.select(root);

        List<Department> res = em.createQuery(query).getResultList();
        em.close();
        return res;

    }

    @Override
    public Department update(Department toUpdate) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();

        try {
            Department res = em.merge(toUpdate);
            em.getTransaction().commit();
            return res;
        } catch (
                PersistenceException e
        ) {
            if (e.getCause().getClass().equals(ConstraintViolationException.class)) {
                throw new IllegalArgumentException("Задано не уникальное новое имя для департамента!\n" + e.getMessage());
            } else {
                throw e;
            }
        } finally {
            em.close();
        }


    }

    @Override
    public boolean hasChildren(Long parentId) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Department> root = query.from(Department.class);
        Join<Department, Department> children = root.join("parent");
        children.on(
                cb.and(
                        cb.equal(children.get("id"), parentId),
                        cb.equal(children.get("isActive"), true),
                        cb.equal(root.get("isActive"), true)

                )
        );
        query.select(cb.count(children));

        return em.createQuery(query).getSingleResult() > 0L;


    }


    public static IDepartmentDao getInstance() {
        return Holder.instance;

    }
}
