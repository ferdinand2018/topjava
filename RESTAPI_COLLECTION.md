# REST API запросы

## Meals

### Получить meal по id
```curl --location 'http://localhost:8081/topjava/rest/profile/meals/100008'```

### Удалить meal по id
```curl --location --request DELETE 'http://localhost:8081/topjava/rest/profile/meals/100008'```

### Получить полный список meal
```curl --location 'http://localhost:8081/topjava/rest/profile/meals'```

### Создать meal
```
curl --location 'http://localhost:8081/topjava/rest/profile/meals' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{
    "dateTime": "2020-02-01T12:00",
    "description": "Created lunch",
    "calories": 300
}'
```

### Обновить meal
```
curl --location --request PUT 'http://localhost:8081/topjava/rest/profile/meals/100013' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{
    "dateTime": "2021-02-01T12:00",
    "description": "Updated lunch",
    "calories": 121
}'
```

