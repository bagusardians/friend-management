# Friend-Management

Friend management is an api where you can manage your friend list. you can add friend, check your friend list, see common friends, subscribe updates from user, block updates from your friends, and check who will receive your updates.

## Run and Test
### Public Cloud
Accessing the public cloud directly without installing in local. 

App: https://mysterious-springs-34492.herokuapp.com/friend-management/

Swagger-UI: https://mysterious-springs-34492.herokuapp.com/friend-management/swagger-ui.html#/

to test the application, you can use the swagger-ui or use [Postman].

the postman api collection can be found the main folder with the name: **Friend Management Heroku.postman_collection.json**

*please be advised that this is free public cloud, which has limited resources and not located in asia. do expect slow performance.*


### Local
Build the spring boot app, by going to its folder in friend-management

*this gradle build is optional, since the latest jar is already provided in /deploy folder*
```sh
$ cd friend-management
$ ./gradlew build
```
Go back to the main folder and run the docker-compose
```sh
$ cd ..
$ docker-compose up --build
```
Wait until both database and app is up and running

the application can be accessed in http://localhost:8090/friend-management/

Swagger-UI: http://localhost:8090/friend-management/swagger-ui.html#/

to test the application, you can use the swagger-ui or use [Postman].

the postman api collection can be found the main folder with the name: **Friend Management Local.postman_collection.json**


## Feature, API Docs and Assumptions

### Adding Friend
> As a user, I need an API to create a friend connection between two email addresses.
  - Provides two email address to make a friend connection between them
  - Email format will be validated before friend connection executed
  - Return success if the process is successful.

#### assumption
 - App will return success, even though the email provided is already a friend, unless blocked
 - if the of the inputted user block the other, it will throw error that the user is in block relation
 - only two email address that can be inputted at a time
#### input
```
{
  friends:
    [
      'andy@example.com',
      'john@example.com'
    ]
}
```
#### output success (200)
```
{
  "success": true
}
```
#### output validation error (422)
```
{
  "success": false
  "error": 
  {
    "developerMessage": "Invalid email format",
    "userMessage": "Invalid input(s)",
    "error": "422E001"
  }
}
```

### Get Friend List
> As a user, I need an API to retrieve the friends list for an email address.
  - Provides email address to see the friend list
  - Email format will be validated before friend connection executed
  - Return list of friends and the count

#### assumption
 - if the email provided is never adding any friend, it will return error as the user is not registered
#### input
```
{
  "email": "bagus@example.com" 
}
```
#### output success (200)
```
{
  "success": true,
  "friends" :
    [
      "john@example.com"
    ],
  "count" : 1   
}
```
#### output validation error (422)
```
{
  "success": false
  "error": 
  {
    "developerMessage": "Cannot find the specified user",
    "userMessage": "Invalid input(s)",
    "error": "422E009"
  }
}
```
### Get Common Friend List
> As a user, I need an API to retrieve the common friends list between two email addresses.
  - Provides two email address to retrieves common friends
  - Email format will be validated before friend connection executed
  - Return success if the process is successful and the list of common friend and its count.

#### assumption
 - if the email provided is never adding any friend, it will return error as the user is not registered
#### input
```
{
  friends:
    [
      "andy@example.com",
      "john@example.com"
    ]
}
```
#### output success (200)
```
{
  "success": true,
  "friends" :
    [
      "common@example.com"
    ],
  "count" : 1   
}
```
#### output validation error (422)
```
{
  "success": false
  "error": 
  {
    "developerMessage": "Invalid email format",
    "userMessage": "Invalid input(s)",
    "error": "422E001"
  }
}
```
### Subscribe updates
> As a user, I need an API to subscribe to updates from an email address.
  - Provides requestor email to subscribe updates from target email
  - Email address will be validated
  - Will return success if the operation succeed

#### assumption
 - App will return success, even though the email provided is already subscribed
#### input
```
{
  "requestor": "lisa@example.com",
  "target": "john@example.com"
}
```
#### output success (200)
```
{
  "success": true
}
```
#### output validation error (422)
```
{
  "success": false
  "error": 
  {
    "developerMessage": "Invalid email format",
    "userMessage": "Invalid input(s)",
    "error": "422E001"
  }
}
```
### Block updates
> As a user, I need an API to block updates from an email address.
  - Provides requestor email to block updates from target email
  - Email format will be validated before friend connection executed
  - Return success if the process is successful.

