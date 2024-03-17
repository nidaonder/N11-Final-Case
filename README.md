# N11 Final Case
* This document serves as an all-encompassing manual for navigating the functionalities and services 
offered by our application, focusing on the intricate world of restaurant and 
user management. With an emphasis on seamless interaction and user-friendly experiences, 
our application facilitates a variety of operations including the management of restaurants and 
users, as well as offering personalized restaurant recommendations. Dive into the details of our 
Restaurant and User Services, where you can explore how to save, delete, update, and manage restaurants 
and users, alongside a specialized feature for calculating and providing top restaurant recommendations 
based on user preferences and proximity. Below, you will find detailed endpoints that empower you to interact 
with our services efficiently and effectively.

* Developed as a microservice project. Restaurant is running port:8080, User is running port:8081

* The restaurant service stores data in Apache Solr and is developed as a backend application with Spring Boot.

* The user service uses PostgreSQL as its database and is developed as a backend application with Spring Boot.



## Restaurant Service
* Operations for saving, deleting, updating, and reading **RESTAURANTS** are performed.

### Endpoints
#### Restaurant Management API Endpoints

| Method | Endpoint                                 | Description                                                                        |
|--------|------------------------------------------|------------------------------------------------------------------------------------|
| GET    | `/api/v1/restaurants`                    | Lists all restaurants.                                                             |
| GET    | `/api/v1/restaurants/{id}`               | Displays a specific restaurant by ID.                                              |
| POST   | `/api/v1/restaurants`                    | Saves a restaurant with the necessary information.                                 |
| PUT    | `/api/v1/restaurants/{id}`               | Updates the information of a restaurant by ID.                                     |
| PUT    | `/api/v1/restaurants/{id}/average-score` | Uses requests sent from the User Service to update the restaurant's average score. |
| DELETE | `/api/v1/restaurants/{id}`               | Deletes a restaurant by ID.                                                        |

## User Service
* Operations for saving, deleting, updating, updating passwords, and reading **USERS** are performed.
* Operations for saving, deleting, updating scores and comments, and viewing **USER REVIEWS** are performed.
* Calculations are made based on the user's distance to the restaurant and the restaurant's score, in order to provide 
the top 3 **RESTAURANT RECOMMENDATIONS**.

### Endpoints
#### User Management API Endpoints

| Method | Endpoint                   | Description                                                             |
|--------|----------------------------|-------------------------------------------------------------------------|
| GET    | `/api/v1/users`            | Lists all users.                                                        |
| GET    | `/api/v1/users/{id}`       | Displays a specific user by ID.                                         |
| POST   | `/api/v1/users`            | Registers a new user.                                                   |
| PUT    | `/api/v1/users/{id}`       | Updates the information of a user by their ID, except for the password. |
| PATCH  | `/api/v1/users/{id}`       | Updates the password of a specific user.                                |
| DELETE | `/api/v1/users/{id}`       | Deletes a user by their ID.                                             |

#### UserReview Management API Endpoints

| Method | Endpoint                        | Description                                                        |
|--------|---------------------------------|--------------------------------------------------------------------|
| GET    | `/api/v1/user-reviews`          | Displays all User Reviews.                                         |
| GET    | `/api/v1/user-reviews/{id}`     | Displays the requested User Review by ID.                          |
| POST   | `/api/v1/user-reviews`          | Saves a User Review with the necessary information.                |
| PUT    | `/api/v1/user-reviews/{id}`     | Updates a User Review by ID. Score and comment can be updated.     |
| DELETE | `/api/v1/user-reviews/{id}`     | Deletes the requested User Review by ID.                           |

#### RestaurantRecommender Management API Endpoints

| Method | Endpoint                                   | Description                                                                                         |
|--------|--------------------------------------------|-----------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/restaurant-recommenders/{userId}` | Provides the top 3 restaurant suggestions based on the user's location and the restaurant score.    |
