create table ecom_order (id integer not null, last_event_sequence_number bigint, version bigint, number integer, order_status varchar(255), price double precision, product_id integer, primary key (id)) ENGINE=InnoDB;
create table ecom_product (id integer not null, last_event_sequence_number bigint, version bigint, description varchar(255), price double precision, stock integer, primary key (id)) ENGINE=InnoDB;
alter table ecom_order add constraint FK_f3rnd79i90twofold1Isiahi foreign key (product_id) references ecom_product (id);
create table ecom_product_view(id INT , price DOUBLE, stock INT ,description VARCHAR(255));
create table ecom_order_view(id INT , price DOUBLE, number INT ,description VARCHAR(225),status VARCHAR(50));
create table ecom_order_audit(id INT ,status VARCHAR(50),date TIMESTAMP);

insert into ecom_product(id,description,price,stock,version) values(1,'Shirts',100,5,0);
insert into ecom_product(id,description,price,stock,version) values(2,'Pants',100,5,0);
insert into ecom_product(id,description,price,stock,version) values(3,'T-Shirt',100,5,0);
insert into ecom_product(id,description,price,stock,version) values(4,'Shoes',100,5,0);

insert into ecom_product_view(id,description,price,stock) values(1,'Shirts',100,5);
insert into ecom_product_view(id,description,price,stock) values(2,'Pants',100,5);
insert into ecom_product_view(id,description,price,stock) values(3,'T-Shirt',100,5);
insert into ecom_product_view(id,description,price,stock) values(4,'Shoes',100,5);
