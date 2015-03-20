## Releases

### 0.3.6
* Fix parsing of rollback count

### 0.3.5
* Allow dynamic binding of \*default-migration-dir\* in create.clj code

### 0.3.4
* Use binding/dynamic variable for migrations directory (rebound in tests, etc)

### 0.3.3
* fix for issue with (Long.) function in migration rollback

### 0.3.2
* add pomegranate dependency

### 0.3.1
* jdbc 0.3.3 compatibility
* more testing
* make migration table creation more db agnostic

### 0.3.0
* Allow for dependency repository injection into migration files (includes clojars and maven central by default)

### 0.2.0
* Support for specifying how many migrations to rollback (eg: `lein clj-sql-up rollback 2`)
* Support for multiple environment `ENV=test lein clj-sql-up migrate`
* Added this changelog :)

### 0.1.0 (Initial)
* Basic create/migrate/rollback functionality
