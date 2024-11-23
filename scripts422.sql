create table person(
    id integer primary key,
    name varchar(255) not null,
    age integer not null,
    is_driver boolean not null,
    car_id integer unique not null references car (id)
);


create table car(
    id integer primary key,
    mark varchar(255) not null,
    model varchar(255) not null,
    cost integer not null
);
