create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;

insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);

create table hotel
(
    id          bigint not null,
    country     varchar(32),
    description varchar(2048),
    image_src   varchar(255),
    location    varchar(86),
    name        varchar(256),
    primary key (id)
) engine = InnoDB;

create table tour
(
    id          bigint   not null,
    description varchar(2048),
    end_date    datetime not null,
    image_src   varchar(255),
    seat_count  integer  not null,
    start_date  datetime not null,
    title       varchar(32),
    hotel_id    bigint,
    primary key (id)
) engine = InnoDB;

create table tour_reservation
(
    id        bigint not null,
    comment   varchar(2048),
    is_active bit    not null,
    user_id   bigint,
    tour_id   bigint not null,
    primary key (id)
) engine = InnoDB;

create table user
(
    id            bigint  not null,
    active        INTEGER not null,
    email         varchar(64),
    full_name     varchar(64),
    mobile_number varchar(10),
    password      varchar(512),
    username      varchar(32),
    primary key (id)
) engine = InnoDB;

create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
) engine = InnoDB;


alter table hotel add constraint unique_name unique (name);
alter table user add constraint unique_emaik unique (email);
alter table user add constraint unique_username unique (username);
alter table tour add constraint FK_tour_hotel_id foreign key (hotel_id) references hotel (id);
alter table tour_reservation add constraint FK_tour_reservation_tour_id foreign key (tour_id) references tour (id);
alter table tour_reservation add constraint tour_reservation_user_id foreign key (user_id) references user (id);
alter table user_role add constraint user_role_user_id foreign key (user_id) references user (id);