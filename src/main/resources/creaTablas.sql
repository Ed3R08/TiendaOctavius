CREATE DATABASE tiendaOctavius;

USE tiendaOctavius;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);
INSERT INTO usuario (username, password, rol, activo)
VALUES ('admin', '$2a$10$7YqzBYl2FQ/NQnshQOBdruW.nIEO3Dq/rLaYHovudfJG6NYkElI7e', 'ADMIN', TRUE);

USE tiendaOctavius;
CREATE USER 'octavius_user'@'localhost' IDENTIFIED BY 'Clave_123';
GRANT ALL PRIVILEGES ON tiendaOctavius.* TO 'octavius_user'@'localhost';
FLUSH PRIVILEGES;