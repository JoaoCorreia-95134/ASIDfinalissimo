-- For development!

INSERT INTO departments (departments.name)
VALUES ('Administrative and management'), ('Facilities'),('Pupil support and welfare'), ('Specialist and technical');

INSERT INTO employees (id, egn, age, email, first_name, gender, last_name, middle_name,work_hours, job_title, department_id, town_id)
VALUES
    (1, '1234567892', 20, '3@gmail.com', 'L', 'F', 'F', 'M','20','job_title', 1,1),
    (2, '0000000003', 21, '4@gmail.com', 'M', 'F', 'F', 'M','20','job_title', 1,1);