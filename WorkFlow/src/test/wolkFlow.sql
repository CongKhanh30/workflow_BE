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
values (1, 'Nhóm 1'),
       (2, 'Nhóm 2'),
       (3, 'Nhóm 3');


insert into board(id, description, is_public, name, team_id)
values (1, 'Chào buổi sáng!', true, 'Quản lý nhân viên', 1),
       (2, 'Chào buổi sáng!', true, 'Quản lý dự án', 1),
       (3, 'Chào buổi sáng!', true, 'Công việc hàng ngày', 1),
       (4, 'Chào buổi sáng!', true, 'Dự án cá nhân', 2),
       (5, 'Chào buổi sáng!', true, 'Xây dựng dự án', 2),
       (6, 'Chào buổi sáng!', true, 'Kế hoạch tháng', 3),
       (7, 'Chào buổi sáng!', true, 'Kế hoạch tuần', 3);


insert into permission(id, name)
values (1, 'Manager'),
       (2, 'Member');


insert into permission_board(id, account_id, board_id, permission_id)
values (1, 1, 1, 1),
       (2, 1, 1, 2),
       (3, 1, 2, 2),
       (4, 2, 2, 1),
       (5, 3, 2, 2),
       (6, 4, 2, 2),
       (7, 1, 3, 1),
       (8, 5, 3, 2),
       (9, 6, 3, 2);


insert into permission_team(id, account_id, permission_id, teams_id)
values (1, 1, 1, 1),
       (2, 2, 2, 1),
       (3, 3, 2, 1),
       (4, 4, 1, 2),
       (5, 5, 2, 2),
       (6, 6, 2, 2),
       (7, 1, 1, 3),
       (8, 5, 2, 3),
       (9, 6, 2, 3);


-- Thêm các giá trị cho bảng "col"
insert into col(id, name, position, board_id)
values (1, 'ToDo', 1, 1),
       (2, 'Doing', 2, 1),
       (3, 'Done', 3, 1),
       (4, 'Note', 4, 1),
       (5, 'ToDo', 1, 2),
       (6, 'Doing', 2, 2),
       (7, 'Done', 3, 2),
       (8, 'Note', 4, 2),
       (9, 'ToDo', 1, 3),
       (10, 'Doing', 2, 3),
       (11, 'Done', 3, 3),
       (12, 'Note', 4, 3);


-- Thêm các giá trị cho bảng "card"
insert into card(id, description, due_date, title, col_id)
values (1, 'Mô tả', '2023-11-01', 'Tạo hồ sơ nhân viên mới', 1),
       (2, 'Mô tả', '2023-11-01', 'Lập mô tả công việc', 1),
       (3, 'Mô tả', '2023-11-01', 'Giao nhiệm vụ cụ thể', 1),
       (4, 'Mô tả', '2023-11-01', 'Đào tạo nhân viên', 1),
       (5, 'Mô tả', '2023-11-01', 'Xây dựng chính sách nhân sự', 1),
       (6, 'Mô tả', '2023-11-01', 'Theo dõi hiệu suất', 1),
       (7, 'Mô tả', '2023-11-01', 'Đánh giá hàng năm', 1),
       (8, 'Mô tả', '2023-11-02', 'Quản lý thời gian làm việc', 2),
       (9, 'Mô tả', '2023-11-02', 'Khen thưởng hiệu suất', 2),
       (10, 'Mô tả', '2023-11-02', 'Thăng tiến nghề nghiệp', 2),
       (11, 'Mô tả', '2023-11-02', 'Quản lý lương và phúc lợi', 2),
       (12, 'Mô tả', '2023-11-02', 'Quản lý thông tin lương', 2),
       (13, 'Mô tả', '2023-11-02', 'Tuân thủ quy tắc lao động', 2),
       (14, 'Mô tả', '2023-11-02', 'Tạo môi trường tích cực', 2),
       (15, 'Mô tả', '2023-11-03', 'Quản lý thay đổi nhân sự', 3),
       (16, 'Mô tả', '2023-11-03', 'Xử lý sa thải', 3),
       (17, 'Mô tả', '2023-11-03', 'Giám sát việc làm', 3),
       (18, 'Mô tả', '2023-11-03', 'Đối phó xung đột', 3),
       (19, 'Mô tả', '2023-11-03', 'Thúc đẩy phát triển cá nhân', 3),
       (20, 'Mô tả', '2023-11-03', 'Đào tạo nâng cao năng lực', 3),
       (21, 'Mô tả', '2023-11-04', 'Đặt mục tiêu rõ ràng', 4),
       (22, 'Mô tả', '2023-11-04', 'Phát triển kỹ năng', 4),
       (23, 'Mô tả', '2023-11-04', 'Lập kế hoạch thăng tiến', 4),
       (24, 'Mô tả', '2023-11-04', 'Tạo môi trường tích cực', 4),
       (25, 'Mô tả', '2023-11-04', 'Quản lý hiệu suất', 4);

-- Thêm các giá trị cho bảng "card_account"
insert into card_account(card_id, account_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 5);

-- Thêm các giá trị cho bảng "card_attachment"
insert into card_attachment(id, file_name, location, uploader_date, card_id)
values (1, 'Tệp đính kèm 1', '/đường/dẫn/tới/tệp1', '2023-10-26', 1),
       (2, 'Tệp đính kèm 2', '/đường/dẫn/tới/tệp2', '2023-10-27', 2),
       (3, 'Tệp đính kèm 3', '/đường/dẫn/tới/tệp3', '2023-10-28', 3);


-- Thêm các giá trị cho bảng "label"
insert into label(id, color, name)
values (1, 'Đỏ', 'Nhãn 1'),
       (2, 'Xanh', 'Nhãn 2'),
       (3, 'Xanh lá cây', 'Nhãn 3');


-- Thêm các giá trị cho bảng "card_labels"
insert into card_labels(card_id, label_id)
values (1, 1),
       (1, 2),
       (2, 2),
       (3, 1),
       (4, 3);

-- Thêm các giá trị cho bảng "comment"
insert into comment(id, content, date, account_id, card_id)
values (1, 'Bình luận 1', '2023-10-26', 1, 1),
       (2, 'Bình luận 2', '2023-10-27', 2, 1),
       (3, 'Bình luận 3', '2023-10-28', 3, 2);


-- Thêm các giá trị cho bảng "notification"
insert into notification(id, content, date)
values (1, 'Nội dung thông báo 1', '2023-10-26'),
       (2, 'Nội dung thông báo 2', '2023-10-27'),
       (3, 'Nội dung thông báo 3', '2023-10-28');

-- Thêm các giá trị cho bảng "notification_account"
insert into notification_account(id, is_read, account_id, notification_id)
values (1, false, 1, 1),
       (2, true, 2, 2),
       (3, false, 3, 3);
