# Use an official Tomcat image
FROM tomcat:8

ENV APPDIR /usr/local/tomcat

WORKDIR ${APPDIR}

# Create deployments directory
RUN mkdir /home/deployments

# update apt-get
RUN apt-get update

# Install zip
RUN apt-get -y --allow-unauthenticated install zip

# Install vim
RUN apt-get -y --allow-unauthenticated install vim

# Install dos2unix
RUN apt-get install dos2unix -f --allow-unauthenticated

# Install postgresql
RUN apt-get -y install postgresql --allow-unauthenticated

# Download required scripts
COPY docker-dashboard-installation.sh /tmp/docker-dashboard-installation.sh
COPY create_table.sql /tmp/create_table.sql
ARG WAR_FILE
COPY target/${WAR_FILE} /home/deployments/ccsdk-app.war

# Run docker-dashboard-installation.sh
RUN dos2unix /tmp/create_table.sql
RUN dos2unix /tmp/docker-dashboard-installation.sh
RUN chmod +x /tmp/create_table.sql
RUN chmod +x /tmp/docker-dashboard-installation.sh

CMD ["/tmp/docker-dashboard-installation.sh"]