DELETE FROM users;
DELETE FROM users_roles;
DELETE FROM roles;
DELETE FROM site_information;
DELETE FROM site_information_types;
DELETE FROM offices;
DELETE FROM addresses;
DELETE FROM orders;
DELETE FROM order_status;
DELETE FROM services;


CREATE OR REPLACE FUNCTION usersData()
  RETURNS void  AS $$
  DECLARE

	curr_val int8;
    pk_user_role_admin int8;
    pk_user_role_manager int8;
    pk_user_role_ccagent int8;
    pk_user_role_courier int8;
    pk_user_role_vipclient int8;
    pk_user_role_client int8;

    first_admin int8;
    last_admin int8;
    first_manager int8;
    last_manager int8;
    first_ccagent int8;
    last_ccagent int8;
    first_courier int8;
    last_courier int8;
    first_vipclient int8;
    last_vipclient int8;
    first_client int8;
    last_client int8;
    quantity_of_admins int8;
    quantity_of_managers int8;
    quantity_of_ccagents int8;
    quantity_of_couriers int8;
    quantity_of_vipclients int8;
    quantity_of_clients int8;

BEGIN

    quantity_of_admins := 10;
    quantity_of_managers := 10;
    quantity_of_ccagents := 10;
    quantity_of_couriers := 20;
    quantity_of_vipclients := 100;
    quantity_of_clients := 1000;
    ALTER SEQUENCE public.users_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.users_roles_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.roles_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.site_information_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.site_information_types_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.services_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.offices_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.addresses_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.order_status_id_seq RESTART WITH 1;
    ALTER SEQUENCE public.orders_id_seq RESTART WITH 1;

        INSERT INTO roles (name , description)
		VALUES	('admin' , 'admin'),
        		('manager' , 'manager'),
        		('ccagent' , 'ccagent'),
        		('courier' , 'courier'),
        		('VIPclient' , 'VIP Client'),
        		('client' , 'client');

                INSERT INTO site_information_types	(name)
					VALUES	('Advertisment'),
							('Critically importamt info'),
							('Announcement');


    FOR i IN 1..quantity_of_admins BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users (login, password, first_name, last_name, email)
		VALUES ('admin'||currval('users_id_seq')||'@mail.com',
                'admin'||currval('users_id_seq'),
                'admin'||currval('users_id_seq'),
                'admin'||currval('users_id_seq'),
                'admin'||currval('users_id_seq')||'@mail.com');
	END LOOP;
    	first_admin = currval('users_id_seq') -  quantity_of_admins  +1 ;
    	last_admin = currval('users_id_seq');



	FOR i IN 1..quantity_of_managers BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users (login, password, first_name, last_name, email)
		VALUES ('manager'||currval('users_id_seq')||'@mail.com',
                'manager'||currval('users_id_seq'),
                'manager'||currval('users_id_seq'),
                'manager'||currval('users_id_seq'),
                'manager'||currval('users_id_seq')||'@mail.com');
	END LOOP;
    first_manager = currval('users_id_seq') -  quantity_of_managers  +1 ;
    last_manager = currval('users_id_seq');



	FOR i IN 1..quantity_of_ccagents BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users (login, password, first_name, last_name, email)
		VALUES ('ccagent'||currval('users_id_seq')||'@mail.com',
                'ccagent'||currval('users_id_seq'),
                'ccagent'||currval('users_id_seq'),
                'ccagent'||currval('users_id_seq'),
                'ccagent'||currval('users_id_seq')||'@mail.com');
	END LOOP;
    first_ccagent = currval('users_id_seq') -  quantity_of_ccagents +1 ;
    last_ccagent = currval('users_id_seq');



	FOR i IN 1..quantity_of_couriers BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users (login, password, first_name, last_name, email)
		VALUES ('courier'||currval('users_id_seq')||'@mail.com',
                'courier'||currval('users_id_seq'),
                'courier'||currval('users_id_seq'),
                'courier'||currval('users_id_seq'),
                'courier'||currval('users_id_seq')||'@mail.com');
	END LOOP;
    first_courier = currval('users_id_seq') -  quantity_of_couriers  +1 ;
    last_courier = currval('users_id_seq');




	FOR i IN 1..quantity_of_vipclients BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users (login, password, first_name, last_name, email)
		VALUES ('VIPclient'||currval('users_id_seq')||'@mail.com',
                'VIPclient'||currval('users_id_seq'),
                'VIPclient'||currval('users_id_seq'),
                'VIPclient'||currval('users_id_seq'),
                'VIPclient'||currval('users_id_seq')||'@mail.com');
	END LOOP;
    first_vipclient = currval('users_id_seq') -  quantity_of_vipclients  +1 ;
    last_vipclient = currval('users_id_seq');




    FOR i IN 1..quantity_of_clients BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users (login, password, first_name, last_name, email)
		VALUES ('client'||currval('users_id_seq')||'@mail.com',
                'client'||currval('users_id_seq'),
                'client'||currval('users_id_seq'),
                'client'||currval('users_id_seq'),
                'client'||currval('users_id_seq')||'@mail.com');
	END LOOP;
    first_client = currval('users_id_seq') -  quantity_of_vipclients +1 ;
    last_client = currval('users_id_seq');



    SELECT id INTO pk_user_role_admin FROM roles WHERE name = 'admin';
	FOR i IN first_admin..last_admin BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users_roles (user_id, role_id)
		VALUES (i, pk_user_role_admin);
	END LOOP;


    FOR i IN first_admin..last_admin BY 1 LOOP
		-- NOT NULLS
        INSERT INTO site_information (text, admin_id, type_id)
		VALUES ('Need to create some text randomizer' ,i , round(random()*2)+1 );
    	END LOOP;


        SELECT id INTO pk_user_role_manager FROM roles WHERE name = 'manager';
	FOR i IN first_manager..last_manager BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users_roles (user_id, role_id)
		VALUES (i, pk_user_role_manager);
	END LOOP;


        SELECT id INTO pk_user_role_ccagent FROM roles WHERE name = 'ccagent';
	FOR i IN first_ccagent..last_ccagent BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users_roles (user_id, role_id)
		VALUES (i, pk_user_role_ccagent);
	END LOOP;



        SELECT id INTO pk_user_role_courier FROM roles WHERE name = 'courier';
	FOR i IN first_courier..last_courier BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users_roles (user_id, role_id)
		VALUES (i, pk_user_role_courier);
	END LOOP;


    SELECT id INTO pk_user_role_vipclient FROM roles WHERE name = 'VIPclient';
	FOR i IN first_vipclient..last_vipclient BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users_roles (user_id, role_id)
		VALUES (i, pk_user_role_vipclient);
	END LOOP;



    SELECT id INTO pk_user_role_client FROM roles WHERE name = 'client';
	FOR i IN first_client..last_client BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users_roles (user_id, role_id)
		VALUES (i, pk_user_role_client);
	END LOOP;

    SELECT id INTO pk_user_role_client FROM roles WHERE name = 'client';
	FOR i IN 1..1000 BY 1 LOOP
		-- NOT NULLS
		INSERT INTO users_roles (user_id, role_id)
		VALUES (i, pk_user_role_client);
	END LOOP;



				INSERT INTO addresses (street, house, floor, flat)
				VALUES	('Peremohy Ave', 59, 1, 1),
					('Vidradnyi Ave', 89, 1, 1),
					('Oleny Telihy St', 41, 1, 1),
					('Holosiivskyi prospekt', 68, 1, 1),
					('Kharkivs''ke Hwy',201, 1, 1),
					('Volodymyra Mayakovs''koho Ave',5, 1, 1),
					('Tarasa Shevchenko Blvd',4, 1, 1);

                    INSERT INTO offices (name, address_id, description)
			VALUES	('Peremohy Ave', 1,'No description'),
					('Vidradnyi Ave', 2, 'No description'),
					('Oleny Telihy St', 3, 'No description'),
					('Holosiivskyi prospekt', 4, 'No description'),
					('Kharkivs''ke Hwy', 5, 'No description'),
					('Volodymyra Mayakovs''koho Ave',6, 'No description'),
					('Tarasa Shevchenko Blvd',7, 'No description');



