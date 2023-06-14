package org.example.dao.support;

import org.example.dao.utils.HibernateUtil;

import javax.persistence.EntityManager;

public class HibernateSupport {

    public static void main(String[] args) {

        EntityManager em = HibernateUtil.getEntityManager();




    }

}
