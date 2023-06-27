## kotlin-ktor-exposed-chat-test-task

ОСНОВНЫЕ API МЕТОДЫ:

1.  Добавить нового пользователя  
    Запрос:

```plaintext
curl --header "Content-Type: application/json" 
--request POST 
--data '{"username": "user_1"}' 
http://localhost:9000/users/add
```

  
        Ответ: id созданного пользователя или HTTP-код ошибки + описание ошибки.

    2. Создать новый чат между пользователями  
        Запрос:

```plaintext
curl --header "Content-Type: application/json" 
--request POST 
--data '{"name": "chat_1", "users": ["", ""]}' 
http://localhost:9000/chats/add
```

  
       Ответ: id созданного чата или HTTP-код ошибки или HTTP-код ошибки + описание ошибки.

       Количество пользователей в чате не ограничено.

    3. Отправить сообщение в чат от лица пользователя  
        Запрос:

```plaintext
curl --header "Content-Type: application/json" 
--request POST 
--data '{"chat": "", "author": "", "text": "hi"}' 
http://localhost:9000/messages/add
```

  
         Ответ: id созданного сообщения или HTTP-код ошибки + описание ошибки.

     4. Получить список чатов конкретного пользователя  
         Запрос:

```plaintext
curl --header "Content-Type: application/json" 
--request POST 
--data '{"user": ""}' 
http://localhost:9000/chats/get
```

  
        Ответ: cписок всех чатов со всеми полями, отсортированный по времени создания последнего сообщения в чате (от позднего к раннему). Или HTTP-код ошибки + описание ошибки.

    5. Получить список сообщений в конкретном чате  
        Запрос:

```plaintext
curl --header "Content-Type: application/json" 
--request POST 
--data '{"chat": ""}' 
http://localhost:9000/messages/get
```

  
Ответ: список всех сообщений чата со всеми полями, отсортированный по времени создания сообщения (от раннего к позднему). Или HTTP-код ошибки + описание ошибки.

    6. Получить кол-во сообщений в конкретном чате, с указанным текстом  
        Запрос:

```plaintext
curl --header "Content-Type: application/json" 
--request POST 
--data '{"chat": "", "text": "hi"}' 
http://localhost:9000/messages/entry/count
```

  
Ответ: Название чата, поисковый текст, кол-во вхождений. Или HTTP-код ошибки + описание ошибки.
