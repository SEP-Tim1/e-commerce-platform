insert into users(id, username, email, first_name, last_name, password, phone, role, address) values (0, 'user0', 'user0@email.com', 'Milijana', 'Djordjevic', '12345678', '+381641111111', 'USER', 'Adresa0');
insert into users(id, username, email, first_name, last_name, password, phone, role, address) values (1, 'user1', 'user1@email.com', 'Milijana', 'Djordjevic', '12345678', '+381641111111', 'SELLER', 'Adresa1');
insert into stores(id, name, owner_id) values (0, 'MOJ NOVI STOR', 1);
insert into products(id, name, quantity, image_url) values (0, 'Mouse', 150, 'storage/media-content/mis.jpg');
insert into products(id, name, quantity, image_url) values (1, 'Monitor', 10, 'storage/media-content/monitor.jpg');
insert into stores_products(store_id, products_id) values (0, 0);
insert into stores_products(store_id, products_id) values (0, 1);
