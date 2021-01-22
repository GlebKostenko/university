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
    group_id bigint
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
    schedule_id serial primary key,
    group_id bigint,
    date_time timestamp,
    duration int,
    teacher_id bigint,
    hall_id bigint,
    subject_id bigint
);