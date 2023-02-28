create user adminDatabase;
create user adminDatabase with password 'password';

create database dexaprojectdb with template=template0 owner=adminDatabase;
\connect dexaprojectdb;

alter default privileges grant all on tables to adminDatabase;
alter default privileges grant all on sequence to adminDatabase;

CREATE TABLE if not exists employees(
    id bigserial primary key NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    name varchar not null,
    email varchar not null,
    password varchar not null,
    role varchar not null,
    phone_number varchar not null,
    photo_link varchar
);

create table if not exists attendance_lists(
    id bigserial primary key NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    employee_id bigint not null
                        constraint attendance_list_employee_id_fkey
                        references employees(id),
    start_work_at timestamp with time zone,
    end_work_at timestamp with time zone
);

CREATE INDEX if not exists idx_attendance_employee_id on attendance_lists(employee_id);

CREATE TABLE if not exists admins(
    id bigserial primary key NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    username varchar not null,
    password varchar not null
);

create sequence if not exists  employees_id_seq increment 1 start 1;
create sequence if not exists  attendance_lists_id_seq increment 1 start 1;
create sequence if not exists  admins_id_seq increment 1 start 1;