ALTER TABLE users_roles
  ADD FOREIGN KEY (role_fk) REFERENCES roles (role_pk);
ALTER TABLE users_roles
  ADD FOREIGN KEY (user_fk) REFERENCES users (user_pk);
ALTER TABLE users
  ADD FOREIGN KEY (manager) REFERENCES users (user_pk);
ALTER TABLE users
  ADD FOREIGN KEY (address_fk) REFERENCES addresses (address_pk);
ALTER TABLE services
  ADD FOREIGN KEY (order_fk) REFERENCES orders (order_pk);
ALTER TABLE services
  ADD FOREIGN KEY (courier_fk) REFERENCES users (user_pk);
ALTER TABLE services
  ADD FOREIGN KEY (operator_fk) REFERENCES users (user_pk);
ALTER TABLE orders
  ADD FOREIGN KEY (order_status_fk) REFERENCES order_status (order_status_pk);
ALTER TABLE orders
  ADD FOREIGN KEY (office_fk) REFERENCES offices (office_pk);
ALTER TABLE orders
  ADD FOREIGN KEY (user_fk) REFERENCES users (user_pk);
ALTER TABLE orders
  ADD FOREIGN KEY (client_address_fk) REFERENCES addresses (address_pk);
ALTER TABLE feedbacks
  ADD FOREIGN KEY (order_fk) REFERENCES orders (order_pk);
ALTER TABLE orders
	ADD FOREIGN KEY (parent_fk) REFERENCES orders (order_pk);
ALTER TABLE site_information
	ADD FOREIGN KEY (admin_fk) REFERENCES users (user_pk);