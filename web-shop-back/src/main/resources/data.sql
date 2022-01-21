insert into users(id, username, email, first_name, last_name, password, phone, role, address) values (0, 'user0', 'user0@email.com', 'Milijana', 'Djordjevic', '$2a$10$FjGR3Lg/9e7ecJZBc7XJOeh6.Ydeqkeh8pIxcbUtd.rtoaHFRD4ca', '+381641111111', 'USER', 'Adresa0');
insert into users(id, username, email, first_name, last_name, password, phone, role, address) values (1, 'user1', 'user1@email.com', 'Milijana', 'Djordjevic', '$2a$10$0ikOy/axPIlGOJXBY2g36uym.CwtQbKC07XDmnrmEyRCiWlyVn/CS', '+381641111111', 'SELLER', 'Adresa1');
insert into stores(id, name, owner_id) values (0, 'MOJ NOVI STOR', 1);
insert into products(id, name, has_quantity, quantity, image_url) values (0, 'Mouse', true, 150, 'storage/media-content/mis.jpg');
insert into products(id, name, has_quantity, quantity, image_url) values (1, 'Monitor', false, 10, 'storage/media-content/monitor.jpg');
insert into product_price_list(product_id, active_from, price, billing_cycle) values (0, '1/8/2020', 10, 0);
insert into product_price_list(product_id, active_from, price, billing_cycle) values (1, '1/8/2020', 250, 2);
insert into stores_products(store_id, products_id) values (0, 0);
insert into stores_products(store_id, products_id) values (0, 1);
