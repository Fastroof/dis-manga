delete from postgres.public.personal_libraries;
delete from postgres.public.help_requests;
delete from postgres.public.reports;
delete from postgres.public.comments;
delete from postgres.public.book_files;
delete from postgres.public.books;
delete from postgres.public.users;






--
-- Creation Users;
--
INSERT INTO postgres.public.users (id, role_id, username, email, password, provider) VALUES (1, 2, 'admin', 'admin@admin.com', '$2a$10$iWOIpv08YdHeiemEEOqm/O3QHvYtjdBn6azEIipixYe7hoA6rRhxa', 'local');
INSERT INTO postgres.public.users (id, role_id, username, email, password, provider) VALUES (4, 1, 'Пабло Оліве', 'fastroofnpwm2@gmail.com', '$2a$10$tziUhk2/4mlr0FP2Y1Kf0OiMXuXwOnF7wQTnc1vgBVIdU9KKwVHPq', 'local');
INSERT INTO postgres.public.users (id, role_id, username, email, password, provider) VALUES (5, 1, 'Арнольд', 'fastroofnpwm@gmail.com', '$2a$10$p0jnWdjbi9H0SezdEO3eYOyeMMdIYWLHSIrFgxg7UpuDWetp3JAIq', 'local');
INSERT INTO postgres.public.users (id, role_id, username, email, password, provider) VALUES (6, 1, 'fastroofnpwm3@gmail.com', 'fastroofnpwm3@gmail.com', '$2a$10$.28h8PxFArxi3CPWHYdmLeshOtoLa4fVcQf6Glg2NIHccG8IGiUfW', 'local');
INSERT INTO postgres.public.users (id, role_id, username, email, password, provider) VALUES (8, 1, 'sasha', 'sasha@sasha.com', '$2a$10$rjC0iOKAihdg5SzFjT71JeISVa76.yzOwP987mCYWYWuaMyr1CGw2', 'local');
INSERT INTO postgres.public.users (id, role_id, username, email, password, provider) VALUES (9, 1, 'test@test.ua', 'test@test.ua', '$2a$10$yEaTvfzBaTQpmaT6YiHdr.MnALVkFc6.TOx8J9hMSeMoW/ec./4h.', 'local');



--
-- Creation books;
--

INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (2, '2023-04-03', '2023-04-03', 1, 'Дарина Гнатко, “Притулок семи вітрів”', 1, 'https://i.grenka.ua/shop/1/10/505/pritulok-semi-vitriv_e51.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (3, '2023-04-03', '2023-04-03', 1, 'Ірена Карпа, «Як виходити заміж стільки разів, скільки захочете»', 1, 'https://i.grenka.ua/shop/1/10/803/yak-vikhoditi-zamizh-stilki-raziv-skilki-zakhochete_725.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (4, '2023-04-03', '2023-04-03', 1, 'Максим Бутченко, «Жінка в темряві. Зелений клин»', 1, 'https://i.grenka.ua/shop/1/11/4/zhinka-v-temryavi-zelenij-klin_404.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (5, '2023-04-03', '2023-04-03', 1, 'Стівен Кінг, «Довга Хода»', 8, 'https://i.grenka.ua/shop/1/12/928/dovga-khoda_367.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (7, '2023-04-03', '2023-04-03', 1, 'Юрай Червенак, «Богатир. Книга 1: Сталеве жезло»', 1, 'https://i.grenka.ua/shop/1/12/933/bogatir-kniga-1-staleve-zhezlo_fa1.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (8, '2023-04-03', '2023-04-03', 1, 'Анна Беннінг, «Вихрь 2. Девушка, которая прорвалась сквозь время»', 1, 'https://i.grenka.ua/shop/1/12/467/vikhr-2-devushka-kotoraya-prorvalas-skvoz-vremya_af5.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (9, '2023-04-03', '2023-04-03', 1, 'Кен Лю, «Прихована дівчина та інші оповідання»', 1, 'https://i.grenka.ua/shop/1/12/690/prikhovana-divchina-ta-inshi-opovidannya_47f.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (10, '2023-04-03', '2023-04-03', 1, 'Діна Бухольц, «Неофіційна кулінарна книга Гаррі Поттера»', 1, 'https://i.grenka.ua/shop/1/12/899/neofitsijna-kulinarna-kniga-garri-pottera_6fd.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (11, '2023-04-03', '2023-04-03', 1, 'Михайло Жирохов, «Зброя Перемоги»', 1, 'https://i.grenka.ua/shop/1/12/917/zbroya-peremogi_1c1.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (13, '2023-04-03', '2023-04-03', 1, 'Том Шиппі, «Сміючись і помру: про життя і смерть видатних вікінгів»', 1, 'https://i.grenka.ua/shop/1/12/931/smiyuchis-i-pomru-pro-zhittya-i-smert-vidatnikh-vikingiv_093.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (14, '2023-04-03', '2023-04-03', 1, 'Дмитро Муравський, «Через війну»', 1, 'https://i.grenka.ua/shop/1/9/787/cherez-vijnu_885.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (15, '2023-04-03', '2023-04-03', 1, 'Шелбі Мег`юрін, «Змія і голуб»', 1, 'https://i.grenka.ua/shop/1/12/929/zmiya-i-golub_7db.png');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (16, '2023-04-03', '2023-04-03', 1, 'Сергій Плохій, «Ядерне безумство. Історія Карибської кризи»', 1, 'https://i.grenka.ua/shop/1/12/907/yaderne-bezumstvo-istoriya-karibskoyi-krizi_a86.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (12, '2023-04-03', '2023-04-03', 1, 'test book', 1, 'https://i.grenka.ua/shop/1/12/929/kniga-mandrivka-nezalezhni_2c4.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (1, '2023-04-03', '2023-04-03', 1, 'Володимир Лис, “Століття Якова”', 8, 'https://i.grenka.ua/shop/1/4/670/stolittya-yakova_0d7.jpg');
INSERT INTO postgres.public.books (id, updated_at, created_at, tag_id, name, owner_id, link_to_cover) VALUES (6, '2023-04-03', '2023-04-03', 1, 'В. Е. Шваб, «Незриме життя Адді Лярю»', 8, 'https://i.grenka.ua/shop/1/12/929/nezrime-zhittya-addi-lyaryu_998.jpg');



--
-- creation book_files;
--


INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (1, 'Файл', '2023-03-27', 1, '1UvyanCc9nnTzFYN2-b_3QPT1c89R2MmW');
INSERT INTO postgres.public.book_files  (id, name, uploaded_at, book_id, google_drive_id) VALUES (3, 'Файл', '2023-03-27', 2, '1H7aSnnC0p1YXlp8a2KqEfzKZr4di4G3z');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (4, 'Файл', '2023-03-27', 3, '1EbaiiW3ipzcTISRSgp-Zm28THL0nhQkU');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (5, 'Файл', '2023-03-27', 4, '1Lpu8QicpUjZXZD_uZX0-0h6H3RbOaEJk');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (6, 'Файл', '2023-03-27', 5, '1H6oJpqYyYXu6mxGzKWsMWxhKCOT5VzmM');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (7, 'Файл', '2023-03-27', 6, '194TTaggNqyhUu1yx2LRtYhg8TFS-YOgb');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (8, 'Файл', '2023-03-27', 7, '1TLB5ZCu_hoa2_Q4f1qmqsacFkHExpN37');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (9, 'Файл', '2023-03-27', 8, '1p_R3xpk6QGYyspm25-R1sa52e6xFkFLv');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (10, 'Файл', '2023-03-27', 9, '1zSp1XM4SJiHfShqJtBbwqQnO9lsl2QjE');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (11, 'Файл', '2023-03-27', 10, '1tkasDhUE6OjeKUu99bMLVu3M6exNu3uQ');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (12, 'Файл', '2023-03-27', 11, '1Va3ieYrbadLnYxFJS1y86I_fKPVoohGn');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (13, 'Файл', '2023-03-27', 12, '1g0ORpdQ33__krb0NkxpO9nNMIZXzAhzJ');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (14, 'Файл', '2023-03-27', 13, '10ThlCJXbadt9bzn3qq17C6nQ3CYwVKMt');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (15, 'Файл', '2023-03-27', 14, '133kasF8n8XLOvZkTP7MKJaDooPsqS5nW');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (16, 'Файл', '2023-03-27', 15, '1EJ9CWRxm9HrIc6StyxX83z9cAhpwA0KQ');
INSERT INTO postgres.public.book_files (id, name, uploaded_at, book_id, google_drive_id) VALUES (17, 'Файл', '2023-03-27', 16, '1Ph1W2oVOyfqxMaYH1-TuVXd6eTMDf-7g');

--
-- Creation book comments;
--


INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (1, '2023-03-27', 'Тестовий комент', 1, 1);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (2, '2023-03-27', 'another comment', 1, 1);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (3, '2023-03-27', 'Hi', 1, 1);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (5, '2023-03-27', 'Чудо', 1, 3);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (6, '2023-03-27', 'Цікавий текст', 1, 2);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (7, '2023-03-27', 'Прикольно, читайте всі!', 1, 8);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (8, '2023-03-28',
        'On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.',
        1, 2);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (9, '2023-03-28',
        'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
        1, 2);

INSERT INTO postgres.public.comments (id, created_at, text, user_id, book_id)
VALUES (10, '2023-03-30', 'Test 9', 9, 2);

--
-- Creation help_reguests;
--

INSERT INTO postgres.public.help_requests (id, created_at, text, email, status, moderator_id)
VALUES (2, '2023-03-27', 'Hi', 'sddsds@gmail.com', 1, null);

INSERT INTO postgres.public.help_requests (id, created_at, text, email, status, moderator_id)
VALUES (3, '2023-03-27', 'Pablo OriTest', 'd@sd', 1, null);

INSERT INTO postgres.public.help_requests (id, created_at, text, email, status, moderator_id)
VALUES (1, '2023-03-27', 'dd', 'dfsfd@dsdd', 2, 1);

INSERT INTO postgres.public.help_requests (id, created_at, text, email, status, moderator_id)
VALUES (4, '2023-03-28',
        'Петро Джміль///Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        'barabolyagdfdsdfdsfsdfdsfasdfsdsfjuiuNH@gnadfs.com', 1, null);

INSERT INTO postgres.public.help_requests (id, created_at, text, email, status, moderator_id)
VALUES (5, '2023-03-30', 'Павло///Hi dio', 'ddd@ds', 1, null);

INSERT INTO postgres.public.help_requests (id, created_at, text, email, status, moderator_id)
VALUES (6, '2023-03-30', 'hello there', 'test@test.com', 1, null);


--
-- Creation repotrs;
--

INSERT INTO postgres.public.reports (id, created_at, text, user_id, status, moderator_id, book_id)
VALUES (2, '2023-03-27', 'Test heee heeee', 1, 1, null, 8);

INSERT INTO postgres.public.reports (id, created_at, text, user_id, status, moderator_id, book_id)
VALUES (1, '2023-03-27', 'Щось ви не доробили', 1, 2, 1, 8);

--
-- Creation personal_libraries;
--

INSERT INTO postgres.public.personal_libraries (user_id, book_id, id)
VALUES (8, 11, 1);

INSERT INTO postgres.public.personal_libraries (user_id, book_id, id)
VALUES (8, 13, 3);

INSERT INTO postgres.public.personal_libraries (user_id, book_id, id)
VALUES (1, 10, 4);

INSERT INTO postgres.public.personal_libraries (user_id, book_id, id)
VALUES (8, 6, 5);






