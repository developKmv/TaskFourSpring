CREATE TABLE IF NOT EXISTS APPUSER(
id SERIAL PRIMARY KEY,
username varchar(100),
fio varchar(100)
);

CREATE TABLE IF NOT EXISTS Logins(
id SERIAL PRIMARY KEY,
access_date timestamp,
application varchar(100),
user_id integer references APPUSER(id)
);