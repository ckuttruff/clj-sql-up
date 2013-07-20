(ns leiningen.clj-sql-up
  (:require [cemerick.pomegranate :as pome]
            [clj-sql-up.create  :as create]
            [clj-sql-up.migrate :as migrate]))

(defn clj-sql-up
  "Simply manage sql migrations with clojure/jdbc

Commands:
create name      Create migration (eg: migrations/20130712101745082-<name>.clj)
migrate          Run all pending migrations
rollback [n]     Rollback n migrations (defaults to 1)"
  [project command & args]

  (let [opts (:clj-sql-up project)]
    (pome/add-dependencies :coordinates (:deps opts))
    (cond
     (= command "create")   (create/create args)
     (= command "migrate")  (migrate/migrate  (:database opts))
     (= command "rollback") (migrate/rollback (:database opts)))))