#### assumption
 - App will return success, even though the email provided is already blocking updates 
 - if the user not in relation, they cannot make a new friend connection if blocked
#### input
```
{
  "requestor": "lisa@example.com",
  "target": "john@example.com"
}
```
#### output success (200)
```
{
  "success": true
}
```
#### output validation error (422)
```
{
  "success": false
  "error": 
  {
    "developerMessage": "Invalid email format",
    "userMessage": "Invalid input(s)",
    "error": "422E001"
  }
}
```
### Retrieve Recipient of Updates
> As a user, I need an API to retrieve all email addresses that can receive updates from an email address.
  - Provides sender email address and text message
  - Text can mention email address of another user
  - Return list of recipients that can receive this updates.

#### assumption
Eligibility for receiving updates from i.e. "john@example.com":
- has not blocked updates from "john@example.com", and
- at least one of the following:
  - has a friend connection with "john@example.com"
  - has subscribed to updates from "john@example.com"
  - has been @mentioned in the update
- if the email mentioned in the updates never adding friends/subscibe, the system will consider it is not registered user, thus not receiving updates.

#### input
```
{
  "sender":  "john@example.com",
  "text": "Hello World! kate@example.com"
}
```
#### output success (200)
```
{
  "success": true
  "recipients":
    [
      "lisa@example.com",
      "kate@example.com"
    ]
}
```
#### output validation error (422)
```
{
  "success": false
  "error": 
  {
    "developerMessage": "Invalid email format",
    "userMessage": "Invalid input(s)",
    "error": "422E001"
  }
}
```
## Error List

| Error Code | Name | HTTP Status | Description |
| ------ | ------ | ------ | ------ |
| 422E001 | NULL_REQUEST_ENTITY | Unprocessable Entity (422) | Request entity is null |
| 422E002 | EMPTY_FRIEND_PARAM | Unprocessable Entity (422) | Friend List must not be empty |
| 422E003 | INVALID_FRIEND_SIZE_PARAM | Unprocessable Entity (422) | Friend List must contain 2 email |
| 422E004 | SAME_EMAIL_EXIST_PARAM | Unprocessable Entity (422) | Friend List must contains different email |
| 422E005 | INVALID_EMAIL_FORMAT | Unprocessable Entity (422) | Invalid email format |
| 422E006 | REQUESTOR_EMPTY | Unprocessable Entity (422) | Subscribe requestor email is empty |
| 422E007 | TARGET_EMPTY | Unprocessable Entity (422) | Subscribe target email is empty |
| 422E008 | SUBSCRIBE_ITSELF | Unprocessable Entity (422) | Requestor cannor subscribe itself |
| 422E009 | USER_NOT_FOUND | Unprocessable Entity (422) | Cannot find the specified user |
| 422E010 | REQUESTOR_NOT_FOUND | Unprocessable Entity (422) | Cannot find the specified requestor |
| 422E011 | TARGET_NOT_FOUND | Unprocessable Entity (422) | Cannot find the specified target |
| 422E012 | BLOCKED | Unprocessable Entity (422) | Inputted Users are in block relation |
| 422E013 | SENDER_NOT_FOUND | Unprocessable Entity (422) | Cannot find the specified sender |
| 500E001 | INTERNAL_SERVER_ERROR | Internal Server Error (500) | Please check the log for the detail |
| 500E002 | ERROR_FIRST_EMAIL | Internal Server Error (500) | Error in extracting first email |
| 500E003 | ERROR_SECOND_EMAIL | Internal Server Error (500) | Error in extracting second email |

## Tech

* [Java] - Programming language.
* [Spring] - application framework and inversion of control container for the Java platform.
* [Spring Boot] - building production-ready Spring applications.
* [Lombok] -  java library that automatically plugs into editor and build tools.
* [mysql] - The world's most popular open source database
* [Docker] - run applications securely isolated in a container, packaged with all its dependencies and libraries.
* [Swagger] - Api documentation


## Documentation
The documentation of the application is this readme file. 
You can also access the api documentation using swagger UI in http://localhost:8090/friend-management/swagger-ui.html#/

[//]: # 
[Java]: <https://swagger.io/>
[Spring]: <https://spring.io/>
[Spring Boot]: <https://projects.spring.io/spring-boot/>
[Lombok]: <https://projectlombok.org/>
[mysql]: <https://www.mysql.com/>
[Docker]: <https://www.docker.com/>
[Swagger]: <https://swagger.io/>
[Postman]: <https://www.getpostman.com/>
