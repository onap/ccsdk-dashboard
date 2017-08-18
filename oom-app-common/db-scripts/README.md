# ONAP Operations Manager Dashboard Database Scripts

This directory has a DDL to create tables and and DML scripts to populate tables
in Postgresql for the ONAP Operations Manager Dashboard web application.

## Internal deployments

To create a database for internal use:
- Run the common DDL script
- Run the common DML script
- Run the internal-use-only DML script, which can be found in a different project

## Open-source deployments

To create a database for ONAP use:
- Run the common DDL script
- Run the common DML script

## Notes on Postgresql:

Set default schema for user:
	ALTER USER <the-user-name> SET search_path to <the-schema-name>;
	(the above statement is not needed for version 9.4 and above)
