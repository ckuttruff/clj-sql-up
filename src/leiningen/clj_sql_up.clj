(ns leiningen.clj-sql-up
  (:require [leiningen.create :as clj-c]))

(defn clj-sql-up
  "Handles delegation to various functions from leiningen sub-commands"
  [project command & args]
  (cond
   (= command "create") (clj-c/create args)))
