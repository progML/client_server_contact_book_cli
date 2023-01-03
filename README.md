# client_server_contact_book_cli

Клиент-сервеная записная книжка. Приложение обрабатывает введённые данные из командной строки, после чего отправляет их на сервер, который сохраняет полученные сведения в базу данных.


# Архитектура решения

Сервер разработан на фраемворке Spring. Для подключения к базе данных PostrgersSQL был использован jdbc драйвер. Используется базовая аунтификация пользователя, пароль шифруется в Base64.

Приложение клиента разработано на Java, представляет собой классическое консольное приложение, данные считываются посредствам библиотеки Scaner, входящей в пакет java.util. После считывания порции данных, происходит их отправка на сервер в формате json.

# Результат

Регистрация нового пользователя
![reg](https://github.com/progML/client_server_contact_book_cli/blob/master/result/reg.gif)

Авторизация нового пользователя
![auth](https://github.com/progML/client_server_contact_book_cli/blob/master/result/auth.gif)

Добавление в записную книжку
![add_book](https://github.com/progML/client_server_contact_book_cli/blob/master/result/add_book.gif)

Поиск по фильтрам
![find_book](https://github.com/progML/client_server_contact_book_cli/blob/master/result/find_book.gif)
