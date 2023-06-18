package org.example.dao.db;

import org.example.dao.api.IDepartmentDao;
import org.example.dao.entity.Department;
import org.example.dao.utils.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
            return department;
        } finally {
            em.close();
        }

    }


    @Override
    public Department find(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
            Root<Department> root = criteria.from(Department.class);
            criteria.select(root).where(cb.and(cb.equal(root.get("id"), id), cb.equal(root.get("isActive"), true)));

            List<Department> resultList = em.createQuery(criteria).getResultList();

            if (resultList.size() > 0) {
                return resultList.get(0);
            }

            return null;

        } finally {

            em.close();
        }


    }

    @Override
    public Department findNotActive(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
            Root<Department> root = criteria.from(Department.class);
            criteria.select(root).where(cb.and(cb.equal(root.get("id"), id), cb.equal(root.get("isActive"), false)));

            List<Department> resultList = em.createQuery(criteria).getResultList();

            if (resultList.size() > 0) {
                return resultList.get(0);
            }

            return null;


        } finally {
            em.close();

        }


    }

    @Override
    public boolean setInactive(Long id, Long version) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Department> criteriaUpdate = cb.createCriteriaUpdate(Department.class);
            Root<Department> root = criteriaUpdate.from(Department.class);

            Expression<String> expression = root.get("name");
            criteriaUpdate.set("name", cb.concat("(DELETED)", expression));

            Timestamp timestamp = new Timestamp(version);
            criteriaUpdate.set("isActive", false);
            criteriaUpdate.set("dateTimeUpdated", LocalDateTime.now());

            Expression<Timestamp> function = cb.function("date_trunc",
                    Timestamp.class,
                    cb.literal("milliseconds"),
                    root.get("dateTimeUpdated"));


            criteriaUpdate.where(
                    cb.equal(root.get("id"), id),
                    cb.equal(root.get("isActive"), true),
                    cb.equal(function, timestamp)
            );


            em.getTransaction().begin();

            int i = em.createQuery(criteriaUpdate).executeUpdate();
            em.getTransaction().commit();
            return i > 0;

        } finally {
            em.close();

        }


    }

    @Override
    public List<Department> findAll(List<String> filters) {

        EntityManager em = HibernateUtil.getEntityManager();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
            Root<Department> root = criteria.from(Department.class);
            List<Predicate> globalPredicates = new ArrayList<>();
            globalPredicates.add(
                    cb.equal(root.get("isActive"), true)
            );

            if (filters != null && filters.size() > 0) {
                Predicate likeRestriction = null;

                boolean needAnother = false;

                for (String filter : filters) {

                    Predicate likeToFilterStripAndToLower = cb.like(
                            cb.lower(cb.function(
                                    "REPLACE", String.class, root.get("name"),
                                    cb.literal(" "),
                                    cb.literal(""))),

                            filter.toLowerCase().replaceAll(" ", "")
                    );

                    if (needAnother) {

                        likeRestriction = cb.or(
                                likeRestriction,
                                likeToFilterStripAndToLower
                        );

                    } else {
                        likeRestriction = likeToFilterStripAndToLower;
                        needAnother = true;
                    }
                }

                globalPredicates.add(likeRestriction);

            }

            criteria.where(globalPredicates.toArray(new Predicate[0]));

            List<Department> resultList = em.createQuery(criteria).getResultList();

            return resultList;

        } finally {

            em.close();

        }

    }


    @Override
    public List<Department> findChildren(Long parentId) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

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
            return res;

        } finally {

            em.close();
        }

    }

    @Override
    public Department update(Department toUpdate) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();
            Department res = em.merge(toUpdate);
            em.getTransaction().commit();
            return res;

        } finally {
            em.close();
        }

    }

    @Override
    public boolean hasChildren(Long parentId) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

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

        } finally {

            em.close();
        }


    }


    public static IDepartmentDao getInstance() {
        return Holder.instance;

    }
}
