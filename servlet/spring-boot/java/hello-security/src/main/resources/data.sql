-- INSERT INTO users (username, password, roles) 
-- VALUES ('user', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USER');
-- -- The password here is the BCrypt encryption of "password"
create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(100) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

INSERT INTO users (username, password, enabled)
VALUES (
    'user',
    '{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',  -- 'password' 加密后的值
    true
);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');