create table class_schema.location(
    id bigserial,
    name text,
    primary key (id)
);

create table class_schema.department(
    id bigserial,
    name text,
    phone text,
    location_id int8,
    primary key (id),
    foreign key (location_id) references class_schema.location(id)
);

create table class_schema.department_relations(
    parent_id int8 not null,
    child_id int8 not null,
    foreign key (child_id) references class_schema.department(id),
    foreign key (parent_id) references class_schema.department(id)
);

