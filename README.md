#Rest API Authentication with Java Spring Boot sequrity

The purpose of this project is to showcase how webapi authentication can be managed with Spring Security.

#Database Setup

This app requires a postgres database running on port 5432.

Login to psql with the default postgres user:

sudo -u postgres psql

Create a new:

CREATE USER admin1 WITH PASSWORD 'asdasd';

Create the database;

CREATE DATABASE testdb OWNER admin1

Grant all privileges to the new user:

sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE tesdb TO admin1;"

Login to the database (enter password created earlier):

psql -h localhost -U admin1 -d testdb
