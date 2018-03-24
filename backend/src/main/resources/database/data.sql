-- FUNCTION --

CREATE OR REPLACE FUNCTION usersData()
  RETURNS VOID AS $$
DECLARE

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
  DELETE FROM fulfillment_orders;
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
  quantity_of_clients := 200;

  ALTER SEQUENCE users_id_seq RESTART WITH 1;
  ALTER SEQUENCE users_roles_id_seq RESTART WITH 1;
  ALTER SEQUENCE roles_id_seq RESTART WITH 1;
  ALTER SEQUENCE adverts_id_seq RESTART WITH 1;
  ALTER SEQUENCE advert_types_id_seq RESTART WITH 1;
  ALTER SEQUENCE fulfillment_orders_id_seq RESTART WITH 1;
  ALTER SEQUENCE offices_id_seq RESTART WITH 1;
  ALTER SEQUENCE addresses_id_seq RESTART WITH 1;
  ALTER SEQUENCE order_status_id_seq RESTART WITH 1;
  ALTER SEQUENCE orders_id_seq RESTART WITH 1;
  PERFORM (nextval('orders_id_seq'));
  ALTER SEQUENCE orders_id_seq RESTART WITH 1;
  ALTER SEQUENCE working_days_id_seq RESTART WITH 1;

  -- INSERTING --
  INSERT INTO roles (name, description)
  VALUES ('ADMIN', 'Administrate activities of the offices, employees and site information'),
    ('MANAGER', 'Monitor performance and initiate actions for strengthening results'),
    ('CALL_CENTER_AGENT', 'Confirm orders via telephone, interact with courier ect.'),
    ('COURIER', 'Responsible for delivery. Respond to customer inquiries and others'),
    ('VIP_CLIENT', 'Client functionality + discounts and high priority of orders'),
    ('CLIENT', 'Order, review history of shipments, personal cabinet/profile'),
    ('UNVERIFIED_CLIENT', 'Client who haven''t confirmed their account yet');

  INSERT INTO advert_types (name)
  VALUES ('ADVERTISEMENT'),
    ('NOTICE'),
    ('IMPORTANT_ANNOUNCEMENT');

  INSERT INTO order_status (name, description)
  VALUES ('DRAFT', ''),
    ('CANCELLED', ''),
    ('POSTPONED', ''),
    ('ASSOCIATED', ''),
    ('OPEN', ''),
    ('PROCESSING', ''),
    ('CONFIRMED', ''),
    ('EXECUTION', ''),
    ('DELIVERING', ''),
    ('DELIVERED', ''),
    ('WAITING_FOR_FEEDBACK', ''),
    ('FEEDBACK_REVIEWED', '');


  INSERT INTO addresses (street, house, floor, flat)
  VALUES ('Peremohy Ave', 59, 1, 1),
    ('Vidradnyi Ave', 89, 1, 1),
    ('Oleny Telihy St', 41, 1, 1),
    ('Holosiivskyi prospekt', 68, 1, 1),
    ('Kharkivs''ke Hwy', 201, 1, 1),
    ('Volodymyra Mayakovs''koho Ave', 5, 1, 1),
    ('Tarasa Shevchenko Blvd', 4, 1, 1),
    ('Volodymyra Mayakovs''koho Ave', 5, 1, 1),
    ('Tarasa Shevchenko Blvd', 4, 1, 1),
    ('Obolonskyi Ave', 20, 1, 1);


  INSERT INTO offices (name, address_id, description)
  VALUES ('O1 Peremohy Ave', 1, 'No description'),
    ('O2 Vidradnyi Ave', 2, 'No description'),
    ('O3  Oleny Telihy St', 3, 'No description'),
    ('O4 Holosiivskyi prospekt', 4, 'No description'),
    ('O5 Kharkivs''ke Hwy', 5, 'No description'),
    ('O6 Volodymyra Mayakovs''koho Ave', 6, 'No description'),
    ('O7 Tarasa Shevchenko Blvd', 7, 'NO'),
    ('O8 bolonskyi Ave', 8, 'No description');


  FOR i IN 1..quantity_of_admins BY 1 LOOP
    INSERT INTO users (email, password, first_name, last_name, registration_date, phone_number, address_id)
    VALUES ('admin' || currval('users_id_seq') || '@mail.com',
            'admin' || currval('users_id_seq'),
            'admin' || currval('users_id_seq'),
            'admin' || currval('users_id_seq'),
            CURRENT_TIMESTAMP,
            '+38 063 ' || (1000000 + currval('users_id_seq')),
            9);
  END LOOP;

  first_admin = currval('users_id_seq') - quantity_of_admins + 1;
  last_admin = currval('users_id_seq');


  FOR i IN 1..quantity_of_managers BY 1 LOOP
    INSERT INTO users (email, password, first_name, last_name, registration_date, phone_number, address_id)
    VALUES ('manager' || currval('users_id_seq') || '@mail.com',
            'manager' || currval('users_id_seq'),
            'manager' || currval('users_id_seq'),
            'manager' || currval('users_id_seq'),
            CURRENT_TIMESTAMP,
            '+38 063 ' || (1000000 + currval('users_id_seq')),
            9);
  END LOOP;

  first_manager = currval('users_id_seq') - quantity_of_managers + 1;
  last_manager = currval('users_id_seq');


  FOR i IN 1..quantity_of_ccagents BY 1 LOOP
    INSERT INTO users (email, password, first_name, last_name, registration_date, phone_number, address_id)
    VALUES ('ccagent' || currval('users_id_seq') || '@mail.com',
            'ccagent' || currval('users_id_seq'),
            'ccagent' || currval('users_id_seq'),
            'ccagent' || currval('users_id_seq'),
            CURRENT_TIMESTAMP,
            '+38 063 ' || (1000000 + currval('users_id_seq')),
            9);
  END LOOP;

  first_ccagent = currval('users_id_seq') - quantity_of_ccagents + 1;
  last_ccagent = currval('users_id_seq');


  FOR i IN 1..quantity_of_couriers BY 1 LOOP
    INSERT INTO users (email, password, first_name, last_name, registration_date, phone_number, address_id)
    VALUES ('courier' || currval('users_id_seq') || '@mail.com',
            'courier' || currval('users_id_seq'),
            'courier' || currval('users_id_seq'),
            'courier' || currval('users_id_seq'),
            CURRENT_TIMESTAMP,
            '+38 063 ' || (1000000 + currval('users_id_seq')),
            9);
  END LOOP;

  first_courier = currval('users_id_seq') - quantity_of_couriers + 1;
  last_courier = currval('users_id_seq');


  FOR i IN 1..quantity_of_vipclients BY 1 LOOP
    INSERT INTO users (email, password, first_name, last_name, registration_date, phone_number, address_id)
    VALUES ('VIPclient' || currval('users_id_seq') || '@mail.com',
            'VIPclient' || currval('users_id_seq'),
            'VIPclient' || currval('users_id_seq'),
            'VIPclient' || currval('users_id_seq'),
            CURRENT_TIMESTAMP,
            '+38 063 ' || (1000000 + currval('users_id_seq')),
            9);
  END LOOP;

  first_vipclient = currval('users_id_seq') - quantity_of_vipclients + 1;
  last_vipclient = currval('users_id_seq');


  FOR i IN 1..quantity_of_clients BY 1 LOOP
    INSERT INTO users (email, password, first_name, last_name, registration_date, phone_number, address_id)
    VALUES ('client' || currval('users_id_seq') || '@mail.com',
            'client' || currval('users_id_seq'),
            'client' || currval('users_id_seq'),
            'client' || currval('users_id_seq'),
            CURRENT_TIMESTAMP,
            '+38 063 ' || (1000000 + currval('users_id_seq')),
            9);
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
    VALUES ('Header ' || i, 'Need to create some text randomizer', i, round(random() * 2) + 1, CURRENT_TIMESTAMP);
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

  SELECT id
  INTO pk_user_role_client
  FROM roles
  WHERE name = 'CLIENT';
  FOR i IN 1..last_courier BY 1 LOOP
    -- NOT NULLS
    INSERT INTO users_roles (user_id, role_id)
    VALUES (i, pk_user_role_client);
  END LOOP;


  FOR i IN 1..80 BY 1 LOOP
    -- NOT NULLS
    INSERT INTO addresses (street, house, floor, flat)
    VALUES ('Volodymyrska St', round(random() * 90) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Zhylianska St', round(random() * 90) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Borshchahivska St', round(random() * 190) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Tarasa Shevchenko Blvd', round(random() * 38) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Holosiivskyi prospekt', round(random() * 100) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Mykhaila Boichuka St', round(random() * 40) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Volodymyra Mayakovs''koho Ave', round(random() * 50) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Honoré de Balzac Street', round(random() * 80) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Budivel''nykiv St', round(random() * 30) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Kharkivs''ke Hwy', round(random() * 200) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Mykoly Bazhana Ave', round(random() * 30) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Druzhby Narodiv Blvd', round(random() * 30) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Lobanovskyi Ave', round(random() * 50) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Vadyma Hetmana St', round(random() * 40) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Oleny Telihy St', round(random() * 40) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Obolonskyi Ave', round(random() * 50) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Vidradnyi Ave', round(random() * 100) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('bulvar Vatslava Havela', round(random() * 70) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Danyla Shcherbakivskoho St', round(random() * 50) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Peremohy Ave', round(random() * 100) + 1, round(random() * 5) + 1, round(random() * 25) + 1),
      ('Peremohy Ave', round(random() * 100) + 1, round(random() * 5) + 1, round(random() * 25) + 1);
  END LOOP;

  --COMPLITED/DELIVERED ORDERS ( USERS FROM 51 - TILL 300 ) ORDERS 800 (1 - 800)
  FOR i IN 51..300 BY 1 LOOP

    WITH data (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
        creation_time,
        receiver_availability_time_from,
        receiver_availability_time_to,
        execution_time,
        courier_id, ccagent_id,
        confirmation_time,
        receiving_time, shipping_time, attempt ) AS
    ( VALUES (i, 12, i, 300 + currval('orders_id_seq'), 'description', 'Reviewed feedback',
                 CURRENT_TIMESTAMP - INTERVAL '4 day',
                 CURRENT_TIMESTAMP - INTERVAL '3 day' - INTERVAL '120 minutes',
                 CURRENT_TIMESTAMP - INTERVAL '3 day' + INTERVAL '120 minutes',
                 CURRENT_TIMESTAMP - INTERVAL '3 day',
              round(random() * (quantity_of_couriers-1)) + first_courier, round(random() * (quantity_of_ccagents-1)) + first_ccagent,
              CURRENT_TIMESTAMP - INTERVAL '4 day' + INTERVAL '1 hour',
              CURRENT_TIMESTAMP - INTERVAL '3 day' - INTERVAL '1 hour', CURRENT_TIMESTAMP - INTERVAL '3 day', 1))
      ,
        order_insert AS (
        INSERT INTO orders (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
                            creation_time,
                            execution_time,
                            receiver_availability_time_from,
                            receiver_availability_time_to)
          SELECT user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
            creation_time,
            execution_time,
            receiver_availability_time_from,
            receiver_availability_time_to
          FROM data
        ON CONFLICT DO NOTHING
        RETURNING user_id, id AS order_id )
    INSERT INTO fulfillment_orders (order_id, courier_id, ccagent_id, confirmation_time, receiving_time, shipping_time, attempt)
      SELECT
        order_id, courier_id, ccagent_id, confirmation_time, receiving_time,  shipping_time, attempt
      FROM data
        JOIN order_insert USING (user_id);


    WITH data (user_id, office_id, order_status_id, receiver_address_id, description, feedback,
        creation_time,
        receiver_availability_time_from,
        receiver_availability_time_to,
        execution_time,
        courier_id, ccagent_id,
        confirmation_time,
        receiving_time, shipping_time, attempt  ) AS
    ( VALUES (i, 1 + round(random() * 7), 11,  300 + currval('orders_id_seq'), NULL, 'Non Reviewed feedback',
                 CURRENT_TIMESTAMP - INTERVAL '3 day',
                 CURRENT_TIMESTAMP - INTERVAL '2 day' - INTERVAL '180 minutes',
                 CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '120 minutes',
                 CURRENT_TIMESTAMP - INTERVAL '2 day',
              round(random() * (quantity_of_couriers-1)) + first_courier, round(random() * (quantity_of_ccagents-1)) + first_ccagent,
              CURRENT_TIMESTAMP - INTERVAL '3 day' + INTERVAL '2 hour',
              CURRENT_TIMESTAMP - INTERVAL '2 day' - INTERVAL '4 hour',
              CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '60 minutes', 1))
      ,
        order_insert AS (
        INSERT INTO orders (user_id, office_id, order_status_id,  receiver_address_id, description, feedback,
                            creation_time,
                            execution_time,
                            receiver_availability_time_from,
                            receiver_availability_time_to)
          SELECT user_id, office_id, order_status_id, receiver_address_id, description, feedback,
            creation_time,
            execution_time,
            receiver_availability_time_from,
            receiver_availability_time_to
          FROM data
        ON CONFLICT DO NOTHING
        RETURNING user_id, id AS order_id )
    INSERT INTO fulfillment_orders (order_id, courier_id, ccagent_id, confirmation_time, receiving_time, shipping_time, attempt)
      SELECT
        order_id, courier_id, ccagent_id, confirmation_time, receiving_time,  shipping_time, attempt
      FROM data
        JOIN order_insert USING (user_id);


    WITH data (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
        creation_time,
        receiver_availability_time_from,
        receiver_availability_time_to,
        execution_time,
        courier_id, ccagent_id,
        confirmation_time,
        receiving_time, shipping_time, attempt ) AS
    ( VALUES (i, 10, i, 300 + currval('orders_id_seq'), 'desc', NULL,
                 CURRENT_TIMESTAMP - INTERVAL '2 day',
                 CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '120 minutes',
                 CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '300 minutes',
                 CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '240 minutes',
              round(random() * (quantity_of_couriers-1)) + first_courier, round(random() * (quantity_of_ccagents-1)) + first_ccagent,
              CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '150 minutes',
              CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '3 hour',
              CURRENT_TIMESTAMP - INTERVAL '2 day' + INTERVAL '265 minutes', 1))
      ,
        order_insert AS (
        INSERT INTO orders (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
                            creation_time,
                            execution_time,
                            receiver_availability_time_from,
                            receiver_availability_time_to)
          SELECT user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
            creation_time,
            execution_time,
            receiver_availability_time_from,
            receiver_availability_time_to
          FROM data
        ON CONFLICT DO NOTHING
        RETURNING user_id, id AS order_id )
    INSERT INTO fulfillment_orders (order_id, courier_id, ccagent_id, confirmation_time, receiving_time, shipping_time, attempt)
      SELECT
        order_id, courier_id, ccagent_id, confirmation_time, receiving_time,  shipping_time, attempt
      FROM data
        JOIN order_insert USING (user_id);


    IF i % 5 = 0
    THEN

      WITH data (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
          creation_time,
          receiver_availability_time_from,
          receiver_availability_time_to,
          execution_time,
          courier_id, ccagent_id,
          confirmation_time,
          receiving_time, shipping_time, attempt  ) AS
      ( VALUES (i, 12, i, 300 + currval('orders_id_seq'), 'description', 'Reviewed feedback',
                   CURRENT_TIMESTAMP - INTERVAL '1 day' - INTERVAL '240 minutes',
                   CURRENT_TIMESTAMP - INTERVAL '1 day',
                   CURRENT_TIMESTAMP - INTERVAL '1 day' + INTERVAL '200 minutes',
                   CURRENT_TIMESTAMP - INTERVAL '1 day' + INTERVAL '120 minutes',
                round(random() * (quantity_of_couriers-1)) + first_courier, round(random() * (quantity_of_ccagents-1)) + first_ccagent,
                CURRENT_TIMESTAMP - INTERVAL '1 day' + INTERVAL '45 minutes',
                CURRENT_TIMESTAMP - INTERVAL '1 day' - INTERVAL '1 hour',
                CURRENT_TIMESTAMP - INTERVAL '1 day' + INTERVAL '150 minutes', 1))
        ,
          order_insert AS (
          INSERT INTO orders (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
                              creation_time,
                              execution_time,
                              receiver_availability_time_from,
                              receiver_availability_time_to)
            SELECT user_id,  order_status_id, sender_address_id, receiver_address_id, description, feedback,
              creation_time,
              execution_time,
              receiver_availability_time_from,
              receiver_availability_time_to
            FROM data
          ON CONFLICT DO NOTHING
          RETURNING user_id, id AS order_id )
      INSERT INTO fulfillment_orders (order_id, courier_id, ccagent_id, confirmation_time, receiving_time, shipping_time, attempt)
        SELECT
          order_id, courier_id, ccagent_id, confirmation_time, receiving_time,  shipping_time, attempt
        FROM data
          JOIN order_insert USING (user_id);


    END IF;


  END LOOP;

  -- CANCELED ORDERS ( USERS FROM 101 - TILL 200 ) ORDERS 200 (800 - 1000)
  FOR i IN 101..200 BY 1 LOOP
    INSERT INTO orders (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
                        creation_time,
                        receiver_availability_time_from,
                        receiver_availability_time_to)
    VALUES
      (i, 2, i, 300 + currval('orders_id_seq'), NULL, NULL,
       CURRENT_TIMESTAMP - INTERVAL '6 day',
       CURRENT_TIMESTAMP - INTERVAL '5 day',
       CURRENT_TIMESTAMP - INTERVAL '4 day' - INTERVAL '1200 minutes');
    INSERT INTO orders (user_id, office_id, order_status_id,  receiver_address_id, description, feedback,
                        creation_time,
                        receiver_availability_time_from,
                        receiver_availability_time_to)
    VALUES
      (i, 1 + round(random() * 7), 2, 300 + currval('orders_id_seq'), NULL, NULL,
       CURRENT_TIMESTAMP - INTERVAL '8 day',
       CURRENT_TIMESTAMP - INTERVAL '6 day',
       CURRENT_TIMESTAMP - INTERVAL '5 day' - INTERVAL '1200 minutes');
  END LOOP;

  -- CONFIRMED ORDERS ( USERS FROM 51 - TILL 150 ) ORDERS 200 (1000 - 1200)
  FOR i IN 51..150 BY 1 LOOP

    WITH data (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
        creation_time,
        receiver_availability_time_from,
        receiver_availability_time_to,
        execution_time,
        courier_id, ccagent_id,
        confirmation_time,
        receiving_time, shipping_time, attempt  ) AS
    ( VALUES (i, 7, i, 300 + currval('orders_id_seq'), NULL, NULL,
                 CURRENT_TIMESTAMP - INTERVAL '1 day',
                 CURRENT_TIMESTAMP + INTERVAL '120 minutes',
                 CURRENT_TIMESTAMP + INTERVAL '360 minutes',
                 NULL,
              NULL, round(random() * (quantity_of_ccagents-1)) + first_ccagent,
              CURRENT_TIMESTAMP - INTERVAL '1 day' + INTERVAL '75 minutes',
              NULL, NULL, 1))
      ,
        order_insert AS (
        INSERT INTO orders (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
                            creation_time,
                            receiver_availability_time_from,
                            receiver_availability_time_to)
          SELECT
            user_id,  order_status_id, sender_address_id, receiver_address_id, description, feedback,
            creation_time,
            receiver_availability_time_from,
            receiver_availability_time_to
          FROM data
        ON CONFLICT DO NOTHING
        RETURNING user_id, id AS order_id )
    INSERT INTO fulfillment_orders (order_id, ccagent_id, confirmation_time, attempt)
      SELECT
        order_id, ccagent_id, confirmation_time, attempt
      FROM data
        JOIN order_insert USING (user_id);


    WITH data (user_id, office_id, order_status_id, receiver_address_id, description, feedback,
        creation_time,
        receiver_availability_time_from,
        receiver_availability_time_to,
        execution_time,
        courier_id, ccagent_id,
        confirmation_time,
        receiving_time, shipping_time, attempt  ) AS
    ( VALUES (i, 1 + round(random() * 7), 7, 300 + currval('orders_id_seq'), NULL, NULL,
                 CURRENT_TIMESTAMP - INTERVAL '180 minutes',
                 CURRENT_TIMESTAMP + INTERVAL '180 minutes',
                 CURRENT_TIMESTAMP + INTERVAL '360 minutes',
                 NULL,
              NULL, round(random() * (quantity_of_ccagents-1)) + first_ccagent,
              CURRENT_TIMESTAMP - INTERVAL '100 minutes',
              NULL, NULL, 1))
      ,
        order_insert AS (
        INSERT INTO orders (user_id, office_id, order_status_id, receiver_address_id, description, feedback,
                            creation_time,
                            receiver_availability_time_from,
                            receiver_availability_time_to)
          SELECT
            user_id, office_id, order_status_id, receiver_address_id, description, feedback,
            creation_time,
            receiver_availability_time_from,
            receiver_availability_time_to
          FROM data
        ON CONFLICT DO NOTHING
        RETURNING user_id, id AS order_id )
    INSERT INTO fulfillment_orders (order_id, ccagent_id, confirmation_time, attempt)
      SELECT
        order_id, ccagent_id, confirmation_time, attempt
      FROM data
        JOIN order_insert USING (user_id);


  END LOOP;

  --OPEN ( USERS FROM 91 - TILL 120 ) 60 (1200 - 1260)
  FOR i IN 91..120 BY 1 LOOP


    WITH data (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
        creation_time,
        receiver_availability_time_from,
        receiver_availability_time_to,
        execution_time,
        courier_id, ccagent_id,
        confirmation_time,
        receiving_time, shipping_time, attempt  ) AS
    ( VALUES (i , 5, i, 300 + currval('orders_id_seq'), NULL, NULL,
                 CURRENT_TIMESTAMP - ((random() * 10) * INTERVAL '10 minutes'),
                 CURRENT_TIMESTAMP + INTERVAL '180 minutes',
                 CURRENT_TIMESTAMP + INTERVAL '360 minutes',
                NULL,
              NULL,  NULL,
              NULL,
              NULL, NULL, 1))
      ,
        order_insert AS (
        INSERT INTO orders (user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
                            creation_time,
                            receiver_availability_time_from,
                            receiver_availability_time_to)
          SELECT
            user_id, order_status_id, sender_address_id, receiver_address_id, description, feedback,
            creation_time,
            receiver_availability_time_from,
            receiver_availability_time_to
          FROM data
        ON CONFLICT DO NOTHING
        RETURNING user_id, id AS order_id )
    INSERT INTO fulfillment_orders (order_id, attempt)
      SELECT
        order_id, attempt
      FROM data
        JOIN order_insert USING (user_id);





    WITH data (user_id, office_id, order_status_id, receiver_address_id, description, feedback,
        creation_time,
        receiver_availability_time_from,
        receiver_availability_time_to,
        execution_time,
        courier_id, ccagent_id,
        confirmation_time,
        receiving_time, shipping_time, attempt  ) AS
    ( VALUES (i, 1 + round(random() * 7), 5, 300 + currval('orders_id_seq'), NULL, NULL,
                 CURRENT_TIMESTAMP - ((random() * 10) * INTERVAL '25 minutes'),
                 CURRENT_TIMESTAMP + INTERVAL '60 minutes',
                 CURRENT_TIMESTAMP + INTERVAL '300 minutes',
                 NULL,
              NULL,  NULL,
              NULL,
              NULL, NULL, 1))
      ,
        order_insert AS (
        INSERT INTO orders (user_id, office_id, order_status_id, receiver_address_id, description, feedback,
                            creation_time,
                            receiver_availability_time_from,
                            receiver_availability_time_to)
          SELECT
            user_id, office_id, order_status_id, receiver_address_id, description, feedback,
            creation_time,
            receiver_availability_time_from,
            receiver_availability_time_to
          FROM data
        ON CONFLICT DO NOTHING
        RETURNING user_id, id AS order_id )
    INSERT INTO fulfillment_orders (order_id, attempt)
      SELECT
        order_id, attempt
      FROM data
        JOIN order_insert USING (user_id);

  END LOOP;



  -- WORKING DAYS (CCAgents , COURIERS)

  FOR j IN 1..5 BY 1 LOOP
    FOR i IN first_ccagent..last_ccagent BY 1 LOOP
      INSERT INTO working_days (user_id, workday_start, workday_end, worked_out)
      VALUES (i, CURRENT_TIMESTAMP :: DATE + (INTERVAL '8 hour') - (j * INTERVAL '1 day'),
              CURRENT_TIMESTAMP :: DATE + INTERVAL '17 hour' - (j * INTERVAL '1 day'), TRUE);
    END LOOP;
    FOR i IN first_courier..last_courier BY 1 LOOP
      INSERT INTO working_days (user_id, workday_start, workday_end, worked_out)
      VALUES (i, CURRENT_TIMESTAMP :: DATE + (INTERVAL '9 hour') - (j * INTERVAL '1 day'),
              CURRENT_TIMESTAMP :: DATE + INTERVAL '18 hour' - (j * INTERVAL '1 day'), TRUE);
    END LOOP;

    IF j = 5
    THEN
      FOR i IN first_ccagent..last_ccagent BY 1 LOOP
        INSERT INTO working_days (user_id, workday_start, workday_end, worked_out)
        VALUES
          (i, CURRENT_TIMESTAMP :: DATE + (INTERVAL '6 hour'), CURRENT_TIMESTAMP :: DATE + INTERVAL '23 hour', FALSE);
      END LOOP;

      FOR i IN first_courier..last_courier BY 1 LOOP
        INSERT INTO working_days (user_id, workday_start, workday_end, worked_out)
        VALUES
          (i, CURRENT_TIMESTAMP :: DATE + (INTERVAL '9 hour'), CURRENT_TIMESTAMP :: DATE + INTERVAL '18 hour', FALSE);
      END LOOP;

    END IF;

  END LOOP;

  UPDATE users SET password = '$2a$10$EXcrh5KBK8GHbapGcdp7jeAZrnquO80QfJ/ej8dljkC.ZOD6pCdXy' ;

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