FOR i IN 0..200 BY 1 LOOP
		-- NOT NULLS
		INSERT INTO addresses (street, house, floor, flat)
		VALUES	('Volodymyrska St', round(random()*90)+1, round(random()*5)+1, round(random()*25)+1),
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
				('Peremohy Ave', round(random()*100)+1, round(random()*5)+1, round(random()*25)+1);
				END LOOP;

    INSERT INTO order_status (name, description)
		VALUES	('DRAFT','DRAFT'),
        		('POSTPONED','POSTPONED'),
                ('ASSOCIATED','ASSOCIATED'),
                ('CONFIRMED','CONFIRMED'),
                ('PROCESSING','PROCESSING'),
                ('DELIVERING','DELIVERING'),
                ('DELIVERED','DELIVERED'),
                ('COMPLITED','COMPLITED');





			INSERT INTO offices (name, address_id, description)
			VALUES	('Peremohy Ave', 1,'No description'),
					('Vidradnyi Ave', 2, 'No description'),
					('Oleny Telihy St', 3, 'No description'),
					('Holosiivskyi prospekt', 4, 'No description'),
					('Kharkivs''ke Hwy', 5, 'No description'),
					('Volodymyra Mayakovs''koho Ave',6, 'No description'),
					('Tarasa Shevchenko Blvd',7, 'No description');


				INSERT INTO addresses (street, house, floor, flat)
				VALUES	('Peremohy Ave', 59, 1, 1),
					('Vidradnyi Ave', 89, 1, 1),
					('Oleny Telihy St', 41, 1, 1),
					('Holosiivskyi prospekt', 68, 1, 1),
					('Kharkivs''ke Hwy',201, 1, 1),
					('Volodymyra Mayakovs''koho Ave',5, 1, 1),
					('Tarasa Shevchenko Blvd',4, 1, 1);

