(ns clj-sql-up.migrate
  (:require [clojure.java.jdbc :as sql]))

(defn create-migrations-tbl [db]
  (sql/execute! db ["CREATE TABLE IF NOT EXISTS
                     clj_sql_migrations(name varchar(20) NOT NULL UNIQUE)"]))

(defn migrate [db]
  (create-migrations-tbl db)

  )
