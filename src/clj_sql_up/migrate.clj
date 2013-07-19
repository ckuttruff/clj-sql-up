(ns clj-sql-up.migrate
  (:require [clojure.set :as set]
            [clojure.java.jdbc :as sql]
            [clj-sql-up.migration-files :as files]))

(defn create-migrations-tbl [db]
  (sql/execute! db ["CREATE TABLE IF NOT EXISTS
                     clj_sql_migrations(name varchar(20) NOT NULL UNIQUE)"]))

(defn processed-migrations [db]
  (sql/query db ["SELECT name FROM clj_sql_migrations"]
             :row-fn :name))

(defn pending-migrations [db]
  (sort-by #(first (keys %))
           (set/difference (set (files/get-migration-files))
                           (set (processed-migrations db)))))

(defn run-migrations [db migrations]
  
  )

(defn migrate [db]
  (create-migrations-tbl db)
  (run-migrations db (pending-migrations db)))
