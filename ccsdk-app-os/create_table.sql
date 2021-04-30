-- ================================================================================
-- Copyright (c) 2019-2020 AT&T Intellectual Property. All rights reserved.
-- Copyright (c) 2021 Nokia Intellectual Property. All rights reserved.
-- ================================================================================
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- ============LICENSE_END=========================================================

CREATE SCHEMA IF NOT EXISTS dashboard_pg_db_common AUTHORIZATION dashboard_pg_admin;

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.service(
	id varchar(1024) primary key, 
	name varchar(1024),
        address varchar(80), 
	port int);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.healthcheck (
	id varchar(1024) references dashboard_pg_db_common.service (id),
        date timestamp without time zone  default (now() at time zone 'utc'),
        status varchar(80), 
	notes varchar(256), 
	output varchar(4096));

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.blueprints (
	id varchar(80) primary key not null, 
	name varchar(80), 
	blueprint bytea);

--- CREATE SCHEMA (schema name);

--- SET SEARCH_PATH = (schema name);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_timezone (
    timezone_id serial primary key,
    timezone_name character varying(100) not null,
    timezone_value character varying(100) not null
);

-- this sequence is named in Fusion.hbm.xml
CREATE SEQUENCE IF NOT EXISTS dashboard_pg_db_common.seq_fn_user;
CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_user (
    user_id integer NOT NULL DEFAULT nextval('dashboard_pg_db_common.seq_fn_user') primary key,
    org_id int,
    manager_id int,
    first_name character varying(25),
    middle_name character varying(25),
    last_name character varying(25),
    phone character varying(25),
    fax character varying(25),
    cellular character varying(25),
    email character varying(50),
    address_id int,
    alert_method_cd character varying(10),
    hrid character varying(20),
    org_user_id character varying(20),
    org_code character varying(30),
    login_id character varying(25),
    login_pwd character varying(25),
    last_login_date timestamp,
    active_yn character varying(1) default 'y' not null,
    created_id int,
    created_date timestamp default now(),
    modified_id int,
    modified_date timestamp default now(),
    is_internal_yn character(1) default 'n' not null,
    address_line_1 character varying(100),
    address_line_2 character varying(100),
    city character varying(50),
    state_cd character varying(3),
    zip_code character varying(11),
    country_cd character varying(3),
    location_clli character varying(8),
    org_manager_userid character varying(6),
    company character varying(100),
    department_name character varying(100),
    job_title character varying(100),
    timezone int,
    department character varying(25),
    business_unit character varying(25),
    business_unit_name character varying(100),
    cost_center character varying(25),
    fin_loc_code character varying(10),
    silo_status character varying(10)
);

ALTER SEQUENCE dashboard_pg_db_common.seq_fn_user OWNED BY dashboard_pg_db_common.fn_user.user_id;

