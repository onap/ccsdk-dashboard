# Use an official Tomcat base image
FROM tomcat:8.5-alpine

ENV APPDIR /usr/local/tomcat

WORKDIR ${APPDIR}

COPY docker-dashboard-installation.sh /tmp/docker-dashboard-installation.sh
COPY create_table.sql /tmp/create_table.sql

RUN mkdir /home/deployments \
 && mkdir /opt/logs/dcae/dashboard \
 && apk update \
 && apk add zip \
 && apk add vim \
 && apk add dos2unix \
 && apk add postgresql \
 && dos2unix /tmp/create_table.sql \
 && dos2unix /tmp/docker-dashboard-installation.sh \
 && chmod +x /tmp/create_table.sql \
 && chmod +x /tmp/docker-dashboard-installation.sh

ARG WAR_FILE
COPY target/${WAR_FILE} /home/deployments/ccsdk-app.war

CMD ["/tmp/docker-dashboard-installation.sh"]