FOR i IN 0..200 BY 1 LOOP
		-- NOT NULLS
		INSERT INTO addresses (street, house, floor, flat)
		VALUES	('Volodymyrska St', round(random()*90)+1, round(random()*5)+1, round(random()*25)+1),
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
				('Peremohy Ave', round(random()*100)+1, round(random()*5)+1, round(random()*25)+1);
				END LOOP;







					--COMPLITED/DELIVERED ORDERS ( USERS FROM 150 - TILL 650 )
                 	FOR i IN 1..500 BY 1 LOOP
				    INSERT INTO orders	(user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time , confirmation_time, shipping_time, execution_time, description, feedback)
					VALUES				(150+i,	1, 8,  10+i, 510+i, CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2) ,'Some description','Some feedback'),
										(150+i,	1, 8,  10+i, 1010+i, CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2) ,NULL ,'Some feedback'),
										(150+i,	1, 7,  10+i, 1510+i, CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2) ,'Some description', NULL),
										(150+i,	1, 7,  10+i, 2010+i, CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2) ,NULL , NULL);
					END LOOP;

					-- DELIVERING ORDERS ( USERS FROM 540 - TILL 740 )
					FOR i IN 1..200 BY 1 LOOP
				    INSERT INTO orders	(user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time , confirmation_time, shipping_time, execution_time, description, feedback)
					VALUES				(540+i,	1+round(random()*5), 6, 2510+i, 2710+i, CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), NULL ,'Some description', NULL);

					END LOOP;
					-- CONFIRMED ORDERS ( USERS FROM 700 - TILL 800 )
					FOR i IN 1..100 BY 1 LOOP
				    INSERT INTO orders	(user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time , confirmation_time, shipping_time, execution_time, description, feedback)
					VALUES				(700+i,	1+round(random()*5), 5, 2910+i, 3010+i, CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), NULL ,'Some description', NULL);

					END LOOP;
					-- PROCESSING ORDERS ( USERS FROM 800 - TILL 900 )
					FOR i IN 1..100 BY 1 LOOP
				    INSERT INTO orders	(user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time , confirmation_time, shipping_time, execution_time, description, feedback)
					VALUES				(800+i,	1+round(random()*5), 4, 3110+i, 3210+i, CURRENT_TIMESTAMP(2), NULL, NULL, NULL ,'Some description', NULL);

					END LOOP;
					-- POSTPONED ORDERS ( USERS FROM 900 - TILL 1000 )
					FOR i IN 1..100 BY 1 LOOP
				    INSERT INTO orders	(user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time , confirmation_time, shipping_time, execution_time, description, feedback)
					VALUES				(900+i,	1+round(random()*5), 2, 3310+i, 3410+i, CURRENT_TIMESTAMP(2), NULL, NULL, NULL ,'Some description', NULL);

					END LOOP;

					--DRAFT ( USERS FROM 800 - TILL 900 )
					FOR i IN 1..140 BY 1 LOOP
				    INSERT INTO orders	(user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time , confirmation_time, shipping_time, execution_time, description, feedback)
					VALUES				(1000+i,	NULL , 1, 3510+i, 3650+i, CURRENT_TIMESTAMP(2), NULL, NULL, NULL ,NULL, NULL);

					END LOOP;

					-- ASSOCIATED ORDER ( USERS FROM 600 - TILL 650 )
					FOR i IN 1..50 BY 1 LOOP
				    INSERT INTO orders	(user_id, office_id, order_status_id, sender_address_id, receiver_address_id, creation_time , confirmation_time, shipping_time, execution_time, description, feedback)
					VALUES				(600+i,	1+round(random()*5), 3, 3800+i, 3850+i, CURRENT_TIMESTAMP(2), NULL, NULL, NULL ,'Some description', NULL);

					END LOOP;



					FOR i IN 1..2300 BY 1 LOOP
					INSERT INTO services (order_id, courier_id, operator_id, date, attempt)
					VALUES	(i, round(random()*19)+31 ,round(random()*9)+21 , CURRENT_DATE, round(random()*3)+1);
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

-- SELECT * FROM services;
-- SELECT * FROM order_status;


