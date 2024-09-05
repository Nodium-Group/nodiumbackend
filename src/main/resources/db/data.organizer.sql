truncate table users cascade;
INSERT INTO address (state, lga, house_number, street) VALUES
                                                           ('California', 'Los Angeles', '123', 'Main St'),
                                                           ('Texas', 'Houston', '456', 'Elm St'),
                                                           ('New York', 'Manhattan', '789', 'Broadway Ave'),
                                                           ('Florida', 'Miami', '101', 'Ocean Drive'),
                                                           ('Illinois', 'Chicago', '202', 'Lake Shore Dr');
INSERT INTO users (firstname, lastname, password, email, role, address_id)
VALUES
       ('John', 'Doe', 'password123', 'john.doe@example.com', 'USER', 1),
       ('Jane', 'Smith', 'password456', 'jane.smith@example.com', 'PROVIDER', 2),
       ('Alice', 'Johnson', 'password789', 'alice.johnson@example.com', 'USER', 3),
       ('Bob', 'Brown', 'password101', 'bob.brown@example.com', 'USER', 4),
       ('Charlie', 'Davis', 'password202', 'charlie.davis@example.com', 'ADMIN', 5);