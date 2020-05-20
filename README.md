# Rest api in Java for users

This project has as objective to have a rest api build on:

- Gradle
- Java 11
- Spring boot 2.3.0-RELEASE
- MySQL

Using standard rest/web spring-boot strategy, non-reactive.
 
We will use SQL with JPA. We will use MySQL as database.

## What the rest api will stand for?

The rest will have the resource:

```
/users
```

Where the endpoints exposed are:

| Method | Endpoint | Description  |
| ---    |:------- |:-----|
|GET| /users | Get all the users |
|POST| /users | Create a new user |
|GET| /users/{user_id} | Get specific user data |
|DELETE| /users/{user_id} | Delete specific user data |
