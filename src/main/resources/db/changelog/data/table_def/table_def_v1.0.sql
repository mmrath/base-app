INSERT INTO PUBLIC.T_TABLE_DEF(ID, NAME, ALIAS, DISPLAY_LABEL, INSERTABLE, UPDATABLE, DELETABLE, MULTI_SELECTABLE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION) VALUES
(1, 'T_TABLE_DEF', 'table-def', 'Table Def', TRUE, TRUE, TRUE, TRUE, TIMESTAMP '2016-02-21 10:24:35.804', 'system', TIMESTAMP '2016-02-21 10:24:35.804', 'system', 0),
(2, 'T_ROLE', 'role', 'Role', TRUE, TRUE, TRUE, TRUE, TIMESTAMP '2016-02-21 10:24:52.159', 'system', TIMESTAMP '2016-02-21 10:24:52.159', 'system', 0),
(3, 'T_USER', 'user', 'User', TRUE, TRUE, TRUE, TRUE, TIMESTAMP '2016-02-21 10:26:26.858', 'system', TIMESTAMP '2016-02-21 10:26:26.858', 'system', 0),
(4, 'T_USER_GROUP', 'user-group', 'User Group', TRUE, TRUE, TRUE, TRUE, TIMESTAMP '2016-02-21 10:36:50.602', 'system', TIMESTAMP '2016-02-21 10:36:50.602', 'system', 0),
(5, 'T_PERMISSION', 'permission', 'Permission', FALSE, FALSE, FALSE, FALSE, TIMESTAMP '2016-02-21 10:37:36.116', 'system', TIMESTAMP '2016-02-21 10:37:36.116', 'system', 0);

