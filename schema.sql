CREATE DATABASE studentdb;

USE studentdb;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    roll INT UNIQUE,
    subject1 INT,
    subject2 INT,
    subject3 INT
);
