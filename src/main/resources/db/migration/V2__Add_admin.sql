INSERT INTO user
    (id, active, email, full_name, mobile_number, password, username)
values (1, '1', 'admin@gmail.com', 'Admin Admin Admin', '0684871842', '$2a$11$SxLjGe1kDnjRapbFYuJzhu9uR89vU4CR4Tdk9VekH2J8EQSM7bDJm', 'admin');

INSERT INTO user_role
    (user_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER');

