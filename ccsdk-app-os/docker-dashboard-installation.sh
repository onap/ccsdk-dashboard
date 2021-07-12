#!/bin/sh
 #################################################################################
 # =============LICENSE_START=====================================================
 # 
 # ===============================================================================
 #  Copyright (c) 2020-2021 AT&T Intellectual Property. All rights reserved.
 # ===============================================================================
 #  Licensed under the Apache License, Version 2.0 (the "License");
 #  you may not use this file except in compliance with the License.
 #  You may obtain a copy of the License at
 #  
 #      http://www.apache.org/licenses/LICENSE-2.0
 #  
 #  Unless required by applicable law or agreed to in writing, software
 #  distributed under the License is distributed on an "AS IS" BASIS,
 #  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 #  See the License for the specific language governing permissions and
 #  limitations under the License.
 # ============LICENSE_END========================================================

# Unzip the dashboard war file
unzip -qq -d /tmp/ccsdk-app /tmp/ccsdk-app*.war

# Delete the dashboard war file
rm -f /tmp/ccsdk-app*.war

# Update dashboard.properties
cat /tmp/ccsdk-app/WEB-INF/conf/dashboard.properties | \
sed "s~^site.primary.cloudify.url.*$~site.primary.cloudify.url = ${cfy_url}~" | \
sed "s~^site.primary.consul.url.*$~site.primary.consul.url = ${consul_url}~" | \
sed "s~^site.primary.inventory.url.*$~site.primary.inventory.url = ${inventory_url}~" | \
sed "s~^site.primary.dhandler.url.*$~site.primary.dhandler.url = ${dhandler_url}~" | \
sed "s/^site.primary.cloudify.username.*$/site.primary.cloudify.username = ${cloudify_user}/" | \
sed "s~^site.primary.cloudify.password.*$~site.primary.cloudify.password = ${cloudify_password}~" | \
sed "s/^controller.env.*$/controller.env = ${app_env}/" > /tmp/dash.prop
mv /tmp/dash.prop /tmp/ccsdk-app/WEB-INF/conf/dashboard.properties

# Update system.properties
cp /tmp/ccsdk-app/WEB-INF/conf/system.properties.template \
/tmp/ccsdk-app/WEB-INF/conf/system.properties
cat /tmp/ccsdk-app/WEB-INF/conf/system.properties | \
sed "s/^db.encrypt_flag.*$/db.encrypt_flag=false/g" | \
sed "s/postgresql:\/\/.*$/postgresql:\/\/${postgres_ip}:${postgres_port}\/${postgres_db_name}/g" | \
sed "s/^db.userName.*$/db.userName=${postgres_user_dashboard}/g" | \
sed "s~^db.password.*$~db.password=${postgres_password_dashboard}~g"  > /tmp/sys.prop
mv /tmp/sys.prop /tmp/ccsdk-app/WEB-INF/conf/system.properties

# Repackage the war file
cd /tmp/ccsdk-app && zip -rqq ../ccsdk-app.war * && cd -

# Move the war file to Tomcat webapps directory
mv /tmp/ccsdk-app.war $CATALINA_HOME/webapps
rm -Rf /tmp/ccsdk-app

# create the database tables
export PGPASSWORD=$postgres_password_dashboard
psql -h $postgres_ip -U $postgres_user_dashboard $postgres_db_name -f /tmp/create_table.sql

# Update tomcat server.xml to enable HTTPS protocol
if [[ -f /opt/app/osaaf/cert.jks && $CATALINA_HOME/conf/server.xml ]]
then
    echo "<Connector
    protocol=\"org.apache.coyote.http11.Http11NioProtocol\"
    port=\"8443\" maxThreads=\"200\"
    scheme=\"https\" secure=\"true\" SSLEnabled=\"true\"
    keystoreFile=\"/opt/app/osaaf/cert.jks\" keystorePass=\"`sed -e 's/&/\&amp;/g' -e 's/</\&lt;/g' -e 's/>/\&gt;/g' -e 's/"/\&quot;/g' -e "s/'/\&apos;/g" < /opt/app/osaaf/jks.pass`\"
    clientAuth=\"false\" sslProtocol=\"TLS\"/>" >> enablehttps.txt
    sed '/Service name=\"Catalina\">/r enablehttps.txt' $CATALINA_HOME/conf/server.xml > $CATALINA_HOME/conf/server-https.xml
    mv $CATALINA_HOME/conf/server-https.xml $CATALINA_HOME/conf/server.xml
fi

echo 'CATALINA_OPTS="-Djavax.net.ssl.trustStore=/opt/app/osaaf/trust.jks --illegal-access=permit"' > $CATALINA_HOME/bin/setenv.sh

# Start the tomcat server
catalina.sh run
