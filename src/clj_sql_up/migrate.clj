(ns clj-sql-up.migrate
  (:require [clojure.java.jdbc :as sql]))

(defn migrate [db]
  (println (sql/query db ["SELECT NOW()"]))

  ;; (let [s (str "CREATE TABLE clj_sql_migrations(name varchar(20) NOT NULL); "
  ;;              "CREATE UNIQUE INDEX uniq_name ON clj_sql_migrations (name)")]
  ;;   (sql/execute! db-url [s]))
  )


