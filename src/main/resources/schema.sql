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

INSERT INTO t_orders_0(id, order_id, order_message)
VALUES(DEFAULT, 1, 'order 1');
INSERT INTO t_orders_1(id, order_id, order_message)
VALUES(DEFAULT, 2, 'order 2');