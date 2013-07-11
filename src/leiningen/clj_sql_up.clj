(ns leiningen.clj-sql-up
  (:require [clj-sql-up.create  :as create]
            [clj-sql-up.migrate :as migrate]))

(defn clj-sql-up
  "Handles delegation to various functions from leiningen sub-commands"
  [project command & args]
  (cond
   (= command "create")  (create/create args)
   (= command "migrate") (migrate/migrate (-> project :clj-sql-up :database))))
