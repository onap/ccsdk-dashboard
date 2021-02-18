FROM nexus3.onap.org:10001/onap/integration-java11:8.0.0
#FROM tomcat:jdk11-adoptopenjdk-openj9

USER root
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

COPY docker-dashboard-installation.sh /tmp/docker-dashboard-installation.sh
COPY create_table.sql /tmp/create_table.sql
COPY target/ccsdk*.war /tmp/ccsdk-app.war

ARG user=onap
ARG group=onap

RUN apk add --update --no-cache --virtual .setup-deps wget zip dos2unix \
 && apk add postgresql \
 && wget -q http://archive.apache.org/dist/tomcat/tomcat-9/v9.0.37/bin/apache-tomcat-9.0.37.tar.gz \
 && tar -xzf apache-tomcat-9.0.37.tar.gz \
 && rm -f apache-tomcat-9.0.37.tar.gz \
 && rm -fr apache-tomcat-9.0.37/webapps/[a-z]* \
 && mkdir -p ${CATALINA_HOME} \
 && mv apache-tomcat-9.0.37/* ${CATALINA_HOME} \
 && mkdir -p /opt/logs/dcae/dashboard \
 && dos2unix /tmp/create_table.sql \
 && dos2unix /tmp/docker-dashboard-installation.sh \
 && chmod +x /tmp/create_table.sql \
 && chmod +x /tmp/docker-dashboard-installation.sh \
 && chown -R $user:$group ${CATALINA_HOME} \
 && chown -R $user:$group /tmp \
 && chown -R $user:$group /opt/logs/dcae/dashboard \
 && apk del .setup-deps

USER ${user}
WORKDIR $CATALINA_HOME

ENTRYPOINT /tmp/docker-dashboard-installation.sh
#CMD ["/tmp/docker-dashboard-installation.sh"]
