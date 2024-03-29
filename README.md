# Search engine
Search engine это локальный движок поиска текста по веб сайтам.<br>
![SearchEngine ui gif](https://github.com/SmashFlashDash/java-learn-SkillboxGraduated/blob/master/documentation/searchEngine.gif)

# Technologies
```
spring-boot version: 2.7.1
mysql version: 8.0
```

# Installation

1. В конфигурационном файле __application.yml__ перед запуском приложения задаются
    адреса сайтов, по которым движок должен осуществлять поиск.<br>
    Сайты необходимо указать в поле __sites__ параметрами __url__, __name__, __millis__.<br>
    __url__ - url сайта<br>
    __millis__ - пауза между запросами поискового движка к страницам сайта<br>
    __name__ - имя сайта в БД
    ```$xslt
    indexing-settings:
      sites:
        - url: https://www.bequiet.com/ru
          name: bequiet.com
          millis: 0
        - url: https://www.lenta.ru
          name: Лента.ру
          millis: 100
    ```
   настройки библиотеки Jsoup
   ```$xslt
    jsoup-settings:
         user-agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0
         reffer: http://www.google.com
         socket-timeout: 30000
    ```
   настройки длинны сниппета при поиске текста по проиндексированным страницам
   ```$xslt
    search-settings:
      snippet-length: 200
    ```
   
2. Запустить __mysql__, убедиться что в конфигуарции верно указаны настройки подключения к бд:
    ```$xslt
    spring:
      datasource:
        username: user
        password: pass
        url: jdbc:mysql://localhost:3306/search_engine?useSSL=false&requir
          eSSL=false&allowPublicKeyRetrieval=true
    ``` 
    __Еcли приложение запускается впервые__, - необходимо создать таблицы базы данных, для этого изменить в
    application.yml следующие поля:
    ```
    jpa.hibernate.ddl-auto: create
    sql.init.mode: always
    ```
    __При следующем запуске приложения__ вернуть конфигурацию к исходным значнеиям:
    ```
    jpa.hibernate.ddl-auto: validate
    sql.init.mode: never
    ```
3. Запустить приложение SearchEngine.jar `$ java -jar ./SearchEngine.jar`
4. Управление приложением просходит через интерфейс приложения в браузере, по адресу
    localhost:8080 или через API приложения:
    - /api/statistics - статистика по найденным движком страницам _GET-request_;
    - /api/startIndexing - начать индексацию _GET-request_;
    - /api/stopIndexing - остановить индексацию _GET-request_;
    - /api/indexPage - с параметром URL проиндексировать страницу _POST-request_;
    - /api/search - поиск текста в проиндексированных страницы _GET-request с параметрами (query, offset, limit)_;
5. При запуске комманды __/api/startIndexing__ поисковый движок начнет обходить все страницы заданных сайтов 
    и индексировать и добавлять в бд, чтобы потом находить наиболее релевантные страницы по любому поисковому запросу.
6. Поисковый запрос выполняется по команде __/api/search__ .<br>Запрос трансформируется в список слов, переведённых в базовую форму (леммы). Например, для существительных —
именительный падеж, единственное число.
7. В индексе ищутся страницы, на которых встречаются все эти слова. Результаты поиска ранжируются, сортируются и отдаются пользователю.
8. Результаты поиска ранжируются, сортируются и отдаются пользователю.

# Develop installation
1. создание __docker контенйера__ БД с указанием пользователя, пароля и кодировкой
```
docker run --name mysql -p 3306:3306 
-e LANG=C.UTF-8
--env MYSQL_DATABASE=search_engine --env MYSQL_USER=user --env MYSQL_PASSWORD=pass --env MYSQL_ROOT_PASSWORD=root
mysql --character-set-server utf8mb4
```
2. настройка __IJ Idea__<br>
включить в IJ Idea Build-AnnotationPocessor-enable<br>
установить в IJ Idea lombock-plugin чтобы видел методы сгенрированные аннотациями<br>

3. настрйока подключения __библиотеки лемматизатора__<br>
Указать токен Maven-репозитория. Для указания токена найдите или создайте файл settings.xml.<br>
● В Windows он располагается в директории C:/Users/<Имя вашего пользователя>/.<br>
● В Linux — в директории /home/<Имя вашего пользователя>/.m2<br>
● В macOs — по адресу /Users/<Имя вашего пользователя>/.m2<br>
Вставьте в файл код
    <servers>
        <server>
            <id>skillbox-gitlab</id>
            <configuration>
                <httpHeaders>
                    <property>
                        <name>Private-Token</name>
                        <value>wtb5axJDFX9Vm_W1Lexg</value>
                    </property>
                </httpHeaders>
            </configuration>
        </server>
    </servers>
</settings>
В блоке <value> находится уникальный токен доступа. Если у вас возникнет «401 Ошибка Авторизации» при попытке получения зависимостей, возьмите
актуальный токен доступа из документа по ссылке.