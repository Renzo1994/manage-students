DROP TABLE IF EXISTS alumno;

CREATE TABLE alumno (
    id VARCHAR(50)   PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    estado VARCHAR(10) NOT NULL,
    edad INT NOT NULL
);
