# Use an official Tomcat base image
FROM tomcat:8

ENV APPDIR /usr/local/tomcat
ENV APPUSER dash

COPY docker-dashboard-installation.sh /tmp/docker-dashboard-installation.sh
COPY create_table.sql /tmp/create_table.sql

RUN mkdir /home/deployments \
 && mkdir -p /opt/logs/dcae/dashboard \
 && useradd -u 1000 -M -U ${APPUSER} \
 && chown -R 1000:1000 ${APPDIR} \
 && chown -R 1000:1000 /home/deployments \
 && chown -R 1000:1000 /opt/logs/dcae/dashboard \
 && apt-get update \
 && apt-get install -y sudo zip vim dos2unix postgresql \
 && echo "dash ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/dash_update_cert_store \
 && dos2unix /tmp/create_table.sql \
 && dos2unix /tmp/docker-dashboard-installation.sh \
 && chmod +x /tmp/create_table.sql \
 && chmod +x /tmp/docker-dashboard-installation.sh

ARG WAR_FILE
COPY target/${WAR_FILE} /home/deployments/ccsdk-app.war

WORKDIR ${APPDIR}
USER ${APPUSER}

CMD ["/tmp/docker-dashboard-installation.sh"]
