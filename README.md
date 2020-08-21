# DCAE Operations Dashboard

## Overview 

A web application that offers a GUI (DCAE Dashboard) and a RESTful web service for the ONAP DCAE Operations.
This application lets DCAE application users to manage (deploy/undeploy/upgrade) their deployments to docker hosts and Kubernetes clusters.
Application blueprints describe the execution workflow in Cloudify Manager to orchestrate deployment of resources to the target environment.
These blueprints are the media to control deployment of DCAE applications. 
Users can deploy their blueprints either using the UI or the API (REST).

## Interfaces

The REST API provides a north-bound interface to expose the back-end functionality. This API can be used to create bulk deployment automation scripts.
Application back-end communicates with the following downstream services -
- Inventory mS
- Deployment Handler mS
- Cloudify Manager
- Consul 

Persistence layer consists of a Postgres database to store application data and blueprints inventory (via inventory mS).

## Design

Backend - A REST based web services integration architecture pattern.

Frontend - MVC (Model View Controller) application architecture pattern.

AngularJS MVC front-end application uses RESTful (HTTP) APIs to interact with Spring MVC web Java back-end.

A multi-module maven project, comprising the following Maven projects:
- ccsdk-app-common:  packaging target type of JAR, the JAR is deployed as a run-time dependency for the web application in a web container like Tomcat
- ccsdk-app-overlay: packaging target type of WAR, the WAR file contains AngularJS application code, AngularJS libraries, javascript, CSS and HTML resources for the web application
- ccsdk-app-os:      packaging target type of WAR, this is the main web application project with features for ONAP use

ccsdk-app-os module is a Spring MVC web application.

csdk-app-common is packaged as a library (jar) for the ccsdk-app-os application.

ccsdk-app-overlay is a maven WAR overlay to share web resources for the main web application.

### Prerequites

The web application requires these resources:
- Java version 11
- Apache Tomcat version 9.0
- A Postgresql database, version 9.2 or later

## Build instructions

Build all artifacts by invoking maven in the parent directory:

    mvn clean install or mvn clean install docker:build
  
### Deployment steps - as an application docker container

1. Create a PostgreSQL schema for the application database.
2. Configure the application by editing the files portal.properties, system.properties and dashboard.properties.
3. Build jar/war files within each module project ('mvn install').
4. Create a Dockerfile for the dashboard application container
5. Create cloudify blueprint or Helm chart for the application. Copy blueprint artifacts to bootstrap host.
6. Run Install execution workflow using the blueprint [cfy install -b dashboard -d dashboard -i <inputs filepath> <blueprint filepath>
7. As a first-time user, sign up for a user account at the "/login_external.htm" page.
8. Login at the "login_external.htm" page.


### Deployment steps - without container 

1. Create a Postgre schema within a Postgres database
2. Populate the schema using the DDL and DML scripts in the appropriate db-scripts areas; see the README.md there for instructions to set the user's default schema.
3. Configure the application by editing the files portal.properties, system.properties and dashboard.properties.
4. Build a war file within the appropriate web application project ('mvn package').
5. Deploy the war file to Tomcat.
6. Sign up or Login the first time using credentials stored in the fn_user table at the "login_external.htm" page.