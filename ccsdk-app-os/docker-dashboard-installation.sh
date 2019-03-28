#!/bin/bash

# Unzip the dashboard war file
unzip -qq -d /home/deployments/ccsdk-app /home/deployments/ccsdk-app.war

# Delete the dashboard war file
rm -f /home/deployments/ccsdk-app.war

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

# Start the tomcat server
catalina.sh run
