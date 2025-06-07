-- Insertar roles solo si no existen
INSERT IGNORE INTO roles (NOMBRE_ROL) VALUES 
('ROLE_CLIENTE'),
('ROLE_ADMINISTRADOR');
-- Insertar empresas solo si no existen
INSERT IGNORE INTO empresas (CODIGO_EMPRESA, NOMBRE_EMPRESA, RUC_EMPRESA, ESTADO_EMPRESA) VALUES 
('XLR90', 'UNIVERSIDAD TECNOLOGICA DEL PERU', '20462509236', true),
('ACT03', 'UNIVERSIDAD PRIVADA DEL NORTE', '20215276024', true),
('PXT87', 'UNIVERSIDAD CESAR VALLEJO', '20164113532', true);
-- Insertar categorías de solicitud solo si no existen
INSERT IGNORE INTO CATEOGORIA_SOLICITUD (NOMBRE_TIPO_CATEGORIA) VALUES 
('Hardware'),
('Software'),
('Redes'),
('Soporte Tecnico'),
('Consultas');
-- Insertar dispositivos solo si no existen
INSERT IGNORE INTO DISPOSITIVOS (NOMBRE_DISPOSITIVO) VALUES 
('Laptop'),
('Desktop'),
('Impresora'),
('Teléfono'),
('Tablet'),
('Servidor'),
('Switch'),
('Router');
-- Insertar estados solo si no existen
INSERT IGNORE INTO ESTADO (NOMBRE_ESTADO) VALUES 
('En Espera'),
('Resuelto'),
('Rechazado');
-- Insertar tipos de prioridad solo si no existen
INSERT IGNORE INTO TIPO_PRIORIDAD (NOMBRE_TIPO_PRIORIDAD) VALUES 
('Normal'),
('Alta'),
('Urgente');

INSERT INTO usuarios (
    NOMBRE_USUARIO,
    apellido,
    email_usuario,
    password_usuario,
    dni,
    rol_id,
    empresa_id
) VALUES (
    'Jesus',
    'Cortez',
    'jcortez@gmail.com',
    '$2a$10$zpwmWV4RXXIo54Ed7LWacOSxJcj.u5R5x5qJpCMEgusNFfo3KwIXe',
    '72851172',
    2,
    1
),('Yvan',
    'Sodani',
    'sodani@gmail.com',
    '$2a$10$8YxCIiSJ1sne4ZUQ8q.DgON0zlMqRDus2XiBQWQWw15m2VTHmbVwS',
    '72221171',
    2,
    2
),('Lucero',
    'Altamirano',
    'lucero@gmail.com',
    '$2a$10$zpwmWV4RXXIo54Ed7LWacOSxJcj.u5R5x5qJpCMEgusNFfo3KwIXe',
    '721421416',
    2,
    3
),('Matias',
    'Fernandez Salazar',
    'matias@gmail.com',
    '$2a$10$zpwmWV4RXXIo54Ed7LWacOSxJcj.u5R5x5qJpCMEgusNFfo3KwIXe',
    '721421412',
    2,
    1
),('Carlos',
    'Sanchez Soliz',
    'carlit@gmail.com',
    '$2a$10$zpwmWV4RXXIo54Ed7LWacOSxJcj.u5R5x5qJpCMEgusNFfo3KwIXe',
    '721421411',
    2,
    2
),('Paolo',
    'Chirinos Quispe',
    'paolo@gmail.com',
    '$2a$10$zpwmWV4RXXIo54Ed7LWacOSxJcj.u5R5x5qJpCMEgusNFfo3KwIXe',
    '721421422',
    2,
    3
)
;