# Spring + GraphQL

This is the Spring-Boot project for the GraphQL SIG. It is the java alternative to javascripts express/node option to expose the resources necessary for the React front-end. In this track you will add configurations and write several classes to implement a graphQL resource.

## Getting started

This project requires java 8.

See src/main/resources/config/application.yml for the current datasource configuration; it expects a postgresql database at port 5432 with password "postgress_password". You may supply this database with the following docker-command: "docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres_password --name graphqlPostgres postgres" (same as "yarn start-db" in the express track).
NB you may use a different database by changing the datasource configuration accordingly. Note that liquibase (src/main/resources/config/liquibase/db.changelog.xml) and the Domain Access Objects (src/main/java/nl/amis/sig/graphql/domain) are configured with camelCase in mind.

In order to run the project, run the following commands in the graphql-spring root folder:

- `./mvnw install`
- `./mvnw spring-boot:run` or `./mvnw`, which runs the previous command by default

Run the project and try some get-requests with Postman:

- `localhost:3030/api/people`
- `localhost:3030/api/practices`
- `localhost:3030/api/projects`

Each request generates debug logging with the objects that are used to enter or exit a method. This is done using the LoggingAspect, which is applied using the LoggingAspectConfiguration (src/main/java/nl/amis/sig/graphql /aop/logging and /config respectively).

## Hands on

### Step 1: Hello world

### Step 2: Expanding the schema

### Step 3: Wrapping a REST endpoint

### Step 4: Inputs

### Step 5: Writing a new resolver

### Step 6: Nested resolvers

### Step 7: To the front-end!

### Step 8: Mutations

### Step 9: Exception handling