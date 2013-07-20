(ns clj-sql-up.migrate
  (:require [clojure.set :as set]
            [clojure.java.jdbc :as sql]
            [clj-sql-up.migration-files :as files]))

(def migration-files
  (files/get-migration-files))

(defn create-migrations-tbl [db]
  (sql/with-connection db
    (sql/do-commands "CREATE TABLE IF NOT EXISTS
                      clj_sql_migrations(name varchar(20) NOT NULL UNIQUE)")))

(defn completed-migrations [db]
  (sql/with-connection db
    (sql/with-query-results names
      ["SELECT name FROM clj_sql_migrations ORDER BY name DESC"]
      (->> names
           (map #(files/migration-filename
                  (:name %) migration-files))
           vec))))

(defn pending-migrations [db]
  (sort (set/difference (set migration-files)
                        (set (completed-migrations db)))))

(defn run-migrations [db files direction]
  (doseq [file files]
    (load-file (str "migrations/" file))
    (let [sql-arr ((resolve direction))
          migr-id (files/migration-id file)]
      (sql/with-connection db
        (sql/transaction
         (if (= direction 'down)
           (do (println (str "Reversing: " file))
               (sql/delete-rows :clj_sql_migrations ["name=?" migr-id]))
           (do (println (str "Migrating: " file))
               (sql/insert-rows :clj_sql_migrations [migr-id])))
         (doseq [s sql-arr]
           (sql/do-commands s)))))))

(defn migrate [db]
  (create-migrations-tbl db)
  (run-migrations db (pending-migrations db) 'up))

(defn rollback [db]
  (create-migrations-tbl db)
  (run-migrations db (take 1 (completed-migrations db)) 'down))
