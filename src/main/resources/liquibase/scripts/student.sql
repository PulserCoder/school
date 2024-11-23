-- liquibase formatted sql

-- changeset pkuzmin:1

CREATE INDEX student_name_index ON student (name);