-- this sequence is named in Fusion.hbm.xml
CREATE SEQUENCE IF NOT EXISTS dashboard_pg_db_common.seq_fn_role;
CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_role (
    role_id integer NOT NULL DEFAULT nextval('dashboard_pg_db_common.seq_fn_role') primary key,
    role_name character varying(50) not null,
    active_yn character varying(1) default 'y' not null,
    priority numeric(4,0)
);
ALTER SEQUENCE dashboard_pg_db_common.seq_fn_role OWNED BY dashboard_pg_db_common.fn_role.role_id;

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_audit_action (
    audit_action_id integer primary key,
    class_name character varying(500) not null,
    method_name character varying(50) not null,
    audit_action_cd character varying(20) not null,
    audit_action_desc character varying(200),
    active_yn character varying(1)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_audit_action_log (
    audit_log_id serial primary key,
    audit_action_cd character varying(200),
    action_time timestamp,
    user_id int,
    class_name character varying(100),
    method_name character varying(50),
    success_msg character varying(20),
    error_msg character varying(500)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_activity (
    activity_cd character varying(50) not null primary key,
    activity character varying(50) not null
);

-- this sequence is named in Fusion.hbm.xml
CREATE SEQUENCE IF NOT EXISTS dashboard_pg_db_common.seq_fn_audit_log;
CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_audit_log (
    log_id integer NOT NULL DEFAULT nextval('dashboard_pg_db_common.seq_fn_audit_log') primary key,
    user_id int not null,
    activity_cd character varying(50) not null,
    audit_date timestamp default now() not null,
    comments character varying(1000),
    affected_record_id_bk character varying(500),
    affected_record_id character varying(4000),
    constraint fk_fn_audit_ref_209_fn_user foreign key (user_id) references dashboard_pg_db_common.fn_user(user_id)
);
ALTER SEQUENCE dashboard_pg_db_common.seq_fn_audit_log OWNED BY dashboard_pg_db_common.fn_audit_log.log_id;

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_datasource (
    id serial primary key,
    name character varying(50),
    driver_name character varying(256),
    server character varying(256),
    port integer,
    user_name character varying(256),
    password character varying(256),
    url character varying(256),
    min_pool_size integer,
    max_pool_size integer,
    adapter_id integer,
    ds_type character varying(20)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_function (
    function_cd character varying(30) not null primary key,
    function_name character varying(50) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_alert_method (
    alert_method_cd character varying(10) not null,
    alert_method character varying(50) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_broadcast_site (
    broadcast_site_cd character varying(50) not null,
    broadcast_site_descr character varying(100)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_call_times (
    call_time_id int not null,
    call_time_amount int not null,
    call_time_display character varying(50) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_city (
    city_cd character varying(2) not null,
    city character varying(100) not null,
    state_cd character varying(2) not null,
    primary key (city_cd, state_cd)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_country (
    country_cd character varying(3) not null primary key,
    country character varying(100) not null,
    full_name character varying(100),
    webphone_country_label character varying(30)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_menu_set (
    menu_set_cd character varying(10) not null primary key,
    menu_set_name character varying(50) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_priority (
    priority_id int not null,
    priority character varying(50) not null,
    active_yn character(1) not null,
    sort_order numeric(5,0)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_role_type (
    role_type_id int not null,
    role_type character varying(50) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_state (
    state_cd character varying(2) not null,
    state character varying(100) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_tab_set (
    tab_set_cd character varying(30) not null,
    tab_set_name character varying(50) not null
);

-- this sequence is named in Fusion.hbm.xml
CREATE SEQUENCE IF NOT EXISTS dashboard_pg_db_common.seq_fn_menu;
CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_menu (
    menu_id integer NOT NULL DEFAULT nextval('dashboard_pg_db_common.seq_fn_menu') primary key,
    label character varying(100),
    parent_id int,
    sort_order numeric(4,0),
    action character varying(200),
    function_cd character varying(30),
    active_yn character varying(1) default 'y' not null,
    servlet character varying(50),
    query_string character varying(200),
    external_url character varying(200),
    target character varying(25),
    menu_set_cd character varying(10) default 'app',
    separator_yn character(1) default 'n',
    image_src character varying(100),
    constraint fk_fn_menu_ref_196_fn_menu foreign key (parent_id) references dashboard_pg_db_common.fn_menu(menu_id),
    constraint fk_fn_menu_menu_set_cd foreign key (menu_set_cd) references dashboard_pg_db_common.fn_lu_menu_set(menu_set_cd),
    constraint fk_fn_menu_ref_223_fn_funct foreign key (function_cd) references dashboard_pg_db_common.fn_function(function_cd)
);
ALTER SEQUENCE dashboard_pg_db_common.seq_fn_menu OWNED BY dashboard_pg_db_common.fn_menu.menu_id;

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_org (
    org_id int not null,
    org_name character varying(50) not null,
    access_cd character varying(10)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_restricted_url (
    restricted_url character varying(250) not null,
    function_cd character varying(30) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_role_composite (
    parent_role_id int not null,
    child_role_id int not null,
    constraint fk_fn_role_composite_child foreign key (child_role_id) references dashboard_pg_db_common.fn_role(role_id),
    constraint fk_fn_role_composite_parent foreign key (parent_role_id) references dashboard_pg_db_common.fn_role(role_id)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_role_function (
    role_id int not null,
    function_cd character varying(30) not null,
    constraint fk_fn_role__ref_198_fn_role foreign key (role_id) references dashboard_pg_db_common.fn_role(role_id)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_tab (
    tab_cd character varying(30) not null,
    tab_name character varying(50) not null,
    tab_descr character varying(100),
    action character varying(100) not null,
    function_cd character varying(30) not null,
    active_yn character(1) not null,
    sort_order int not null,
    parent_tab_cd character varying(30),
    tab_set_cd character varying(30)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_tab_selected (
    selected_tab_cd character varying(30) not null,
    tab_uri character varying(40) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_user_pseudo_role (
    pseudo_role_id int not null,
    user_id int not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_user_role (
    user_id int not null,
    role_id int not null,
    priority numeric(4,0),
    app_id int default 1,
    constraint fk_fn_user__ref_172_fn_user foreign key (user_id) references dashboard_pg_db_common.fn_user(user_id),
    constraint fk_fn_user__ref_175_fn_role foreign key (role_id) references dashboard_pg_db_common.fn_role(role_id)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_xmltype (
    id int not null,
    xml_document text
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.schema_info (
    schema_id character varying(25) not null,
    schema_desc character varying(75) not null,
    datasource_type character varying(100),
    connection_url varchar(200) not null,
    user_name varchar(45) not null,
    password varchar(45) null default null,
    driver_class varchar(100) not null,
    min_pool_size int not null,
    max_pool_size int not null,
    idle_connection_test_period int not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_app (
  app_id serial primary key,
  app_name varchar(100) not null default '?',
  app_image_url varchar(256) default null,
  app_description varchar(512) default null,
  app_notes varchar(4096) default null,
  app_url varchar(256) default null,
  app_alternate_url varchar(256) default null,
  app_rest_endpoint varchar(2000) default null,
  ml_app_name varchar(50) not null default '?',
  ml_app_admin_id varchar(7) not null default '?',
  mots_id int default null,
  app_password varchar(256) not null default '?',
  open char(1) default 'n',
  enabled char(1) default 'y',
  thumbnail bytea,
  app_username varchar(50),
  ueb_key varchar(256) default null,
  ueb_secret varchar(256) default null,
  ueb_topic_name varchar(256) default null  
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_workflow (
  id serial primary key,
  name varchar(20) NOT NULL unique,
  description varchar(500) DEFAULT NULL,
  run_link varchar(300) DEFAULT NULL,
  suspend_link varchar(300) DEFAULT NULL,
  modified_link varchar(300) DEFAULT NULL,
  active_yn varchar(300) DEFAULT NULL,
  created varchar(300) DEFAULT NULL,
  created_by int DEFAULT NULL,
  modified varchar(300) DEFAULT NULL,
  modified_by int DEFAULT NULL,
  workflow_key varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_schedule_workflows (
  id_schedule_workflows serial primary key,
  workflow_server_url varchar(45) default null,
  workflow_key varchar(45) not null,
  workflow_arguments varchar(45) default null,
  startdatetimecron varchar(45) default null,
  enddatetime timestamp default now(),
  start_date_time timestamp default now(),
  recurrence varchar(45) default null
  );
  
CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_license (
    id int not null,
    app_id int not null,
    ip_address character varying(100) not null,
    quantum_version_id int not null,
    created_date timestamp default now(),
    modified_date timestamp default now(),
    created_id int,
    modified_id int,
    end_date timestamp default '2036-01-19 03:14:07'
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_license_app (
    id int not null,
    app_name character varying(100) not null,
    ctxt_name character varying(100)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_license_contact (
    id int not null,
    license_id integer,
    sbcid character varying(20)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_license_history (
    id int not null,
    license_id int,
    app_id int,
    ip_address character varying(100),
    quantum_version_id int,
    created_date timestamp default now(),
    modified_date timestamp default now(),
    created_id int,
    modified_id int
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_license_version (
    id int not null,
    quantum_version character varying(25)
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.fn_lu_message_location (
    message_location_id int primary key,
    message_location_descr character varying(30) not null
);

CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.ecd_endpoint (
    user_id int not null primary key,
    name character varying(64),
    url character varying(512)
);

alter table dashboard_pg_db_common.ecd_endpoint
	add constraint fk_ecd_endpoint_ref_fn_user foreign key (user_id) references dashboard_pg_db_common.fn_user(user_id);

create view dashboard_pg_db_common.v_url_access as
 select distinct m.action as url,
    m.function_cd
   from dashboard_pg_db_common.fn_menu m
  where (m.action is not null)
union
 select distinct t.action as url,
    t.function_cd
   from dashboard_pg_db_common.fn_tab t
  where (t.action is not null)
union
 select r.restricted_url as url,
    r.function_cd
   from dashboard_pg_db_common.fn_restricted_url r;

alter table dashboard_pg_db_common.fn_audit_log
	add constraint fk_fn_audit_ref_205_fn_lu_ac foreign key (activity_cd) references dashboard_pg_db_common.fn_lu_activity(activity_cd);

alter table dashboard_pg_db_common.fn_role_function
	add constraint fk_fn_role__ref_201_fn_funct foreign key (function_cd) references dashboard_pg_db_common.fn_function(function_cd);

alter table dashboard_pg_db_common.fn_lu_alert_method
    add constraint fn_lu_alert_method_alert_method_cd primary key (alert_method_cd);

alter table dashboard_pg_db_common.fn_lu_broadcast_site
    add constraint fn_lu_broadcast_site_broadcast_site_cd primary key (broadcast_site_cd);

alter table dashboard_pg_db_common.fn_lu_call_times
    add constraint fn_lu_call_times_call_time_id primary key (call_time_id);

alter table dashboard_pg_db_common.fn_lu_priority
    add constraint fn_lu_priority_priority_id primary key (priority_id);

alter table dashboard_pg_db_common.fn_lu_role_type
    add constraint fn_lu_role_type_role_type_id primary key (role_type_id);

alter table dashboard_pg_db_common.fn_lu_state
    add constraint fn_lu_state_state_cd primary key (state_cd);

alter table dashboard_pg_db_common.fn_lu_tab_set
    add constraint fn_lu_tab_set_tab_set_cd primary key (tab_set_cd);

alter table dashboard_pg_db_common.fn_org
    add constraint fn_org_org_id primary key (org_id);

alter table dashboard_pg_db_common.fn_restricted_url
    add constraint fn_restricted_url_restricted_urlfunction_cd primary key (restricted_url, function_cd);

alter table dashboard_pg_db_common.fn_role_composite
    add constraint fn_role_composite_parent_role_idchild_role_id primary key (parent_role_id, child_role_id);

alter table dashboard_pg_db_common.fn_role_function
    add constraint fn_role_function_role_idfunction_cd primary key (role_id, function_cd);

alter table dashboard_pg_db_common.fn_tab
    add constraint fn_tab_tab_cd primary key (tab_cd);

alter table dashboard_pg_db_common.fn_tab_selected
    add constraint fn_tab_selected_selected_tab_cdtab_uri primary key (selected_tab_cd, tab_uri);

alter table dashboard_pg_db_common.fn_user_pseudo_role
    add constraint fn_user_pseudo_role_pseudo_role_iduser_id primary key (pseudo_role_id, user_id);

alter table dashboard_pg_db_common.fn_user_role
    add constraint fn_user_role_user_idrole_id primary key (user_id, role_id, app_id);

alter table dashboard_pg_db_common.fn_license
    add constraint fn_license_id primary key (id);

alter table dashboard_pg_db_common.fn_license_contact
    add constraint fn_license_contact_id primary key (id);

alter table dashboard_pg_db_common.fn_license_history
    add constraint fn_license_history_id primary key (id);

alter table dashboard_pg_db_common.fn_license_version
    add constraint fn_license_version_id primary key (id);

create index fn_audit_log_activity_cd  on dashboard_pg_db_common.fn_audit_log using btree(activity_cd);

create index fn_audit_log_user_id on dashboard_pg_db_common.fn_audit_log using btree(user_id);

create index fn_menu_function_cd on dashboard_pg_db_common.fn_menu using btree(function_cd);

create index fn_org_access_cd on dashboard_pg_db_common.fn_org using btree(access_cd);

create index fn_role_function_function_cd on dashboard_pg_db_common.fn_role_function using btree (function_cd);

create index fn_role_function_role_id on dashboard_pg_db_common.fn_role_function using btree(role_id);

create index fn_user_address_id on dashboard_pg_db_common.fn_user using btree(address_id); 

create index fn_user_alert_method_cd on dashboard_pg_db_common.fn_user using btree (alert_method_cd); 

create unique index fn_user_hrid on dashboard_pg_db_common.fn_user using btree (hrid); 

create unique index fn_user_login_id on dashboard_pg_db_common.fn_user using btree(login_id); 

create index fn_user_org_id on dashboard_pg_db_common.fn_user using btree(org_id); 

create index fn_user_role_role_id on dashboard_pg_db_common.fn_user_role using btree(role_id);

create index fn_user_role_user_id on dashboard_pg_db_common.fn_user_role using btree(user_id);

create unique index fn_xmltype_id on dashboard_pg_db_common.fn_xmltype using btree(id);

create index fk_fn_user__ref_178_fn_app_IDX on dashboard_pg_db_common.fn_user_role using btree(app_id);

create index fn_license_app_id on dashboard_pg_db_common.fn_license_app  using btree(id);

alter table dashboard_pg_db_common.fn_user_role
	add constraint fk_fn_user__ref_178_fn_app foreign key (app_id) references dashboard_pg_db_common.fn_app(app_id);

alter table dashboard_pg_db_common.fn_tab
    add constraint fk_fn_tab_function_cd foreign key (function_cd) references dashboard_pg_db_common.fn_function(function_cd);

alter table dashboard_pg_db_common.fn_tab_selected
    add constraint fk_fn_tab_selected_tab_cd foreign key (selected_tab_cd) references dashboard_pg_db_common.fn_tab(tab_cd);

alter table dashboard_pg_db_common.fn_tab
    add constraint fk_fn_tab_set_cd foreign key (tab_set_cd) references dashboard_pg_db_common.fn_lu_tab_set(tab_set_cd);

alter table dashboard_pg_db_common.fn_user
    add constraint fk_fn_user_ref_110_fn_org foreign key (org_id) references dashboard_pg_db_common.fn_org(org_id); 

alter table dashboard_pg_db_common.fn_user
    add constraint fk_fn_user_ref_123_fn_lu_al foreign key (alert_method_cd) references dashboard_pg_db_common.fn_lu_alert_method(alert_method_cd); 

alter table dashboard_pg_db_common.fn_user  
    add constraint fk_fn_user_ref_197_fn_user foreign key (manager_id) references dashboard_pg_db_common.fn_user(user_id); 

alter table dashboard_pg_db_common.fn_user  
    add constraint fk_fn_user_ref_198_fn_user foreign key (created_id) references dashboard_pg_db_common.fn_user(user_id); 

alter table dashboard_pg_db_common.fn_user  
    add constraint fk_fn_user_ref_199_fn_user foreign key (modified_id) references dashboard_pg_db_common.fn_user(user_id);    

alter table dashboard_pg_db_common.fn_user_pseudo_role 
    add constraint fk_pseudo_role_pseudo_role_id foreign key (pseudo_role_id) references dashboard_pg_db_common.fn_role(role_id);

alter table dashboard_pg_db_common.fn_user_pseudo_role 
    add constraint fk_pseudo_role_user_id foreign key (user_id) references dashboard_pg_db_common.fn_user(user_id);

alter table dashboard_pg_db_common.fn_restricted_url
    add constraint fk_restricted_url_function_cd foreign key (function_cd) references dashboard_pg_db_common.fn_function(function_cd);

alter table dashboard_pg_db_common.fn_license
    add constraint fn_license_r02 foreign key (quantum_version_id) references dashboard_pg_db_common.fn_license_version(id);


--- SET SEARCH_PATH = (schema name);

-- fn_lu_activity
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_role','add_role');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_role','remove_role');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_user_role','add_user_role');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_user_role','remove_user_role');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_role_function','add_role_function');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_role_function','remove_role_function');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('add_child_role','add_child_role');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('remove_child_role','remove_child_role');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('login','Login');
Insert into dashboard_pg_db_common.fn_lu_activity (ACTIVITY_CD,ACTIVITY) values ('logout','Logout');

-- fn_lu_alert_method
Insert into dashboard_pg_db_common.fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('PHONE','Phone');
Insert into dashboard_pg_db_common.fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('FAX','Fax');
Insert into dashboard_pg_db_common.fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('PAGER','Pager');
Insert into dashboard_pg_db_common.fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('EMAIL','Email');
Insert into dashboard_pg_db_common.fn_lu_alert_method (ALERT_METHOD_CD,ALERT_METHOD) values ('SMS','SMS');

-- fn_lu_country
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('YU','Yugoslavia','Yugoslavia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ZA','South Africa','South Africa',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ZM','Zambia','Zambia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ZR','Zaire','Zaire',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ZW','Zimbabwe','Zimbabwe',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AD','Andorra','Andorra',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AE','United Arab Emirates','United Arab Emirates',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AF','Afghanistan','Afghanistan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AG','Antigua and Barbuda','Antigua and Barbuda',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AI','Anguilla','Anguilla',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AL','Albania','Albania',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AM','Armenia','Armenia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AN','Netherlands Antilles','Netherlands Antilles',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AO','Angola','Angola',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AQ','Antarctica','Antarctica',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AR','Argentina','Argentina',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AS','American Samoa','American Samoa',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AT','Austria','Austria',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AU','Australia','Australia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AW','Aruba','Aruba',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('AZ','Azerbaidjan','Azerbaidjan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BA','Bosnia-Herzegovina','Bosnia-Herzegovina',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BB','Barbados','Barbados',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BD','Bangladesh','Bangladesh',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BE','Belgium','Belgium',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BF','Burkina Faso','Burkina Faso',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BG','Bulgaria','Bulgaria',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BH','Bahrain','Bahrain',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BI','Burundi','Burundi',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BJ','Benin','Benin',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BM','Bermuda','Bermuda',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BN','Brunei Darussalam','Brunei Darussalam',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BO','Bolivia','Bolivia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BR','Brazil','Brazil',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BS','Bahamas','Bahamas',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BT','Bhutan','Bhutan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BV','Bouvet Island','Bouvet Island',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BW','Botswana','Botswana',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BY','Belarus','Belarus',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('BZ','Belize','Belize',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CA','Canada','Canada',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CC','Cocos (Keeling) Islands','Cocos (Keeling) Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CF','Central African Republic','Central African Republic',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CG','Congo','Congo',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CH','Switzerland','Switzerland',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CI','Ivory Coast (Cote D''Ivoire)','Ivory Coast (Cote D''Ivoire)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CK','Cook Islands','Cook Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CL','Chile','Chile',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CM','Cameroon','Cameroon',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CN','China','China','China');
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CO','Colombia','Colombia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CR','Costa Rica','Costa Rica',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CS','Former Czechoslovakia','Former Czechoslovakia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CU','Cuba','Cuba',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CV','Cape Verde','Cape Verde',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CX','Christmas Island','Christmas Island',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CY','Cyprus','Cyprus',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('CZ','Czech Republic','Czech Republic',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('DE','Germany','Germany',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('DJ','Djibouti','Djibouti',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('DK','Denmark','Denmark',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('DM','Dominica','Dominica',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('DO','Dominican Republic','Dominican Republic',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('DZ','Algeria','Algeria',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('EC','Ecuador','Ecuador',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('EE','Estonia','Estonia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('EG','Egypt','Egypt',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('EH','Western Sahara','Western Sahara',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ER','Eritrea','Eritrea',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ES','Spain','Spain',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ET','Ethiopia','Ethiopia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('FI','Finland','Finland',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('FJ','Fiji','Fiji',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('FK','Falkland Islands','Falkland Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('FM','Micronesia','Micronesia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('FO','Faroe Islands','Faroe Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('FR','France','France',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('FX','France (European Territory)','France (European Territory)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GA','Gabon','Gabon',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GB','Great Britain','Great Britain',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GD','Grenada','Grenada',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GE','Georgia','Georgia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GF','French Guyana','French Guyana',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GH','Ghana','Ghana',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GI','Gibraltar','Gibraltar',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GL','Greenland','Greenland',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GM','Gambia','Gambia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GN','Guinea','Guinea',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GP','Guadeloupe (French)','Guadeloupe (French)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GQ','Equatorial Guinea','Equatorial Guinea',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GR','Greece','Greece',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GS','S. Georgia and S. Sandwich Isls.','S. Georgia and S. Sandwich Isls.',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GT','Guatemala','Guatemala',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GU','Guam (USA)','Guam (USA)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GW','Guinea Bissau','Guinea Bissau',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('GY','Guyana','Guyana',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('HK','Hong Kong','Hong Kong',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('HM','Heard and McDonald Islands','Heard and McDonald Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('HN','Honduras','Honduras',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('HR','Croatia','Croatia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('HT','Haiti','Haiti',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('HU','Hungary','Hungary',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ID','Indonesia','Indonesia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IE','Ireland','Ireland',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IL','Israel','Israel',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IN','India','India',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IO','British Indian Ocean Territory','British Indian Ocean Territory',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IQ','Iraq','Iraq',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IR','Iran','Iran',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IS','Iceland','Iceland',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('IT','Italy','Italy',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('JM','Jamaica','Jamaica',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('JO','Jordan','Jordan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('JP','Japan','Japan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KE','Kenya','Kenya',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KG','Kyrgyzstan','Kyrgyzstan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KH','Cambodia','Cambodia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KI','Kiribati','Kiribati',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KM','Comoros','Comoros',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KN','Saint Kitts and Nevis Anguilla','Saint Kitts and Nevis Anguilla',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KP','North Korea','North Korea',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KR','South Korea','South Korea',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KW','Kuwait','Kuwait',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KY','Cayman Islands','Cayman Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('KZ','Kazakhstan','Kazakhstan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LA','Laos','Laos',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LB','Lebanon','Lebanon',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LC','Saint Lucia','Saint Lucia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LI','Liechtenstein','Liechtenstein',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LK','Sri Lanka','Sri Lanka',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LR','Liberia','Liberia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LS','Lesotho','Lesotho',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LT','Lithuania','Lithuania',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LU','Luxembourg','Luxembourg',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LV','Latvia','Latvia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('LY','Libya','Libya',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MA','Morocco','Morocco',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MC','Monaco','Monaco',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MD','Moldavia','Moldavia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MG','Madagascar','Madagascar',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MH','Marshall Islands','Marshall Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MK','Macedonia','Macedonia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ML','Mali','Mali',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MM','Myanmar','Myanmar',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MN','Mongolia','Mongolia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MO','Macau','Macau',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MP','Northern Mariana Islands','Northern Mariana Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MQ','Martinique (French)','Martinique (French)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MR','Mauritania','Mauritania',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MS','Montserrat','Montserrat',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MT','Malta','Malta',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MU','Mauritius','Mauritius',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MV','Maldives','Maldives',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MW','Malawi','Malawi',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MX','Mexico','Mexico','Mexico');
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MY','Malaysia','Malaysia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('MZ','Mozambique','Mozambique',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NA','Namibia','Namibia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NC','New Caledonia (French)','New Caledonia (French)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NE','Niger','Niger',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NF','Norfolk Island','Norfolk Island',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NG','Nigeria','Nigeria',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NI','Nicaragua','Nicaragua',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NL','Netherlands','Netherlands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NO','Norway','Norway',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NP','Nepal','Nepal',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NR','Nauru','Nauru',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NU','Niue','Niue',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('NZ','New Zealand','New Zealand',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('OM','Oman','Oman',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PA','Panama','Panama',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PE','Peru','Peru',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PF','Polynesia (French)','Polynesia (French)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PG','Papua New Guinea','Papua New Guinea',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PH','Philippines','Philippines',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PK','Pakistan','Pakistan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PL','Poland','Poland',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PM','Saint Pierre and Miquelon','Saint Pierre and Miquelon',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PN','Pitcairn Island','Pitcairn Island',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PR','Puerto Rico','Puerto Rico',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PT','Portugal','Portugal',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PW','Palau','Palau',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('PY','Paraguay','Paraguay',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('QA','Qatar','Qatar',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('RE','Reunion (French)','Reunion (French)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('RO','Romania','Romania',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('RU','Russian Federation','Russian Federation',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('RW','Rwanda','Rwanda',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SA','Saudi Arabia','Saudi Arabia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SB','Solomon Islands','Solomon Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SC','Seychelles','Seychelles',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SD','Sudan','Sudan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SE','Sweden','Sweden',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SG','Singapore','Singapore',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SH','Saint Helena','Saint Helena',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SI','Slovenia','Slovenia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SJ','Svalbard and Jan Mayen Islands','Svalbard and Jan Mayen Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SK','Slovak Republic','Slovak Republic',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SL','Sierra Leone','Sierra Leone',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SM','San Marino','San Marino',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SN','Senegal','Senegal',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SO','Somalia','Somalia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SR','Suriname','Suriname',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('ST','Saint Tome (Sao Tome) and Principe','Saint Tome (Sao Tome) and Principe',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SU','Former USSR','Former USSR',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SV','El Salvador','El Salvador',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SY','Syria','Syria',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('SZ','Swaziland','Swaziland',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TC','Turks and Caicos Islands','Turks and Caicos Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TD','Chad','Chad',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TF','French Southern Territories','French Southern Territories',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TG','Togo','Togo',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TH','Thailand','Thailand',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TJ','Tadjikistan','Tadjikistan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TK','Tokelau','Tokelau',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TM','Turkmenistan','Turkmenistan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TN','Tunisia','Tunisia',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TO','Tonga','Tonga',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TP','East Timor','East Timor',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TR','Turkey','Turkey',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TT','Trinidad and Tobago','Trinidad and Tobago',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TV','Tuvalu','Tuvalu',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TW','Taiwan','Taiwan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('TZ','Tanzania','Tanzania',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('UA','Ukraine','Ukraine',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('UG','Uganda','Uganda',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('UK','United Kingdom','United Kingdom',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('UM','USA Minor Outlying Islands','USA Minor Outlying Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('US','United States','United States','USA');
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('UY','Uruguay','Uruguay',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('UZ','Uzbekistan','Uzbekistan',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('VA','Vatican City State','Vatican City State',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('VC','Saint Vincent and Grenadines','Saint Vincent and Grenadines',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('VE','Venezuela','Venezuela',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('VG','Virgin Islands (British)','Virgin Islands (British)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('VI','Virgin Islands (USA)','Virgin Islands (USA)',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('VN','Vietnam','Vietnam',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('VU','Vanuatu','Vanuatu',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('WF','Wallis and Futuna Islands','Wallis and Futuna Islands',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('WS','Samoa','Samoa',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('YE','Yemen','Yemen',null);
Insert into dashboard_pg_db_common.fn_lu_country (COUNTRY_CD,COUNTRY,FULL_NAME,WEBPHONE_COUNTRY_LABEL) values ('YT','Mayotte','Mayotte',null);

-- fn_lu_menu_set
Insert into dashboard_pg_db_common.fn_lu_menu_set (MENU_SET_CD,MENU_SET_NAME) values ('APP','Application Menu');

-- fn_lu_priority
Insert into dashboard_pg_db_common.fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (10,'Low','Y',10);
Insert into dashboard_pg_db_common.fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (20,'Normal','Y',20);
Insert into dashboard_pg_db_common.fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (30,'High','Y',30);
Insert into dashboard_pg_db_common.fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (40,'Urgent','Y',40);
Insert into dashboard_pg_db_common.fn_lu_priority (PRIORITY_ID,PRIORITY,ACTIVE_YN,SORT_ORDER) values (50,'Fatal','Y',50);

-- fn_lu_state
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('NJ','NJ - New Jersey');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('NY','NY - New York');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('CA','CA - California');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('CO','CO - Colorado');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('FL','FL - Florida');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('GA','GA - Georgia');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('VA','VA - Virginia');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('KY','KY - Kentucky');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('TX','TX - Texas');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('AK','AK - Alaska');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('AL','AL - Alabama');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('AR','AR - Arkansas');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('AZ','AZ - Arizona');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('CT','CT - Connecticut');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('DC','DC - District Of Columbia');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('DE','DE - Delaware');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('HI','HI - Hawaii');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('ID','ID - Idaho');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('IL','IL - Illinois');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('IN','IN - Indiana');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('IA','IA - Iowa');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('KS','KS - Kansas');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('LA','LA - Louisiana');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('MA','MA - Massachusetts');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('MD','MD - Maryland');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('ME','ME - Maine');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('MI','MI - Michigan');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('MN','MN - Minnesota');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('MO','MO - Missouri');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('MS','MS - Mississippi');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('MT','MT - Montana');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('NC','NC - North Carolina');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('ND','ND - North Dakota');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('NE','NE - Nebraska');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('NH','NH - New Hampshire');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('NM','NM - New Mexico');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('NV','NV - Nevada');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('OH','OH - Ohio');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('OK','OK - Oklahoma');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('OR','OR - Oregon');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('PA','PA - Pennsylvania');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('PR','PR - Puerto Rico');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('RI','RI - Rhode Island');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('SC','SC - South Carolina');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('SD','SD - South Dakota');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('TN','TN - Tennessee');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('UT','UT - Utah');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('VT','VT - Vermont');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('WA','WA - Washington');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('WV','WV - West Virginia');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('WI','WI - Wisconsin');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('WY','WY - Wyoming');
Insert into dashboard_pg_db_common.fn_lu_state (STATE_CD,STATE) values ('VI','VI-Virgin Island');

-- fn_lu_tab_set
Insert into dashboard_pg_db_common.fn_lu_tab_set (TAB_SET_CD,TAB_SET_NAME) values ('APP','Application Tabs');

-- fn_lu_timezone
Insert into dashboard_pg_db_common.fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (10,'US/Eastern','US/Eastern');
Insert into dashboard_pg_db_common.fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (20,'US/Central','US/Central');
Insert into dashboard_pg_db_common.fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (30,'US/Mountain','US/Mountain');
Insert into dashboard_pg_db_common.fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (40,'US/Arizona','America/Phoenix');
Insert into dashboard_pg_db_common.fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (50,'US/Pacific','US/Pacific');
Insert into dashboard_pg_db_common.fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (60,'US/Alaska','US/Alaska');
Insert into dashboard_pg_db_common.fn_lu_timezone (TIMEZONE_ID,TIMEZONE_NAME,TIMEZONE_VALUE) values (70,'US/Hawaii','US/Hawaii');

-- fn_function
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('login','Login');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_ecd','Home Menu');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_inventory','Inventory Menu');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_consul','Consul Menu');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_profile','Profile Menu');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_profile_create','Profile Create');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_profile_import','Profile Import');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_admin','Admin Menu');
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME) values ('menu_logout','Logout Menu');

-- fn_menu
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (1, 'Root', NULL, 10, 'ecd', 'menu_ecd', 'N', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);
INSERT INTO dashboard_pg_db_common.fn_menu 
	(MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (30001,'Home',                  1,     15,'ecd#',          'menu_ecd',           'Y','N/A','N/A','N/A','N/A','APP','N','icon-building-home');
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (30007,'System Health', 1,       45,'#',           'menu_consul',           'Y','N/A','N/A','N/A','N/A','APP','N','icon-datanetwork-softwareasaservice');
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (30008,'Service Health', 30007,  50,'ecd#/sh',     'menu_consul',           'Y','N/A','N/A','N/A','N/A','APP','N',NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (30009,'Node Health', 30007,     55,'ecd#/nh',     'menu_consul',           'Y','N/A','N/A','N/A','N/A','APP','N',NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC)
	VALUES (30010,'Data Centers', 30007,    60,'ecd#/dc',     'menu_consul',   'N','N/A','N/A','N/A','N/A','APP','N',NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC)
	VALUES (30011,'Inventory', 1,         25,'#',            'menu_inventory',           'Y','N/A','N/A','N/A','N/A','APP','N','icon-building-door');
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC)
	VALUES (30012,'Blueprints', 30011,     35,'ecd#/ibp',      'menu_inventory',           'Y','N/A','N/A','N/A','N/A','APP','N',NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC)
	VALUES (30013,'Deployments', 30011,     40,'ecd#/idep',    'menu_inventory',           'Y','N/A','N/A','N/A','N/A','APP','N',NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (9,   'Users', 				1,  90, '#', 					'menu_profile', 	'Y', NULL, NULL, NULL, NULL, 'APP', 'N', 'icon-people-oneperson');
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (930, 'Search', 				9,  15, 'ecd#/profile_search',  'menu_admin', 		'Y', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (94,  'Self', 				9,  40, 'ecd#/self_profile', 	'menu_profile', 	'Y', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (10,  'Admin', 				1, 110, '#', 					'menu_admin', 		'Y', NULL, NULL, NULL, NULL, 'APP', 'N', 'icon-controls-settingsconnectedactivity');
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (101, 'Roles', 			   10,  20, 'ecd#/role_list', 		'menu_admin', 		'Y', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (102, 'Role Functions', 	   10,  30, 'ecd#/role_function_list', 'menu_admin', 	'Y', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (105, 'Cache Admin', 	  	   10,  40, 'ecd#/jcs_admin', 		'menu_admin', 		'Y', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (108, 'Usage', 			   10,  80, 'ecd#/usage_list', 		'menu_admin', 		'Y', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (150022, 'Menus', 		   10,  60, 'ecd#/admin_menu_edit', 'menu_admin', 		'Y', NULL, NULL, NULL, NULL, 'APP', 'N', NULL);

-- fn_restricted_url
INSERT INTO dashboard_pg_db_common.fn_restricted_url (restricted_url, function_cd) VALUES ('role.htm','menu_admin');
INSERT INTO dashboard_pg_db_common.fn_restricted_url (restricted_url, function_cd) VALUES ('role_function.htm','menu_admin');
INSERT INTO dashboard_pg_db_common.fn_restricted_url (restricted_url, function_cd) VALUES ('profile.htm','menu_profile_create');

-- fn_role
Insert into dashboard_pg_db_common.fn_role (ROLE_ID,ROLE_NAME,ACTIVE_YN,PRIORITY) values (1,'System Administrator','Y',1);
Insert into dashboard_pg_db_common.fn_role (ROLE_ID,ROLE_NAME,ACTIVE_YN,PRIORITY) values (2,'Write Access','Y',2);
Insert into dashboard_pg_db_common.fn_role (ROLE_ID,ROLE_NAME,ACTIVE_YN,PRIORITY) values (3,'Read Access','Y',3);
Insert into dashboard_pg_db_common.fn_role (ROLE_ID,ROLE_NAME,ACTIVE_YN,PRIORITY) values (16,'Standard User','Y',5);

-- fn_role_composite
Insert into dashboard_pg_db_common.fn_role_composite (PARENT_ROLE_ID,CHILD_ROLE_ID) values (1,16);
Insert into dashboard_pg_db_common.fn_role_composite (PARENT_ROLE_ID,CHILD_ROLE_ID) values (1,2);
Insert into dashboard_pg_db_common.fn_role_composite (PARENT_ROLE_ID,CHILD_ROLE_ID) values (1,3);
Insert into dashboard_pg_db_common.fn_role_composite (PARENT_ROLE_ID,CHILD_ROLE_ID) values (2,3);

-- fn_role_function
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'login');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_ecd');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_inventory');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_consul');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_profile');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_admin');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_profile_create');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_profile_import');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_logout');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (2,'login');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (2,'menu_ecd');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (2,'menu_inventory');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (2,'menu_consul');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (2,'menu_profile');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (2,'menu_logout');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (3,'login');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (3,'menu_ecd');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (3,'menu_inventory');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (3,'menu_consul');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (3,'menu_profile');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (3,'menu_logout');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'login');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_ecd');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_inventory');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_consul');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_profile');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_logout');

-- fn_user
-- This row defines a superuser which is accepted by login_extern.htm
Insert into dashboard_pg_db_common.fn_user 
	(USER_ID,ORG_ID,MANAGER_ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,PHONE,FAX,CELLULAR,EMAIL,ADDRESS_ID,ALERT_METHOD_CD,HRID,ORG_USER_ID,ORG_CODE,LOGIN_ID,LOGIN_PWD,LAST_LOGIN_DATE,ACTIVE_YN,CREATED_ID,CREATED_DATE,MODIFIED_ID,MODIFIED_DATE,IS_INTERNAL_YN,ADDRESS_LINE_1,ADDRESS_LINE_2,CITY,STATE_CD,ZIP_CODE,COUNTRY_CD,LOCATION_CLLI,ORG_MANAGER_USERID,COMPANY,DEPARTMENT_NAME,JOB_TITLE,TIMEZONE,DEPARTMENT,BUSINESS_UNIT,BUSINESS_UNIT_NAME,COST_CENTER,FIN_LOC_CODE,SILO_STATUS) 
	values 
	(1,null,null,'Super',null,'User','908-901-2494',null,null,'email@om.ops.com',null,null,null,'su1234',null,'su1234','fusion',to_date('21-AUG-14','dd-MON-YY'),'Y',null,to_date('15-DEC-05','dd-MON-YY'),1,to_date('21-AUG-14','dd-MON-YY'),'N',null,null,null,'NJ',null,'US',null,null,null,null,null,10,null,null,null,null,null,null)
	;

-- fn_app
Insert into dashboard_pg_db_common.fn_app (APP_ID,APP_NAME,APP_IMAGE_URL,APP_DESCRIPTION,APP_NOTES,APP_URL,APP_ALTERNATE_URL,APP_REST_ENDPOINT,ML_APP_NAME,ML_APP_ADMIN_ID,MOTS_ID,APP_PASSWORD,OPEN,ENABLED,THUMBNAIL,APP_USERNAME,UEB_KEY,UEB_SECRET,UEB_TOPIC_NAME) VALUES (1,'EC-DASH-APP','assets/images/tmp/portal1.png','Some Default Description','Some Default Note','http://www.att.com','http://www.att.com',null,'ECPP','?','1','JuCerIRKt/faEcx8QdgncLEEv+IOZjpHe7Pi5DEPqKs=','N','Y',null,'Default',null,null,'ECOMP-PORTAL-INBOX');

-- fn_user_role
Insert into dashboard_pg_db_common.fn_user_role (USER_ID,ROLE_ID,PRIORITY,APP_ID) values (1,1,null,1);

ALTER ROLE dashboard_pg_admin SET search_path TO dashboard_pg_db_common;

-- ---------------------------------------------------------------------------------------------------------------
-- This script creates and populates component table 
-- ---------------------------------------------------------------------------------------------------------------
 
CREATE SEQUENCE IF NOT EXISTS dashboard_pg_db_common.seq_ecd_component;
CREATE TABLE IF NOT EXISTS dashboard_pg_db_common.ecd_component (
    ecd_component_id integer NOT NULL DEFAULT nextval('dashboard_pg_db_common.seq_ecd_component') primary key,
    ecd_component_name varchar(80),
    ecd_component_display varchar(80));
    
ALTER SEQUENCE dashboard_pg_db_common.seq_ecd_component OWNED BY dashboard_pg_db_common.ecd_component.ecd_component_id;

INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('controller', 'CONTROLLER');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('mso', 'MSO');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('appc', 'APP-C');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('clamp', 'CLAMP');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('scheduler', 'ECOMP SCHEDULER');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('policy', 'POLICY');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('vid', 'VID');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('conductor', 'CONDUCTOR');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('eipam', 'EIPAM');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('sdc', 'ASDC');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('sdncp', 'SDN-CP');    
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('sdngc', 'SDN-GC');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('sniro', 'SNIRO');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('valet', 'E-VALET');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('cpads-sdba', 'SDBA');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('pdasms', 'PDAS');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('aai', 'A&AI');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('dcae', 'DCAE');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('portal', 'PORTAL');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('sdngp', 'SDN-GP');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('sdna', 'SDN-A');
INSERT INTO dashboard_pg_db_common.ecd_component(
    ecd_component_name, ecd_component_display)
    VALUES ('music', 'MUSIC');
    
CREATE unique index ecd_component_nm on dashboard_pg_db_common.ecd_component using btree (ecd_component_name);

-- ---------------------------------------------------------------------------------------------------------------
-- set of changes to keep schema in sync with current portal SDK library
-- ---------------------------------------------------------------------------------------------------------------

INSERT INTO dashboard_pg_db_common.fn_restricted_url (restricted_url, function_cd) VALUES ('profile/removeRole','menu_profile_create');
INSERT INTO dashboard_pg_db_common.fn_restricted_url (restricted_url, function_cd) VALUES ('profile/addNewRole','menu_profile_create');

alter table dashboard_pg_db_common.fn_function
add type VARCHAR(20);

alter table dashboard_pg_db_common.fn_function
add action VARCHAR(20);

ALTER TABLE dashboard_pg_db_common.fn_function
ADD CONSTRAINT function UNIQUE (FUNCTION_CD,TYPE,ACTION);

update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_ecd';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_inventory';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_consul';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_profile';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_profile_create';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_profile_import';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_admin';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'menu_logout';
update dashboard_pg_db_common.fn_function set type = 'menu' , action = '*'  where function_cd = 'login';

-- REST API docs
Insert into dashboard_pg_db_common.fn_function (FUNCTION_CD,FUNCTION_NAME,TYPE,ACTION) values ('menu_api','API Menu','menu','*');

INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (20,'REST API', 1, 35,'#', 'menu_api', 'Y','N/A','N/A','N/A','N/A','APP','N','icon-arrows-upload');

INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (21,'Documentation', 20, 35,'ecd#/api', 'menu_api', 'Y','N/A','N/A','N/A','N/A','APP','N','');
	
INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC) 
	VALUES (22,'Swagger Spec', 20, 40,'ecd#/api-spec', 'menu_api', 'Y','N/A','N/A','N/A','N/A','APP','N','');
	
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (1,'menu_api');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (2,'menu_api');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (3,'menu_api');
Insert into dashboard_pg_db_common.fn_role_function (ROLE_ID,FUNCTION_CD) values (16,'menu_api');

INSERT INTO dashboard_pg_db_common.fn_menu (MENU_ID, LABEL, PARENT_ID, SORT_ORDER, ACTION, FUNCTION_CD, ACTIVE_YN, SERVLET, QUERY_STRING, EXTERNAL_URL, TARGET, MENU_SET_CD, SEPARATOR_YN, IMAGE_SRC)
	VALUES (30015,'Cloudify Plugins', 30011,     50,'ecd#/iplug',      'menu_inventory',           'Y','N/A','N/A','N/A','N/A','APP','N',NULL);
