DROP TABLE IF EXISTS t_orders_0;
CREATE TABLE t_orders_0 (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NULL,
    order_message VARCHAR(255) NULL
);
ALTER TABLE t_orders_0 OWNER TO test;
GRANT ALL PRIVILEGES ON TABLE t_orders_0 TO test;

DROP TABLE IF EXISTS t_orders_1;
CREATE TABLE t_orders_1 (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NULL,
    order_message VARCHAR(255) NULL
);

ALTER TABLE t_orders_1 OWNER TO test;
GRANT ALL PRIVILEGES ON TABLE t_orders_1 TO test;

----
DROP TABLE IF EXISTS t_fixed_date_202201;
CREATE TABLE t_fixed_date_202201 (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NULL,
    order_message VARCHAR(255) NULL,
    create_time TIMESTAMP NULL
);
ALTER TABLE t_fixed_date_202201 OWNER TO test;
GRANT ALL PRIVILEGES ON TABLE t_fixed_date_202201 TO test;

DROP TABLE IF EXISTS t_fixed_date_202202;
CREATE TABLE t_fixed_date_202202 (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NULL,
    order_message VARCHAR(255) NULL,
    create_time TIMESTAMP NULL
);
ALTER TABLE t_fixed_date_202202 OWNER TO test;
GRANT ALL PRIVILEGES ON TABLE t_fixed_date_202202 TO test;

DROP TABLE IF EXISTS t_fixed_date_202203;
CREATE TABLE t_fixed_date_202203 (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NULL,
    order_message VARCHAR(255) NULL,
    create_time TIMESTAMP NULL
);
ALTER TABLE t_fixed_date_202203 OWNER TO test;
GRANT ALL PRIVILEGES ON TABLE t_fixed_date_202203 TO test;

DROP TABLE IF EXISTS t_fixed_date_202204;
CREATE TABLE t_fixed_date_202204 (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NULL,
    order_message VARCHAR(255) NULL,
    create_time TIMESTAMP NULL
);
ALTER TABLE t_fixed_date_202204 OWNER TO test;
GRANT ALL PRIVILEGES ON TABLE t_fixed_date_202204 TO test;

----
DROP TABLE IF EXISTS t_auto_create_table_20220101;
CREATE TABLE t_auto_create_table_20220101 (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NULL,
    order_message VARCHAR(255) NULL,
    create_time TIMESTAMP NULL
);
ALTER TABLE t_auto_create_table_20220101 OWNER TO test;
GRANT ALL PRIVILEGES ON TABLE t_auto_create_table_20220101 TO test;

----
INSERT INTO t_orders_0(id, order_id, order_message)
VALUES(DEFAULT, 1, 'order 1');
INSERT INTO t_orders_1(id, order_id, order_message)
VALUES(DEFAULT, 2, 'order 2');