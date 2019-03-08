# Spring-services-with-authorization-and-filter
## Technologies and libraries:
* Java 8
* Spring boot 2.1.3.RELEASE
* Spring Data (Authorization, Services)
* JPA (Authorization, Services)
* MySQL (Authorization, Services)
* Zuul proxy (Filter)
* Liquibase (Services)
* Maven
* Swagger
* Lombok


## Modules: 

### Services:
* Main services for application
* Example test CRUD Entity: TestObject
* It can be broken down into microservices

### Authorization:
* Login/Logout
* Registration new account and cofirming email
* Deleting account

### Filter:
* Filter all requests and responses for Authorization and Services
* Creating Jwt token for login session
