# Spring Boot 3 JDBC Session + H2 database

Small example of using jdbc session to get access to the context of one session from another session. 

### H2 database console
- `http://localhost:8080/h2-console`

### Test
1. `curl http://localhost:8080/ -vks` will reture a session id.
   - Call `curl http://localhost:8080/ -H "Cookie: SESSION=<Id from Set-Cookie: SESSION=>" -vks` and get the same id, like from the previous session and every call will return the same id.
2. With `curl http://localhost:8080/session?externalSessionId=<body-with-id-from-the-previous-request> -vks` you should see "Some value". That value from comes from the context of another session.
