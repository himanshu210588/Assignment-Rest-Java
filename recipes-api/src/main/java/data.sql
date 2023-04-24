insert into recipe(recipe_id,capacity,formatted_creation_date_time,ingredients,is_vegetarian,recipe_name,recipe_owner)
values(10001,4, '2022-04-04 12:00', ARRAY ['tomato','onion'],'true','Dosa','Akash');

/*
create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);
*/