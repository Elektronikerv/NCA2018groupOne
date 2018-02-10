ALTER TABLE "user_role"
  ADD  FOREIGN  KEY ("role_fk") REFERENCES "role" ("role_pk");
ALTER TABLE "user_role"
  ADD  FOREIGN  KEY ("user_fk") REFERENCES "user" ("user_pk");
ALTER TABLE "user"
  ADD  FOREIGN  KEY ("manager") REFERENCES "user" ("user_pk");
ALTER TABLE "service"
  ADD  FOREIGN  KEY ("order_fk") REFERENCES "order" ("order_pk");
ALTER TABLE "service"
  ADD  FOREIGN  KEY ("courier_fk") REFERENCES "user" ("user_pk");
ALTER TABLE "service"
  ADD  FOREIGN  KEY ("operator_fk") REFERENCES "user" ("user_pk");
ALTER TABLE "order"
  ADD  FOREIGN  KEY ("order_status_fk") REFERENCES "order_status" ("order_status_pk");
ALTER TABLE "order"
  ADD  FOREIGN  KEY ("office_fk") REFERENCES "office" ("office_pk");
ALTER TABLE "order"
  ADD  FOREIGN  KEY ("user_fk") REFERENCES "user" ("user_pk");