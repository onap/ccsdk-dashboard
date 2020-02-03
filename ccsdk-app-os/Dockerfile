# Use an official Tomcat image
FROM tomcat:8

ENV APPDIR /usr/local/tomcat

ENV APPUSER dash

# create application user
RUN useradd -o --uid 1000 ${APPUSER}

WORKDIR ${APPDIR}

RUN chown -R 1000:1000 ${APPDIR}

# Create deployments directory
RUN mkdir /home/deployments

RUN chown -R 1000:1000 /home/deployments

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

# set container user to non-root
USER ${APPUSER}

CMD ["/tmp/docker-dashboard-installation.sh"]
