(ns clj-sql-up.migration-files
  (:require [clojure.java.io :as io]
            ;;[clojure.java.jdbc :as sql]
            [clojure.string :as str]))

(defn- migration-hash [file]
  (if-let [arr (re-find #"([0-9]+)-.*\.clj$"
                        (.getName (io/file file)))]
    {(last arr) (first arr)}))

(defn get-migration-files []
  (map migration-hash (.listFiles (io/file "migrations"))))

;;  { "20130714150641624" "20130714150641624-create-posts.clj" }