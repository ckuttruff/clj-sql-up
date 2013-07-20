(ns clj-sql-up.migrate
  (:require [clojure.set :as set]
            [clojure.java.jdbc :as sql]
            [clj-sql-up.migration-files :as files]))

(defn create-migrations-tbl [db]
  (sql/with-connection db
    (sql/do-commands "CREATE TABLE IF NOT EXISTS
                      clj_sql_migrations(name varchar(20) NOT NULL UNIQUE)")))

(defn completed-migrations [db]
  (sql/with-connection db
    (sql/with-query-results names
      ["SELECT name FROM clj_sql_migrations"]
      (map :name names))))

(defn pending-migrations [db]
  (sort (set/difference (set (files/get-migration-files))
                        (set (completed-migrations db)))))

(defn run-migrations [db migration-files direction]
  (doseq [file migration-files]
    (load-file (str "migrations/" file))
    (let [sql-arr ((resolve direction))]
      (sql/with-connection db
        (sql/transaction
         (sql/insert-rows :clj_sql_migrations [(files/migration-id file)])
         (doseq [s sql-arr]
           (sql/do-commands s)))))))

(defn migrate [db]
  (create-migrations-tbl db)
  (run-migrations db (pending-migrations db) 'up))

(defn rollback [db]
  (create-migrations-tbl db)
  (run-migrations db (completed-migrations db) 'down))
