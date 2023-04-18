# articlerecords-springboot-jwt
springboot-jwt

## Database overview:
![mysql](https://user-images.githubusercontent.com/105952128/232654617-33d5c8ef-b62e-4b5e-adee-22f8b53606e0.png)

## Features:
1. Register user
2. Login
3. Save article
4. Update article
5. Delete article

## API overview:
![1  Register Page](https://user-images.githubusercontent.com/105952128/232654976-e85b1d5c-36b7-4b28-8b98-66cfcca2f2b9.png)
![2  Login Page](https://user-images.githubusercontent.com/105952128/232655028-db38d2b5-1d93-4b54-bf63-ff0fd1dddc5a.png)
![3  List Page](https://user-images.githubusercontent.com/105952128/232655178-3cccc4b1-ca73-4bb0-9994-c5987ae0e58a.png)
![4  Save article](https://user-images.githubusercontent.com/105952128/232655213-539846c9-c775-4d3b-abed-a34223267551.png)
![5  Update Page](https://user-images.githubusercontent.com/105952128/232655224-dc5425a2-cd11-4c99-b412-c467ffdefdf4.png)

## Test using Postman
### 1. Test Register a New User
![1  Test Register a New User](https://user-images.githubusercontent.com/105952128/232656073-11a8c428-43dd-420e-b226-3961ad49fdba.png)
![2  Test Register a New User-mysql](https://user-images.githubusercontent.com/105952128/232656086-9374ae6c-2ec1-41bd-8eae-a17d9a4fc68b.png)
![3  Test Register a New User-same username](https://user-images.githubusercontent.com/105952128/232656098-3fc5b671-86a5-4e38-9379-e1f58fc6a83f.png)
![4  Test Register a New User-password don't match](https://user-images.githubusercontent.com/105952128/232656102-0ebeb102-78fb-4647-925e-8e8f3c886004.png)
### 2. Test Login
![1  Test Login-success1](https://user-images.githubusercontent.com/105952128/232656260-bb25e89c-725d-4c02-9d4e-4cea8e91df35.png)
![1  Test Login-success2](https://user-images.githubusercontent.com/105952128/232656272-52667c52-e9b5-4366-a9a9-6b2f2b7c50f7.png)
![2  Test Login-wrong password](https://user-images.githubusercontent.com/105952128/232656281-020087f1-3c75-4a9a-9760-4b147ac5a934.png)
![3  Test Login-wrong username](https://user-images.githubusercontent.com/105952128/232656296-52d097ce-0f86-47b1-abee-f47f2e54bbff.png)
### 3. Test Save article
![1  Test Save article-success1](https://user-images.githubusercontent.com/105952128/232656415-e500287f-c431-4970-ba69-e24d7c4706f5.png)
![1  Test Save article-success2](https://user-images.githubusercontent.com/105952128/232656425-f98f0617-3569-4351-959a-6caa8f9ef385.png)
![2  Test Save article-mysql](https://user-images.githubusercontent.com/105952128/232656431-02d53e9a-3d83-41ce-8422-fcb49805dc8e.png)
### 4. Test List articles
![1  Test List articles](https://user-images.githubusercontent.com/105952128/232656480-e6abe064-55cf-4451-909e-eb9df67e0bb8.png)
### 5. Test Update article
![1  Test Update Article-success](https://user-images.githubusercontent.com/105952128/232656532-24043767-b394-4fe1-a33a-aa8ea11abfa8.png)
![2  Test Update Article-mysql](https://user-images.githubusercontent.com/105952128/232656547-83ec3abd-e176-496d-ab0f-d297a388175e.png)
### 6. Test Delete article
![1  Test Delete article-success](https://user-images.githubusercontent.com/105952128/232656597-2be00fc0-228d-4c69-9599-898a2dfbb240.png)
![2  Test Delete article-mysql](https://user-images.githubusercontent.com/105952128/232656603-3671ccbb-b897-405b-b0fb-9a3db0927b86.png)

## Test cases 
1. testRegisterationSuccess
![1  testRegisterationSuccess](https://user-images.githubusercontent.com/105952128/232656696-ca1b3fb1-91e8-473d-985c-b939e2a5e7e9.png)
![2  testRegisterationSuccess-mysql](https://user-images.githubusercontent.com/105952128/232656748-aa397300-29a2-44da-aa4f-3f277b48af5e.png)
2. testRegisterationFailurePasswordsDoNotMatch
![3  testRegisterationFailurePasswordsDoNotMatch](https://user-images.githubusercontent.com/105952128/232656794-7f55847e-5462-400d-bc2b-14a77d746117.png)
3. testRegisterationFailureUsernameAlreadyExists
![4  testRegisterationFailureUsernameAlreadyExists](https://user-images.githubusercontent.com/105952128/232656856-27382c2e-e70a-4b4a-925f-aa9e1029e529.png)
4. testLoginSuccess
![5  testLoginSuccess](https://user-images.githubusercontent.com/105952128/232656900-8855d714-f96d-48f4-9395-bf959fe62143.png)
5. testLoginFailureWrongPassword
![6  testLoginFailureWrongPassword](https://user-images.githubusercontent.com/105952128/232656935-094792c9-d483-4ba5-bd5a-cb3b70cff620.png)
6. testLoginFailureWrongUsername
![7  testLoginFailureWrongUsername](https://user-images.githubusercontent.com/105952128/232656972-5792b966-0d72-4879-87ac-8fcfc40904d2.png)
