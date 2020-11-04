# Todo API (Java/Spring) With Tasks

Earlier in this class you built a TODO API with Java/Spring.

In short it allowed the API consumer (like the Todo Client, or curl) to:

- POST new TodoItems
- GET the collection of all TodoItems
- GET a TodoItem by id
- PATCH a TodoItem which changed the completed property to true
- DELETE a TodoItem by id which removed it from the underlying database

This repository has the base source code with tests for the functionality listed above.

Our goal is to expand the functionality of this Todo API by giving the consumer the ability to add any number of additional tasks to any one TodoItem.

# Familiarize yourself with provided code

## Completed Sections

The following files are the heart of the Todo API and are fully functional. You can run the gradle `test` task and see that this API is currently passing all tests.

This completed section is analogous to the code you wrote when creating your own Todo API.

### Models

- IncomingTodoItem.java
- TodoItem.java

We have our entity (`TodoItem`) and it's associated DTO (`IncomingTodoItem`).

You can probably guess by the name, and verify by looking at the `TodoController`, that the `IncomingTodoItem` DTO is used when an HTTP POST request is made to the server.

A JSON string representation is made to our server, it is then deserialized from a JSON string into an `IncomingTodoItem` plain old java object (POJO). We can then use that `IncomingTodoItem` POJO to create our entity a TodoItem.

### Controllers

- TodoController.java

Our controller contains all of our HTTP logic. Defining which endpoints are available, how requests to those endpoints are handled, and the HTTP responses served at each endpoint.

### Data Repositories

- TodoRepository.java

Our JpaRepository of TodoItems. This is our interface to the underlying Postgres database.

### Tests

- todo/GetTodosTests.java
- todo/PostTodosTests.java
- todo/GetTodoTests.java
- todo/PatchTodoTests.java

All of the tests that dictate the API behaves the way we want it to.

MockMvc requests are built, and then we verify that the responses match our expectations.

## Incomplete Sections

### Models

- OutgoingTodoItem.java
- TaskDto.java
- Task.java

### Controllers

- TaskController.java

### Data Repositories

You will need to create a new JpaRespository for your Task entity so that Hibernate can create the table for you.

This file has not been provided for you and you will need to create it from scratch.

### Tests

- task/GetTasksTests.java
- task/PostTasksTests.java

# Objective

We want to add functionality to this project.

We want to give the consumer the ability to add any number of tasks to any TodoItem.

From a user perspective they may create the TodoItem:

- Clean Kitchen

However, they may want to subdivide this further by adding tasks to the greater TodoItem:

- Load dishwasher (Task)
- Clean stove top (Task)
- Clean counters (Task)
- Sweep (Task)
- Mop (Task)

So in the end they have one TodoItem (Clean Kitchen) and a collection of tasks related to the TodoItem.

An API consumer would be able to make a request for all a TodoItem's associated tasks by:

- GET /todos/{id}/features -> 200 collection of tasks

An API consumer would be able to add tasks toa  TodoItem by:

- POST /todos/{id}/features ({"title": "some sub task"}) -> 201

## Take Inventory

This will require us as the developers of the API to make some changes.

In no particular order:

We will have to create a Task entity with a ManyToOne relationship to the TodoItem entity.

We will have to refactor the TodoItem entity so that it has a OneToMany relationship with the Task entity.

We will have to add controller logic that allows consumers to:

- GET /todos/{id}/tasks -> 200 Collection of Tasks entities
- POST /todos/{id}/tasks ({"text": "some task"}) -> 201

This will go in the provided, but empty, `TaskController.java` file.

We will have to create a `TaskDto` for controlling the serialization of incoming, and outgoing requests that contain Tasks.

We will have to create an `OutgoingTodoItem` DTO for controlling the serialization of outgoing TodoItem representations.

We will have to write tests to ensure the new endpoints listed above work properly.

# Setup

A `docker-compose.yml` file has been provided for setting up a development, and testing database. You can run `docker-compose up -d` which will launch the two databases on different ports.

You will notice in the `build.gradle` file the gradle tasks `test` and `bootRun` have already been setup to work with environment variables. Likewise the `application.properties` file is expecting environment variables.

You can run tests from the root of your project with: `./gradlew test -D DB_HOST=localhost -D DB_PORT=5445 -D DB_NAME=todo_test -D DB_USER=todo_test_user -D DB_PASS=todopass`

You can launch a development server (in which you can fire your own manual curl requests) with: `./gradlew bootRun -D DB_HOST=localhost -D DB_PORT=5444 -D DB_NAME=todo -D DB_USER=todo_user -D DB_PASS=todopass`

Tests for the base API objectives have been written for you. You will need to write your own integration tests around Tasks in order to test that you have completed the objective properly.

# Bonus

- Validate incoming JSON and return a `400` response if the incoming data does not meet the validation requirements
- Write tests for the validation additions you made in the previous step
- Look into Lombok for cleaning up the various models