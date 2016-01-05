--User
INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'READ', 'View User' FROM t_resource WHERE name='SYSTEM_USER';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'CREATE', 'Create User' FROM t_resource WHERE name='SYSTEM_USER';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'UPDATE', 'Update User' FROM t_resource WHERE name='SYSTEM_USER';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'DELETE', 'Delete User' FROM t_resource WHERE name='SYSTEM_USER';

--Role
INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'READ', 'View Role' FROM t_resource WHERE name='SYSTEM_ROLE';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'CREATE', 'Create Role' FROM t_resource WHERE name='SYSTEM_ROLE';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'UPDATE', 'Update Role' FROM t_resource WHERE name='SYSTEM_ROLE';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'DELETE', 'Delete Role' FROM t_resource WHERE name='SYSTEM_ROLE';


--User Group
INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'READ', 'View User Groups' FROM t_resource WHERE name='SYSTEM_USER_GROUP';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'CREATE', 'Create User Groups' FROM t_resource WHERE name='SYSTEM_USER_GROUP';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'UPDATE', 'Update User Groups' FROM t_resource WHERE name='SYSTEM_USER_GROUP';

INSERT INTO t_permission(resource_id,access_level,description)
SELECT id, 'DELETE', 'Delete User Groups' FROM t_resource WHERE name='SYSTEM_USER_GROUP';

