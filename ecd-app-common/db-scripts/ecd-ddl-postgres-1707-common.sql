-- ---------------------------------------------------------------------------------------------------------------
-- This script creates tables for the ECOMP Controller Dashboard web app.
-- in the 1707 release, same tables for both internal and external use.
-- ------------------------------------------------------------------------------------------------------------------
CREATE SCHEMA (schema name);

SET SEARCH_PATH = (schema name);

create table fn_lu_timezone (
    timezone_id serial primary key,
    timezone_name character varying(100) not null,
    timezone_value character varying(100) not null
);

-- this sequence is named in Fusion.hbm.xml
create sequence seq_fn_user;
create table fn_user (
    user_id serial primary key,
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

-- this sequence is named in Fusion.hbm.xml
create sequence seq_fn_role;
create table fn_role (
    role_id serial primary key,
    role_name character varying(50) not null,
    active_yn character varying(1) default 'y' not null,
    priority numeric(4,0)
);

create table fn_audit_action (
    audit_action_id integer primary key,
    class_name character varying(500) not null,
    method_name character varying(50) not null,
    audit_action_cd character varying(20) not null,
    audit_action_desc character varying(200),
    active_yn character varying(1)
);

create table fn_audit_action_log (
    audit_log_id serial primary key,
    audit_action_cd character varying(200),
    action_time timestamp,
    user_id int,
    class_name character varying(100),
    method_name character varying(50),
    success_msg character varying(20),
    error_msg character varying(500)
);

create table fn_lu_activity (
    activity_cd character varying(50) not null primary key,
    activity character varying(50) not null
);

-- this sequence is named in Fusion.hbm.xml
create sequence seq_fn_audit_log;
create table fn_audit_log (
    log_id serial primary key,
    user_id int not null,
    activity_cd character varying(50) not null,
    audit_date timestamp default now() not null,
    comments character varying(1000),
    affected_record_id_bk character varying(500),
    affected_record_id character varying(4000),
    constraint fk_fn_audit_ref_209_fn_user foreign key (user_id) references fn_user(user_id)
);

create table fn_datasource (
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

create table fn_function (
    function_cd character varying(30) not null primary key,
    function_name character varying(50) not null
);

create table fn_lu_alert_method (
    alert_method_cd character varying(10) not null,
    alert_method character varying(50) not null
);

create table fn_lu_broadcast_site (
    broadcast_site_cd character varying(50) not null,
    broadcast_site_descr character varying(100)
);

create table fn_lu_call_times (
    call_time_id int not null,
    call_time_amount int not null,
    call_time_display character varying(50) not null
);

create table fn_lu_city (
    city_cd character varying(2) not null,
    city character varying(100) not null,
    state_cd character varying(2) not null,
    primary key (city_cd, state_cd)
);

create table fn_lu_country (
    country_cd character varying(3) not null primary key,
    country character varying(100) not null,
    full_name character varying(100),
    webphone_country_label character varying(30)
);

create table fn_lu_menu_set (
    menu_set_cd character varying(10) not null primary key,
    menu_set_name character varying(50) not null
);

create table fn_lu_priority (
    priority_id int not null,
    priority character varying(50) not null,
    active_yn character(1) not null,
    sort_order numeric(5,0)
);

create table fn_lu_role_type (
    role_type_id int not null,
    role_type character varying(50) not null
);

create table fn_lu_state (
    state_cd character varying(2) not null,
    state character varying(100) not null
);

create table fn_lu_tab_set (
    tab_set_cd character varying(30) not null,
    tab_set_name character varying(50) not null
);

-- this sequence is named in Fusion.hbm.xml
create sequence seq_fn_menu;
create table fn_menu (
    menu_id serial primary key,
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
    constraint fk_fn_menu_ref_196_fn_menu foreign key (parent_id) references fn_menu(menu_id),
    constraint fk_fn_menu_menu_set_cd foreign key (menu_set_cd) references fn_lu_menu_set(menu_set_cd),
    constraint fk_fn_menu_ref_223_fn_funct foreign key (function_cd) references fn_function(function_cd)
);

create table fn_org (
    org_id int not null,
    org_name character varying(50) not null,
    access_cd character varying(10)
);

create table fn_restricted_url (
    restricted_url character varying(250) not null,
    function_cd character varying(30) not null
);

create table fn_role_composite (
    parent_role_id int not null,
    child_role_id int not null,
    constraint fk_fn_role_composite_child foreign key (child_role_id) references fn_role(role_id),
    constraint fk_fn_role_composite_parent foreign key (parent_role_id) references fn_role(role_id)
);

create table fn_role_function (
    role_id int not null,
    function_cd character varying(30) not null,
    constraint fk_fn_role__ref_198_fn_role foreign key (role_id) references fn_role(role_id)
);

create table fn_tab (
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

create table fn_tab_selected (
    selected_tab_cd character varying(30) not null,
    tab_uri character varying(40) not null
);

create table fn_user_pseudo_role (
    pseudo_role_id int not null,
    user_id int not null
);

create table fn_user_role (
    user_id int not null,
    role_id int not null,
    priority numeric(4,0),
    app_id int default 1,
    constraint fk_fn_user__ref_172_fn_user foreign key (user_id) references fn_user(user_id),
    constraint fk_fn_user__ref_175_fn_role foreign key (role_id) references fn_role(role_id)
);

create table fn_xmltype (
    id int not null,
    xml_document text
);

create table schema_info (
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

create table fn_app (
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

create table fn_workflow (
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

create table fn_schedule_workflows (
  id_schedule_workflows serial primary key,
  workflow_server_url varchar(45) default null,
  workflow_key varchar(45) not null,
  workflow_arguments varchar(45) default null,
  startdatetimecron varchar(45) default null,
  enddatetime timestamp default now(),
  start_date_time timestamp default now(),
  recurrence varchar(45) default null
  );
  
create table fn_license (
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

create table fn_license_app (
    id int not null,
    app_name character varying(100) not null,
    ctxt_name character varying(100)
);

create table fn_license_contact (
    id int not null,
    license_id integer,
    sbcid character varying(20)
);

create table fn_license_history (
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

create table fn_license_version (
    id int not null,
    quantum_version character varying(25)
);

create table fn_lu_message_location (
    message_location_id int primary key,
    message_location_descr character varying(30) not null
);

create table ecd_endpoint (
    user_id int not null primary key,
    name character varying(64),
    url character varying(512)
);

alter table ecd_endpoint
	add constraint fk_ecd_endpoint_ref_fn_user foreign key (user_id) references fn_user(user_id);

create view v_url_access as
 select distinct m.action as url,
    m.function_cd
   from fn_menu m
  where (m.action is not null)
union
 select distinct t.action as url,
    t.function_cd
   from fn_tab t
  where (t.action is not null)
union
 select r.restricted_url as url,
    r.function_cd
   from fn_restricted_url r;

alter table fn_audit_log
	add constraint fk_fn_audit_ref_205_fn_lu_ac foreign key (activity_cd) references fn_lu_activity(activity_cd);

alter table fn_role_function
	add constraint fk_fn_role__ref_201_fn_funct foreign key (function_cd) references fn_function(function_cd);

alter table fn_lu_alert_method
    add constraint fn_lu_alert_method_alert_method_cd primary key (alert_method_cd);

alter table fn_lu_broadcast_site
    add constraint fn_lu_broadcast_site_broadcast_site_cd primary key (broadcast_site_cd);

alter table fn_lu_call_times
    add constraint fn_lu_call_times_call_time_id primary key (call_time_id);

alter table fn_lu_priority
    add constraint fn_lu_priority_priority_id primary key (priority_id);

alter table fn_lu_role_type
    add constraint fn_lu_role_type_role_type_id primary key (role_type_id);

alter table fn_lu_state
    add constraint fn_lu_state_state_cd primary key (state_cd);

alter table fn_lu_tab_set
    add constraint fn_lu_tab_set_tab_set_cd primary key (tab_set_cd);

alter table fn_org
    add constraint fn_org_org_id primary key (org_id);

alter table fn_restricted_url
    add constraint fn_restricted_url_restricted_urlfunction_cd primary key (restricted_url, function_cd);

alter table fn_role_composite
    add constraint fn_role_composite_parent_role_idchild_role_id primary key (parent_role_id, child_role_id);

alter table fn_role_function
    add constraint fn_role_function_role_idfunction_cd primary key (role_id, function_cd);

alter table fn_tab
    add constraint fn_tab_tab_cd primary key (tab_cd);

alter table fn_tab_selected
    add constraint fn_tab_selected_selected_tab_cdtab_uri primary key (selected_tab_cd, tab_uri);

alter table fn_user_pseudo_role
    add constraint fn_user_pseudo_role_pseudo_role_iduser_id primary key (pseudo_role_id, user_id);

alter table fn_user_role
    add constraint fn_user_role_user_idrole_id primary key (user_id, role_id, app_id);

alter table fn_license
    add constraint fn_license_id primary key (id);

alter table fn_license_contact
    add constraint fn_license_contact_id primary key (id);

alter table fn_license_history
    add constraint fn_license_history_id primary key (id);

alter table fn_license_version
    add constraint fn_license_version_id primary key (id);

create index fn_audit_log_activity_cd  on fn_audit_log using btree(activity_cd);

create index fn_audit_log_user_id on fn_audit_log using btree(user_id);

create index fn_menu_function_cd on fn_menu using btree(function_cd);

create index fn_org_access_cd on fn_org using btree(access_cd);

create index fn_role_function_function_cd on fn_role_function using btree (function_cd);

create index fn_role_function_role_id on fn_role_function using btree(role_id);

create index fn_user_address_id on fn_user using btree(address_id); 

create index fn_user_alert_method_cd on fn_user using btree (alert_method_cd); 

create unique index fn_user_hrid on fn_user using btree (hrid); 

create unique index fn_user_login_id on fn_user using btree(login_id); 

create index fn_user_org_id on fn_user using btree(org_id); 

create index fn_user_role_role_id on fn_user_role using btree(role_id);

create index fn_user_role_user_id on fn_user_role using btree(user_id);

create unique index fn_xmltype_id on fn_xmltype using btree(id);

create index fk_fn_user__ref_178_fn_app_IDX on fn_user_role using btree(app_id);

create index fn_license_app_id on fn_license_app  using btree(id);

alter table fn_user_role
	add constraint fk_fn_user__ref_178_fn_app foreign key (app_id) references fn_app(app_id);

alter table fn_tab
    add constraint fk_fn_tab_function_cd foreign key (function_cd) references fn_function(function_cd);

alter table fn_tab_selected
    add constraint fk_fn_tab_selected_tab_cd foreign key (selected_tab_cd) references fn_tab(tab_cd);

alter table fn_tab
    add constraint fk_fn_tab_set_cd foreign key (tab_set_cd) references fn_lu_tab_set(tab_set_cd);

alter table fn_user
    add constraint fk_fn_user_ref_110_fn_org foreign key (org_id) references fn_org(org_id); 

alter table fn_user
    add constraint fk_fn_user_ref_123_fn_lu_al foreign key (alert_method_cd) references fn_lu_alert_method(alert_method_cd); 

alter table fn_user  
    add constraint fk_fn_user_ref_197_fn_user foreign key (manager_id) references fn_user(user_id); 

alter table fn_user  
    add constraint fk_fn_user_ref_198_fn_user foreign key (created_id) references fn_user(user_id); 

alter table fn_user  
    add constraint fk_fn_user_ref_199_fn_user foreign key (modified_id) references fn_user(user_id);    

alter table fn_user_pseudo_role 
    add constraint fk_pseudo_role_pseudo_role_id foreign key (pseudo_role_id) references fn_role(role_id);

alter table fn_user_pseudo_role 
    add constraint fk_pseudo_role_user_id foreign key (user_id) references fn_user(user_id);

alter table fn_restricted_url
    add constraint fk_restricted_url_function_cd foreign key (function_cd) references fn_function(function_cd);

alter table fn_license
    add constraint fn_license_r02 foreign key (quantum_version_id) references fn_license_version(id);
