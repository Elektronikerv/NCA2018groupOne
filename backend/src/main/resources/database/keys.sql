ALTER TABLE users_roles
  ADD FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE users_roles
  ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE users_roles
  ADD CONSTRAINT unique_user_id_and_role_id UNIQUE (user_id, role_id);

ALTER TABLE users
  ADD FOREIGN KEY (manager_id) REFERENCES users (id);
ALTER TABLE users
  ADD FOREIGN KEY (address_id) REFERENCES addresses (id);


ALTER TABLE fulfillment_orders
  ADD FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE fulfillment_orders
  ADD FOREIGN KEY (courier_id) REFERENCES users (id);
ALTER TABLE fulfillment_orders
  ADD FOREIGN KEY (ccagent_id) REFERENCES users (id);

ALTER TABLE orders
  ADD FOREIGN KEY (order_status_id) REFERENCES order_status (id);
ALTER TABLE orders
  ADD FOREIGN KEY (office_id) REFERENCES offices (id);
ALTER TABLE orders
  ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE orders
  ADD FOREIGN KEY (sender_address_id) REFERENCES addresses (id);
ALTER TABLE orders
  ADD FOREIGN KEY (receiver_address_id) REFERENCES addresses (id);
ALTER TABLE orders
  ADD FOREIGN KEY (parent_id) REFERENCES orders (id);


ALTER TABLE offices
  ADD FOREIGN KEY (address_id) REFERENCES addresses (id);

ALTER TABLE adverts
  ADD FOREIGN KEY (admin_id) REFERENCES users (id);
ALTER TABLE adverts
  ADD FOREIGN KEY (type_id) REFERENCES advert_types (id);


ALTER TABLE working_days
  ADD FOREIGN KEY (user_id) REFERENCES users (id);