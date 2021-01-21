-- create database university;
create user root with password 'root';
alter user root with superuser;
create table groups(
    group_id serial primary key,
    group_name varchar(255)
);
create table students(
    student_id serial primary key ,
    first_name varchar(255),
    last_name varchar(255),
    group_id integer references groups(group_id)
);
create table teachers(
    teacher_id serial primary key,
    first_name varchar(255),
    last_name varchar(255)
);
create table lecture_halls(
    hall_id serial primary key,
    hall_name varchar(255)
);
 create table subjects(
     subject_id serial primary key,
     subject_name varchar(255)
 );
create table schedules(
    group_id integer references groups(group_id),
    date_time timestamp,
    duration int,
    teacher_id integer references teachers(teacher_id),
    lecture_hall integer references lecture_halls(hall_id),
    subject integer references subjects(subject_id)
);