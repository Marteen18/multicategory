# Обзор

## Описание

Проект MultiCategory App представляет из себя каталог товаров. Товары могут быть распределены по категориям, при этом 
вложенность неограниченная. Каждый товар может иметь набор дополнительных атрибутов, которыми можно управлять на странице редактирования.

Каждая категория может быть публичной и приватной. Приватные категории и прикрепленные к ним товары видны только 
авторизованным пользователям.

## Стек

- Spring Framework
- MySQL Connector
- Gson 
- Lombok
- Flyway DB
- Thymeleaf Layout Dialect

## Скриншоты

![1](/screenshots/1.jpeg)
![2](/screenshots/2.jpeg)
![3](/screenshots/3.jpeg)
![4](/screenshots/4.jpeg)
![5](/screenshots/5.jpeg)
![6](/screenshots/6.jpeg)

# Подготовка

## Настройка IDE

В проекте используется библиотека [lombok](https://projectlombok.org/), позволяющая автоматически генерировать геттеры, 
сеттеры и конструкторы.  
Чтобы подсветка синтаксиса работала исправно, необходимо установить плагин по инструкции с сайта [Lombok](https://projectlombok.org/setup/overview).

## Конфигурационный файл

Рядом с jar файлом или в рабочей директории проекта нужно создать файл `application.properties`, который будет хранить 
настройки проекта.

```yaml
app.title=Multi-Category App
spring.datasource.url=jdbc:mysql://localhost:3306/multicategory?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.flyway.url=jdbc:mysql://localhost:3306/multicategory?useSSL=false&serverTimezone=UTC
spring.flyway.user=root
spring.flyway.password=
```

- `spring.datasource.url` – JDBC строка соединения с MySQL базой данных.
- `spring.datasource.username` – имя пользователя базы данных
- `spring.datasource.password` – пароль пользователя базы данных

Параметры `spring.flyway.*` нужны для библиотеки, которая применит миграции к базе данных и создаст нужные таблицы. 
Пользователь для Flyway должен иметь доступ к изменению структуры базы данных. 

# Запуск

## Через Maven

Для запуска проекта через Maven нужно выполнить команду 
```
mvn spring-boot:run
```

## Напрямую запуск jar

```
java -jar multicategory-0.0.1-SNAPSHOT.jar
``` 

При первом запуске создастся пользователь по умолчанию. 
- Логин `default@default`
- Пароль `123456`

Это поведение можно переопределить, отредактировав файл миграции  по пути `/src/main/java/db/migration/V1_1__CreateDefaultUser.java`.

В дальнейшем через раздел "Пользователи" на сайте можно изменить пароль.

# Сборка

Получить jar можно, выполнив следующую команду:
```
mvn package
```

Конечный jar файл будет находиться в папке `target`.