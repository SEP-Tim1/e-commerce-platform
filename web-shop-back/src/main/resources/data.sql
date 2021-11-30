insert into users(id, username, email, first_name, last_name, password, phone, role, address) values (0, 'user0', 'user0@email.com', 'Milijana', 'Djordjevic', '12345678', '+381641111111', 'USER', 'Adresa0');
insert into users(id, username, email, first_name, last_name, password, phone, role, address) values (1, 'user1', 'user1@email.com', 'Milijana', 'Djordjevic', '12345678', '+381641111111', 'SELLER', 'Adresa1');
insert into stores(id, name, owner_id) values (0, 'store0', 1);
insert into products(id, name, price, quantity, image_url) values (0, 'product0', 500, 10, '');
insert into stores_products(store_id, products_id) values (0, 0);
