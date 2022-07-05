--Users
DROP SEQUENCE IF EXISTS user_seq;
CREATE SEQUENCE user_seq START 1 INCREMENT 1;
DROP table if exists  users cascade ;
create table users
(
    id int8 not null,
    archieved boolean not null,
    email varchar(255),
    name varchar(255),
    password varchar(255),
    role varchar(255),
    bucket_id int8,
    primary key (id)
);
--Buckets
DROP SEQUENCE IF EXISTS bucket_seq;
CREATE SEQUENCE bucket_seq START 1 INCREMENT 1;
DROP table if exists  buckets cascade ;
create table buckets(
                        id int8 not null,
                        user_id int8,
                        primary key (id)
);
--Link Buckets and Users
alter table if exists buckets
    add constraint buckets_fk_user
        foreign key (user_id) references users;
alter table if exists users
    add constraint users_fk_bucket
        foreign key (bucket_id) references buckets;

--Category
DROP SEQUENCE IF EXISTS category_seq;
CREATE SEQUENCE category_seq START 1 INCREMENT 1;
DROP table if exists  categories cascade ;
create table categories(
                           id int8 not null,
                           title varchar(255),
                           primary key (id)
);
--Product
DROP SEQUENCE IF EXISTS product_seq;
CREATE SEQUENCE product_seq START 1 INCREMENT 1;
DROP table if exists  products cascade ;
create table products(
                         id int8 not null,
                         price numeric(19, 2),
                         title varchar(255),
                         primary key (id)
);
--CATEGORY AND PRODUCT
drop table if exists products_categories cascade;
create table products_categories (
                                     product_id int8 not null,
                                     category_id int8 not null
);
ALTER TABLE IF EXISTS products_categories
    ADD CONSTRAINT products_categories_fk_categories
        foreign key (product_id) references categories;

alter table if exists products_categories
    add constraint products_categories_fk_products
        foreign key (product_id) references products;

--Product in bucket
drop table if exists buckets_products cascade;
create table buckets_products(
                                 bucket_id int8 not null,
                                 product_id int8 not null
);
alter table if exists buckets_products
    add constraint buckets_products_fk_products
        foreign key (product_id) references products;
alter table if exists buckets_products
    add constraint buckets_products_fk_bucket
        foreign key (bucket_id) references buckets;
--Orders
DROP SEQUENCE IF EXISTS order_seq;
CREATE SEQUENCE order_seq START 1 INCREMENT 1;
DROP table if exists  orders cascade ;
create table orders (
                        id int8 not null,
                        adress varchar(255),
                        created timestamp,
                        status varchar(255),
                        sum numeric(19, 2),
                        updated timestamp,
                        user_id int8,
                        primary key (id)
);
alter table if exists orders
    add constraint orders_fk_user
        foreign key (user_id) references users;
--Order details
DROP SEQUENCE IF EXISTS order_details_seq;
CREATE SEQUENCE order_details_seq START 1 INCREMENT 1;
DROP table if exists  orders_details cascade ;
create table orders_details (
                                id int8 not null,
                                amount numeric(19, 2),
                                price numeric(19, 2),
                                order_id int8,
                                product_id int8,
                                details_id int8 not null,
                                primary key (id)
);
alter table if exists orders_details
    add constraint orders_details_fk_orders
        foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint orders_details_fk_products foreign key (product_id) references products
