-- FUNCTION --

CREATE OR REPLACE FUNCTION usersData()
  RETURNS void AS $$
DECLARE

  curr_val               INT8;
  random                 INT8;

  pk_user_role_admin     INT8;
  pk_user_role_manager   INT8;
  pk_user_role_ccagent   INT8;
  pk_user_role_courier   INT8;
  pk_user_role_vipclient INT8;
  pk_user_role_client    INT8;

  first_admin            INT8;
  last_admin             INT8;
  first_manager   INT8;
  last_manager    INT8;
  first_ccagent   INT8;
  last_ccagent    INT8;
  first_courier   INT8;
  last_courier    INT8;
  first_vipclient INT8;
  last_vipclient  INT8;
  first_client    INT8;
  last_client     INT8;
  quantity_of_admins     INT8;
  quantity_of_managers   INT8;
  quantity_of_ccagents   INT8;
  quantity_of_couriers   INT8;
  quantity_of_vipclients INT8;
  quantity_of_clients    INT8;

  n50                    INT8;
  n100                   INT8;
  n200                   INT8;
  n300                   INT8;

BEGIN

  -- WIPENING ALL DATA --
  DELETE FROM working_days;
  DELETE FROM services;
  DELETE FROM orders;
  DELETE FROM adverts;
  DELETE FROM advert_types;
  DELETE FROM users_roles;
  DELETE FROM users;
  DELETE FROM roles;
  DELETE FROM offices;
  DELETE FROM addresses;
  DELETE FROM order_status;


  n50 := 50;
  n100 := 100;
  n200 := 200;
  n300 := 300;

  quantity_of_admins := 5;
  quantity_of_managers := 5;
  quantity_of_ccagents := 10;
  quantity_of_couriers := 30;
  quantity_of_vipclients := 50;
  quantity_of_clients := 400;

  ALTER SEQUENCE PUBLIC.users_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.users_roles_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.roles_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.adverts_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.advert_types_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.services_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.offices_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.addresses_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.order_status_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.orders_id_seq RESTART WITH 1;
  PERFORM(nextval('orders_id_seq'));
  ALTER SEQUENCE PUBLIC.orders_id_seq RESTART WITH 1;
  ALTER SEQUENCE PUBLIC.working_days_id_seq RESTART WITH 1;

  -- INSERTING --
  INSERT INTO roles (name, description)
  VALUES ('ADMIN', 'Administrate activities of the offices, employees and site information'),
  ('MANAGER', 'Monitor performance and initiate actions for strengthening results'),
  ('CALL_CENTER_AGENT', 'Confirm orders via telephone, interact with courier ect.'),
  ('COURIER', 'Responsible for delivery. Respond to customer inquiries and others'),
  ('VIP_CLIENT', 'Client functionality + discounts and high priority of orders'),
  ('CLIENT', 'Order, review history of shipments, personal cabinet/profile'),
  ('UNVERIFIED_CLIENT', 'Client who haven''t confirmed their account yet');

	INSERT INTO advert_types	(name)
	VALUES	('ADVERTISEMENT'),
		('NOTICE'),
		('IMPORTANT_ANNOUNCEMENT');

  FOR i IN 1..quantity_of_admins BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users (email, password, first_name, last_name, registration_date)
  VALUES ('admin' || currval('users_id_seq') || '@mail.com',
          'admin' || currval('users_id_seq'),
          'admin' || currval('users_id_seq'),
          'admin' || currval('users_id_seq'),
						CURRENT_TIMESTAMP );
END LOOP;

  first_admin = currval('users_id_seq') - quantity_of_admins + 1;
  last_admin = currval('users_id_seq');


  FOR i IN 1..quantity_of_managers BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users (email, password, first_name, last_name, registration_date)
  VALUES ('manager' || currval('users_id_seq') || '@mail.com',
          'manager' || currval('users_id_seq'),
          'manager' || currval('users_id_seq'),
          'manager' || currval('users_id_seq'),
						CURRENT_TIMESTAMP );
END LOOP;

  first_manager = currval('users_id_seq') - quantity_of_managers + 1;
  last_manager = currval('users_id_seq');


  FOR i IN 1..quantity_of_ccagents BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users (email, password, first_name, last_name, registration_date)
  VALUES ('ccagent' || currval('users_id_seq') || '@mail.com',
          'ccagent' || currval('users_id_seq'),
          'ccagent' || currval('users_id_seq'),
          'ccagent' || currval('users_id_seq'),
						CURRENT_TIMESTAMP );
