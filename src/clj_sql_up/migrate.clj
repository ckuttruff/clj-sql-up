(ns clj-sql-up.migrate
  (:require [clojure.java.jdbc :as sql]
            [cemerick.pomegranate :as pome]))

(defn load-deps
  [deps]
  (pome/add-dependencies
   :coordinates deps
   :repositories (merge cemerick.pomegranate.aether/maven-central
                        {"clojars" "http://clojars.org/repo"})))

(defn migrate [project db]
  (load-deps (-> project :clj-sql-up :deps))
  (sql/execute! db  ["CREATE TABLE IF NOT EXISTS
                      clj_sql_migrations(name varchar(20) NOT NULL UNIQUE)"]))
