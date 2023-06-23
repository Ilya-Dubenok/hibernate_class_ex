CREATE SCHEMA IF NOT EXISTS class_schema;

CREATE TABLE IF NOT EXISTS class_schema.location
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    create_stamp timestamp without time zone,
    update_stamp timestamp without time zone,
    name character varying(255) NOT NULL,
    CONSTRAINT location_pkey PRIMARY KEY (id),
    CONSTRAINT location_unique_name UNIQUE (name)
);



CREATE TABLE IF NOT EXISTS class_schema.department
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    create_stamp timestamp without time zone,
    update_stamp timestamp without time zone,
    active boolean NOT NULL DEFAULT true,
    name character varying(255) NOT NULL,
    phone character varying(255),
    location_id bigint,
    CONSTRAINT department_pkey PRIMARY KEY (id),
    CONSTRAINT department_unique_name_constraint UNIQUE (name),
    CONSTRAINT location_department_fk FOREIGN KEY (location_id)
        REFERENCES class_schema.location (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS class_schema.department_relations
(
    parent_id bigint NOT NULL,
    child_id bigint NOT NULL,
    CONSTRAINT department_relations_parent_id_department_id_fk FOREIGN KEY (parent_id)
        REFERENCES class_schema.department (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT department_relations_child_id_department_id_fk FOREIGN KEY (child_id)
        REFERENCES class_schema.department (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE OR REPLACE FUNCTION class_schema."hasActiveChildren"(
	"idOfParent" bigint)
    RETURNS bigint
    LANGUAGE 'sql'
    COST 100
    VOLATILE PARALLEL UNSAFE

RETURN (SELECT 1 WHERE (EXISTS (SELECT dr.child_id FROM (
(SELECT department_relations.child_id FROM class_schema.department_relations
WHERE (department_relations.parent_id = "hasActiveChildren"."idOfParent")) dr JOIN class_schema.department d
ON (((dr.child_id = d.id) AND (d.active = true)))))));

ALTER TABLE IF EXISTS class_schema.department
    ADD CONSTRAINT has_active_children CHECK (active = true OR class_schema."hasActiveChildren"(id) <> 1)
    NOT VALID;
    