INSERT INTO PUBLIC.T_COLUMN_DEF(ID, TABLE_ID, INDEX, COLUMN_NAME, NAME, DISPLAY_LABEL, DATA_TYPE, COLUMN_TYPE, NULLABLE, INSERTABLE, UPDATABLE, SEARCHABLE, SORTABLE, SHOW_IN_LIST, LENGTH, DATA_TEMPLATE, HEADER_TEMPLATE, VALID_PATTERN, VALID_PATTERN_MESSAGE, DEFAULT_VALUE) VALUES
(1, 1, 0, 'ID', 'id', 'Id', 'NUMBER', 'PRIMARY_KEY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 19, NULL, NULL, NULL, NULL, NULL),
(2, 1, 1, 'NAME', 'name', 'Name', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 64, NULL, NULL, NULL, NULL, NULL),
(3, 1, 2, 'ALIAS', 'alias', 'Alias', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 32, NULL, NULL, NULL, NULL, NULL),
(4, 1, 3, 'DISPLAY_LABEL', 'displayLabel', 'Display Label', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 64, NULL, NULL, NULL, NULL, NULL),
(5, 1, 4, 'INSERTABLE', 'insertable', 'Insertable', 'BOOLEAN', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 1, NULL, NULL, NULL, NULL, NULL),
(6, 1, 5, 'UPDATABLE', 'updatable', 'Updatable', 'BOOLEAN', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 1, NULL, NULL, NULL, NULL, NULL),
(7, 1, 6, 'DELETABLE', 'deletable', 'Deletable', 'BOOLEAN', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 1, NULL, NULL, NULL, NULL, NULL),
(8, 1, 7, 'MULTI_SELECTABLE', 'multiSelectable', 'Multi Selectable', 'BOOLEAN', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 1, NULL, NULL, NULL, NULL, NULL),
(9, 1, 8, 'CREATED_DATE', 'createdDate', 'Created Date', 'DATETIME', 'CREATED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(10, 1, 9, 'CREATED_BY', 'createdBy', 'Created By', 'STRING', 'CREATED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(11, 1, 10, 'LAST_MODIFIED_DATE', 'lastModifiedDate', 'Last Modified Date', 'DATETIME', 'LAST_MODIFIED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(12, 1, 11, 'LAST_MODIFIED_BY', 'lastModifiedBy', 'Last Modified By', 'STRING', 'LAST_MODIFIED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(13, 1, 12, 'VERSION', 'version', 'Version', 'NUMBER', 'VERSION', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 10, NULL, NULL, NULL, NULL, NULL),
(14, 2, 0, 'ID', 'id', 'Id', 'NUMBER', 'PRIMARY_KEY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 19, NULL, NULL, NULL, NULL, NULL),
(15, 2, 1, 'NAME', 'name', 'Name', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 30, NULL, NULL, NULL, NULL, NULL),
(16, 2, 2, 'DESCRIPTION', 'description', 'Description', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 255, NULL, NULL, NULL, NULL, NULL),
(17, 2, 3, 'CREATED_DATE', 'createdDate', 'Created Date', 'DATETIME', 'CREATED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(18, 2, 4, 'CREATED_BY', 'createdBy', 'Created By', 'STRING', 'CREATED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(19, 2, 5, 'LAST_MODIFIED_DATE', 'lastModifiedDate', 'Last Modified Date', 'DATETIME', 'LAST_MODIFIED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(20, 2, 6, 'LAST_MODIFIED_BY', 'lastModifiedBy', 'Last Modified By', 'STRING', 'LAST_MODIFIED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(21, 2, 7, 'VERSION', 'version', 'Version', 'NUMBER', 'VERSION', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 10, NULL, NULL, NULL, NULL, NULL),
(22, 3, 0, 'ID', 'id', 'Id', 'NUMBER', 'PRIMARY_KEY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 19, NULL, NULL, NULL, NULL, NULL),
(23, 3, 1, 'EMAIL', 'email', 'Email', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 64, NULL, NULL, NULL, NULL, NULL),
(24, 3, 2, 'ENABLED', 'enabled', 'Enabled', 'BOOLEAN', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 1, NULL, NULL, NULL, NULL, NULL),
(25, 3, 3, 'FIRST_NAME', 'firstName', 'First Name', 'STRING', 'REGULAR', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, 255, NULL, NULL, NULL, NULL, NULL),
(26, 3, 4, 'LAST_NAME', 'lastName', 'Last Name', 'STRING', 'REGULAR', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, 255, NULL, NULL, NULL, NULL, NULL);

INSERT INTO PUBLIC.T_COLUMN_DEF(ID, TABLE_ID, INDEX, COLUMN_NAME, NAME, DISPLAY_LABEL, DATA_TYPE, COLUMN_TYPE, NULLABLE, INSERTABLE, UPDATABLE, SEARCHABLE, SORTABLE, SHOW_IN_LIST, LENGTH, DATA_TEMPLATE, HEADER_TEMPLATE, VALID_PATTERN, VALID_PATTERN_MESSAGE, DEFAULT_VALUE) VALUES
(27, 3, 5, 'USERNAME', 'username', 'Username', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 30, NULL, NULL, NULL, NULL, NULL),
(28, 3, 6, 'CREATED_DATE', 'createdDate', 'Created Date', 'DATETIME', 'CREATED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(29, 3, 7, 'CREATED_BY', 'createdBy', 'Created By', 'STRING', 'CREATED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(30, 3, 8, 'LAST_MODIFIED_DATE', 'lastModifiedDate', 'Last Modified Date', 'DATETIME', 'LAST_MODIFIED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(31, 3, 9, 'LAST_MODIFIED_BY', 'lastModifiedBy', 'Last Modified By', 'STRING', 'LAST_MODIFIED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(32, 3, 10, 'VERSION', 'version', 'Version', 'NUMBER', 'VERSION', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 10, NULL, NULL, NULL, NULL, NULL),
(33, 4, 0, 'ID', 'id', 'Id', 'NUMBER', 'PRIMARY_KEY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 19, NULL, NULL, NULL, NULL, NULL),
(34, 4, 1, 'NAME', 'name', 'Name', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 64, NULL, NULL, NULL, NULL, NULL),
(35, 4, 2, 'DESCRIPTION', 'description', 'Description', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 64, NULL, NULL, NULL, NULL, NULL),
(36, 4, 3, 'CREATED_DATE', 'createdDate', 'Created Date', 'DATETIME', 'CREATED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(37, 4, 4, 'CREATED_BY', 'createdBy', 'Created By', 'STRING', 'CREATED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(38, 4, 5, 'LAST_MODIFIED_DATE', 'lastModifiedDate', 'Last Modified Date', 'DATETIME', 'LAST_MODIFIED_DATE', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 23, NULL, NULL, NULL, NULL, NULL),
(39, 4, 6, 'LAST_MODIFIED_BY', 'lastModifiedBy', 'Last Modified By', 'STRING', 'LAST_MODIFIED_BY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 64, NULL, NULL, NULL, NULL, NULL),
(40, 4, 7, 'VERSION', 'version', 'Version', 'NUMBER', 'VERSION', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 10, NULL, NULL, NULL, NULL, NULL),
(41, 5, 0, 'ID', 'id', 'Id', 'NUMBER', 'PRIMARY_KEY', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, 19, NULL, NULL, NULL, NULL, NULL),
(42, 5, 1, 'RESOURCE_ID', 'resourceId', 'Resource Id', 'NUMBER', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 19, NULL, NULL, NULL, NULL, NULL),
(43, 5, 2, 'ACCESS_LEVEL', 'accessLevel', 'Access Level', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 64, NULL, NULL, NULL, NULL, NULL),
(44, 5, 3, 'DESCRIPTION', 'description', 'Description', 'STRING', 'REGULAR', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, 255, NULL, NULL, NULL, NULL, NULL);
