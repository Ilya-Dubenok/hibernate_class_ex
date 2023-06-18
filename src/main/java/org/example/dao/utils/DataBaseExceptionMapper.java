package org.example.dao.utils;

import org.example.dao.entity.Department;
import org.example.dao.entity.Location;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

public class DataBaseExceptionMapper {


    public static Constraint findConstraint(String constraintName) {
        if (constraintName == null) {
            return null;
        }
        for (Constraint value : Constraint.values()) {
            if (value.getConstrName().equals(constraintName)) {
                return value;
            }


        }
        return null;
    }

    public static PropertyLimit findPropertyLimit(String entityName, String propertyName) {

        if (entityName == null || propertyName == null) {
            return null;
        }

        for (PropertyLimit value : PropertyLimit.values()) {


            if (value.getClassName().equals(entityName) && value.getPropName().equals(propertyName)) {
                return value;

            }

        }

        return null;


    }



    //TODO DISCUSS WITH SHAD SOMETIMESS THROWS ROLLBACKEXCEPTION, HAVE TO DIG INTO CAUSE TREE
    public static void throwHandledExceptionIfApplied(Throwable e) {
        Throwable cause = e.getCause();


        if (cause instanceof ConstraintViolationException) {

            String constraintName = ((ConstraintViolationException) cause).getConstraintName();
            Constraint constraint = findConstraint(constraintName);
            if (constraint != null) {

                throw new IllegalArgumentException(constraint.getConstrMessage());

            } else {
                throw new RuntimeException(cause);
            }

        } else if (cause instanceof PropertyValueException) {

            String entityName = ((PropertyValueException) cause).getEntityName();
            String propertyName = ((PropertyValueException) cause).getPropertyName();
            PropertyLimit propertyLimit = findPropertyLimit(entityName, propertyName);
            if (propertyLimit != null) {

                throw new IllegalArgumentException(propertyLimit.getMessage());

            } else {

                throw new RuntimeException(cause);
            }


        } else {

            if (cause.getCause().equals(cause) || cause.getCause().getClass() == cause.getClass()) {
                return;
            }
            throwHandledExceptionIfApplied(cause);
        }
    }


//    public static Throwable getOneOfHandledExceptionsInCausedBy(Exception e) {
//
//        while (
//
//        )
//
//
//    }

    public enum Constraint {


        DEP_HAS_ACTIVE_CHILDREN(
                "has_active_children",
                "у обрабатываемого департамента есть живые дети!"

        ),
        LOCATION_DEPARTMENT_FK(
                "location_department_fk",
                "blablabla"
        ),

        DEP_NAME_UNIQUE(
                "department_unique_name_constraint",
                "Нельзя выполнить операцию с департаментом с таким именем, т.к. оно уже занято"
        ),

        LOC_NAME_UNIQUE(
                "location_unique_name",
                "Нельзя выполнить операцию с локацией с таким именем, т.к. оно уже занято"
        );


        private final String constrName;

        private final String constrMessage;

        Constraint(String constrName, String constrMessage) {
            this.constrName = constrName;
            this.constrMessage = constrMessage;
        }

        public String getConstrName() {
            return constrName;
        }

        public String getConstrMessage() {
            return constrMessage;
        }
    }

    public enum PropertyLimit {
        DEP_NAME_NOT_NULL(
                Department.class,
                "name",
                "Имя департамента не должно быть пустым!"),
        LOC_NAME_NOT_NULL(
                Location.class,
                "name",
                "Имя локации не должно быть пустым!");




        private final Class Entity;

        private final String className;

        private final String propName;

        private final String message;


        PropertyLimit(Class entity, String propName, String propMessage) {
            Entity = entity;
            this.className = entity.getName();
            this.propName = propName;
            this.message = propMessage;
        }

        public String getClassName() {
            return className;
        }

        public String getPropName() {
            return propName;
        }

        public String getMessage() {
            return message;
        }
    }


}