END LOOP;

  first_ccagent = currval('users_id_seq') - quantity_of_ccagents + 1;
  last_ccagent = currval('users_id_seq');


  FOR i IN 1..quantity_of_couriers BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users (email, password, first_name, last_name, registration_date)
  VALUES ('courier' || currval('users_id_seq') || '@mail.com',
          'courier' || currval('users_id_seq'),
          'courier' || currval('users_id_seq'),
          'courier' || currval('users_id_seq'),
						CURRENT_TIMESTAMP );
END LOOP;

  first_courier = currval('users_id_seq') - quantity_of_couriers + 1;
  last_courier = currval('users_id_seq');


  FOR i IN 1..quantity_of_vipclients BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users (email, password, first_name, last_name, registration_date)
  VALUES ('VIPclient' || currval('users_id_seq') || '@mail.com',
          'VIPclient' || currval('users_id_seq'),
          'VIPclient' || currval('users_id_seq'),
          'VIPclient' || currval('users_id_seq'),
						CURRENT_TIMESTAMP );
END LOOP;

  first_vipclient = currval('users_id_seq') - quantity_of_vipclients + 1;
  last_vipclient = currval('users_id_seq');


  FOR i IN 1..quantity_of_clients BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users (email, password, first_name, last_name, registration_date)
  VALUES ('client' || currval('users_id_seq') || '@mail.com',
          'client' || currval('users_id_seq'),
          'client' || currval('users_id_seq'),
          'client' || currval('users_id_seq'),
						CURRENT_TIMESTAMP );
END LOOP;

  first_client = currval('users_id_seq') - quantity_of_clients + 1;
  last_client = currval('users_id_seq');


  SELECT id
  INTO pk_user_role_admin
  FROM roles
  WHERE name = 'ADMIN';
  FOR i IN first_admin..last_admin BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users_roles (user_id, role_id)
  VALUES (i, pk_user_role_admin);
END LOOP;


  FOR i IN first_admin..last_admin BY 1 LOOP
  -- NOT NULLS
  INSERT INTO adverts (header, text, admin_id, type_id, date_of_publishing)
  VALUES ( 'Header '||i,'Need to create some text randomizer', i, round(random() * 2) + 1, CURRENT_TIMESTAMP);
END LOOP;


  SELECT id
  INTO pk_user_role_manager
  FROM roles
  WHERE name = 'MANAGER';
  FOR i IN first_manager..last_manager BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users_roles (user_id, role_id)
  VALUES (i, pk_user_role_manager);
END LOOP;


  SELECT id
  INTO pk_user_role_ccagent
  FROM roles
  WHERE name = 'CALL_CENTER_AGENT';
  FOR i IN first_ccagent..last_ccagent BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users_roles (user_id, role_id)
  VALUES (i, pk_user_role_ccagent);
END LOOP;


  SELECT id
  INTO pk_user_role_courier
  FROM roles
  WHERE name = 'COURIER';
  FOR i IN first_courier..last_courier BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users_roles (user_id, role_id)
  VALUES (i, pk_user_role_courier);
END LOOP;


  SELECT id
  INTO pk_user_role_vipclient
  FROM roles
  WHERE name = 'VIP_CLIENT';
  FOR i IN first_vipclient..last_vipclient BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users_roles (user_id, role_id)
  VALUES (i, pk_user_role_vipclient);
END LOOP;


  SELECT id
  INTO pk_user_role_client
  FROM roles
  WHERE name = 'CLIENT';
  FOR i IN first_client..last_client BY 1 LOOP
  -- NOT NULLS
  INSERT INTO users_roles (user_id, role_id)
  VALUES (i, pk_user_role_client);
