use workflow;

insert into account(id, name, password, username)
values (1, 'Admin', 123456, 'admin123'),
       (2, 'User', 123456, 'user123'),
       (3, 'Cong Khanh', 123456, 'congkhanh'),
       (4, 'Xuan Bang', 123456, 'xuanbang'),
       (5, 'Duc Nguyen', 123456, 'ducnguyen'),
       (6, 'Quoc Khanh', 123456, 'quockhanh');


insert into role(id, name)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');


insert into account_role(account_id, role_id)
values (1, 1),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2);


insert into teams(id, name)
values (1, 'Team 1'),
       (2, 'Team 2'),
       (3, 'Team 3');


insert into board(id, description, is_public, name, team_id)
values (1, 'Chào buổi sáng!', true, 'Quản lý nhân viên', 1),
       (1, 'Chào buổi sáng!', true, 'Quản lý dự án', 1),
       (1, 'Chào buổi sáng!', true, 'Công việc hàng ngày', 2),
       (1, 'Chào buổi sáng!', true, 'Dự án cá nhân', 2),
       (1, 'Chào buổi sáng!', true, 'Đi chợ', 3),
       (1, 'Chào buổi sáng!', true, 'Đi chơi', 3),
       (1, 'Chào buổi sáng!', true, 'Vui vui', 1);


insert into permission(id, name)
values (1, 'manage'),
       (2, 'member');


insert into board(id, description, is_public, name, team_id)
values (1, 'Xin chào', true, 'Chao xìn', 1),
       (2, 'Xin chào', true, 'Chao xìn', 2),
       (3, 'Xin chào', false, 'Chao xìn', 3);


insert into permission_board(id, account_id, board_id, permission_id)
values (1, 1, 1, 1),
       (2, 2, 2, 2),
       (3, 3, 3, 2);


insert into permission_team(id, account_id, permission_id, teams_id)
values (1, 1, 1, 1),
       (2, 2, 2, 2),
       (3, 3, 2, 3);

