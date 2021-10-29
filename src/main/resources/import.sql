-------------------ROL QUERY----------------
INSERT INTO rol (rol) VALUES ('ROLE_SUPERADMIN');
INSERT INTO rol (rol) VALUES ('ROLE_ADMIN');
INSERT INTO rol (rol) VALUES ('ROLE_USER');
--------------------------------------------

----------------------SUPERADMIN QUERY--------------------------------
INSERT INTO usuario(nombre_usuario, apellido_usuario, email_usuario, username_usuario, password_usuario, estado_usuario) VALUES ('Super', 'Admin', 'superadmin_dessmo@dessmo.com', 'superadmin_dessmo', '$2a$10$h2eSHh/j/6Vtbp1rY5p5N.vgi5DaZIj6BpwivSNqaxjG0lYPwgWKO','ACTIVO');

INSERT INTO usuarios_roles(id_usuario, id_rol) VALUES (1,1);
------------------------------------------------------------------