# Rest API Authentication with Java Spring Boot sequrity

The purpose of this project is to showcase how webapi authentication can be managed with Spring Security and dtabase persistence with Postgres.


## Docker

This app is built with docker compose and requires a docker install to run.

## Database Setup

This app requires a postgres database running on port 5432. The following database steps assumes a Linux environment:

Login to psql with the default postgres user:

sudo -u postgres psql

Create a new database user:

CREATE USER admin1 WITH PASSWORD 'asdasd';

Create the database;

CREATE DATABASE testdb OWNER admin1

Grant all privileges to the new user:

sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE tesdb TO admin1;"

Login to the database (enter password created earlier):

psql -h localhost -U admin1 -d testdb


## Unit testing

Run the unit tests without triggering Hibernate:

mvn test -Dspring.profiles.active=test
