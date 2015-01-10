# clj-sql-up
A Leiningen plugin to manage SQL database migrations simply and dynamically with clojure/jdbc.

## Features
* Database agnostic (migrations are created from sequences of sql strings)
* Supports creation of stored procedures / other complicated sql statements
* Supports running migrations under multiple environments with "ENV" environment variable
* Runs within clojure so sql strings can easily be dynamically constructed
* Has a simple `create` command for generating migration files

## Installation
#### In your project.clj file:

Put clj-sql-up into your plugins vector:
```clojure
:plugins [[clj-sql-up "0.3.4"]]
```

Add database connection info (both your driver and jdbc connection string):

```clojure
:clj-sql-up {:database "jdbc:postgresql://foo@127.0.0.1:5432/foo"
             :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]}
;; OR
:clj-sql-up {:database {:subprotocol "mysql"
                        :subname "//127.0.0.1:3306/foo"
                        :user "foo"
                        :password ""}
             :deps [[mysql/mysql-connector-java "5.1.6"]]}
;; OR (for use with multiple environments or a custom repo dependency)
:clj-sql-up {:database-test "jdbc:postgresql://foo@127.0.0.1:5432/foo_test"
             :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]
	     ;; Note, this is included already in your migrations, but
	     ;;   is an example of adding additional dependency repos
	     :repos { "clojars" "http://clojars.org/repo" }}

```

## Usage
Basic usage (though it doesn't get much more complicated):

    $ lein clj-sql-up create create-posts

```clojure
;; migrations/20130714150641624-create-posts.clj

;; Note: the generated methods would just return a blank vector
;;       Just inserting some statements as an example
(defn up []
  ["CREATE TABLE foo(id int)"
   "CREATE INDEX foo_index ON foo(id)"])

(defn down []
  ["drop table foo"])
```
	$ lein clj-sql-up migrate
	Migrating: 20130714150634587-create-timestamps-fn.clj
	Migrating: 20130714150641624-create-posts.clj
	$ lein clj-sql-up rollback
	Reversing: 20130714150641624-create-posts.clj

### Multiple environments
To run migrations on a different database, ensure you define the connection in your project.clj file (see examples above)
and run:

	$ ENV=test lein clj-sql-up migrate
	$ ENV=test lein clj-sql-up rollback

And this will run the migration on the database specfied by :database-test in your config section

### Note for heroku users
Heroku uses leiningen 1.x by default which introduces some dependency conflicts for pomegranate (used by clj-sql-up).  See this issue for more detail: https://github.com/ckuttruff/clj-sql-up/issues/5

Please include the following within your project.clj to use leiningen 2.x in your app: `:min-lein-version "2.0.0"`

## TODO
* Add more tests
* Make certain aspects more generic (specifying migrations dir, etc.)

## Motivation
The motivation behind clj-sql-up is to create a migration library that is as simple as possible, but allows for migration statements to exist within the context of clojure (not as plain sql files).  It also allows for creation of more complicated sql commands (eg: stored procedures/functions) - something certain clojure migration libraries currently fail to do properly.

## Contributors
* **[@peterschwarz](https://github.com/peterschwarz)**
    * added jdbc 0.3.3 compatibility
    * created additional tests with hsqldb
    * ensured db-agnostic migration table creation
* **[@kitallis](https://github.com/kitallis)**
    * Fixed issue with use of java's Long casting
* **[@adomokos](https://github.com/adomokos)**
    * Updates to dynamic binding of migrations directory

## License
Copyright © 2013 Christopher Kuttruff

Distributed under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html), the same as Clojure.
