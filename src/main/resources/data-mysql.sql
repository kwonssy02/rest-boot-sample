INSERT INTO users (username, name, email, password, create_time) VALUES ('dbaldud333', '유미영', 'test@test.com', '$2a$10$6LIuCi6ZM9YhT5w00EWWE.LrA2uruPwqKGtcoEyHhukrzTa8D9xlq', '2019-04-06 12:22:15');
INSERT INTO users (username, name, email, password, create_time) VALUES ('kwonssy02', '권혁찬', 'test@test.com', '$2a$10$TGtJPTDvPnjBwnwFc9yZ5.iHKW4.4riVB4VbCZ5aWx9HG.F7zDJFO', '2019-04-06 12:19:12');

INSERT INTO authorities (username, authority) VALUES ('kwonssy02', 'USER');
INSERT INTO authorities (username, authority) VALUES ('kwonssy02', 'ADMIN');
INSERT INTO authorities (username, authority) VALUES ('dbaldud333', 'ADMIN');