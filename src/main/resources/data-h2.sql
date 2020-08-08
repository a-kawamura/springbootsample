INSERT INTO meeting_room (room_name) VALUES ('新木場');
INSERT INTO meeting_room (room_name) VALUES ('辰巳');
INSERT INTO meeting_room (room_name) VALUES ('豊洲');
INSERT INTO meeting_room (room_name) VALUES ('月島');
INSERT INTO meeting_room (room_name) VALUES ('新富町');
INSERT INTO meeting_room (room_name) VALUES ('銀座一丁目');
INSERT INTO meeting_room (room_name) VALUES ('有楽町');

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 1);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 2);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 2);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 2);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 3);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 3);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 3);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 4);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 4);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 4);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 5);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 5);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 5);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 6);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 6);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 6);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 7);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 7);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 7);

-- ダミーユーザー(password = demo)
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('taro-yamada', '太郎', '山田', '$2a$10$wry7qXnKDKfz6Y0jDJ/w7.ziH4qmxW6REB79I9CZs6WuQP.G3dlRu', 'USER');

INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('aaaa', 'Aaa', 'Aaa', '$2a$10$wry7qXnKDKfz6Y0jDJ/w7.ziH4qmxW6REB79I9CZs6WuQP.G3dlRu', 'USER');
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('bbbb', 'Bbb', 'Bbb', '$2a$10$wry7qXnKDKfz6Y0jDJ/w7.ziH4qmxW6REB79I9CZs6WuQP.G3dlRu', 'USER');
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('cccc', 'Ccc', 'Ccc', '$2a$10$wry7qXnKDKfz6Y0jDJ/w7.ziH4qmxW6REB79I9CZs6WuQP.G3dlRu', 'ADMIN');