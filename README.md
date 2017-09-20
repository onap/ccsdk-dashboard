# ONAP Operations Manager Dashboard

## Overview 

This is the web UI for the ONAP Operations Manager, also known as the OOM Dashboard. 
It consists of the following Maven projects:
- oom-app-common:  Java classes that run in a web container like Tomcat
- oom-app-overlay: CSS, HTML and Javascript resources for the web application
- oom-app-os:      Web application project with features for ONAP use

### Prerequites

The web application requires these resources:
- Java version 8
- Apache Tomcat version 8.0 or 8.5
- A Postgresql database, version 9.2 or later

## Build instructions

Build all artifacts by invoking maven in this directory:

    mvn package
  
### Deployment steps

1. Create a Postgre schema within a Postgres database
2. Populate the schema using the DDL and DML scripts in the appropriate db-scripts areas; see the README.md there for instructions to set the user's default schema.
3. Configure the application by editing the files portal.properties, system.properties and dashboard.properties.
4. Build a war file within the appropriate web application project ('mvn package').
5. Deploy the war file to Tomcat.
6. Login the first time using credentials stored in the fn_user table at the "login_external.htm" page.
