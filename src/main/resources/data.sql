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