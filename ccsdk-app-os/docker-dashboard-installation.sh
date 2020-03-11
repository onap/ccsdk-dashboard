#!/bin/bash
 #################################################################################
 # =============LICENSE_START=====================================================
 # 
 # ===============================================================================
 #  Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
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

# run import for ca certs
if [ -e /usr/local/share/ca-certificates/cacert.pem ]
then
    sudo mv /usr/local/share/ca-certificates/cacert.pem /usr/local/share/ca-certificates/cacert.crt
    sudo -- bash -c 'export JAVA_HOME=/usr/local/openjdk-8; /usr/sbin/update-ca-certificates'
fi

# Unzip the dashboard war file
unzip -qq -d /home/deployments/ccsdk-app /home/deployments/ccsdk-app*.war

# Delete the dashboard war file
rm -f /home/deployments/ccsdk-app*.war

# Update dashboard.properties
cat /home/deployments/ccsdk-app/WEB-INF/conf/dashboard.properties | \
sed "s/^dev.is_encrypted.*$/dev.is_encrypted=false/g" | \
sed "s~^dev.url.*$~dev.url = ${cfy_url}~g" | \
sed "s~^dev.inventory.url.*$~dev.inventory.url = ${inventory_url}~g" | \
sed "s~^dev.dhandler.url.*$~dev.dhandler.url = ${dhandler_url}~g" | \
sed "s~^dev.consul.url.*$~dev.consul.url = ${consul_url}~g" | \
sed "s/^dev.username.*$/dev.username = ${cloudify_user}/g" | \
sed "s/^dev.password.*$/dev.password = ${cloudify_password}/g" | \
sed "s/^controller.env.*$/controller.env = ${app_env}/g"  > /tmp/dash.prop
mv /tmp/dash.prop /home/deployments/ccsdk-app/WEB-INF/conf/dashboard.properties

# Update system.properties
cp /home/deployments/ccsdk-app/WEB-INF/conf/system.properties.template \
/home/deployments/ccsdk-app/WEB-INF/conf/system.properties
cat /home/deployments/ccsdk-app/WEB-INF/conf/system.properties | \
sed "s/^db.encrypt_flag.*$/db.encrypt_flag=false/g" | \
sed "s/postgresql:\/\/.*$/postgresql:\/\/${postgres_ip}:${postgres_port}\/${postgres_db_name}/g" | \
sed "s/^db.userName.*$/db.userName=${postgres_user_dashboard}/g" | \
sed "s/^db.password.*$/db.password=${postgres_password_dashboard}/g"  > /tmp/sys.prop
mv /tmp/sys.prop /home/deployments/ccsdk-app/WEB-INF/conf/system.properties

# Repackage the war file
cd /home/deployments/ccsdk-app && zip -rqq ../ccsdk-app.war * && cd -

# Move the war file to Tomcat webapps directory
mv /home/deployments/ccsdk-app.war $CATALINA_HOME/webapps
rm -Rf /home/deployments/ccsdk-app

# create the database tables
export PGPASSWORD=$postgres_password_dashboard
psql -h $postgres_ip -U $postgres_user_dashboard $postgres_db_name -f /tmp/create_table.sql
psql -h $postgres_ip -U $postgres_user_dashboard $postgres_db_name -c "update FN_APP set app_username='${aaf_app_user}' where app_id=1"

# Update tomcat server.xml to enable HTTPS protocol
if [[ -f /usr/local/share/ca-certificates/cert.jks && $CATALINA_HOME/conf/server.xml ]]
then
    echo "<Connector
    protocol=\"org.apache.coyote.http11.Http11NioProtocol\"
    port=\"8443\" maxThreads=\"200\"
    scheme=\"https\" secure=\"true\" SSLEnabled=\"true\"
    keystoreFile=\"/usr/local/share/ca-certificates/cert.jks\" keystorePass=\"`sed -e 's/&/\&amp;/g' -e 's/</\&lt;/g' -e 's/>/\&gt;/g' -e 's/"/\&quot;/g' -e "s/'/\&apos;/g" < /usr/local/share/ca-certificates/jks.pass`\"
    clientAuth=\"false\" sslProtocol=\"TLS\"/>" >> enablehttps.txt
    sed '/Service name=\"Catalina\">/r enablehttps.txt' $CATALINA_HOME/conf/server.xml > $CATALINA_HOME/conf/server-https.xml
    mv $CATALINA_HOME/conf/server-https.xml $CATALINA_HOME/conf/server.xml
fi

# Start the tomcat server
catalina.sh run
