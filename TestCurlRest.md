Java Enterprise Online Project
===============================

### Curl commands for testing MealRestController:
#### 1. Get All: 
`curl http://localhost:8080/topjava/rest/meals/`
#### 2. Get:
`curl http://localhost:8080/topjava/rest/meals/100009`
#### 3. Get Between:
`curl "http://localhost:8080/topjava/rest/meals/filter?startLocalDate=2020-01-30&startLocalTime=13:00&endLocalDate=2020-01-30&endLocalTime=21:00"`
#### 4. Create With Location:
`curl -d '{"dateTime":"2020-02-01T18:00:00","description":"Созданный ужин1","calories":300}' -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals/`
#### 5. Update:
`curl -d '{"id":100003,"dateTime":"2020-01-30T10:02:00","description":"Обновленный завтрак","calories":200}' -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100003`
#### 6. Delete:
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100003 `

