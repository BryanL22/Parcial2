-- Base de datos de la aplicacion Gestion de Seguros.
-- Ejecutar una vez antes de iniciar la aplicacion:
--   mysql -u root -p < bd/gestion_seguros.sql

DROP DATABASE IF EXISTS gestion_seguros;
CREATE DATABASE gestion_seguros CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_seguros;

-- Asegurados: datos predeterminados (solo lectura desde la aplicacion).
CREATE TABLE asegurado (
    identificacion     VARCHAR(20)  NOT NULL PRIMARY KEY,
    nombre             VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL
);

-- Seguros: herencia en una sola tabla. "tipo" indica la subclase;
-- las columnas de la otra subclase quedan en NULL.
CREATE TABLE seguro (
    numero              VARCHAR(20)   NOT NULL PRIMARY KEY,
    fecha_expedicion    DATE          NOT NULL,
    valor               DECIMAL(15,2) NOT NULL CHECK (valor > 0),
    estado              VARCHAR(20)   NOT NULL,
    tipo                VARCHAR(10)   NOT NULL CHECK (tipo IN ('VIDA', 'VEHICULO')),
    id_asegurado        VARCHAR(20)   NOT NULL,
    nombre_beneficiario VARCHAR(100)  NULL,
    parentesco          VARCHAR(50)   NULL,
    marca               VARCHAR(50)   NULL,
    CONSTRAINT fk_seguro_asegurado FOREIGN KEY (id_asegurado) REFERENCES asegurado (identificacion)
);

INSERT INTO asegurado (identificacion, nombre, correo_electronico) VALUES
    ('1010101010', 'Ana Maria Gomez',     'ana.gomez@correo.com'),
    ('1020202020', 'Carlos Andres Ruiz',  'carlos.ruiz@correo.com'),
    ('1030303030', 'Laura Sofia Martinez','laura.martinez@correo.com'),
    ('1040404040', 'Juan Pablo Torres',   'juan.torres@correo.com');

INSERT INTO seguro (numero, fecha_expedicion, valor, estado, tipo, id_asegurado,
                    nombre_beneficiario, parentesco, marca) VALUES
    ('SV-001', '2025-03-15', 150000000, 'ACTIVO',   'VIDA',     '1010101010', 'Pedro Gomez', 'Hijo',    NULL),
    ('SA-001', '2025-06-01',  45000000, 'ACTIVO',   'VEHICULO', '1010101010', NULL,          NULL,      'Mazda'),
    ('SA-002', '2024-11-20',  38000000, 'VENCIDO',  'VEHICULO', '1020202020', NULL,          NULL,      'Renault');
