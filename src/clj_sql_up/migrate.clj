(ns clj-sql-up.migrate
  (:require [clojure.set :as set]
            [clojure.java.jdbc :as sql]
            [clj-sql-up.migration-files :as files]))

(defn create-migrations-tbl [db]
  (sql/execute! db ["CREATE TABLE IF NOT EXISTS
                     clj_sql_migrations(name varchar(20) NOT NULL UNIQUE)"]))

(defn completed-migrations [db]
  (sql/query db ["SELECT name FROM clj_sql_migrations"]
             :row-fn :name))

(defn pending-migrations [db]
  (sort (set/difference (set (files/get-migration-files))
                        (set (completed-migrations db)))))

(defn run-migrations [db migration-files direction]
  (doseq [file migration-files]
    (load-file (str "migrations/" file))
    ;; TODO: actually implement code to run these migrations :)
    (let [sql-arr ((resolve direction))]
      (println sql-arr)
      ;;(sql/db-do-commands db sql-arr)
      ;; (sql/insert! db :clj_sql_migrations {:name  (files/migration-id file)})
      )))

(defn migrate [db]
  (create-migrations-tbl db)
  (run-migrations db (pending-migrations db) 'up))

(defn rollback [db]
  (create-migrations-tbl db)
  (run-migrations db (completed-migrations db) 'down))