END LOOP;


  INSERT INTO addresses (street, house, floor, flat)
  VALUES ('Peremohy Ave', 59, 1, 1),
  ('Vidradnyi Ave', 89, 1, 1),
  ('Oleny Telihy St', 41, 1, 1),
  ('Holosiivskyi prospekt', 68, 1, 1),
  ('Kharkivs''ke Hwy', 201, 1, 1),
  ('Volodymyra Mayakovs''koho Ave', 5, 1, 1),
  ('Tarasa Shevchenko Blvd', 4, 1, 1),
  ('Obolonskyi Ave', 20, 1, 1);


  INSERT INTO offices (name, address_id, description)
  VALUES ('Peremohy Ave', 1, 'No description'),
  ('Vidradnyi Ave', 2, 'No description'),
  ('Oleny Telihy St', 3, 'No description'),
  ('Holosiivskyi prospekt', 4, 'No description'),
  ('Kharkivs''ke Hwy', 5, 'No description'),
  ('Volodymyra Mayakovs''koho Ave', 6, 'No description'),
  ('Tarasa Shevchenko Blvd', 7, 'NO' ),
  ('Obolonskyi Ave', 8, 'No description');


  FOR i IN 1..200 BY 1 LOOP
  -- NOT NULLS
  INSERT INTO addresses (street, house, floor, flat)
  VALUES ('Volodymyrska St', round(random() * 90) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
  ('Zhylianska St', round(random()*90)+1, round(random()*5)+1, round(random()*25)+1),
  ('Borshchahivska St', round(random()*190)+1, round(random()*5)+1, round(random()*25)+1),
  ('Tarasa Shevchenko Blvd', round(random()*38)+1, round(random()*5)+1, round(random()*25)+1),
  ('Holosiivskyi prospekt', round(random()*100)+1, round(random()*5)+1, round(random()*25)+1),
  ('Mykhaila Boichuka St', round(random()*40)+1, round(random()*5)+1, round(random()*25)+1),
  ('Volodymyra Mayakovs''koho Ave', round(random()*50)+1, round(random()*5)+1, round(random()*25)+1),
  ('Honoré de Balzac Street', round(random()*80)+1, round(random()*5)+1, round(random()*25)+1),
  ('Budivel''nykiv St', round(random()*30)+1, round(random()*5)+1, round(random()*25)+1),
  ('Kharkivs''ke Hwy', round(random()*200)+1, round(random()*5)+1, round(random()*25)+1),
  ('Mykoly Bazhana Ave', round(random()*30)+1, round(random()*5)+1, round(random()*25)+1),
  ('Druzhby Narodiv Blvd', round(random()*30)+1, round(random()*5)+1, round(random()*25)+1),
  ('Lobanovskyi Ave', round(random()*50)+1, round(random()*5)+1, round(random()*25)+1),
  ('Vadyma Hetmana St', round(random()*40)+1, round(random()*5)+1, round(random()*25)+1),
  ('Oleny Telihy St', round(random()*40)+1, round(random()*5)+1, round(random()*25)+1),
  ('Obolonskyi Ave', round(random()*50)+1, round(random()*5)+1, round(random()*25)+1),
  ('Vidradnyi Ave', round(random()*100)+1, round(random()*5)+1, round(random()*25)+1),
  ('bulvar Vatslava Havela', round(random()*70)+1, round(random()*5)+1, round(random()*25)+1),
  ('Danyla Shcherbakivskoho St', round(random()*50)+1, round(random()*5)+1, round(random()*25)+1),
  ('Peremohy Ave', round(random()*100)+1, round(random()*5)+1, round(random()*25)+1),
  ('Peremohy Ave', round(random()*100)+1, round(random()*5)+1, round(random()*25)+1);
END LOOP;

  INSERT INTO order_status (name, description)
  VALUES ('DRAFT', 'DRAFT'),
  ('CANCELLED', 'CANCELLED'),
  ('PROCESSING', 'PROCESSING'),
  ('POSTPONED', 'POSTPONED'),
  ('ASSOCIATED', 'ASSOCIATED'),
  ('CONFIRMED', 'CONFIRMED'),
  ('DELIVERING', 'DELIVERING'),
  ('DELIVERED', 'DELIVERED'),
  ('WAITING_FOR_FEEDBACK', 'WAITING_FOR_FEEDBACK'),
  ('FEEDBACK_REVIEWED', 'FEEDBACK_REVIEWED');


  --COMPLITED/DELIVERED ORDERS ( USERS FROM 101 - TILL 400 )
  FOR i IN 101..400 BY 1 LOOP
  INSERT INTO orders (user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time, execution_time, description, feedback)
  VALUES (i, 1 + round(random() * 7), 8, i, 500 + currval('orders_id_seq'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
          'Some description', 'Some feedback'),
  (i, 1+round(random()*7), 8, i, 500+ CURRVAL ('orders_id_seq'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'Some feedback'),
  (i, 1+round(random()*7), 7, i, 500+ CURRVAL ('orders_id_seq'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Some description', NULL );
END LOOP;

  -- DELIVERING ORDERS ( USERS FROM 301 - TILL 450 )
  FOR i IN 301..450 BY 1 LOOP
  INSERT INTO orders (user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time, execution_time, description, feedback)
  VALUES (i, 1 + round(random() * 7), 6, i, 500 + currval('orders_id_seq'), CURRENT_TIMESTAMP, NULL, 'Some description',
          NULL);
END LOOP;

  -- CONFIRMED ORDERS ( USERS FROM 401 - TILL 500 )
  FOR i IN 401..500 BY 1 LOOP
  INSERT INTO orders (user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time, execution_time, description, feedback)
  VALUES (i, 1 + round(random() * 7), 5, i, 500 + currval('orders_id_seq'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
          'Some description', NULL);
END LOOP;

  -- PROCESSING ORDERS ( USERS FROM 451 - TILL 500 )
  FOR i IN 451..500 BY 1 LOOP
  INSERT INTO orders (user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time, execution_time, description, feedback)
  VALUES (i, 1 + round(random() * 7), 4, i, 500 + currval('orders_id_seq'), CURRENT_TIMESTAMP, NULL, 'Some description',
          NULL);
END LOOP;

  -- POSTPONED ORDERS ( USERS FROM 451 - TILL 500 )
  FOR i IN 451..500 BY 1 LOOP
  INSERT INTO orders (user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time, execution_time, description, feedback)
  VALUES (i, 1 + round(random() * 7), 2, i, 500 + currval('orders_id_seq'), CURRENT_TIMESTAMP, NULL, 'Some description',
          NULL);
END LOOP;

  --DRAFT ( USERS FROM 201 - TILL 400 )
  FOR i IN 201..400 BY 1 LOOP
  INSERT INTO orders (user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time, execution_time, description, feedback)
  VALUES (i, NULL, 1, i, 500 + currval('orders_id_seq'), CURRENT_TIMESTAMP, NULL, NULL, NULL);
END LOOP;

  -- ASSOCIATED ORDER ( USERS FROM 101 - TILL 200 )
  FOR i IN 101..200 BY 1 LOOP
  INSERT INTO orders (user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time, execution_time, description, feedback)
  VALUES (i, 1 + round(random() * 7), 3, i, 500 + currval('orders_id_seq'), CURRENT_TIMESTAMP, NULL, 'Some description',
          NULL);
END LOOP;


  FOR i IN 1..900 BY 1 LOOP

  INSERT INTO services (order_id, courier_id, ccagent_id, confirmation_time, shipping_time, attempt)
  VALUES (i, round(random() * 29) + 20, round(random() * 9) + 10, CURRENT_TIMESTAMP - interval '1 day',
          CURRENT_TIMESTAMP - interval '1 day', 1);
  random := round(random() * 5) + 1;
  IF random = 10
  THEN
    INSERT INTO services (order_id, courier_id, ccagent_id, date, attempt)
    VALUES (i, round(random() * 29) + 20, round(random() * 9) + 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);
  END IF;

END LOOP;

  -- DELIVERING ORDERS ( USERS FROM 301 - TILL 450 )
  FOR i IN 901..1050 BY 1 LOOP

  INSERT INTO services (order_id, courier_id, ccagent_id, confirmation_time, shipping_time, attempt)
  VALUES (i, round(random() * 29) + 20, round(random() * 9) + 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

END LOOP;

  -- CONFIRMED ORDERS ( USERS FROM 401 - TILL 500 )
  FOR i IN 1050..1150 BY 1 LOOP

  INSERT INTO services (order_id, courier_id, ccagent_id, confirmation_time, shipping_time, attempt)
  VALUES (i, round(random() * 29) + 20, round(random() * 9) + 10, CURRENT_TIMESTAMP - interval '1 day', NULL, 1);

END LOOP;


  FOR i IN first_ccagent..last_ccagent BY 1 LOOP
  INSERT INTO working_days (user_id, workday_start, workday_end, worked_out)
  VALUES (i, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE);
END LOOP;

  FOR i IN first_courier..last_courier BY 1 LOOP
  INSERT INTO working_days (user_id, workday_start, workday_end, worked_out)
  VALUES (i, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE);
END LOOP;


END;
$$
LANGUAGE 'plpgsql';

SELECT usersData();
-- SELECT * FROM users_roles;
-- SELECT * FROM roles;
-- SELECT * FROM site_information;
-- SELECT * FROM site_information_types;

-- SELECT * FROM offices;
-- SELECT * FROM addresses;


-- SELECT * FROM orders;
-- SELECT * FROM order_status;

-- SELECT * FROM users;
-- SELECT * FROM order_status;

