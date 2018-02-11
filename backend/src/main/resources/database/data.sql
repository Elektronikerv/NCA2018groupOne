INSERT INTO roles (name , description) 
VALUES	('Client' , 'Client'),
		('Admin' , 'Admin'),
       	('Courier' , 'Courier'),
        ('Manager' , 'Manager'),
        ('Call Center Agent' , 'Call Center Operator'),
        ('VIP Client' , 'VIP Client')
;

INSERT INTO site_information (text, admin_fk, type)
VALUES ();

INSERT INTO offices (name, description, addres_fk)
VALUES ();

INSERT INTO addresses (street, house, floor, flat)
VALUES ();

INSERT INTO users_roles (user_fk, role_fk)
VALUES ();

INSERT INTO users (login, first_name, last_name, phone_number, email, manager, address_fk)
VALUES ();

INSERT INTO orders (user_fk, office_fk, order_status_fk, client_addres_fk, description, time, parent_fk)
VALUES ();

INSERT INTO feedbacks (order_fk, text)
VALUES ();

INSERT INTO services (order_fk, courier_fk, operator_fk, date, attempt)
VALUES ();

INSERT INTO users_roles (user_fk, role_fk)
VALUES ();

INSERT INTO order_status (order_status_fk, courier_fk, operator_fk, date, attempt)
VALUES ();

